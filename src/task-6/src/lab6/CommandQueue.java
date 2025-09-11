package lab6;

import java.util.Vector;
import lab5.Command;

/**
 * Реалізація черги команд з Worker Thread;
 * Створює обробник потока, що виконує об'єкти з інтерфейсом Command, шаблон Worker Thread.
 * @author Темний Юрій
 * @version 1.0
 */
public class CommandQueue implements Queue {
    /**
     *  Черга завдань 
     */
    private Vector<Command> tasks;
    /** Флаг очікування */
    private boolean waiting;
    /** Флаг завершення */
    private boolean shutdown;
    /**
     * Конструктор - створює чергу та запускає Worker Thread
     */
    public CommandQueue() {
        tasks = new Vector<Command>();
        waiting = false;
        shutdown = false;
        new Thread(new Worker()).start();} 
    /**
     * Зупинка Worker Thread
     */
    public void shutdown() {
        shutdown = true; }
    @Override
    public void put(Command cmd) {
        tasks.add(cmd);
        if (waiting) {
            synchronized (this) {
                notifyAll();
            }}}
    @Override
    public Command take() {
        if (tasks.isEmpty()) {
            synchronized (this) {
                waiting = true;
                try {
                    wait();
                } catch (InterruptedException ie) {
                    waiting = false;
                }}}
        return tasks.remove(0);
    }
    /**
     * Внутрішній клас Worker Thread
     */
    private class Worker implements Runnable {
        @Override
        public void run() {
            while (!shutdown) {
                Command cmd = take();
                cmd.execute();
            }
        }
    }
}

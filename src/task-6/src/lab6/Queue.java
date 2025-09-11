package lab6;

import lab5.Command;

/**
 * Інтерфейс черги для Worker Thread pattern;
 * Представляє методи для поміщення та вилучення задач обробником 
 * потока, шаблон Worker Thread.
 * @author Темний Юрій
 * @version 1.0
 */
public interface Queue {
    /**
     * Додає завдання до черги
     * @param cmd команда для виконання
     */
    void put(Command cmd);
    
    /**
     * Витягує завдання з черги
     * @return команда для виконання
     */
    Command take();
}
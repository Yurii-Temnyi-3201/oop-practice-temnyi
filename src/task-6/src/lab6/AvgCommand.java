package lab6;

import java.util.concurrent.TimeUnit;
import lab3.ViewResistanceResult;
import lab3.ResistanceResult;
import lab5.Command;

/**
 * Команда обчислення середнього опору;
 * Задача, використовувана обробником потока, шаблон Worker Thread.
 * @author Темний Юрій
 * @version 1.0
 */ 
public class AvgCommand implements Command {
    /** 
     * Результат - середнє значення опору */
    private double result = 0.0;
    /**
     *  Прогрес виконання 0-100% */
    private int progress = 0;
    /** 
     * Колекція результатів для обробки */
    private ViewResistanceResult viewResult;
    /**
     * Конструктор
     * @param viewResult колекція для обробки
     */
    public AvgCommand(ViewResistanceResult viewResult) {
        this.viewResult = viewResult;
    }
    /**
     * Отримати результат
     * @return середнє значення опору
     */
    public double getResult() {
        return result;
    }
    /**
     * Перевірка чи виконується команда
     * @return true якщо ще виконується
     */
    public boolean running() {
        return progress < 100;
    }@Override
    public void undo() {
        // Порожня реалізація
    }
    @Override
    public void execute() {
        progress = 0;
        System.out.println("Average resistance calculation started...");
        result = 0.0;
    int idx = 1;
    int size = viewResult.getItems().size();
    //AvgCommand - обчислення середнього
    for (ResistanceResult item : viewResult.getItems()) {
    	result += item.getTotalResistance();
        progress = idx * 100 / size;
    if (idx++ % (size / 2) == 0) {
    	System.out.println("Average progress: " + progress + "%");}
        try {TimeUnit.MILLISECONDS.sleep(2000 / size);
            } catch (InterruptedException e) {
             System.err.println(e);
            }
        }
        result /= size;
        System.out.println("Average done. Result = " + String.format("%.2f Ом", result));
        progress = 100;
    }
}
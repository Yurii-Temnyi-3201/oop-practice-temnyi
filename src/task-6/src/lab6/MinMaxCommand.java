package lab6;

import java.util.concurrent.TimeUnit;
import lab3.ViewResistanceResult;
import lab3.ResistanceResult;
import lab5.Command;

/**
 * Команда пошуку мінімального позитивного та максимального негативного опору;
 * Задача, використовувана обробником потока, шаблон Worker Thread.
 * @author Темний Юрій
 * @version 6.0
 */
public class MinMaxCommand implements Command {
    /** Результат - індекс мінімального позитивного опору */
    private int resultMin = -1;
    /** Результат - індекс максимального негативного опору */
    private int resultMax = -1;
    /** Прогрес виконання 0-100% */
    private int progress = 0;
    /** Колекція результатів для обробки */
    private ViewResistanceResult viewResult;
    
    /**
     * Конструктор
     * @param viewResult колекція для обробки
     */
    public MinMaxCommand(ViewResistanceResult viewResult) {
        this.viewResult = viewResult;
    }
    
    /**
     * Отримати мінімальний результат
     * @return індекс мінімального позитивного опору
     */
    public int getResultMin() {
        return resultMin;
    }
    
    /**
     * Отримати максимальний результат
     * @return індекс максимального негативного опору
     */
    public int getResultMax() {
        return resultMax;
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
        System.out.println("MinMax resistance search started...");
        
        int idx = 0;
        int size = viewResult.getItems().size();
        double threshold = 10.0; // Поріг для фільтрації
        
        for (ResistanceResult item : viewResult.getItems()) {
            double resistance = item.getTotalResistance();
            
            if (resistance < threshold) {
                // Шукаємо максимальний серед малих опорів
                if ((resultMax == -1) || 
                    (viewResult.getItems().get(resultMax).getTotalResistance() < resistance)) {
                    resultMax = idx;
                }
            } else {
                // Шукаємо мінімальний серед великих опорів
                if ((resultMin == -1) || 
                    (viewResult.getItems().get(resultMin).getTotalResistance() > resistance)) {
                    resultMin = idx;
                }
            }
            
            idx++;
            progress = idx * 100 / size;
            if (idx % (size / 5) == 0) {
                System.out.println("MinMax progress: " + progress + "%");
            }
            
            try {
                TimeUnit.MILLISECONDS.sleep(5000 / size);
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        }
        
        System.out.print("MinMax done. ");
        if (resultMin > -1) {
            System.out.print("Min high resistance #" + resultMin + " found: " + 
                String.format("%.2f Ом. ", viewResult.getItems().get(resultMin).getTotalResistance()));
        } else {
            System.out.print("Min high resistance not found. ");
        }
        
        if (resultMax > -1) {
            System.out.println("Max low resistance #" + resultMax + " found: " + 
                String.format("%.2f Ом.", viewResult.getItems().get(resultMax).getTotalResistance()));
        } else {
            System.out.println("Max low resistance not found.");
        }
        
        progress = 100;
    }
}
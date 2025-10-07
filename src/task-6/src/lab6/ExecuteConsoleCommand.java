package lab6;

import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import lab3.View;
import lab3.ViewResistanceResult;
import lab3.ResistanceResult;
import lab5.ConsoleCommand;

/**
 * Консольна команда Execute all threads, шаблон Command.
 * @author Темний Юрій
 * @version 1.0
 */
public class ExecuteConsoleCommand implements ConsoleCommand {
    /** Об'єкт для роботи з колекцією */
    private View view;
    
    /**
     * Конструктор
     * @param view об'єкт для роботи з колекцією
     */
    public ExecuteConsoleCommand(View view) {
        this.view = view;
    }

    @Override
    public void undo() {
        // Порожня реалізація
    }
    
    @Override
    public char getKey() {
        return 'e';
    }
    
    @Override
    public String toString() {
        return "'e'xecute";
    }
    
    @Override
    public void execute() {
        System.out.println("Execute all threads...");
        
        // Створюємо дві черги для паралельної обробки
        CommandQueue queue1 = new CommandQueue();
        CommandQueue queue2 = new CommandQueue();
        
        // Створюємо команди для обробки
        ViewResistanceResult viewResult = (ViewResistanceResult) view;
        MaxCommand maxCommand = new MaxCommand(viewResult);
        AvgCommand avgCommand = new AvgCommand(viewResult);
        MinCommand minCommand = new MinCommand(viewResult);
        FilterCommand filterCommand = new FilterCommand(viewResult);
        
        // Розподіляємо команди по чергах
        queue1.put(minCommand);
        queue1.put(filterCommand);
        queue2.put(maxCommand);
        queue2.put(avgCommand);
        
        try {
            // Очікуємо завершення всіх команд
            while (avgCommand.running() || 
                   maxCommand.running() || 
                   minCommand.running() ||
                   filterCommand.running()) {
                TimeUnit.MILLISECONDS.sleep(100);
            }
            
            // Зупиняємо черги
            queue1.shutdown();
            queue2.shutdown();
            
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            System.err.println(e);
        }
        
        System.out.println("All threads completed!");
    }
    
    /**
     * Внутрішній клас FilterCommand для фільтрації за критерієм
     */
    private static class FilterCommand implements lab5.Command {
        /** Результат - список індексів відфільтрованих елементів */
        private ArrayList<Integer> result = new ArrayList<>();
        /** Прогрес виконання 0-100% */
        private int progress = 0;
        /** Колекція результатів для обробки */
        private ViewResistanceResult viewResult;
        
        
        /**
         * Конструктор
         * @param viewResult колекція для обробки
         */
        public FilterCommand(ViewResistanceResult viewResult) {
            this.viewResult = viewResult;
        }
        /**
         * Перевірка чи виконується команда
         * @return true якщо ще виконується
         */
        public boolean running() {
            return progress < 100;
        }

        @Override
        public void undo() {
            // Порожня реалізація
        }
        /** Критерій фільтрації */
        private double threshold = 10.0;
        @Override
        public void execute() {
        progress = 0;
        System.out.println("Filter resistance search started...");
        result.clear();
        int idx = 0;
        int size = viewResult.getItems().size();
        for (ResistanceResult item : viewResult.getItems()) {
           if (item.getTotalResistance() > threshold) {//перевірка за критерієм
           result.add(idx);}
                idx++;
                progress = idx * 100 / size;
                if (idx % (size/3) == 0) //відображення прогресу
                {System.out.println("Filter progress: " + progress + "%");}
                try {TimeUnit.MILLISECONDS.sleep(3000 / size);} 
                catch (InterruptedException e) 
                {System.err.println(e);}
         } 
         System.out.print("Filter done. Found " + result.size() + 
                " items > " + String.format("%.1f", threshold) + " Ом: [");
         for (int i=0; i < result.size(); i++) {
        	 if (i>0) System.out.print("; ");
                System.out.print(String.format("%.2f", 
                viewResult.getItems().get(result.get(i)).getTotalResistance()));}
            System.out.println("]");
            progress = 100;
        }
    }
}
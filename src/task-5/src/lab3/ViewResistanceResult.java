package lab3;

import java.io.*;
import java.util.*;

/**
 * ConcreteProduct (шаблон проектування Factory Method). Виконання функцій, зохранення та відображення елементів.
 * @author Темний Ю.
 * @version 1.0
 */
public class ViewResistanceResult implements View {
    
    /**
     * Ім'я файлу, що використовується при серіалізації
     */
    private static final String FNAME = "resistance_data.ser";
    
    /**
     * Визначає кількість значень для обчислення за замовчуванням
     */
    private static final int DEFAULT_NUM = 5;
    
    /**
     * Колекція результатів обчислень опорів
     */
    private ArrayList<ResistanceResult> items = new ArrayList<ResistanceResult>();
    
    /**
     * Викликає ViewResistanceResult(int n) з параметром DEFAULT_NUM
     */
    public ViewResistanceResult() {
        this(DEFAULT_NUM);
    }
    
    /**
     * Ініціалізує колекцію items
     * @param n початкова кількість елементів
     */
    public ViewResistanceResult(int n) {
        for(int ctr = 0; ctr < n; ctr++) {
            items.add(new ResistanceResult());
        }
    }
    
    /**
     * Отримати значення items
     * @return поточне значення посилання на об'єкт ArrayList
     */
    public ArrayList<ResistanceResult> getItems() {
        return items;
    }
    
    /**
     * Обчислює значення опорів та зберігає результат в колекції items
     * @param stepCurrent крок приросту струму
     */
    public void init(double stepCurrent) {
        double current = 1.0;
        for(ResistanceResult item : items) {
            if (current <= 0.0) {
                current = 0.1;
            }
            
            double voltage1 = 5.0 + Math.random() * 10.0; // 5-15 В
            double voltage2 = 6.0 + Math.random() * 10.0; // 6-16 В  
            double voltage3 = 4.0 + Math.random() * 12.0; // 4-16 В
            item.setValues(current, voltage1, voltage2, voltage3);
            current += stepCurrent;
        }
    }
    
    /**
     * Викликає init(double stepCurrent) з випадковим значенням аргументу
     */
    @Override
    public void viewInit() {
        init(Math.random() * 2.0 + 0.5); // 0.5-2.5 А крок
    }
    
    /**
     * Реалізація методу View.viewSave()
     */
    @Override
    public void viewSave() throws IOException {
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(FNAME));
        os.writeObject(items);
        os.flush();
        os.close();
    }
    
    /**
     * Реалізація методу View.viewRestore()
     */
    @SuppressWarnings("unchecked")
    @Override
    public void viewRestore() throws Exception {
        ObjectInputStream is = new ObjectInputStream(new FileInputStream(FNAME));
        items = (ArrayList<ResistanceResult>) is.readObject();
        is.close();
    }
    
    /**
     * Реалізація методу View.viewHeader()
     */
    @Override
    public void viewHeader() {
        System.out.println("=== Результати обчислення опорів провідників ===");
        System.out.println("  Струм  | Напруга1 | Напруга2 | Напруга3 |  Загальний опір | 8-кова  |  16-кова ");
        System.out.println("---------|----------|----------|----------|-----------------|---------|----------");
    
    }
    
    @Override
    public void viewBody() {
        for(ResistanceResult item : items) {
            System.out.printf(" %6.2fА | %7.2fВ | %7.2fВ | %7.2fВ |%13.2f Ом | %7s |%8s%n", 
                item.getCurrent(), item.getVoltage1(), item.getVoltage2(), item.getVoltage3(), 
                item.getTotalResistance(), item.getOctalRepresentation(), item.getHexRepresentation());
        }
    }
    
    /**
     * Реалізація методу View.viewFooter()
     */
    @Override
    public void viewFooter() {
        System.out.println("=== Кінець ===");
    }
    
    /**
     * Реалізація методу View.viewShow()
     */
    @Override
    public void viewShow() {
        viewHeader();
        viewBody();
        viewFooter();
    }
}
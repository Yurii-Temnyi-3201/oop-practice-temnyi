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
    }/**
     * Ініціалізує колекцію items
     * @param n початкова кількість елементів
     */
    public ViewResistanceResult(int n) {
        for(int ctr = 0; ctr < n; ctr++) {
            items.add(new ResistanceResult());
        }
    }/**
     * Отримати значення items
     * @return поточне значення посилання на об'єкт ArrayList
     */
    public ArrayList<ResistanceResult> getItems() {
        return items;
    }/**
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
    }/**
     * Викликає init(double stepCurrent) з випадковим значенням аргументу
     */
    @Override
    public void viewInit() {
        init(Math.random() * 2.0 + 0.5); // 0.5-2.5 А крок
    }/**
     * Реалізація методу View.viewSave()
     */
    @Override
    public void viewSave() throws IOException {
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(FNAME));
        os.writeObject(items);
        os.flush();
        os.close();
    }/**
     * Реалізація методу View.viewRestore()
     */
    @SuppressWarnings("unchecked")
    @Override
    public void viewRestore() throws Exception {
        ObjectInputStream is = new ObjectInputStream(new FileInputStream(FNAME));
        items = (ArrayList<ResistanceResult>) is.readObject();
        is.close();
    }
    
    @Override
    public void viewHeader() {
        System.out.println("=== Результати обчислення опорів провідників ===");
    }
    @Override
    public void viewBody() {
        for(int i = 0; i < items.size(); i++) {
            ResistanceResult item = items.get(i);
            System.out.printf("Результат %d: I=%.2f А, U1=%.2f В, U2=%.2f В, U3=%.2f В, " +
                             "R1=%.2f Ом, R2=%.2f Ом, R3=%.2f Ом, Rзаг=%.2f Ом, 8-кова=%s, 16-кова=%s%n",
                             (i + 1), item.getCurrent(), item.getVoltage1(), item.getVoltage2(), 
                             item.getVoltage3(), item.getR1(), item.getR2(), item.getR3(),
                             item.getTotalResistance(), item.getOctalRepresentation(), 
                             item.getHexRepresentation());
        } }
    @Override
    public void viewFooter() {
        System.out.println("=== Кінець ===");
    }
    @Override
    public void viewShow() {
        viewHeader();
        viewBody();
        viewFooter();
    }
}
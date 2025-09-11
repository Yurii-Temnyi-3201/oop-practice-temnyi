package lab4;

import lab3.ResistanceResult;
import lab3.View;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ConcreteProduct (шаблон проектування Factory Method).Вивід у вигляді таблиці.<br>
 * Реалізує інтерфейс {@link View} для форматованого відображення, збереження та відновлення даних.
 * @author Темний Ю.
 * @version 1.0
 */
public class ViewTable implements View, Serializable {
    private static final long serialVersionUID = 1L;
    private static final int DEFAULT_WIDTH = 12;
    private static final int DEFAULT_PRECISION = 2;
    private static final int DEFAULT_SIZE = 5;
    private static final String FNAME = "resistance_data.ser";

    /** Поточна ширина стовпців таблиці */
    private int width;
    /** Поточна точність відображення чисел */
    private int precision;
    /** Список результатів розрахунків */
    private ArrayList<ResistanceResult> items;

    /**
     * Конструктор за замовчуванням. Створює таблицю з параметрами за замовчуванням.
     */
    public ViewTable() {
        this(DEFAULT_SIZE, DEFAULT_WIDTH, DEFAULT_PRECISION);
    }

    /**
     * Конструктор з параметрами.
     * @param size кількість рядків
     * @param width ширина стовпців
     * @param precision точність чисел
     */
    public ViewTable(int size, int width, int precision) {
        this.width = width;
        this.precision = precision;
        this.items = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            items.add(new ResistanceResult());
        }
    }

    /**
     * Встановлює ширину стовпців таблиці.
     * @param width нова ширина стовпців
     */
    public void setFormat(int width) {
        this.width = Math.max(width, 6);
    }

    /**
     * Встановлює ширину стовпців та точність чисел.
     * @param width нова ширина стовпців
     * @param precision кількість знаків після коми
     */
    public void setFormat(int width, int precision) {
        this.width = Math.max(width, 6);
        this.precision = Math.max(precision, 0);
    }

    /**
     * Змінює кількість рядків у таблиці.
     * @param newSize нова кількість рядків
     */
    public void setSize(int newSize) {
        if (newSize < 1) newSize = 1;
        while (items.size() < newSize) {
            items.add(new ResistanceResult());
        }
        while (items.size() > newSize) {
            items.remove(items.size() - 1);
        }
    }

    /**
     * Повертає поточну ширину стовпців.
     * @return ширина стовпців
     */
    public int getWidth() { return width; }

    /**
     * Повертає поточну точність чисел.
     * @return точність
     */
    public int getPrecision() { return precision; }

    /**
     * Повертає список результатів.
     * @return список {@link ResistanceResult}
     */
    public List<ResistanceResult> getItems() { return items; }

    /**
     * Ініціалізує дані випадковими значеннями.
     * Перевизначає метод {@link View#viewInit()}.
     */
    @Override
    public void viewInit() {
        double current = 1.0;
        for (ResistanceResult item : items) {
            double voltage1 = 5.0 + Math.random() * 10.0;
            double voltage2 = 6.0 + Math.random() * 10.0;
            double voltage3 = 4.0 + Math.random() * 12.0;
            item.setValues(current, voltage1, voltage2, voltage3);
            current += Math.random() * 2.0 + 0.5;
        }
    }

    /**
     * Зберігає дані у файл.
     * @throws IOException якщо виникла помилка запису
     */
    @Override
    public void viewSave() throws IOException {
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(FNAME));
        os.writeObject(items);
        os.flush();
        os.close();
    }

    /**
     * Відновлює дані з файлу.
     * @throws Exception якщо виникла помилка читання або десеріалізації
     */
    @SuppressWarnings("unchecked")
    @Override
    public void viewRestore() throws Exception {
        ObjectInputStream is = new ObjectInputStream(new FileInputStream(FNAME));
        items = (ArrayList<ResistanceResult>) is.readObject();
        is.close();
    }

    /**
     * Виводить заголовок таблиці.
     */
    @Override
    public void viewHeader() {
        String format = "%-" + width + "s";
        System.out.printf(format + "|" + format + "|" + format + "|" + format + "|" + format + "|" + format + "|" + format + "\n",
                "I, A", "U1, V", "U2, V", "U3, V", "R, Ом", "8-кова", "16-кова");
        printLine();
    }

    /**
     * Виводить тіло таблиці.
     */
    @Override
    public void viewBody() {
        String format = "%-" + width + "." + precision + "f";
        for (ResistanceResult item : items) {
            System.out.printf(format + "|" + format + "|" + format + "|" + format + "|" + format + "|%-" + width + "s|%-" + width + "s\n",
                    item.getCurrent(),
                    item.getVoltage1(),
                    item.getVoltage2(),
                    item.getVoltage3(),
                    item.getTotalResistance(),
                    item.getOctalRepresentation(),
                    item.getHexRepresentation());
        }
    }

    /**
     * Виводить футер таблиці.
     */
    @Override
    public void viewFooter() {
        printLine();
        System.out.println("=== Кінець таблиці ===");
    }

    /**
     * Виводить повну таблицю (заголовок, тіло, футер).
     */
    @Override
    public void viewShow() {
        viewHeader();
        viewBody();
        viewFooter();
    }

    /**
     * Виводить роздільну лінію для таблиці.
     */
    private void printLine() {
        for (int i = 0; i < width * 7 + 6; i++) System.out.print("-");
        System.out.println();
    }
}
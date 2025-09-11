package lab4;

import lab3.View;
import lab3.Viewable;

/**
 * ConcreteCreator (шаблон проектування Factory Method)<br>
 * Створює об'єкти {@link ViewTable} для табличного виводу даних.
 * @author Темний Ю.
 * @version 1.0
 */
public class ViewableTable implements Viewable {
 /**
     * Конструктор за замовчуванням.
     */
    public ViewableTable() {
        // Конструктор без параметрів
    }/**
     * Створює та повертає новий екземпляр {@link ViewTable}.
     * @return новий об'єкт ViewTable для табличного відображення результатів
     */
    @Override
    public View getView() {
        return new ViewTable();
    }
}
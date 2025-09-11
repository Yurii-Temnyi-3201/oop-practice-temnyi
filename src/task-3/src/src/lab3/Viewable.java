package lab3;

/**
 * Creator (шаблон проектування Factory Method). Оголошує метод "фабрикуючий" об'єкти.
 * @author Темний Ю.
 * @version 1.0
 */
public interface Viewable {
    
    /**
     * Factory Method - створює конкретний View об'єкт
     * @return новий екземпляр View
     */
    View getView();
}
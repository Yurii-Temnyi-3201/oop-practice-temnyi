package lab3;

/**
 * ConcreteCreator (шаблон проектування Factory Method). Оголошує метод "фабрикуючий" об'єкти.
 * @author Темний Ю.
 * @version 1.0
 */
public class ViewableResistance implements Viewable {
    
    /**
     * Конструктор за замовчуванням
     */
    public ViewableResistance() {
        // конструктор за замовчуванням
    }
    
    /**
     * Factory Method - створює ViewResistanceResult
     * @return новий екземпляр ViewResistanceResult
     */
    @Override
    public View getView() {
        return new ViewResistanceResult();
    }
}
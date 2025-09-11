package lab5;
import lab3.ViewResistanceResult;
import lab3.ResistanceResult;
import lab3.View;
import java.util.ArrayList;
import java.util.List;

/**
 * Консольна команда Change item;
 * шаблон Command
 * @author Темний Ю.
 * @version 1.0
 */
public class ChangeConsoleCommand extends ChangeItemCommand implements ConsoleCommand {
    /**
     * Об'єкт, що реалізує інтерфейс View;
     * обслуговує колекцію об'єктів ResistanceResult
     */
    private View view;
    
    /**
     * Список команд для індивідуальних елементів, для можливості скасування
     */
    private List<ChangeItemCommand> itemCommands = new ArrayList<>();
    
    /**
     * Коефіцієнт масштабування для збереження
     */
    private double lastOffset;
    
    /**
     * Повертає поле view
     * @return значення view
     */
    public View getView() {
        return view;
    }
    
    /**
     * Встановлює поле view
     * @param view значення для view
     * @return нове значення view
     */
    public View setView(View view) {
        return this.view = view;
    }
    
    /**
     * Ініціалізує поле view
     * @param view об'єкт, що реалізує інтерфейс View
     */
    public ChangeConsoleCommand(View view) {
        this.view = view;
    }
    
    @Override
    public char getKey() {
        return 'c';
    }
    
    @Override
    public String toString() {
        return "'c'hange";
    }
    
    @Override
    public void execute() {
        // Очищаємо старі команди
        itemCommands.clear();
        
        // Генеруємо новий коефіцієнт
        lastOffset = Math.random() * 2.0 + 0.5;
        System.out.println("Зміна елементів: коефіцієнт масштабування " + lastOffset);
        
        for (ResistanceResult item : ((ViewResistanceResult)view).getItems()) {
            // Для кожного елементу створюємо окрему команду
            ChangeItemCommand cmd = new ChangeItemCommand();
            cmd.setItem(item);
            cmd.setOffset(lastOffset);
            cmd.execute();
            // Зберігаємо команду для можливості скасування
            itemCommands.add(cmd);
        }
        view.viewShow();
    }
    
    @Override
    public void undo() {
        System.out.println("Скасування зміни елементів (коефіцієнт " + lastOffset + ")");
        // Скасовуємо кожну індивідуальну команду
        for (ChangeItemCommand cmd : itemCommands) {
            cmd.undo();
        }
        view.viewShow();
    }
}
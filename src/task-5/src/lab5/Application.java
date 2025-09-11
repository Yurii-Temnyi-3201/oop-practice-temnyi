package lab5;

import lab3.View;
import lab4.ViewableTable;
import java.util.Stack;
/**
 * Формує та відображає меню;
 * реалізує шаблон Singleton
 * @author Темний Ю.
 * @version 1.0
 */
public class Application {
    /**
     * Посилання на екземпляр класу Application; шаблон Singleton
     */
    private static Application instance = new Application();
    /**
     * Закритий конструктор; шаблон Singleton
     */
    private Application() {}
    /**
     * Повертає посилання на екземпляр класу Application;
     * шаблон Singleton
     * @return екземпляр Application
     * @see Application
     */
    public static Application getInstance() {
        return instance;
    }
    
    /**
     * Об'єкт, що реалізує інтерфейс View;
     * обслуговує колекцію об'єктів ResistanceResult;
     * ініціалізується за допомогою Factory Method
     */
    private View view = new ViewableTable().getView();  // Factory Method! 
    /**
     * Об'єкт класу Menu;
     * макрокоманда (шаблон Command)
     */
    private Menu menu = new Menu();
    
    /**
     * Історія виконаних команд для можливості скасування
     */
    private Stack<Command> history = new Stack<>();
    
    /**
     * Додає команду до історії виконаних команд
     * @param command виконана команда
     */
    public void addToHistory(ConsoleCommand command) {
        history.push(command);
    }
    /**
     * Скасовує останню виконану команду
     */
    public void undoLastCommand() {
        if (!history.isEmpty()) {
            Command lastCommand = history.pop();
            lastCommand.undo();
        } else {
            System.out.println("Немає команд для скасування!");
        }
    }
    /**
     * Повертає об'єкт View
     * @return об'єкт, що реалізує інтерфейс View
     */
    public View getView() {
        return view;
    }
    /**
     * Обробка команд користувача
     */
    public void run() {
        menu.add(new ViewConsoleCommand(view));
        menu.add(new GenerateConsoleCommand(view));
        menu.add(new ChangeConsoleCommand(view));
        menu.add(new SaveConsoleCommand(view));
        menu.add(new RestoreConsoleCommand(view));
        menu.add(new UndoConsoleCommand(this));//команда Undo
        menu.execute();
    }
	}

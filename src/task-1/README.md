# ООП - Task 1

## Завдання
Написати просту консольну програму, яка виводить аргументи командного рядка.

## Опис програми
Програма написана мовою Java, виводить аргументи командного рядка. Якщо аргументів немає, програма повідомляє про це.

### Код програми
```java
public class Main {
    public static void main(String[] args) {
        System.out.print("Argumenty komandnogo ryadka: ");
        if (args.length == 0) {
            System.out.println(" nema argumentov ");
        } else {
            for (String arg : args) {
                System.out.print(arg + " ");
            }
            System.out.println();
        }
    }
}

## Результат:
[![Результат роботи програми](<Знімок екрана-1.png>)](https://github.com/Yurii-Temnyi-3201/oop-practice-temnyi/blob/main/src/task-1/%D0%97%D0%BD%D1%96%D0%BC%D0%BE%D0%BA%20%D0%B5%D0%BA%D1%80%D0%B0%D0%BD%D0%B0.png)

package calc;

import java.util.Scanner;

/**
 * Консольна взаємодія з користувачем.
 * Для виходу введіть "q" замість першого числа.
 */
public class Main {

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Калькулятор (операції: + - * / sqrt; q — вихід) ===");

        while (true) {
            System.out.print("\nВведіть перше число: ");
            if (!scanner.hasNextLine()) break;
            String first = scanner.nextLine();
            if (first.trim().equalsIgnoreCase("q")) break;

            try {
                double a = Calculator.parseNumber(first);
                System.out.print("Введіть операцію (+, -, *, /, sqrt): ");
                String op = scanner.nextLine().trim();
                double b = 0;
                if (!op.equals("sqrt")) {
                    System.out.print("Введіть друге число: ");
                    b = Calculator.parseNumber(scanner.nextLine());
                }
                double result = calculator.calculate(a, b, op);
                System.out.println("Результат: " + result);
            } catch (ArithmeticException e) {
                System.out.println("Помилка обчислення: " + e.getMessage());
            } catch (InvalidInputException e) {
                System.out.println("Некоректні дані: " + e.getMessage());
            } finally {
                System.out.println("Обробку запиту завершено.");
            }
        }
        System.out.println("До побачення!");
    }
}

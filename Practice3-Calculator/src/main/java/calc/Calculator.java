package calc;

/**
 * Калькулятор з базовими арифметичними операціями.
 * Кожен метод перевіряє вхідні значення і викидає InvalidInputException,
 * а ділення на нуль — ArithmeticException.
 */
public class Calculator {

    public double add(double a, double b) throws InvalidInputException {
        validate(a, b);
        return a + b;
    }

    public double subtract(double a, double b) throws InvalidInputException {
        validate(a, b);
        return a - b;
    }

    public double multiply(double a, double b) throws InvalidInputException {
        validate(a, b);
        return a * b;
    }

    public double divide(double a, double b) throws InvalidInputException {
        validate(a, b);
        if (b == 0) {
            throw new ArithmeticException("Ділення на нуль неможливе");
        }
        return a / b;
    }

    /** Квадратний корінь: аргумент не може бути від'ємним. */
    public double sqrt(double a) throws InvalidInputException {
        validate(a);
        if (a < 0) {
            throw new InvalidInputException("Неможливо добути квадратний корінь з від'ємного числа: " + a);
        }
        return Math.sqrt(a);
    }

    /** Виконує операцію за її символом (+, -, *, /, sqrt). */
    public double calculate(double a, double b, String operation) throws InvalidInputException {
        if (operation == null) {
            throw new InvalidInputException("Операцію не вказано");
        }
        return switch (operation.trim()) {
            case "+" -> add(a, b);
            case "-" -> subtract(a, b);
            case "*" -> multiply(a, b);
            case "/" -> divide(a, b);
            case "sqrt" -> sqrt(a);
            default -> throw new InvalidInputException("Невідома операція: " + operation);
        };
    }

    /** Перетворює рядок на число; рядок, що не є числом, — некоректні дані. */
    public static double parseNumber(String text) throws InvalidInputException {
        if (text == null || text.isBlank()) {
            throw new InvalidInputException("Порожнє значення замість числа");
        }
        try {
            return Double.parseDouble(text.trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            throw new InvalidInputException("'" + text + "' не є числом", e);
        }
    }

    private static void validate(double... values) throws InvalidInputException {
        for (double v : values) {
            if (Double.isNaN(v) || Double.isInfinite(v)) {
                throw new InvalidInputException("Некоректне значення: " + v);
            }
        }
    }
}

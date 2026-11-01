package calc;

/**
 * Власне виключення: викидається, коли вхідні дані некоректні
 * (рядок замість числа, NaN/нескінченність, від'ємне число під коренем тощо).
 */
public class InvalidInputException extends Exception {

    public InvalidInputException(String message) {
        super(message);
    }

    public InvalidInputException(String message, Throwable cause) {
        super(message, cause);
    }
}

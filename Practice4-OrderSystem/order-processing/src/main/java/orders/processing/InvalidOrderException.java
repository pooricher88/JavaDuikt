package orders.processing;

/** Замовлення не пройшло перевірку (null-товар, кількість <= 0, порожній покупець). */
public class InvalidOrderException extends Exception {

    public InvalidOrderException(String message) {
        super(message);
    }
}

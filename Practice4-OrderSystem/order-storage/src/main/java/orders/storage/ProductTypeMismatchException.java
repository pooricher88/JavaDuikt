package orders.storage;

/** Виключення для узагальнених типів: очікувався один підтип Product, а отримано інший. */
public class ProductTypeMismatchException extends Exception {

    public ProductTypeMismatchException(Class<?> expected, Class<?> actual) {
        super("Очікувався товар типу " + expected.getSimpleName() + ", але знайдено " + actual.getSimpleName());
    }
}

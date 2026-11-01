package orders.model;

import lombok.Getter;
import lombok.ToString;

/**
 * Базовий тип товару. Усі узагальнення системи обмежені ним: {@code <T extends Product>}.
 */
@Getter
@ToString
public abstract class Product {

    private final String id;
    private final String name;
    private final double price;

    protected Product(String id, String name, double price) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Назва товару не може бути порожньою");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Ціна не може бути від'ємною: " + price);
        }
        this.id = id;
        this.name = name;
        this.price = price;
    }

    /** Категорія товару (електроніка, одяг, ...). */
    public abstract String getCategory();
}

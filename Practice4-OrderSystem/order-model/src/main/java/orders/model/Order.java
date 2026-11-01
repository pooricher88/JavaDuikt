package orders.model;

import lombok.Getter;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Замовлення на товар типу T (T обмежений Product).
 *
 * @param <T> тип товару: Product або його підтип
 */
@Getter
@ToString
public class Order<T extends Product> {

    private static final AtomicInteger SEQUENCE = new AtomicInteger(1);

    private final int id;
    private final T product;
    private final int quantity;
    private final String customer;
    private volatile OrderStatus status = OrderStatus.NEW;
    private volatile String processedBy;

    public Order(T product, int quantity, String customer) {
        this.id = SEQUENCE.getAndIncrement();
        this.product = product;
        this.quantity = quantity;
        this.customer = customer;
    }

    public double getTotal() {
        return product.getPrice() * quantity;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setProcessedBy(String processedBy) {
        this.processedBy = processedBy;
    }
}

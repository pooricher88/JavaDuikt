package orders.storage;

import orders.model.Order;
import orders.model.Product;
import orders.storage.internal.StorageIndex;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Потокобезпечне сховище замовлень на товари типу T.
 *
 * @param <T> тип товару (обмеження: T extends Product)
 */
public class OrderRepository<T extends Product> {

    private final StorageIndex<Order<T>> index = new StorageIndex<>();

    public void save(Order<T> order) {
        if (order == null) {
            throw new IllegalArgumentException("Замовлення не може бути null");
        }
        index.put(order.getId(), order);
    }

    public Optional<Order<T>> findById(int id) {
        return Optional.ofNullable(index.get(id));
    }

    /**
     * Повертає товар замовлення, перевіривши, що він має очікуваний підтип.
     * Захищає від ClassCastException при роботі з узагальненими типами.
     */
    public <R extends T> R findProduct(int orderId, Class<R> expectedType) throws ProductTypeMismatchException {
        Order<T> order = findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Замовлення #" + orderId + " не знайдено"));
        T product = order.getProduct();
        if (!expectedType.isInstance(product)) {
            throw new ProductTypeMismatchException(expectedType, product.getClass());
        }
        return expectedType.cast(product);
    }

    public List<Order<T>> findAll() {
        return index.values();
    }

    public List<Order<T>> findBy(Predicate<? super Order<T>> condition) {
        return index.values().stream().filter(condition).toList();
    }

    public int size() {
        return index.size();
    }
}

package orders.processing;

import orders.model.Clothing;
import orders.model.Electronics;
import orders.model.Order;
import orders.model.OrderStatus;

/** Статичні методи-предикати: зручно передавати як посилання на методи (OrderFilters::isExpensive). */
public final class OrderFilters {

    public static final double EXPENSIVE_LIMIT = 10_000;

    private OrderFilters() {
    }

    public static boolean isElectronics(Order<?> order) {
        return order.getProduct() instanceof Electronics;
    }

    public static boolean isClothing(Order<?> order) {
        return order.getProduct() instanceof Clothing;
    }

    public static boolean isExpensive(Order<?> order) {
        return order.getTotal() >= EXPENSIVE_LIMIT;
    }

    public static boolean isCompleted(Order<?> order) {
        return order.getStatus() == OrderStatus.COMPLETED;
    }

    public static String describe(Order<?> order) {
        return String.format("#%-3d %-12s %-28s x%d = %10.2f грн  [%s, %s]",
                order.getId(), order.getProduct().getCategory(), order.getProduct().getName(),
                order.getQuantity(), order.getTotal(), order.getStatus(),
                order.getProcessedBy() == null ? "-" : order.getProcessedBy());
    }
}

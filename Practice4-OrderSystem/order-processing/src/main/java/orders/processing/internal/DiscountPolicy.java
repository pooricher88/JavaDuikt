package orders.processing.internal;

/**
 * Внутрішня політика знижок. Пакет не експортується з модуля order.processing,
 * тож модулі order.concurrency та order.app не мають до нього доступу.
 */
public class DiscountPolicy {

    public double apply(double total, int quantity) {
        double discount = quantity >= 5 ? 0.10 : quantity >= 3 ? 0.05 : 0.0;
        return Math.round(total * (1 - discount) * 100) / 100.0;
    }
}

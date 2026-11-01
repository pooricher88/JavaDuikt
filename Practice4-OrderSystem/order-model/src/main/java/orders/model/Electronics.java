package orders.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/** Електроніка. Об'єкти створюються через Lombok @Builder: Electronics.builder()...build(). */
@Getter
@ToString(callSuper = true)
public class Electronics extends Product {

    private final String brand;
    private final int warrantyMonths;

    @Builder
    public Electronics(String id, String name, double price, String brand, int warrantyMonths) {
        super(id, name, price);
        this.brand = brand;
        this.warrantyMonths = warrantyMonths;
    }

    @Override
    public String getCategory() {
        return "Електроніка";
    }
}

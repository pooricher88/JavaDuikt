package orders.app;

import com.github.javafaker.Faker;
import orders.model.Clothing;
import orders.model.Electronics;

import java.util.Locale;
import java.util.UUID;

/** Наповнює об'єкти випадковими даними за допомогою JavaFaker і будує їх через Lombok @Builder. */
public class ProductFactory {

    private static final String[] BRANDS = {"Samsung", "Apple", "Lenovo", "Xiaomi", "Sony", "Asus"};
    private static final String[] ELECTRONICS = {"Смартфон", "Ноутбук", "Навушники", "Планшет", "Монітор", "Смарт-годинник"};
    private static final String[] CLOTHES = {"Футболка", "Куртка", "Джинси", "Светр", "Худі", "Сорочка"};
    private static final String[] SIZES = {"XS", "S", "M", "L", "XL"};

    private final Faker faker = new Faker(Locale.ENGLISH);

    public Electronics randomElectronics() {
        String brand = faker.options().option(BRANDS);
        return Electronics.builder()
                .id(UUID.randomUUID().toString().substring(0, 8))
                .name(faker.options().option(ELECTRONICS) + " " + brand)
                .brand(brand)
                .price(faker.number().randomDouble(2, 1_500, 60_000))
                .warrantyMonths(faker.options().option(6, 12, 24))
                .build();
    }

    public Clothing randomClothing() {
        return Clothing.builder()
                .id(UUID.randomUUID().toString().substring(0, 8))
                .name(faker.options().option(CLOTHES) + " " + faker.color().name())
                .size(faker.options().option(SIZES))
                .material(faker.commerce().material())
                .price(faker.number().randomDouble(2, 300, 5_000))
                .build();
    }

    public String randomCustomer() {
        return faker.name().fullName();
    }

    public int randomQuantity() {
        return faker.number().numberBetween(1, 6);
    }
}

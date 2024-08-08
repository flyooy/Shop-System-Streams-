import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void addProduct() {
        Customer ahmed = new Customer("Ahmed", "Nasr", "ahmed@super-code.de", Instant.now());
        Order order = new Order(ahmed);

        Product product = new Product("Handy", "Gutes Handy wenn deines kaputt ist", 1000, "H292902");
        order.addProduct(product, 1);

        assertEquals(1, order.getProductQuantity(product));
    }

    @Test
    void addProductMultipleTimesToSumQuantity() {
        Customer ahmed = new Customer("Ahmed", "Nasr", "ahmed@super-code.de", Instant.now());
        Order order = new Order(ahmed);

        Product product = new Product("Handy", "Gutes Handy wenn deines kaputt ist", 1000, "H292902");

        order.addProduct(product, 1);
        order.addProduct(product, 10);

        assertEquals(11, order.getProductQuantity(product));
    }

    @Test
    void removeProduct() {
        Customer ahmed = new Customer("Ahmed", "Nasr", "ahmed@super-code.de", Instant.now());
        Order order = new Order(ahmed);

        Product product = new Product("Handy", "Gutes Handy wenn deines kaputt ist", 1000, "H292902");

        order.addProduct(product, 1);
        assertEquals(1, order.getProductQuantity(product));

        order.removeProduct(product);

        assertEquals(0, order.getProductQuantity(product));
    }
}
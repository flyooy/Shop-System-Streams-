import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class Order {
    private Customer customer;
    private Map<Product, Integer> products;
    private boolean hasPaid;
    private Instant orderDate;
    private String shippingAddress;

    public Order(Customer customer) {
        this.setCustomer(customer);
        this.products = new HashMap<>();
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public float totalOrderValue() {
        return (float) products
                .entrySet()
                .stream()
                .mapToDouble((e) -> e.getKey().getPrice() * e.getValue())
                .sum();
    }

    public Product addProduct(Product product, int quantity) {
        if(!products.containsKey(product)) products.put(product, 0);
        Integer productQuantity = products.get(product);
        products.replace(product, productQuantity + quantity);
        return product;
    }

    public int getProductQuantity(Product product) {
        Integer productQuantity = products.get(product);
        return productQuantity != null ? productQuantity : 0;
    }

    public Product removeProduct(Product product) {
        products.remove(product);
        return product;
    }


    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public boolean isHasPaid() {
        return hasPaid;
    }

    public void setHasPaid(boolean hasPaid) {
        this.hasPaid = hasPaid;
    }

    public Instant getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Instant orderDate) {
        this.orderDate = orderDate;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }


}
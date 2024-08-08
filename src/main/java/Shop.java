import java.util.*;
import java.util.stream.Collectors;
import java.util.Comparator;

public class Shop {
    private List<Order> orderList;

    public Shop() {
        this.orderList = new ArrayList<>();
    }

    public Order addOrder(Order order) {
        this.orderList.add(order);
        return order;
    }

    public Order removeOrder(Order order) {
        this.orderList.remove(order);
        return order;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    // o1, o2, o3,     o4, ...
    // <c1> <c1> <c1>  <c2>
    public Customer customerWithMostOrder() {
        return orderList
                .stream()
                .collect(Collectors.groupingBy(Order::getCustomer))
                .entrySet().stream()
                .max(Comparator.comparing(entry -> entry.getValue().size()))
                .map(entry -> entry.getKey())
                .orElse(null);
    }

    public Customer customerWithHighestLifetimeValue() {
        return orderList.stream()
                .collect(Collectors.groupingBy(Order::getCustomer))
                .entrySet().stream()
                .map(entry->new AbstractMap.SimpleEntry<Customer, Float>(entry.getKey(), (float) entry.
                        getValue()
                        .stream()
                        .mapToDouble(Order::totalOrderValue)
                        .sum()))
                .max(Comparator.comparing(e->e.getValue()))
                .map(e->e.getKey())
                .orElse(null);
    }

    public Product mostPopularProduct(){
        return orderList.stream()
                .flatMap(order -> order.getProducts().entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        Integer::sum
                ))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public Product leastPopularProduct(){
        return orderList.stream()
                .flatMap(order -> order.getProducts().entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        Integer::sum
                ))
                .entrySet()
                .stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public double  theAverageValueOfAnOrder(){
        return orderList.stream()
                .mapToDouble(Order::totalOrderValue)
                .average()
                .getAsDouble();
    }

    public Customer cutomerHasBeenWithUstheLongest(){
        return   orderList.stream()
                .map(Order::getCustomer)
                .distinct()
                .min(Comparator.comparing(Customer::getCustomerSince))
                .orElse(null);

    }
}   
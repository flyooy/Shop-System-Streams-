import java.time.Instant;
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

    public List<Customer> customersWithOrdersOverCertainValue(float price){
            return orderList.stream()
                    .filter(o->o.totalOrderValue() > price)
                    .map(Order::getCustomer)
                    .distinct()
                    .collect(Collectors.toList());
    }

    public List<Customer> topNCustomerPerProduct (Product product, int n) {
        return orderList
                .stream()
                .map(order ->
                        new AbstractMap.SimpleEntry<Customer, Integer>(
                                order.getCustomer(), // key von entry
                                order.getProducts().containsKey(product) ? order.getProducts().get(product) : 0 // value von entry
                        )
                )
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.summingInt(Map.Entry::getValue)))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<Customer, Integer>comparingByValue().reversed())
                .limit(n)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public Category topCategoryByRevenue() {
        Optional<Category> foundCategory = orderList
                .stream()
                .flatMap(order -> order.getProducts().entrySet().stream())
                .map(orderProductEntry -> new AbstractMap.SimpleEntry<Category, Double>(
                        orderProductEntry.getKey().getCategory(), // product dieser bestellung
                        orderProductEntry.getKey().getPrice() * orderProductEntry.getValue().doubleValue() // umsatz durch product dieser bestellung
                ))
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.summingDouble(Map.Entry::getValue)))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
        if(foundCategory.isEmpty()) throw new IllegalStateException("No top Category found");
        else return foundCategory.get();
    }

    public double averageValueOfAnOrderInPeriod(Instant date1, Instant date2){
        return orderList.stream()
                .filter(o->o.getOrderDate().isAfter(date1) && o.getOrderDate().isBefore(date2))
                .mapToDouble(Order::totalOrderValue)
                .average()
                .orElse(0.0);
    }

    public double totalStoreTurnoverInPeriod(Instant date1, Instant date2){
        return orderList.stream()
                .filter(o->o.getOrderDate().isAfter(date1) && o.getOrderDate().isBefore(date2))
                .mapToDouble(Order::totalOrderValue)
                .sum();
    }

    public double averageNumberOfProducrsPerOrder(){
        return  orderList.stream()
                .mapToInt(order->order.getProducts().values().stream().mapToInt(Integer::intValue).sum())
                .average()
                .orElse(0.0);

    }

    public Product productThatWasOrderedTheMostByDifferentCustomers(){
        return orderList.stream()
                .flatMap(order->order.getProducts().keySet().stream()
                        .map(product -> new AbstractMap.SimpleEntry<>(product, order.getCustomer())))
                .distinct()
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public Instant highestSalesDay(){
        return orderList.stream()
                .collect(Collectors.groupingBy(Order::getOrderDate, Collectors.summingDouble(Order::totalOrderValue)))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

}   
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(Lifecycle.PER_CLASS)
class ShopTest {

    private Shop shop;
    private Customer kazim;
    private Customer anna;
    private Customer viktor;
    private Product monitor;
    private Product uhr;
    private Product soundMachine;
    private Product pinzette;
    @BeforeAll
    void setup() {
        shop = new Shop();

        LocalDateTime kursbeginn = LocalDateTime.of(2024, Month.MAY, 21, 8, 45);
        Instant kursbeginnInstant = kursbeginn.toInstant(ZoneOffset.UTC);

        kazim = new Customer("Kazim", "Sakip", "k.s@gmail.com", Instant.now());
        viktor = new Customer("Viktor", "Steiner", "viktors@gmail.com", kursbeginnInstant);
        anna = new Customer("Anna", "Patschen", "annapatschen@gmail.com", Instant.now());

        uhr = new Product("Uhr", "hilft dabei die Zeit zu lesen", 150.0f, "U139030",Category.ACCESSORIES);
        Product rolex = new Product("Rolex Uhr", "hilft dabei die Zeit zu lesen", 18000.0f, "U144430",Category.ACCESSORIES);
        soundMachine = new Product("Sound Machine", "Macht viele geräusche auf Tastenklick", 9.0f, "SM239",Category.ELECTRONICS);
        pinzette = new Product("Pinzette", "Pinzette mit integrierte LED", 2.49f, "P800",Category.BEAUTY);
        monitor = new Product("Monitor Groß", "Monitor mit vielen kleinen Pixeln", 250.0f, "LG239340e",Category.ELECTRONICS);
        Product keyboard = new Product("Keyboard", "Keyboard mit vielen Tasten", 100.0f, "K239340e",Category.ELECTRONICS);
        Product perfume = new Product("Perfume", "Riecht echt gut hmmmn (100ml)", 100.0f, "P239340e",Category.BEAUTY);

        Order o1 = new Order(kazim);
        Order o2 = new Order(kazim);
        Order o3 = new Order(kazim);

        Order o4 = new Order(viktor);

        Order o5 = new Order(anna);
        Order o6 = new Order(anna);

        o1.addProduct(uhr, 1);
        o1.addProduct(rolex, 1);
        o1.addProduct(pinzette, 2);
        o1.setHasPaid(true);
        o1.setOrderDate(Instant.parse("2024-08-09T13:16:00Z"));
        shop.addOrder(o1);
        //18154.98

        o2.addProduct(monitor, 1);
        o2.setHasPaid(false);
        o2.setOrderDate(Instant.parse("2024-08-09T13:16:00Z"));
        shop.addOrder(o2);
        //250

        o3.addProduct(soundMachine, 1);
        o3.setHasPaid(true);
        o3.setOrderDate(Instant.parse("2024-08-09T13:16:00Z"));
        shop.addOrder(o3);
        //9

        o4.addProduct(monitor, 12);
        o4.addProduct(keyboard, 12);
        o4.setHasPaid(true);
        o4.setOrderDate(kursbeginnInstant);
        shop.addOrder(o4);
        //4200

        o5.addProduct(perfume, 1);
        o5.addProduct(rolex, 2);
        o5.setHasPaid(true);
        o5.setOrderDate(Instant.parse("2024-08-09T13:16:00Z"));
        shop.addOrder(o5);
        //36100

        o6.addProduct(pinzette, 1);
        o6.setHasPaid(true);
        o6.setOrderDate(Instant.parse("2024-08-09T13:16:00Z"));
        shop.addOrder(o6);
        //2,49
    }


    @Test
    void totalOrderValue() {
        assertEquals(18154.98f, shop.getOrderList().get(0).totalOrderValue());
    }

    @Test
    void customerWithMostOrders() {
        assertEquals(kazim, shop.customerWithMostOrder());
    }


    @Test
    void customerWithHighestLifetimeValue() {
        assertEquals(anna, shop.customerWithHighestLifetimeValue());
    }

    @Test
    void mostPopularProduct(){
        assertEquals(monitor, shop.mostPopularProduct());
    }
    @Test
    void leastPopularProduct(){
        assertEquals(soundMachine, shop.leastPopularProduct());
    }

    @Test
    void cutomerHasBeenWithUstheLongest(){
        assertEquals(viktor,shop.cutomerHasBeenWithUstheLongest());
    }

    @Test
    void theAverageValueOfAnOrder(){
        //58716,47/6 = 9786,078333333333333
        assertEquals(9786.078333333333333,shop.theAverageValueOfAnOrder(),0.01);
    }

    @Test
    void customersWithOrdersOverCertainValue(){
        float price = 5000;
       List<Customer> expectedCustomers = Arrays.asList(anna,kazim);
       List<Customer> actualCustomers = shop.customersWithOrdersOverCertainValue(price);
       assertEquals(expectedCustomers.size(), actualCustomers.size());
       assertTrue(actualCustomers.containsAll(expectedCustomers));
    }

    @Test
    void averageValueOfAnOrderInPeriod(){
        Instant date1 = Instant.parse("2024-08-07T11:19:42.12Z");;
        Instant date2 = Instant.parse("2024-08-10T11:19:42.12Z");
        // 54516,47/5 = 10903,294
        assertEquals(10903.294,shop.averageValueOfAnOrderInPeriod(date1, date2),0.01);
    }

    @Test
    void  totalStoreTurnoverInPeriod(){
        Instant date1 = Instant.parse("2024-08-07T11:19:42.12Z");;
        Instant date2 = Instant.parse("2024-08-10T11:19:42.12Z");
        assertEquals(54516.47,shop.totalStoreTurnoverInPeriod(date1, date2),0.01);
    }

    @Test
    void averageNumberOfProducrsPerOrder(){
        //34/6 = 5.67
        assertEquals(5.67,shop.averageNumberOfProducrsPerOrder(),0.01);
    }

    @Test
    void testProductThatWasOrderedTheMostByDifferentCustomers() {
        assertEquals(pinzette,shop.productThatWasOrderedTheMostByDifferentCustomers());
    }

    @Test
    void highestSalesDay(){
        Instant now = Instant.parse("2024-08-09T13:16:00Z");
        Instant expectedHighestSalesDay = now;
        assertEquals(expectedHighestSalesDay,shop.highestSalesDay());
    }

    // Welcher Kunde ist am längsten Dabei ?
    // Was ist das beliebteste Produkt ?
    // Was ist das unbeliebteste Produkt ?
    // Welcher Kunde hat den größten Umsatz gebracht ? AUFGABE
    // Was ist der durschnittliche Wert einer Bestellung ?
    //Produkt dass am Meisten von unterschiedlichen Kunden bestellt wurde...
    // Umsatzstärkster Tag...
}
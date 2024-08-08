import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;

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
    @BeforeAll
    void setup() {
        shop = new Shop();

        LocalDateTime kursbeginn = LocalDateTime.of(2024, Month.MAY, 21, 8, 45);
        Instant kursbeginnInstant = kursbeginn.toInstant(ZoneOffset.UTC);

        kazim = new Customer("Kazim", "Sakip", "k.s@gmail.com", Instant.now());
        viktor = new Customer("Viktor", "Steiner", "viktors@gmail.com", kursbeginnInstant);
        anna = new Customer("Anna", "Patschen", "annapatschen@gmail.com", Instant.now());

        uhr = new Product("Uhr", "hilft dabei die Zeit zu lesen", 150.0f, "U139030");
        Product rolex = new Product("Rolex Uhr", "hilft dabei die Zeit zu lesen", 18000.0f, "U144430");
        soundMachine = new Product("Sound Machine", "Macht viele geräusche auf Tastenklick", 9.0f, "SM239");
        Product pinzette = new Product("Pinzette", "Pinzette mit integrierte LED", 2.49f, "P800");
        monitor = new Product("Monitor Groß", "Monitor mit vielen kleinen Pixeln", 250.0f, "LG239340e");
        Product keyboard = new Product("Keyboard", "Keyboard mit vielen Tasten", 100.0f, "K239340e");
        Product perfume = new Product("Perfume", "Riecht echt gut hmmmn (100ml)", 100.0f, "P239340e");

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
        o1.setOrderDate(Instant.now());
        shop.addOrder(o1);
        //18154.98

        o2.addProduct(monitor, 1);
        o2.setHasPaid(false);
        o2.setOrderDate(Instant.now());
        shop.addOrder(o2);
        //250

        o3.addProduct(soundMachine, 1);
        o3.setHasPaid(true);
        o3.setOrderDate(Instant.now());
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
        o5.setOrderDate(Instant.now());
        shop.addOrder(o5);
        //36100

        o6.addProduct(pinzette, 1);
        o6.setHasPaid(true);
        o6.setOrderDate(Instant.now());
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


    // Welcher Kunde ist am längsten Dabei ?
    // Was ist das beliebteste Produkt ?
    // Was ist das unbeliebteste Produkt ?

    // Welcher Kunde hat den größten Umsatz gebracht ? AUFGABE

    // Was ist der durschnittliche Wert einer Bestellung ?

}
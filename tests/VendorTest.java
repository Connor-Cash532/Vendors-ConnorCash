import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VendorTest {
    private final ByteArrayOutputStream OUTPUT_STREAM = new ByteArrayOutputStream();
    private final PrintStream ORIGINAL_OUT = System.out;

    @BeforeEach
    void setOUTPUT_STREAM(){
        System.setOut(new PrintStream(OUTPUT_STREAM));
    }
    @AfterEach
    void resetOUTPUT_STREAM(){
        System.setOut(ORIGINAL_OUT);
    }
    @Test
    void addition() {
        Vending v1 = new Vending(10, 10);
        v1.addMoney(2);
        assertEquals(2, v1.getBalance());
    }

    /**
     * Test for buying an item that is in stock and user has money.
     */
    @Test
    void buyItemInStock(){
        Vending v1 = new Vending(1, 1);
        v1.addMoney(10);
        v1.select("Gum");
        assertEquals(9.50, v1.getBalance());
    }
    /**
     * Test for buying an item that is not in stock.
     */
    @Test
    void buyItemOutOfStock(){
        Vending v1 = new Vending(1, 1);
        v1.addMoney(10);
        v1.select("Oreo");
        String expectedOutput = "Sorry, don't know that item" + System.lineSeparator();
        assertEquals(expectedOutput, OUTPUT_STREAM.toString());
    }
    /**
     * Test for not having enough money for an item in stock
     */
    @Test
    void buyItemWithInsufficientMoney(){
        Vending v1 = new Vending(1, 1);
        v1.addMoney(0.50);
        v1.select("Gum");
        assertEquals(0, v1.getBalance());
        v1.select("Gum");
        assertEquals(0, v1.getBalance());
        String expectedOutput = "Gimme more money" + System.lineSeparator();
        assertEquals(expectedOutput, OUTPUT_STREAM.toString());
    }


}
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
    void addMoney() {
        Vending v1 = new Vending(10, 10);
        v1.addMoney(2);
        v1.addMoney(-2);
        v1.addMoney(0);
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
    void buyItemNotInStock(){
        Vending v1 = new Vending(1, 1);
        v1.addMoney(10);
        v1.select("Oreo");
        String expectedOutput = "Sorry, don't know that item" + System.lineSeparator();
        assertEquals(expectedOutput, OUTPUT_STREAM.toString());
    }
    /**
     * Test for buying an item that is out of stock.
     */
    @Test
    void buyItemOutOfStock(){
        Vending v1 = new Vending(1, 1);
        v1.addMoney(10);
        v1.select("Candy");
        v1.select("Candy");
        String expectedOutput = "Item is out of stock" + System.lineSeparator();
        assertEquals(expectedOutput, OUTPUT_STREAM.toString());
    }
    /**
     * Test for not having enough money for an item in stock
     */
    @Test
    void buyItemWithInsufficientMoney(){
        Vending v1 = new Vending(1, 2);
        v1.addMoney(0.50);
        v1.select("Gum");
        assertEquals(0, v1.getBalance());
        v1.select("Gum");
        assertEquals(0, v1.getBalance());
        String expectedOutput = "Gimme more money" + System.lineSeparator();
        assertEquals(expectedOutput, OUTPUT_STREAM.toString());
    }

    /**
     * Test for buying an item from a vending machine
     * with no items in stock.
     */
    @Test
    void emptyStock(){
        Vending v1 = new Vending(2, 1);
        v1.addMoney(10);
        v1.select("Candy");
        v1.select("Candy");
        v1.select("Gum");
        v1.select("Gum");
        String expectedOutput = "Vending machine is empty" + System.lineSeparator();
        assertEquals(expectedOutput, OUTPUT_STREAM.toString());
    }

    /**
     * Test for restock() method in Vendor.java
     */
    @Test
    void restockEntireVendor(){
        Vending v1 = new Vending(1, 1);
        v1.addMoney(10);
        v1.select("Gum");
        v1.select("Candy");
        v1.select("Candy");
        String expectedOutput = "Vending machine is empty" + System.lineSeparator();
        assertEquals(expectedOutput, OUTPUT_STREAM.toString());
        v1.restockEntireVendor();
        v1.select("Gum");
        assertEquals(7.75, v1.getBalance());
        v1.select("Candy");
        assertEquals(6.50, v1.getBalance());
    }

    @Test
    void restockSingleItem(){
        Vending v1 = new Vending(1, 2);
        v1.addMoney(10);
        v1.select("Gum");
        v1.select("Gum");
        v1.select("Gum");
        v1.select("Gum");
        assertEquals(8.50, v1.getBalance());
        v1.select("Gum");
        assertEquals(8.00, v1.getBalance());

    }

    @Test
    void changeItemName(){
        Vending v1 = new Vending(1, 2);
        v1.addMoney(10);
        v1.changeName("Gum", "Pineapple");
        v1.select("Pineapple");
        assertEquals(9.50, v1.getBalance());
        v1.select("Gum");
        String expectedOutput = "Sorry, don't know that item" + System.lineSeparator();
        assertEquals(expectedOutput, OUTPUT_STREAM.toString());
    }

    @Test
    void vendors(){
        Vending v1 = new Vending(1, 2);
        Vending v2 = new Vending(1, 2);
        Vending v3 = new Vending(1, 2);

        Vendors v = new Vendors();
        v.addVendors("Jon's Store", v1);
        v.addVendors("Jim's Store", v2);
        v.addVendors("Jane's Store", v3);

        String expected = "";
        expected += "Vendor Name: Jane's Store\n";
        expected += "Item Name: Candy\nQuantity in stock: 1\nCost (USD): 1.25\n\n" +
                "Item Name: Gum\nQuantity in stock: 2\nCost (USD): 0.50\n\n";
        expected += "Vendor Name: Jim's Store\n";
        expected += "Item Name: Candy\nQuantity in stock: 1\nCost (USD): 1.25\n\n" +
                "Item Name: Gum\nQuantity in stock: 2\nCost (USD): 0.50\n\n";
        expected += "Vendor Name: Jon's Store\n";
        expected += "Item Name: Candy\nQuantity in stock: 1\nCost (USD): 1.25\n\n" +
                "Item Name: Gum\nQuantity in stock: 2\nCost (USD): 0.50\n\n";
        assertEquals(expected, v.toString());

    }

    @Test
    void vendingToString(){
        Vending v1 = new Vending(1, 2);
        String expected= "Item Name: Candy\nQuantity in stock: 1\nCost (USD): 1.25\n\n" +
                "Item Name: Gum\nQuantity in stock: 2\nCost (USD): 0.50\n\n";
        assertEquals(expected, v1.toString());
        v1.addMoney(10);
        v1.select("Candy");
        expected= "Item Name: Candy\nQuantity in stock: 0\nCost (USD): 1.25\n\n" +
                "Item Name: Gum\nQuantity in stock: 2\nCost (USD): 0.50\n\n";
        assertEquals(expected, v1.toString());

    }

    @Test
    void vendingEmptyToString(){
        Vending v1 = new Vending(1, 2);
        String expected= "";
        v1.removeItem("Candy");
        v1.removeItem("Gum");
        v1.removeItem("Tree");
        assertEquals(expected, v1.toString());
    }

    @Test
    void removeItems(){
        Vending v1 = new Vending(1, 2);
        v1.removeItem("Candy");

        String expected = "";
        expected += "Item Name: Gum\nQuantity in stock: 2\nCost (USD): 0.50\n\n";
        assertEquals(expected, v1.toString());

        v1.removeItem("Tree");
        assertEquals(expected, v1.toString());
    }

    @Test
    void kFrequentPurchasedItems(){
        Vending v1 = new Vending(1, 2);
        v1.select("Candy");
        v1.select("Oreo");

        String[] expected = {"Candy", "Gum"};
        assertEquals(Arrays.toString(expected), Arrays.toString(v1.topKPurchasedItems(2)));
        assertEquals(Arrays.toString(expected), Arrays.toString(v1.topKPurchasedItems(3)));
        v1.removeItem("Candy");
        assertEquals(Arrays.toString(expected), Arrays.toString(v1.topKPurchasedItems(2)));
    }

    @Test
    void makePurchase(){
        
    }
}
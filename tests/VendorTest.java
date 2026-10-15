import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.HashMap;

import static org.junit.gen5.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

public class VendorTest {
    //Test that you can add money to a vendor and money most be positive.
    @Test
    void addMoney() {
        Vendor v1 = new Vendor(10, 10);
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
        Vendor v1 = new Vendor(1, 1);
        v1.addMoney(10);
        v1.select("Gum");
        assertEquals(9.50, v1.getBalance());
    }
    /**
     * Test for buying an item that is not in stock.
     */
    @Test
    void buyItemNotInStock(){
        Vendor v1 = new Vendor(1, 1);
        v1.addMoney(10);
        String expectedOutput = "Sorry, don't know that item" ;
        String actual = v1.select("Oreo");
        assertEquals(expectedOutput, actual);
    }
    /**
     * Test for buying an item that is out of stock.
     */
    @Test
    void buyItemOutOfStock(){
        Vendor v1 = new Vendor(1, 1);
        v1.addMoney(10);
        String actual = v1.select("Candy");
        actual = v1.select("Candy");
        String expectedOutput = "Item is out of stock" ;
        assertEquals(expectedOutput, actual);
    }
    /**
     * Test for not having enough money for an item in stock
     */
    @Test
    void buyItemWithInsufficientMoney(){
        Vendor v1 = new Vendor(1, 2);
        v1.addMoney(0.50);
        v1.select("Gum");
        assertEquals(0, v1.getBalance());
        String actual = v1.select("Gum");
        assertEquals(0, v1.getBalance());
        String expectedOutput = "Gimme more money";
        assertEquals(expectedOutput,actual);
    }

    /**
     * Test for buying an item from a vending machine
     * with no items in stock.
     */
    @Test
    void emptyStock(){
        Vendor v1 = new Vendor(2, 1);
        v1.addMoney(10);
        v1.select("Candy");
        v1.select("Candy");
        v1.select("Gum");
        String actual = v1.select("Gum");
        String expectedOutput = "Vendor machine is empty";
        assertEquals(expectedOutput, actual);
    }

    /**
     * Test for restockEntireVendor() method in Vendor.java
     */
    @Test
    void restockEntireVendor(){
        Vendor v1 = new Vendor(1, 1);
        v1.addMoney(10);
        v1.select("Gum");
        v1.select("Candy");
        String actual = v1.select("Candy");
        String expectedOutput = "Vendor machine is empty";
        assertEquals(expectedOutput, actual);
        v1.restockEntireVendor();
        v1.select("Gum");
        assertEquals(7.75, v1.getBalance());
        v1.select("Candy");
        assertEquals(6.50, v1.getBalance());
    }

    //Test for restocking out of stock items in a vendor if it is out of stock
    @Test
    void restockOutOfStockItems(){
        Vendor v1 = new Vendor(1, 2);
        v1.addMoney(10);
        v1.select("Gum");
        v1.select("Gum");
        v1.select("Gum");
        v1.select("Gum");
        assertEquals(8.50, v1.getBalance());
        v1.select("Gum");
        assertEquals(8.00, v1.getBalance());
    }

    //Test for manually restocking an item (User Story 1)
    @Test
    void restockItem(){
        Vendor v1 = new Vendor(1, 2);
        v1.addMoney(10);
        v1.select("Gum");
        v1.restockItem("Gum");
        HashMap<String, Item> stock = v1.getStock();
        assertEquals(2,stock.get("Gum").stock);
    }

    // Test for User Story 3
    @Test
    void changeItemName(){
        Vendor v1 = new Vendor(1, 2);
        v1.addMoney(10);
        v1.changeName("Gum", "Pineapple");
        v1.select("Pineapple");
        assertEquals(9.50, v1.getBalance());
        String actual = v1.select("Gum");
        String expectedOutput = "Sorry, don't know that item";
        assertEquals(expectedOutput, actual);
    }


    @Test
    void vendors(){
        Vendor v1 = new Vendor(1, 2);
        Vendor v2 = new Vendor(1, 2);
        Vendor v3 = new Vendor(1, 2);

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
        Vendor v1 = new Vendor(1, 2);
        String expected= "Item Name: Candy\nQuantity in stock: 1\nCost (USD): 1.25\n\n" +
                "Item Name: Gum\nQuantity in stock: 2\nCost (USD): 0.50\n\n";
        assertEquals(expected, v1.toString());
        v1.addMoney(10);
        v1.select("Candy");
        expected= "Item Name: Candy\nQuantity in stock: 0\nCost (USD): 1.25\n\n" +
                "Item Name: Gum\nQuantity in stock: 2\nCost (USD): 0.50\n\n";
        assertEquals(expected, v1.toString());

    }

    //Test that empty vendor toString method shows the vendor has nothing.
    @Test
    void vendingEmptyToString(){
        Vendor v1 = new Vendor(1, 2);
        String expected= "";
        v1.removeItem("Candy");
        v1.removeItem("Gum");
        v1.removeItem("Tree");
        assertEquals(expected, v1.toString());
    }

    //Test for removing an item from a vendor's stock (User Story 5)
    @Test
    void removeItems(){
        Vendor v1 = new Vendor(1, 2);
        v1.removeItem("Candy");

        String expected = "";
        expected += "Item Name: Gum\nQuantity in stock: 2\nCost (USD): 0.50\n\n";
        assertEquals(expected, v1.toString());

        v1.removeItem("Tree");
        assertEquals(expected, v1.toString());
    }

    //Test for user being able to track purchases and find the most sold items (User Story 6)
    @Test
    void purchaseTrackingAndPopularItems(){
        Vendor v1 = new Vendor(1, 2);
        v1.select("Candy");
        v1.select("Oreo");

        String[] expected = {"Candy", "Gum"};
        assertEquals(Arrays.toString(expected), Arrays.toString(v1.topKPurchasedItems(2)));
        assertEquals(Arrays.toString(expected), Arrays.toString(v1.topKPurchasedItems(3)));
        v1.removeItem("Candy");
        assertEquals(Arrays.toString(expected), Arrays.toString(v1.topKPurchasedItems(2)));
    }

    //Test for user being able to track 5 vendors and print their stock (User Story 4)
    @Test
    void print5VendorsValid(){
        Vendor v1 = new Vendor(1, 2);
        Vendor v2 = new Vendor(2, 2);
        Vendor v3 = new Vendor(1, 1);
        Vendor v4 = new Vendor(2, 1);
        Vendor v5 = new Vendor(1, 3);
        Vendors v = new Vendors();
        v.addVendors("Store 1", v1);
        v.addVendors("Store 2", v2);
        v.addVendors("Store 3", v3);
        v.addVendors("Store 4", v4);
        v.addVendors("Store 5", v5);

        String actual = v.print5Vendors("Store 1", "Store 2", "Store 4", "Store 3", "Store 5");
        String expected1 = "Vendor Name: Store 1\nItem Name: Candy\nQuantity in stock: 1\nCost (USD): 1.25\n\n" +
            "Item Name: Gum\nQuantity in stock: 2\nCost (USD): 0.50\n\n";
        String expected2 = "Vendor Name: Store 2\nItem Name: Candy\nQuantity in stock: 2\nCost (USD): 1.25\n\n" +
                "Item Name: Gum\nQuantity in stock: 2\nCost (USD): 0.50\n\n";
        String expected3 = "Vendor Name: Store 3\nItem Name: Candy\nQuantity in stock: 1\nCost (USD): 1.25\n\n" +
                "Item Name: Gum\nQuantity in stock: 1\nCost (USD): 0.50\n\n";
        String expected4 = "Vendor Name: Store 4\nItem Name: Candy\nQuantity in stock: 2\nCost (USD): 1.25\n\n" +
                "Item Name: Gum\nQuantity in stock: 1\nCost (USD): 0.50\n\n";
        String expected5 = "Vendor Name: Store 5\nItem Name: Candy\nQuantity in stock: 1\nCost (USD): 1.25\n\n" +
                "Item Name: Gum\nQuantity in stock: 3\nCost (USD): 0.50\n\n";

        String expected = expected1 + expected2 + expected4 + expected3 + expected5;
        assertEquals(expected, actual);
    }

    //Negative test for printing 5 vendors when one of the vendors is not in the system
    @Test
    void print5VendorsInvalid(){
        Vendor v1 = new Vendor(1, 2);
        Vendor v2 = new Vendor(2, 2);
        Vendor v3 = new Vendor(1, 1);
        Vendor v4 = new Vendor(2, 1);
        Vendor v5 = new Vendor(1, 3);
        Vendors v = new Vendors();
        v.addVendors("Store 1", v1);
        v.addVendors("Store 2", v2);
        v.addVendors("Store 3", v3);
        v.addVendors("Store 4", v4);
        v.addVendors("Store 5", v5);

        String actual = v.print5Vendors("Store 1", "Store Banana", "Store 4", "Store 3", "Store 5");
        String expected = "Invalid vendor list";
        assertEquals(expected, actual);
    }

    //Test for setting an item to be on discount (User Story 8)
    @Test
    void discountItems(){
        Vendor v1 = new Vendor(2, 2);
        v1.putItemOnSale("Candy", 1.00);
        v1.addMoney(10);
        v1.select("Candy");
        assertEquals(9, v1.getBalance());
        v1.endItemSale("Candy");
        v1.select("Candy");
        assertEquals(7.75, v1.getBalance());
    }

    //Test that the system updates items as bestsellers based on their purchases
    @Test
    void checkBestSellers(){
        Vendor v1 = new Vendor(5, 2);
        assertFalse(v1.getStock().get("Candy").bestseller);
        v1.addMoney(100);
        v1.select("Candy");
        v1.select("Candy");
        v1.select("Candy");
        assertTrue(v1.getStock().get("Candy").bestseller);
    }

    // Test that a vendor system dynamically changes inventory over time (User Story 2)
    @Test
    void addNewItems(){
        Vendor v1 = new Vendor(5, 2);
        v1.addMoney(10);
        v1.select("Cookies");
        v1.select("Soup");
        v1.select("Mozzarella");
        assertEquals(10, v1.getBalance());
        v1.restockEntireVendor();
        v1.select("Soup");
        v1.select("Cookies");
        assertEquals(4, v1.getBalance());
    }

    //Test that the user can see an item description and its benefits before purchasing.
    @Test
    void getItemDescription(){
        Item butter = new Item(4, 10);
        butter.setBenefits("Makes food significantly more tasty. Is very good on bread");
        butter.setDetails("4 Half Sticks of butter with 65% butterfat");

        String expected = "Item price: 4.00\nItem benefits: Makes food significantly more tasty. Is very good on bread\nItem details: 4 Half Sticks of butter with 65% butterfat";
        assertEquals(expected, butter.getItemDescription());
    }

    @Test
    void overloadConstructor(){
        Item[] items = {
                new Item(4, 6),
                new Item(3, 5),
                new Item(1, 8),
                new Item(5, 1),
                new Item(6, 3),
                new Item(9, 4),

        };
        String[] names = {
                "Coke",
                "Sprite",
                "Fanta",
                "Pop",
                "Pork",
                "Bacon"
        };
        Vendor v1 = new Vendor(names, items);
        v1.addMoney(100);
        v1.select("Coke");
        assertEquals(96, v1.getBalance());
    }
}
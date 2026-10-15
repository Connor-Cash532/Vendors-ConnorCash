import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.*;


/**
 * Class for a Vendor Machine.  Contains a hashtable mapping item names to item data, as
 * well as the current balance of money that has been deposited into the machine.
 */
class Vendor {
    private HashMap<String, Item> stock = new HashMap<String,Item>();
    private HashMap<String, Item> bestsellers = new HashMap<String,Item>();

    private HashMap<String, Integer> purchases = new HashMap<>();
    private double balance;
    private HashMap<String, Integer> maxStocks = new HashMap<>();
    private ArrayList<String> unknownItems = new ArrayList<>();

    Vendor(int numCandy, int numGum) {
        stock.put("Candy", new Item(1.25, numCandy));
        stock.put("Gum", new Item(.5, numGum));
        maxStocks.put("Candy", numCandy);
        maxStocks.put("Gum", numGum);
        purchases.put("Candy", 0);
        purchases.put("Gum", 0);
        this.balance = 0;
    }

    Vendor(String[] names, Item[] items) {
        for(int i = 0; i < names.length; i++){
            stock.put(names[i], items[i]);
        }
        this.balance = 0;
    }

    /** resets the Balance to 0 */
    void resetBalance () {
        this.balance = 0;
    }

    /** returns the current balance */
    double getBalance () {
        return this.balance;
    }

    /** adds money to the machine's balance
     * @param amt how much money to add
     * */
    void addMoney (double amt) {
        if(amt > 0)
            this.balance = this.balance + amt;
    }

    /** Message returned if vending machine has no items in stock.
     * attempt to purchase named item.  Message is returned if selected item is out of stock.
     * Message returned if the balance isn't sufficient to cover the item cost.
     * Message is returned if selected item is not in the stock of the vending machine.
     * @param name The name of the item to purchase ("Candy" or "Gum")
     */
    public String select (String name) {
        int stockSum = 0;
        for(Item i : stock.values()){
            stockSum += i.stock;
        }
        if(stockSum == 0){
            return "Vendor machine is empty";
        }
        if (stock.containsKey(name)) {
            Item item = stock.get(name);
            if (item.stock <= 0) {
                restockItem(name);
                return "Item is out of stock";
            }
            else if (balance >= item.price) {
                item.purchase(1);
                purchases.replace(name, purchases.get(name) + 1);
                if(item.onSale){
                    this.balance = this.balance - item.discountedPrice;
                }
                else{
                    this.balance = this.balance - item.price;
                }
                findBestsellers();
                return name + " purchased";
            }
            else
                return "Gimme more money";

        }
        unknownItems.add(name);
        return "Sorry, don't know that item";

    }

    /**
     * Method that sets all items in the vending map back to their maximum stocks
     */
    void restockEntireVendor(){
        for(String name : unknownItems){
            if(VALID_ITEMS.containsKey(name)){
                stock.put(name, VALID_ITEMS.get(name));
                purchases.put(name, 0);
                maxStocks.put(name, VALID_ITEMS.get(name).stock);
            }
        }
        for(String s : stock.keySet()){
            Item i  = stock.get(s);
            i.restock(maxStocks.get(s));
        }
    }

    /**
     * Method that sets an item that was selected an out of stock back to its maximum stock
     * @param name
     */
    void restockItem(String name){
        Item i = stock.get(name);
        i.restock(maxStocks.get(name));
    }

    /**
     * Method to change the name of an Item in the vending map.
     * @param oldName
     * @param newName
     */
    void changeName(String oldName, String newName){
        if(stock.containsKey(oldName)){
            Item i = stock.get(oldName);
            stock.put(newName, i);
            purchases.put(newName, 0);
            maxStocks.put(newName, maxStocks.get(oldName));
            stock.remove(oldName);
        }
    }

    public void removeItem(String name){
        stock.remove(name);
    }

    /**
     *
     * @param k
     * @return k most frequently purchased items
     */
    public String[] topKPurchasedItems(int k){
        String[] keys = purchases.keySet().toArray(new String[0]);

        if(k >= purchases.size())
            return keys;
        String[] kFrequent = new String[k];
        List<Integer> frequencies = new ArrayList<>(purchases.values());
        int[] kFrequencies = new int[k];
        frequencies.sort(Collections.reverseOrder());
        for(int i = 0; i < k; i++){
            kFrequencies[i] = frequencies.get(i);
        }

        int count = 0;
        for(int i = 0; i < keys.length && count < k; i++){
            if(purchases.get(keys[i]) == kFrequencies[count]){
                kFrequent[count] = keys[i];
                count++;
            }
        }
        Arrays.sort(kFrequent);
        return kFrequent;
    }

    /**
     * Marks items in the stock as bestsellers if they are greater than 2 times purchase frequency of the median purchased
     * item in the stock.
     */
    public void findBestsellers(){
        List<Integer> frequencies = new ArrayList<>(purchases.values());
        frequencies.sort(Collections.reverseOrder());
        int median;
        if(frequencies.size() % 2 != 0)
            median = frequencies.get( frequencies.size() / 2);
        else
            median = (frequencies.get( frequencies.size() / 2) + frequencies.get( (frequencies.size() / 2) - 1 )) / 2;

        Item tempItem;
        int tempPurchases;
        for(String n : stock.keySet()){
            tempPurchases = purchases.get(n);
            if(tempPurchases > median * 2){
                tempItem = stock.get(n);
                tempItem.bestseller = true;
                stock.replace(n, tempItem);
            }
            else{
                tempItem = stock.get(n);
                tempItem.bestseller = false;
                stock.replace(n, tempItem);
            }
        }

        HashMap<String, Item> temp = new HashMap<>();
        for(String n : stock.keySet()){
            if(stock.get(n).bestseller)
                temp.put(n, stock.get(n));
        }
        bestsellers = temp;
    }

    /**
     * Getter method for the stock
     * @return
     */
    public HashMap<String, Item> getStock() {
        return stock;
    }

    /**
     *
     * @return string with Items the vendor has, their stock, and their cost.
     */
    @Override
    public String toString(){
        String s = "";
        DecimalFormat df = new DecimalFormat("##0.00");
        String tempCost;
        Item temp;
        for(String i : stock.keySet()){
            temp = stock.get(i);
            tempCost = df.format(temp.price);
            s += "Item Name: " + i + "\n";
            s += "Quantity in stock: " + temp.stock + "\n";
            s += "Cost (USD): " + tempCost + "\n\n";
        }
        return s;
    }

    /**
     * Changes a item to be in the vending hashmap to be on sale
     * @param itemName
     * @param discountPrice
     */
    public void putItemOnSale(String itemName, double discountPrice){
        if(stock.containsKey(itemName) && discountPrice >= 0){
            Item i = stock.get(itemName);
            i.setDiscountedPrice(discountPrice);
            stock.replace(itemName, i);
        }
    }

    /**
     * Changes a item to no longer be on sale if it was on sale
     * @param itemName
     */
    public void endItemSale(String itemName){
        if(stock.containsKey(itemName)){
            Item i = stock.get(itemName);
            i.endDiscountPrice();
            stock.replace(itemName, i);
        }
    }

    //Items that can be added to the store if unavailable during selection
    final static HashMap<String, Item> VALID_ITEMS = validItems();
    private static HashMap<String, Item> validItems(){
        HashMap<String, Item> temp = new HashMap<>();
        temp.put("Cookies", new Item(2, (int) (100 * Math.random()) ));
        temp.put("Shampoo", new Item(6, (int) (100 * Math.random()) ));
        temp.put("Chips", new Item(1, (int) (100 * Math.random()) ));
        temp.put("Candy", new Item(1.75, (int) (100 * Math.random()) ));
        temp.put("Gum", new Item(0.5, (int) (100 * Math.random()) ));
        temp.put("Turkey", new Item(10, (int) (100 * Math.random()) ));
        temp.put("Chicken", new Item(5, (int) (100 * Math.random()) ));
        temp.put("Ham", new Item(10, (int) (100 * Math.random()) ));
        temp.put("Ice Cream", new Item(4, (int) (100 * Math.random()) ));
        temp.put("Apple Pie", new Item(5, (int) (100 * Math.random()) ));
        temp.put("Pasta", new Item(1.99, (int) (100 * Math.random()) ));
        temp.put("Noodles", new Item(3, (int) (100 * Math.random()) ));
        temp.put("Biscuits", new Item(2, (int) (100 * Math.random()) ));
        temp.put("Cake", new Item(4, (int) (100 * Math.random()) ));
        temp.put("Gumbo", new Item(5, (int) (100 * Math.random()) ));
        temp.put("Steak", new Item(20, (int) (100 * Math.random()) ));
        temp.put("Beef", new Item(9, (int) (100 * Math.random()) ));
        temp.put("Soup", new Item(4, (int) (100 * Math.random()) ));
        temp.put("Bread", new Item(3.50, (int) (100 * Math.random()) ));
        temp.put("Eggs", new Item(2.75, (int) (100 * Math.random()) ));
        temp.put("Milk", new Item(3.15, (int) (100 * Math.random()) ));
        temp.put("Celery", new Item(2.15, (int) (100 * Math.random()) ));
        temp.put("Garlic", new Item(1.15, (int) (100 * Math.random()) ));
        temp.put("Bell Pepper", new Item(2.15, (int) (100 * Math.random()) ));
        temp.put("Carrots", new Item(1.55, (int) (100 * Math.random()) ));
        temp.put("Potatoes", new Item(1, (int) (100 * Math.random()) ));
        return temp;
    }
}

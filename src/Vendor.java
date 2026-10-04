import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.*;


/**
 * Class for a Vending Machine.  Contains a hashtable mapping item names to item data, as
 * well as the current balance of money that has been deposited into the machine.
 */
class Vending {
    private HashMap<String, Item> Stock = new HashMap<String,Item>();
    private HashMap<String, Integer> purchases = new HashMap<>();
    private double balance;
    private HashMap<String, Integer> maxStocks = new HashMap<>();
    private HashMap<String, Item> newItems = new HashMap<>();

    Vending(int numCandy, int numGum) {
        Stock.put("Candy", new Item(1.25, numCandy));
        Stock.put("Gum", new Item(.5, numGum));
        maxStocks.put("Candy", numCandy);
        maxStocks.put("Gum", numGum);
        purchases.put("Candy", 0);
        purchases.put("Gum", 0);
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
    void select (String name) {
        int stockSum = 0;
        for(Item i : Stock.values()){
            stockSum += i.stock;
        }
        if(stockSum == 0){
            System.out.println("Vending machine is empty");
            return;
        }
        if (Stock.containsKey(name)) {
            Item item = Stock.get(name);
            if (item.stock <= 0) {
                System.out.println("Item is out of stock");
                restockItem(name);
            }
            else if (balance >= item.price) {
                item.purchase(1);
                purchases.replace(name, purchases.get(name) + 1);
                this.balance = this.balance - item.price;
            }
            else
                System.out.println("Gimme more money");
        }
        else {
            //Item i = new Item(, )
            System.out.println("Sorry, don't know that item");
        }
    }

    /**
     * Method that sets all items in the vending map back to their maximum stocks
     */
    void restockEntireVendor(){
        for(String s : Stock.keySet()){
            Item i  = Stock.get(s);
            i.restock(maxStocks.get(s));
        }
    }

    /**
     * Method that sets an item that was selected an out of stock back to its maximum stock
     * @param name
     */
    void restockItem(String name){
        Item i = Stock.get(name);
        i.restock(maxStocks.get(name));
    }

    /**
     * Method to change the name of an Item in the vending map.
     * @param oldName
     * @param newName
     */
    void changeName(String oldName, String newName){
        if(Stock.containsKey(oldName)){
            Item i = Stock.get(oldName);
            Stock.put(newName, i);
            Stock.remove(oldName);
        }
    }

    public void removeItem(String name){
        Stock.remove(name);
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

    @Override
    public String toString(){
        String s = "";
        DecimalFormat df = new DecimalFormat("##0.00");
        String tempCost;
        Item temp;
        for(String i : Stock.keySet()){
            temp = Stock.get(i);
            tempCost = df.format(temp.price);
            s += "Item Name: " + i + "\n";
            s += "Quantity in stock: " + temp.stock + "\n";
            s += "Cost (USD): " + tempCost + "\n\n";
        }
        return s;
    }
}

class Examples {
}

class Vendors{
    private HashMap<String, Vending> allVendors = new HashMap<>();

    public void addVendors(String vendorName, Vending v){
        allVendors.put(vendorName, v);
    }

    @Override
    public String toString(){
        String s = "";
        Vending temp;
        List<String> list = new ArrayList<>(allVendors.keySet());
        Collections.sort(list);
        for(String i : list){
            temp = allVendors.get(i);
            s += "Vendor Name: " + i + "\n";
            s += temp.toString();
        }
        return s;
    }
}

import java.util.HashMap;


/**
 * Class for a Vending Machine.  Contains a hashtable mapping item names to item data, as
 * well as the current balance of money that has been deposited into the machine.
 */
class Vending {
    private static HashMap<String, Item> Stock = new HashMap<String,Item>();
    private double balance;

    Vending(int numCandy, int numGum) {
        Stock.put("Candy", new Item(1.25, numCandy));
        Stock.put("Gum", new Item(.5, numGum));
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
            if (item.stock <= 0)
                System.out.println("Item is out of stock");
            else if (balance >= item.price) {
                item.purchase(1);
                this.balance = this.balance - item.price;
            }
            else
                System.out.println("Gimme more money");
        }
        else System.out.println("Sorry, don't know that item");
    }

}

class Examples {
}


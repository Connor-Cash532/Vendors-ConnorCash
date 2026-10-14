import java.util.Scanner;

class Item {
    double price;
    int stock;
    double discountedPrice;
    boolean onSale;
    String details;
    String benefits;
    boolean bestseller;
    Item(double price, int numPieces) {
        this.price = price;
        discountedPrice = price;
        onSale = false;
        this.stock = numPieces;
        this.details = "";
        this.benefits = "";
        bestseller = false;
    }

    void restock(int amount) {
        if(amount > 0)
            this.stock = this.stock + amount;
    }

    void purchase(int amount) {
        if(amount > 0 && this.stock - amount >= 0)
            this.stock = this.stock - amount;
    }

    public void setDiscountedPrice(double discount){
        discountedPrice = discount;
        onSale = true;
    }

    public void endDiscountPrice(){
        discountedPrice = price;
        onSale = false;
    }

    public String toString(){
        String s = "Item price: " + price;
        s += "Item benefits: " + benefits;
        s += "Item details: " + details;

        return s;
    }
}
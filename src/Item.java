import java.text.DecimalFormat;
import java.util.Scanner;

class Item {
    double price;
    int stock;
    double discountedPrice;
    boolean onSale;
    private String details;
    private String benefits;
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

    public void purchase(int amount) {
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

    public void setOnSale(boolean onSale) {
        this.onSale = onSale;
    }

    public double getDiscountedPrice() {
        return discountedPrice;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getItemDescription(){
        DecimalFormat df = new DecimalFormat("##0.00");

        String s = "Item price: " + df.format(price);
        s += "\nItem benefits: " + benefits;
        s += "\nItem details: " + details;

        return s;
    }
}
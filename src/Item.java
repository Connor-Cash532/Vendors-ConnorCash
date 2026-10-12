import java.util.Scanner;

class Item {
    double price;
    int stock;
    String details;
    String benefits;

    Item(double price, int numPieces) {
        this.price = price;
        this.stock = numPieces;
        this.details = "";
        this.benefits = "";
    }

    void restock(int amount) {
        if(amount > 0)
            this.stock = this.stock + amount;
    }

    void purchase(int amount) {
        if(amount > 0 && this.stock - amount >= 0)
            this.stock = this.stock - amount;
    }
}
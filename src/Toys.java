package src;

public class Toys {
    private int price;
    private String description;  // optional

    public Toys(int price, String description) {
        this.price = price;
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}
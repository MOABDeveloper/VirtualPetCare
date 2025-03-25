package src;

public class Gifts {
    private String type;      // e.g. "Hat", "Shirt"
    private String model;     // e.g. "Holiday Edition", "Minimalist"
    private int price;

    public Gifts(String type, String model, int price) {
        this.type = type;
        this.model = model;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public String getModel() {
        return model;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return type + " - " + model;
    }
}
package src;

public class Food {
    private String name;        // ADD THIS
    private int price;
    private int fullness;
    private String description;

    public Food(String name, int price, int fullness, String description) {
        this.name = name;
        this.price = price;
        this.fullness = fullness;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getFullness() {
        return fullness;
    }

    public String getDescription() {
        return description;
    }
}

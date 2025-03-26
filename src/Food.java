package src;

public class Food {
    private String name;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Food other = (Food) obj;
        return name.equals(other.name); // compare by name
    }

    @Override
    public int hashCode() {
        return name.hashCode(); // ensure it hashes consistently
    }

}

package src;

public class Toys {
    private String name;
    private int price;
    private String description;  // optional

    public Toys(String name, int price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Toys other = (Toys) obj;
        return name.equals(other.name); // assuming name is unique
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}
package group19;

public class Gifts {
    private String name;
    private int price;

    public Gifts(String name, int price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Gifts other = (Gifts) obj;
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


}
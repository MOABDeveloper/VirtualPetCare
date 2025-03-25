package src;

import java.util.HashMap;
import java.util.Map;

public class Store {
    private final Map<String, Food> foodMap;
    private final Map<String, Gifts> giftsMap;
    private final Map<String, Toys> toysMap;

    public Store() {

        //Load in Default food items
        foodMap = new HashMap<>();
        foodMap.put("Apple", new Food("Apple", 100, 8, "Fresh red apple"));
        foodMap.put("Burger", new Food("Burger", 300, 12, "Grilled beef burger"));
        foodMap.put("Salad", new Food("Salad", 300, 11, "Green salad"));
        foodMap.put("Steak", new Food("Steak", 600, 55, "Sizzling and seared to perfection"));
        foodMap.put("Chicken", new Food("Chicken", 450, 35, "Tender and golden"));
        foodMap.put("Shrimp", new Food("Shrimp", 450, 40, "Plump little ocean gems"));
        foodMap.put("Salmon", new Food("Salmon", 550, 50, "Fancy but friendly"));

        //Load in default gifts
        giftsMap = new HashMap<>();
        giftsMap.put("Festive Hat", new Gifts("Hat", "Holiday Sparkle Edition", 1299));
        giftsMap.put("Retro Shirt", new Gifts("Shirt", "Throwback Vibes", 1999));
        giftsMap.put("Comfy Pants", new Gifts("Pants", "Winter Lounge Fit", 2499));
        giftsMap.put("Trail Shoes", new Gifts("Shoes", "Outdoor Explorer", 4599));
        giftsMap.put("Party Shirt", new Gifts("Shirt", "Birthday Bash Special", 1799));
        //add gifts


        //Load in Default toy items
        toysMap = new HashMap<>();
        toysMap.put("Bouncy Ball", new Toys(199, "Perfect for endless tail-chasing fun"));
        toysMap.put("Flying Frisbee", new Toys(299, "Catch it mid-air — doggo approved"));
        toysMap.put("Tug Rope", new Toys(349, "Strong enough for serious tug-of-war sessions"));
        toysMap.put("Squeaky Bone", new Toys(249, "Annoyingly squeaky, irresistibly fun"));
        toysMap.put("Catnip Mouse", new Toys(199, "Sneaky, squeaky, and stuffed with catnip"));

    }

    public Food getFood(String name) {
        return foodMap.get(name);
    }

    public boolean hasFood(String name) {
        return foodMap.containsKey(name);
    }

    public Gifts getGift(String name) {
        return giftsMap.get(name);
    }

    public boolean hasGift(String name) {
        return giftsMap.containsKey(name);
    }

    public Toys getToy(String name) {
        return toysMap.get(name);
    }

    public boolean hasToys(String name) {
        return toysMap.containsKey(name);
    }
}

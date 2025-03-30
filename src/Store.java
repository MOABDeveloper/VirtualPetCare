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
        foodMap.put("Orange", new Food("Orange", 50, 5, "A tangy, sweet taste!"));
        foodMap.put("Bunny Cookie", new Food("Bunny Cookie", 60, 6, "Jumps around in your mouth!"));
        foodMap.put("Swiss Roll", new Food("Swiss Roll", 200, 20, "Sweet swirls of joy"));
        foodMap.put("Carrot Cake", new Food("Carrot Cake", 350, 35, "With uncomfortably orange carrot icing"));
        foodMap.put("Lamb Chop", new Food("Lamb Chop", 1100, 100, "The fanciest bite in town!"));
        foodMap.put("Chicken", new Food("Chicken",  650,65, "A complete feast for your soul"));

        //Load in Default toy items
        toysMap = new HashMap<>();
        toysMap.put("Wand", new Toys("Wand",199, "For aspiring magicians!"));
        toysMap.put("Stuffed Animal", new Toys("Stuffed Animal",299, "Slightly lumpy from all that love!"));
        toysMap.put("Unicorn Balloon", new Toys("Unicorn Balloon",349, "Hold on tight so it doesn't fly away!"));
        toysMap.put("Fan", new Toys("Fan",249, "Who knew wind could be this fun?"));
        toysMap.put("Basketball", new Toys("Basketball",199, "Perfect for indoor slam dunks"));
        toysMap.put("Guitar", new Toys("Guitar",299,"Perfect  for shredding... literally!"));

        //Load in default gifts
        giftsMap = new HashMap<>();
        giftsMap.put("outfit1", new Gifts("outfit1", 1000));
        giftsMap.put("outfit2", new Gifts("outfit2", 1500));
        giftsMap.put("outfit3", new Gifts("outfit3", 3000));

    }

    public Map<String, Food> getAllFood() {
        return foodMap;
    }

    public Map<String, Toys> getAllToys() {
        return toysMap;
    }

    public Map<String, Gifts> getAllGifts() {
        return giftsMap;
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

    public boolean buyFood(String name, PlayerInventory inventory, int quantity) {
        // if the player has entered a valid food name
        if (hasFood(name)) {
            Food food = getFood(name);
            int cost = food.getPrice();

            // if the player has enough coins
            if (inventory.getPlayerCoins() >= cost) {
                // Decrease the coins and increase the qty by the amount
                inventory.setPlayerCoins(inventory.getPlayerCoins() - cost);
                inventory.addFood(food, quantity);

                return true;
            }
        }
        return false;
    }

    public boolean buyToy(String name, PlayerInventory inventory, int quantity) {
        // if the player has entered a valid toy name
        if (hasToys(name)) {
            Toys toy = getToy(name);
            int cost = toy.getPrice();

            // if the player has enough coins
            if (inventory.getPlayerCoins() >= cost) {
                // Decrease the coins and increase the qty by the amount
                inventory.setPlayerCoins(inventory.getPlayerCoins() - cost);
                inventory.addToy(toy, quantity);

                return true;
            }
        }
        return false;
    }

    public boolean buyGift(String name, PlayerInventory inventory, int quantity) {
        // if the player has entered a valid gift name
        if (hasGift(name)) {
            Gifts gift = getGift(name);
            int cost = gift.getPrice();

            // if the player has enough coins
            if (inventory.getPlayerCoins() >= cost) {
                // Decrease the coins and increase the qty by the amount
                inventory.setPlayerCoins(inventory.getPlayerCoins() - cost);
                inventory.addGift(gift, quantity);

                return true;
            }
        }
        return false;
    }

}














package src;
import java.util.HashMap;
import java.util.Map;


public class PlayerInventory {

    private int playerCoins;
    private boolean hasHat;
    private boolean hasShirt;
    private boolean hasPants;
    private boolean hasShoes;

    private final Map<Food, Integer> foodInventory = new HashMap<>();
    private final Map<Gifts, Integer> giftInventory = new HashMap<>();
    private final Map<Toys, Integer> toyInventory = new HashMap<>();

    public int getPlayerCoins() {
        return playerCoins;
    }

    public void setPlayerCoins(int playerCoins) {
        this.playerCoins = playerCoins;
    }

    // === INVENTORY ADD METHODS ===
    public void addFood(Food food, int quantity) {
        foodInventory.put(food, foodInventory.getOrDefault(food, 0) + quantity);
    }

    public void addGift(Gifts gift, int quantity) {
        giftInventory.put(gift, giftInventory.getOrDefault(gift, 0) + quantity);
    }

    public void addToy(Toys toy, int quantity) {
        toyInventory.put(toy, toyInventory.getOrDefault(toy, 0) + quantity);
    }

    //INVENTORY COUNT GETTERS
    public int getFoodCount(Food food) {
        return foodInventory.getOrDefault(food, 0);
    }

    public int getGiftCount(Gifts gift) {
        return giftInventory.getOrDefault(gift, 0);
    }

    public int getToyCount(Toys toy) {
        return toyInventory.getOrDefault(toy, 0);
    }

    //USE ITEMS
    public boolean consumeFood(Food food) {
        int count = getFoodCount(food);
        if (count > 0) {
            foodInventory.put(food, count - 1);
            return true;
        }
        return false;
    }

    public boolean giveGift(Gifts gift) {
        int count = getGiftCount(gift);
        if (count > 0) {
            giftInventory.put(gift, count - 1);
            return true;
        }
        return false;
    }

    public boolean hasToy(Toys toy) {
        return getToyCount(toy) > 0;
    }


}
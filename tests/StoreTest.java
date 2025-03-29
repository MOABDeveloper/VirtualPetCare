package tests;
import src.Store;
import src.Toys;
import src.Food;
import src.Gifts;
import src.PlayerInventory;

public class StoreTest {

    public static void main(String[] args) {
        Store store = new Store();
        PlayerInventory inventory = new PlayerInventory(store); // Player starts with 1000 coins
        inventory.setPlayerCoins(1000);

        // Test 1: Check retrieval methods
        System.out.println("Test 1: Retrieving All Items");
        System.out.println("Expected Food Size: 7, Actual: " + store.getAllFood().size());
        System.out.println("Expected Toys Size: 5, Actual: " + store.getAllToys().size());
        System.out.println("Expected Gifts Size: 3, Actual: " + store.getAllGifts().size());

        // Test 2: Check individual item retrieval
        System.out.println("\nTest 2: Get Specific Items");
        System.out.println("Expected: Apple, Actual: " + store.getFood("Apple").getName());
        System.out.println("Expected: Squeaky Bone, Actual: " + store.getToy("Squeaky Bone").getName());
        System.out.println("Expected: outfit2, Actual: " + store.getGift("outfit2").getName());

        // Test 3: Check existence methods
        System.out.println("\nTest 3: Item Existence Check");
        System.out.println("Expected: true, Actual: " + store.hasFood("Burger"));
        System.out.println("Expected: true, Actual: " + store.hasToys("Catnip Mouse"));
        System.out.println("Expected: true, Actual: " + store.hasGift("outfit3"));
        System.out.println("Expected: false, Actual: " + store.hasFood("Pizza"));

        // Test 4: Buying food
        System.out.println("\nTest 4: Buying Food");
        boolean boughtFood = store.buyFood("Salad", inventory, 1);
        System.out.println("Expected: true, Actual: " + boughtFood);
        System.out.println("Expected Coins <= 1000, Actual Coins: " + inventory.getPlayerCoins());

        // Test 5: Buying toy with enough coins
        System.out.println("\nTest 5: Buying Toy");
        boolean boughtToy = store.buyToy("Bouncy Ball", inventory, 1);
        System.out.println("Expected: true, Actual: " + boughtToy);
        System.out.println("Expected Coins <= prev, Actual Coins: " + inventory.getPlayerCoins());

        // Test 6: Buying gift with enough coins
        System.out.println("\nTest 6: Buying Gift");
        boolean boughtGift = store.buyGift("outfit1", inventory, 1);
        System.out.println("Expected: false, Actual: " + boughtGift);
        System.out.println("Expected Coins <= prev, Actual Coins: " + inventory.getPlayerCoins());

        // Test 7: Buying item with insufficient coins
        System.out.println("\nTest 7: Buying Expensive Gift (Expect Failure)");
        boolean boughtExpensiveGift = store.buyGift("outfit3", inventory, 1); // Should fail
        System.out.println("Expected: false, Actual: " + boughtExpensiveGift);

        // Test 8: Buying item that doesn't exist
        System.out.println("\nTest 8: Buying Invalid Item");
        boolean boughtInvalid = store.buyFood("Pizza", inventory, 1);
        System.out.println("Expected: false, Actual: " + boughtInvalid);
    }


    //JUnit tests 
    // errors due to not implementing maven yet
package src;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;
import java.util.HashMap;


class DummyInventory implements PlayerInventory {
    private int playerCoins;
    private final Map<String, Integer> foodInventory = new HashMap<>();
    private final Map<String, Integer> toyInventory = new HashMap<>();
    private final Map<String, Integer> giftInventory = new HashMap<>();

    public DummyInventory(int initialCoins) {
        this.playerCoins = initialCoins;
    }

    @Override
    public int getPlayerCoins() {
        return playerCoins;
    }

    @Override
    public void setPlayerCoins(int coins) {
        this.playerCoins = coins;
    }

    @Override
    public void addFood(Food food, int quantity) {
        foodInventory.put(food.getName(), foodInventory.getOrDefault(food.getName(), 0) + quantity);
    }

    @Override
    public void addToy(Toys toy, int quantity) {
        toyInventory.put(toy.getName(), toyInventory.getOrDefault(toy.getName(), 0) + quantity);
    }

    @Override
    public void addGift(Gifts gift, int quantity) {
        giftInventory.put(gift.getName(), giftInventory.getOrDefault(gift.getName(), 0) + quantity);
    }
    
    public int getFoodQuantity(String name) {
        return foodInventory.getOrDefault(name, 0);
    }
    
    public int getToyQuantity(String name) {
        return toyInventory.getOrDefault(name, 0);
    }
    
    public int getGiftQuantity(String name) {
        return giftInventory.getOrDefault(name, 0);
    }
}


public class StoreTest {

    @Test
    public void testStoreLoadsDefaultItems() {
        Store store = new Store();
        Map<String, Food> foodMap = store.getAllFood();
        Map<String, Toys> toysMap = store.getAllToys();
        Map<String, Gifts> giftsMap = store.getAllGifts();

        // Check expected sizes 
        assertEquals(8, foodMap.size(), "Store should have 8 food items");
        assertEquals(5, toysMap.size(), "Store should have 5 toy items");
        assertEquals(3, giftsMap.size(), "Store should have 3 gift items");
    }

    @Test
    public void testGetAndHasMethods() {
        Store store = new Store();
        
        // Food
        assertTrue(store.hasFood("Apple"), "Store should have 'Apple'");
        assertNotNull(store.getFood("Apple"), "'Apple' should be retrievable");
        assertFalse(store.hasFood("Nonexistent"), "Store should not have a food named 'Nonexistent'");
        
        // Toys
        assertTrue(store.hasToys("Bouncy Ball"), "Store should have 'Bouncy Ball'");
        assertNotNull(store.getToy("Bouncy Ball"), "'Bouncy Ball' should be retrievable");
        assertFalse(store.hasToys("NonexistentToy"), "Store should not have a toy named 'NonexistentToy'");
        
        // Gifts
        assertTrue(store.hasGift("outfit1"), "Store should have 'outfit1'");
        assertNotNull(store.getGift("outfit1"), "'outfit1' should be retrievable");
        assertFalse(store.hasGift("NonexistentGift"), "Store should not have a gift named 'NonexistentGift'");
    }

    @Test
    public void testBuyFoodSuccess() {
        Store store = new Store();
        DummyInventory inventory = new DummyInventory(100);
        
        boolean purchaseResult = store.buyFood("Apple", inventory, 2);
        assertTrue(purchaseResult, "Purchase of 'Apple' should succeed with sufficient coins");
        assertEquals(50, inventory.getPlayerCoins(), "Player coins should be reduced by 50");
        assertEquals(2, inventory.getFoodQuantity("Apple"), "Inventory should reflect 2 'Apple' items added");
    }

    @Test
    public void testBuyFoodInsufficientCoins() {
        Store store = new Store();
        DummyInventory inventory = new DummyInventory(30);  // Less than cost of "Apple"
        
        boolean purchaseResult = store.buyFood("Apple", inventory, 1);
        assertFalse(purchaseResult, "Purchase should fail with insufficient coins");
        assertEquals(30, inventory.getPlayerCoins(), "Player coins should remain unchanged");
        assertEquals(0, inventory.getFoodQuantity("Apple"), "No 'Apple' should be added to inventory");
    }

    @Test
    public void testBuyToySuccess() {
        Store store = new Store();
        DummyInventory inventory = new DummyInventory(300);
        
        boolean purchaseResult = store.buyToy("Bouncy Ball", inventory, 1);
        assertTrue(purchaseResult, "Purchase of 'Bouncy Ball' should succeed with sufficient coins");
        assertEquals(101, inventory.getPlayerCoins(), "Player coins should be reduced by 199");
        assertEquals(1, inventory.getToyQuantity("Bouncy Ball"), "Inventory should reflect 1 'Bouncy Ball'");
    }

    @Test
    public void testBuyToyInsufficientCoins() {
        Store store = new Store();
        DummyInventory inventory = new DummyInventory(100);  // Less than cost of "Bouncy Ball"
        
        boolean purchaseResult = store.buyToy("Bouncy Ball", inventory, 1);
        assertFalse(purchaseResult, "Purchase should fail with insufficient coins");
        assertEquals(100, inventory.getPlayerCoins(), "Player coins should remain unchanged");
        assertEquals(0, inventory.getToyQuantity("Bouncy Ball"), "No toy should be added to inventory");
    }

    @Test
    public void testBuyGiftSuccess() {
        Store store = new Store();
        DummyInventory inventory = new DummyInventory(2000);
        
        boolean purchaseResult = store.buyGift("outfit1", inventory, 1);
        assertTrue(purchaseResult, "Purchase of 'outfit1' should succeed with sufficient coins");
        assertEquals(1000, inventory.getPlayerCoins(), "Player coins should be reduced by 1000");
        assertEquals(1, inventory.getGiftQuantity("outfit1"), "Inventory should reflect 1 'outfit1'");
    }

    @Test
    public void testBuyGiftInsufficientCoins() {
        Store store = new Store();
        DummyInventory inventory = new DummyInventory(500);  // Less than cost of "outfit1"
        
        boolean purchaseResult = store.buyGift("outfit1", inventory, 1);
        assertFalse(purchaseResult, "Purchase should fail with insufficient coins");
        assertEquals(500, inventory.getPlayerCoins(), "Player coins should remain unchanged");
        assertEquals(0, inventory.getGiftQuantity("outfit1"), "No gift should be added to inventory");
    }

    @Test
    public void testBuyInvalidItem() {
        Store store = new Store();
        DummyInventory inventory = new DummyInventory(1000);
        
        assertFalse(store.buyFood("Nonexistent", inventory, 1), "Buying a nonexistent food should fail");
        assertFalse(store.buyToy("NonexistentToy", inventory, 1), "Buying a nonexistent toy should fail");
        assertFalse(store.buyGift("NonexistentGift", inventory, 1), "Buying a nonexistent gift should fail");
    }
}

}

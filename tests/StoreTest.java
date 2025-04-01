package tests;
import src.Store;
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
}

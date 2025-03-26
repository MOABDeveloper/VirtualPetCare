package tests;

import src.*;

public class GameDataTest {
    public static void main(String[] args) {
        // Step 1: Create a Pet and Inventory
        Pet pet = new Pet("Fluffy", 100, 100, 100, 100);
        PlayerInventory inventory = new PlayerInventory();
        inventory.setPlayerCoins(1500);

        // Create sample food, toy, and gift
        Food apple = new Food("Apple", 100, 8, "Fresh red apple");
        Toys frisbee = new Toys("Flying Frisbee", 299, "Catch it mid-air — doggo approved");
        Gifts outfitGift = new Gifts("outfit1", 1000);

        // Add them to inventory
        inventory.addFood(apple, 3);
        inventory.addToy(frisbee, 2);
        inventory.addGift(outfitGift, 1);
        inventory.addOutfit("outfit1");

        // Equip the pet with the outfit
        pet.setOutfit("outfit1");

        // Load previously saved play time if available
        GameData existing = GameDataManager.loadGame();
        long previousPlayTime = (existing != null) ? existing.getTotalPlayTime() : 0;

        // Simulate some play time (e.g. 2 seconds)
        try {
            System.out.println("🕒 Simulating play session...");
            Thread.sleep(2000); // 2 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Save the game with accumulated time
        GameDataManager.saveGame(pet, inventory, previousPlayTime);

        // Load the game again to verify totalPlayTime
        GameData loaded = GameDataManager.loadGame();
        if (loaded == null) {
            System.out.println("❌ Failed to load game.");
            return;
        }

        // Step 2: Verify loaded data
        Pet loadedPet = loaded.getPet();
        PlayerInventory loadedInventory = loaded.getInventory();

        System.out.println("\n✅ Loaded Pet Info:");
        System.out.println("Name: " + loadedPet.getName());
        System.out.println("Health: " + loadedPet.getHealth());
        System.out.println("Happiness: " + loadedPet.getHappiness());
        System.out.println("Outfit: " + loadedPet.getCurrentOutfit());

        System.out.println("\n✅ Loaded Inventory Info:");
        System.out.println("Coins: " + loadedInventory.getPlayerCoins());
        System.out.println("Apple Count: " + loadedInventory.getFoodCount(apple));
        System.out.println("Frisbee Count: " + loadedInventory.getToyCount(frisbee));
        System.out.println("Gift Count: " + loadedInventory.getGiftCount(outfitGift));
        System.out.println("Owns outfit1? " + loadedInventory.ownsOutfit("outfit1"));

        System.out.println("\n🕓 Total Play Time: " + (loaded.getTotalPlayTime() / 1000) + " seconds");
    }
}

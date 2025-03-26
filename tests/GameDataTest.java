package tests;

import src.*;

public class GameDataTest {
    public static void main(String[] args) {
        // Simulate previous play time
        long previousPlayTime = 8000;

        // Create pet with known, non-max stats
        Pet pet = new Pet("Fluffy", 100, 100, 100, 100);
        pet.decreaseHealth(20);     // health = 80
        pet.decreaseHappiness(30);  // happiness = 70
        pet.decreaseSleep(40);      // sleep = 60
        pet.decreaseFullness(10);   // fullness = 90
        pet.setOutfit("outfit1");

        // Create inventory
        PlayerInventory inventory = new PlayerInventory();
        inventory.setPlayerCoins(2000);

        // Add one item to each category
        Food apple = new Food("Apple", 100, 8, "Fresh red apple");
        Toys rope = new Toys("Tug Rope", 349, "Strong enough for serious tug-of-war sessions");
        Gifts gift = new Gifts("outfit1", 1000);

        inventory.addFood(apple, 1);
        inventory.addToy(rope, 1);
        inventory.addGift(gift, 1);
        inventory.addOutfit("outfit1");

        // Save to file
        String saveFile = "saves/test_pet_save.json";
        GameDataManager.saveGame(saveFile, pet, inventory, previousPlayTime);

        // Load from file
        GameData loaded = GameDataManager.loadGame(saveFile);
        if (loaded == null) {
            System.out.println("❌ Load failed.");
            return;
        }

        Pet loadedPet = loaded.getPet();
        PlayerInventory loadedInventory = loaded.getInventory();

        // Output checks
        System.out.println("\n✅ Loaded Pet Info:");
        System.out.println("Name: " + loadedPet.getName());
        System.out.println("Health: " + loadedPet.getHealth());           // should be 80
        System.out.println("Happiness: " + loadedPet.getHappiness());     // should be 70
        System.out.println("Sleep: " + loadedPet.getSleepiness());        // should be 60
        System.out.println("Fullness: " + loadedPet.getFullness());       // should be 90
        System.out.println("Outfit: " + loadedPet.getCurrentOutfit());    // outfit1

        System.out.println("\n✅ Loaded Inventory:");
        System.out.println("Coins: " + loadedInventory.getPlayerCoins());                 // 2000
        System.out.println("Apple count: " + loadedInventory.getFoodCount(apple));        // 1
        System.out.println("Rope count: " + loadedInventory.getToyCount(rope));           // 1
        System.out.println("Gift count: " + loadedInventory.getGiftCount(gift));          // 1
        System.out.println("Owns outfit1? " + loadedInventory.ownsOutfit("outfit1"));     // true

        System.out.println("\n🕒 Total Play Time: " + (loaded.getTotalPlayTime() / 1000) + " seconds");
    }
}

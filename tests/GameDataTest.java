package tests;

import src.*;

public class GameDataTest {
    public static void main(String[] args) {
        long previousPlayTime = 8000;

        // === Create a new pet and assign type ===
        Pet pet = new Pet(
                "Shadow", 100, 100, 100, 100,   // health, sleep, fullness, happiness
                100, 100, 100, 100,             // maxHealth, maxSleep, maxFullness, maxHappiness
                5, 5, 5, 5,                     // healthDeclineRate, fullnessDeclineRate, sleepDeclineRate, happinessDeclineRate
                false, false, true, false,      // isSleeping, isHungry, isHappy, isDead
                0, 30, 0, 20,                   // lastVetVisitTime, vetCooldownDuration, lastPlayTime, playCooldownDuration
                "default_outfit"                // currentOutfit
        );
        pet.setPetType("Dragon"); // Available options: Dragon, Dog, Turtle (based on your Pet constructor)
        System.out.println("🧬 Pet type set to: " + pet.getPetType());

        // Lower pet stats to simulate game state
        pet.decreaseHealth(20);     // Health = 80
        pet.decreaseHappiness(30);  // Happiness = 70
        pet.decreaseSleep(40);      // Sleep = 60
        pet.decreaseFullness(10);   // Fullness = 90
        pet.setOutfit("outfit1");

        // === Inventory Setup ===
        PlayerInventory inventory = new PlayerInventory();
        inventory.setPlayerCoins(2000);

        Food apple = new Food("Apple", 100, 8, "Fresh red apple");
        Toys rope = new Toys("Tug Rope", 349, "Strong enough for serious tug-of-war sessions");
        Gifts gift = new Gifts("outfit1", 1000);

        inventory.addFood(apple, 1);
        inventory.addToy(rope, 1);
        inventory.addGift(gift, 1);
        inventory.addOutfit("outfit1");

        // === Save the game ===
        String saveFile = "saves/test_pet_save.json";
        GameDataManager.saveGame(saveFile, pet, inventory, previousPlayTime);

        // === Load the game ===
        GameData loaded = GameDataManager.loadGame(saveFile);
        if (loaded == null) {
            System.out.println("❌ Load failed.");
            return;
        }

        Pet loadedPet = loaded.getPet();
        PlayerInventory loadedInventory = loaded.getInventory();

        // === Output results ===
        System.out.println("\n✅ Loaded Pet Info:");
        System.out.println("Name: " + loadedPet.getName());
        System.out.println("Pet Type: " + loadedPet.getPetType());
        System.out.println("Health: " + loadedPet.getHealth());
        System.out.println("Happiness: " + loadedPet.getHappiness());
        System.out.println("Sleep: " + loadedPet.getSleepiness());
        System.out.println("Fullness: " + loadedPet.getFullness());
        System.out.println("Outfit: " + loadedPet.getCurrentOutfit());

        System.out.println("\n✅ Loaded Inventory:");
        System.out.println("Coins: " + loadedInventory.getPlayerCoins());
        System.out.println("Apple count: " + loadedInventory.getFoodCount(apple));
        System.out.println("Rope count: " + loadedInventory.getToyCount(rope));
        System.out.println("Gift count: " + loadedInventory.getGiftCount(gift));
        System.out.println("Owns outfit1? " + loadedInventory.ownsOutfit("outfit1"));

        System.out.println("\n🕒 Total Play Time: " + (loaded.getTotalPlayTime() / 1000) + " seconds");
    }
}

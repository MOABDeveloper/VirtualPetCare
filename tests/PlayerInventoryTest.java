package tests;

import src.*;

public class PlayerInventoryTest {
    public static void main(String[] args) {
        // Set up pet and inventory
        Pet pet = new Pet(
                "Shadow", "Petoption1", 100, 100, 100, 100,   // health, sleep, fullness, happiness
                100, 100, 100, 100,             // maxHealth, maxSleep, maxFullness, maxHappiness
                5, 5, 5, 5,                     // healthDeclineRate, fullnessDeclineRate, sleepDeclineRate, happinessDeclineRate
                false, false, true, false,      // isSleeping, isHungry, isHappy, isDead
                0, 30, 0, 20,                   // lastVetVisitTime, vetCooldownDuration, lastPlayTime, playCooldownDuration
                "default_outfit"                // currentOutfit
        );
        pet.setPetType("PetOption1"); // ensures decay rates are initialized
        pet.setVetCooldownDuration(5); // seconds
        pet.setPlayCooldownDuration(5); // seconds

        Store store = new Store();  // Create store instance
        PlayerInventory  inventory = new PlayerInventory(store); // Pass store to inventory

        //PlayerInventory  = new PlayerInventory();
        inventory.setPlayerCoins(1000);

        // Add items to inventory
        Food apple = new Food("Apple", 100, 10, "A juicy apple");
        inventory.addFood(apple, 3);

        Gifts gift = new Gifts("outfit1", 500);
        inventory.addGift(gift, 2);

        Toys toy = new Toys("Flying Frisbee", 299, "Zoom!");
        inventory.addToy(toy, 1);

        // Assign initial stat values for demonstration
        pet.decreaseSleep(30);
        pet.decreaseFullness(50);
        pet.decreaseHappiness(40);
        pet.decreaseHealth(25);
        pet.printStats();

        System.out.println("\n--- Action a: Go to bed ---");
        inventory.putPetToBed(pet);
        pet.printStats();

        System.out.println("\n--- Action b: Feed Pet ---");
        boolean fed = inventory.feedPet(pet, apple);
        System.out.println("Fed? " + fed);
        pet.printStats();

        System.out.println("\n--- Action c: Give Gift ---");
        boolean gifted = inventory.giveGiftToPet(pet, gift);
        System.out.println("Gifted? " + gifted);
        pet.printStats();

        System.out.println("\n--- Action d: Take to Vet ---");
        int currentTime = 10;
        pet.setLastVetVisitTime(0); // simulate last visit at time 0
        boolean vetSuccess = inventory.takePetToVet(pet, currentTime);
        System.out.println("Vet success? " + vetSuccess);
        pet.printStats();

        System.out.println("\n--- Action e: Play ---");
        pet.setLastPlayTime(0);
        boolean playSuccess = inventory.playWithPet(pet, currentTime);
        System.out.println("Played? " + playSuccess);
        pet.printStats();

        System.out.println("\n--- Action f: Exercise ---");
        inventory.exercisePet(pet);
        pet.printStats();
    }
}

package src;

import java.util.HashMap;
import java.util.Map;

public class PlayerInventory {

    private int playerCoins;

    private final Map<Food, Integer> foodInventory = new HashMap<>();
    private final Map<Gifts, Integer> giftInventory = new HashMap<>();
    private final Map<Toys, Integer> toyInventory = new HashMap<>();
    private final Map<String, Boolean> outfitInventory = new HashMap<>();

    public PlayerInventory(Store store) {
        this.playerCoins = 5000; // Set default coins to 500


        // Retrieve predefined items from Store
        Food orange = store.getFood("Orange");
        Toys wand = store.getToy("Wand");

        // Ensure these items exist before adding them
        if (orange != null) {
            addFood(orange, 5);
        } else {
            System.err.println("Error: Orange not found in store.");
        }

        if (wand != null) {
            addToy(wand, 1);
        } else {
            System.err.println("Error: Bouncy Ball not found in store.");
        }
    }


    public int getPlayerCoins() {
        return playerCoins;
    }

    public void setPlayerCoins(int playerCoins) {
        this.playerCoins = playerCoins;
    }

    public void addFood(Food food, int quantity) {
        foodInventory.put(food, foodInventory.getOrDefault(food, 0) + quantity);
    }

    public int getFoodCount(Food food) {
        return foodInventory.getOrDefault(food, 0);
    }

    public void addGift(Gifts gift, int quantity) {
        giftInventory.put(gift, giftInventory.getOrDefault(gift, 0) + quantity);
    }

    public boolean giveGift(Gifts gift) {
        int count = getGiftCount(gift);
        if (count > 0) {
            giftInventory.put(gift, count - 1);
            return true;
        }
        return false;
    }

    public void addToy(Toys toy, int quantity) {
        toyInventory.put(toy, toyInventory.getOrDefault(toy, 0) + quantity);
    }

    public int getGiftCount(Gifts gift) {
        return giftInventory.getOrDefault(gift, 0);
    }

    public int getToyCount(Toys toy) {
        return toyInventory.getOrDefault(toy, 0);
    }

    public boolean consumeFood(Food food) {
        int count = getFoodCount(food);
        if (count > 0) {
            foodInventory.put(food, count - 1);
            return true;
        }
        return false;
    }

    public boolean hasToy(Toys toy) {
        return getToyCount(toy) > 0;
    }
    public boolean hasGift(Gifts gift) {
        return getGiftCount(gift) > 0;
    }

    public boolean equipOutfit(String outfitName, Pet pet) {
        if (!ownsOutfit(outfitName)) {
            System.out.println("Player does not own outfit: " + outfitName);
            return false; // Can't equip if player doesn't own it
        }

        // If the pet is already wearing an outfit, unequip it first
        if (pet.isWearingOutfit()) {
            unequipOutfit(pet);
        }

        // Equip the new outfit
        pet.setOutfit(outfitName);
        outfitInventory.put(outfitName, false); // Mark as equipped
        System.out.println("Equipped outfit: " + outfitName);
        return true;
    }



    public void addOutfit(String outfitName) {
        // If the player doesn't own the outfit, mark it as owned
        if (!outfitInventory.containsKey(outfitName)) {
            outfitInventory.put(outfitName, true);  // "true" means the outfit is owned
            System.out.println("Outfit added: " + outfitName);
        } else {
            System.out.println("Player already owns " + outfitName);
        }
    }

    public void unequipOutfit(Pet pet) {
        String currentOutfit = pet.getCurrentOutfit();
        if (currentOutfit != null && !currentOutfit.isEmpty()) {
            pet.setOutfit(null); // This removes the outfit
            outfitInventory.put(currentOutfit, true); // Mark it as owned again
            System.out.println("Unequipped: " + currentOutfit);
        } else {
            System.out.println("No outfit to unequip.");
        }
    }

    public boolean ownsOutfit(String outfitName) {
        return outfitInventory.getOrDefault(outfitName, false);
    }



    // Go to bed — only increases sleep if not already max
    public void putPetToBed(Pet pet) {
        if (!pet.isDead() && pet.getSleepiness() < 100) {
            pet.increaseSleep(pet.getSleepDeclineRate()); // or fixed rate
        }
    }

    // feed — consumes food from inventory
    public boolean feedPet(Pet pet, Food food) {
        if (consumeFood(food)) {
            pet.increaseFullness(food.getFullness());
            // Give the player back 25% of the food price back when feeding.
            int returnOnFeeding = (int) (food.getPrice() * 0.25);
            setPlayerCoins(getPlayerCoins()+ returnOnFeeding);
            return true;
        }
        return false;
    }

    // Give Gift — consumes gift from inventory
    public boolean giveGiftToPet(Pet pet, Gifts gift) {
        if (giveGift(gift)) {
            pet.increaseHappiness(10); // or scale with gift
            return true;
        }
        return false;
    }

    // Take to vet — apply health boost if cooldown allows
    public boolean takePetToVet(Pet pet, int currentTime) {
        if (currentTime - pet.getLastVetVisitTime() >= pet.getVetCooldownDuration()) {
            pet.increaseHealth(30); // or tunable
            pet.setLastVetVisitTime(currentTime);
            //Give 500 when taking to the vet
            setPlayerCoins(getPlayerCoins()+500);
            return true;
        }
        return false;
    }

    // Play toys — apply happiness if cooldown allows
    public boolean playWithPet(Pet pet, int currentTime) {
        if (currentTime - pet.getLastPlayTime() >= pet.getPlayCooldownDuration()) {
            pet.increaseHappiness(15);
            pet.setLastPlayTime(currentTime);
            //Give 750 coins when playing with the pet.
            setPlayerCoins(getPlayerCoins()+750);
            return true;
        }
        return false;
    }

    // f) Exercise — trade sleep and fullness for health
    public void exercisePet(Pet pet) {
        pet.decreaseSleep(10);
        pet.decreaseFullness(10);
        pet.increaseHealth(15);
        //Give 500 when exercising
        setPlayerCoins(getPlayerCoins()+500);
    }


    public Map<Food, Integer> getFoodInventory() {
        return foodInventory;
    }

    public Map<Toys, Integer> getToyInventory() {
        return toyInventory;
    }

    public Map<Gifts, Integer> getGiftInventory() {
        return giftInventory;
    }

    public Map<String, Boolean> getOutfitInventory() {
        return outfitInventory;
    }

}

package src;

import java.util.HashMap;
import java.util.Map;

public class PlayerInventory {

    private int playerCoins;

    private final Map<Food, Integer> foodInventory = new HashMap<>();
    private final Map<Gifts, Integer> giftInventory = new HashMap<>();
    private final Map<Toys, Integer> toyInventory = new HashMap<>();
    private final Map<String, Boolean> outfitInventory = new HashMap<>();

    public PlayerInventory() {
        this.playerCoins = 0;
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

    public boolean equipOutfit(String outfitName, Pet pet) {
        // If player doesn't own this outfit, they can't equip it
        if (!ownsOutfit(outfitName)) {
            return false;
        }

        // If pet is already wearing something, return it to inventory
        if (pet.isWearingOutfit()) {
            outfitInventory.put(pet.getCurrentOutfit(), true); // returned to player
        }

        // Equip the new outfit
        pet.setOutfit(outfitName);
        outfitInventory.put(outfitName, false); // now "in use" by pet

        return true;
    }

    public void addOutfit(String outfitName) {
        outfitInventory.put(outfitName, true);
    }

    public boolean ownsOutfit(String outfitName) {
        return outfitInventory.getOrDefault(outfitName, false);
    }

    // a) Go to bed — only increases sleep if not already max
    public void putPetToBed(Pet pet) {
        if (!pet.isDead() && pet.getSleepiness() < 100) {
            pet.increaseSleep(pet.getSleepDeclineRate()); // or fixed rate
        }
    }

    // b) Feed — consumes food from inventory
    public boolean feedPet(Pet pet, Food food) {
        if (consumeFood(food)) {
            pet.increaseFullness(food.getFullness());
            return true;
        }
        return false;
    }

    // c) Give Gift — consumes gift from inventory
    public boolean giveGiftToPet(Pet pet, Gifts gift) {
        if (giveGift(gift)) {
            pet.increaseHappiness(10); // or scale with gift
            return true;
        }
        return false;
    }

    // d) Take to vet — apply health boost if cooldown allows
    public boolean takePetToVet(Pet pet, int currentTime) {
        if (currentTime - pet.getLastVetVisitTime() >= pet.getVetCooldownDuration()) {
            pet.increaseHealth(30); // or tunable
            pet.setLastVetVisitTime(currentTime);
            return true;
        }
        return false;
    }

    // e) Play — apply happiness if cooldown allows
    public boolean playWithPet(Pet pet, int currentTime) {
        if (currentTime - pet.getLastPlayTime() >= pet.getPlayCooldownDuration()) {
            pet.increaseHappiness(15);
            pet.setLastPlayTime(currentTime);
            return true;
        }
        return false;
    }

    // f) Exercise — trade sleep and fullness for health
    public void exercisePet(Pet pet) {
        pet.decreaseSleep(10);
        pet.decreaseFullness(10);
        pet.increaseHealth(15);
    }


}

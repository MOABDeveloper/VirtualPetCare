package group19;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class Pet {
    // Pet name
    @SerializedName("name")
    private String name;
    private final Map<String, PetType> petTypeMap;
    @SerializedName("petType")
    private String petType;

    // Current stats
    @SerializedName("health")
    private int health;
    @SerializedName("Sleep")
    private int sleep;
    @SerializedName("fullness")
    private int fullness;
    @SerializedName("happiness")
    private int happiness;

    // Max values for each stat
    @SerializedName("maxHealth")
    private final int maxHealth;
    @SerializedName("maxSleep")
    private final int maxSleep;
    @SerializedName("maxFullness")
    private final int maxFullness;
    @SerializedName("maxHappiness")
    private final int maxHappiness;

    // Decline rates for each stat
    @SerializedName("healthDeclineRate")
    private int healthDeclineRate;
    @SerializedName("fullnessDeclineRate")

    private int fullnessDeclineRate;
    @SerializedName("sleepDeclineRate")

    private int sleepDeclineRate;
    @SerializedName("happinessDeclineRate")

    private int happinessDeclineRate;

    // State flags
    @SerializedName("isSleeping")

    private boolean isSleeping;
    @SerializedName("isHungry")

    private boolean isHungry;
    @SerializedName("isHappy")

    private boolean isHappy;
    @SerializedName("isDead")

    private boolean isDead;

    // Cooldowns
    @SerializedName("lastVetVisitTime")

    private int lastVetVisitTime;
    @SerializedName("vetCooldownDuration")

    private int vetCooldownDuration;
    @SerializedName("lastPlayTime")

    private int lastPlayTime;
    @SerializedName("playCooldownDuration")

    private int playCooldownDuration;

    // Current outfit name; null means no outfit equipped
    @SerializedName("currentOutfit")

    private String currentOutfit;

//
//    public Pet(String name, int maxHealth, int maxSleep, int maxFullness, int maxHappiness) {
//        this.name = name;
//
//        //SET THE THREE SELECTABLE PET TYPES AND THEIR CHARACTERISTICS
//        this.petTypeMap = new HashMap<>();
//        petTypeMap.put("PetOption1", new PetType(1.2F,.5F,.6F,1.3F));
//        petTypeMap.put("PetOption2",  new PetType(2F,2F,2F,2F));
//        petTypeMap.put("PetOption3",  new PetType(1.2F,.5F,.6F,1.3F));
//
//        this.maxHealth = maxHealth;
//        this.maxSleep = maxSleep;
//        this.maxFullness = maxFullness;
//        this.maxHappiness = maxHappiness;
//
//        this.health = maxHealth;
//        this.sleep = maxSleep;
//        this.fullness = maxFullness;
//        this.happiness = maxHappiness;
//
//    }
    // Ensure Gson can access final fields
    public Pet(String name, String petType, int health, int sleep, int fullness, int happiness,
               int maxHealth, int maxSleep, int maxFullness, int maxHappiness,
               int healthDeclineRate, int fullnessDeclineRate, int sleepDeclineRate, int happinessDeclineRate,
               boolean isSleeping, boolean isHungry, boolean isHappy, boolean isDead,
               int lastVetVisitTime, int vetCooldownDuration, int lastPlayTime, int playCooldownDuration,
               String currentOutfit) {
        this.name = name;
        this.petType = petType;

        //SET THE THREE SELECTABLE PET TYPES AND THEIR CHARACTERISTICS
        this.petTypeMap = new HashMap<>();
        petTypeMap.put("PetOption1", new PetType(1.2F,.5F,.6F,1.3F));
        petTypeMap.put("PetOption2",  new PetType(2F,2F,2F,2F));
        petTypeMap.put("PetOption3",  new PetType(1.2F,.5F,.6F,1.3F));


        this.name = name;
        this.health = health;
        this.sleep = sleep;
        this.fullness = fullness;
        this.happiness = happiness;
        this.maxHealth = maxHealth;
        this.maxSleep = maxSleep;
        this.maxFullness = maxFullness;
        this.maxHappiness = maxHappiness;
        this.healthDeclineRate = healthDeclineRate;
        this.fullnessDeclineRate = fullnessDeclineRate;
        this.sleepDeclineRate = sleepDeclineRate;
        this.happinessDeclineRate = happinessDeclineRate;
        this.isSleeping = isSleeping;
        this.isHungry = isHungry;
        this.isHappy = isHappy;
        this.isDead = isDead;
        this.lastVetVisitTime = lastVetVisitTime;
        this.vetCooldownDuration = vetCooldownDuration;
        this.lastPlayTime = lastPlayTime;
        this.playCooldownDuration = playCooldownDuration;
        this.currentOutfit = currentOutfit;
        setPetType(petType);  // triggers rate update
    }


    public int getLastVetVisitTime() {
        return lastVetVisitTime;
    }

    public void setLastVetVisitTime(int lastVetVisitTime) {
        this.lastVetVisitTime = lastVetVisitTime;
    }

    public int getVetCooldownDuration() {
        return vetCooldownDuration;
    }

    public void setVetCooldownDuration(int vetCooldownDuration) {
        this.vetCooldownDuration = vetCooldownDuration;
    }

    public int getLastPlayTime() {
        return lastPlayTime;
    }

    public void setLastPlayTime(int lastPlayTime) {
        this.lastPlayTime = lastPlayTime;
    }

    public int getPlayCooldownDuration() {
        return playCooldownDuration;
    }

    public void setPlayCooldownDuration(int playCooldownDuration) {
        this.playCooldownDuration = playCooldownDuration;
    }

    public String getName() {
        return name;
    }

    public int increaseHappiness(int amount) {
        happiness = Math.min(happiness + amount, maxHappiness);
        return happiness;
    }

    public int decreaseHappiness(int amount) {
        happiness = Math.max(happiness - amount, 0);
        return happiness;
    }

    public int increaseFullness(int amount) {
        fullness = Math.min(fullness + amount, maxFullness);
        return fullness;
    }

    public int decreaseFullness(int amount) {
        fullness = Math.max(fullness - amount, 0);
        return fullness;
    }

    public int increaseHealth(int amount) {
        health = Math.min(health + amount, maxHealth);
        return health;
    }

    public int decreaseHealth(int amount) {
        health = Math.max(health - amount, 0);
        return health;
    }

    public int increaseSleep(int amount) {
        sleep = Math.min(sleep + amount, maxSleep);
        return sleep;
    }

    public int decreaseSleep(int amount) {
        sleep = Math.max(sleep - amount, 0);
        return sleep;
    }

    public int getHappiness() {
        return this.happiness;
    }

    public int getFullness() {
        return this.fullness;
    }

    public int getHealth() {
        return this.health;
    }

    public int getSleepiness() {
        return this.sleep;
    }

    public Map<String, PetType> getPetTypeMap() {
        return petTypeMap;
    }

    public int getSleep() {
        return sleep;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getMaxSleep() {
        return maxSleep;
    }

    public int getMaxFullness() {
        return maxFullness;
    }

    public int getMaxHappiness() {
        return maxHappiness;
    }

    public boolean isHappy() {
        return isHappy;
    }

    public void updateRatesBasedOfType() {
        PetType type = petTypeMap.get(this.petType);
        if (type == null) return;

        // Set default base decline rates (these can be constants or tuned)
        int baseHealth = 5;
        int baseFullness = 5;
        int baseSleep = 5;
        int baseHappiness = 5;

        // Apply multipliers from the selected PetType
        this.healthDeclineRate = Math.round(baseHealth * type.getHealthDeclineMultiplier());
        this.fullnessDeclineRate = Math.round(baseFullness * type.getFullnessDeclineMultiplier());
        this.sleepDeclineRate = Math.round(baseSleep * type.getSleepDeclineMultiplier());
        this.happinessDeclineRate = Math.round(baseHappiness * type.getHappinessDeclineMultiplier());
    }

    public String getPetType() {
        return petType;
    }

    public boolean isSleeping() {
        return isSleeping;
    }

    public boolean isHungry() {
        return isHungry;
    }

    public boolean isAngry() {
        return !isHappy && !isDead;
    }

    public boolean isDead() {
        if(getHealth() <= 0) {
            isDead = true;
        }
        else{
            isDead = false;
        }
        return isDead;
    }

    public void setDead(boolean isDead) {
        this.isDead = isDead;
    }

    public void setSleeping(boolean isSleeping) {
        this.isSleeping = isSleeping;
    }

    public void setHungry(boolean isHungry) {
        this.isHungry = isHungry;
    }

    public void setHealthDeclineRate(int rate) {
        this.healthDeclineRate = rate;
    }

    public void setFullnessDeclineRate(int rate) {
        this.fullnessDeclineRate = rate;
    }

    public void setSleepDeclineRate(int rate) {
        this.sleepDeclineRate = rate;
    }

    public void setHappinessDeclineRate(int rate) {
        this.happinessDeclineRate = rate;
    }

    public int getHealthDeclineRate() {
        return healthDeclineRate;
    }

    public int getFullnessDeclineRate() {
        return fullnessDeclineRate;
    }

    public int getSleepDeclineRate() {
        return sleepDeclineRate;
    }

    public int getHappinessDeclineRate() {
        return happinessDeclineRate;
    }

    public void setPetType(String petType) {
        if (petTypeMap.containsKey(petType)) {
            this.petType = petType;
            updateRatesBasedOfType();
        } else {
            System.out.println("❌ Invalid pet type: " + petType);
        }
    }

    public void applyDecline() {
        if (isDead) return;

        // 1. Passive stat decline (except health)
        if (!isSleeping) {
            decreaseFullness(fullnessDeclineRate);
            decreaseSleep(sleepDeclineRate);
            decreaseHappiness(fullness <= 0 ? happinessDeclineRate * 2 : happinessDeclineRate);
        } else {
            // Regenerate sleep while sleeping
            increaseSleep(sleepDeclineRate);
        }

        // 2. Check Hunger (Fullness == 0)
        if (fullness <= 0) {
            isHungry = true;
            decreaseHealth(healthDeclineRate); // gradual health loss
        } else {
            isHungry = false;
        }

        // 3. Check Happiness (Angry state)
        isHappy = happiness > 0;

        // 4. Sleep logic
        if (sleep <= 0) {
            decreaseHealth(healthDeclineRate); // penalty for exhaustion
            sleep = 0;
            isSleeping = true;
        } else if (sleep >= maxSleep) {
            isSleeping = false; // wakes up when rested
        }

        // 5. Check Death
        if (health <= 0) {
            health = 0;
            isDead = true;
        }
    }

    public boolean isWearingOutfit() {
        return currentOutfit != null;
    }

    public boolean isWarningHealth() {
        return health <= (maxHealth / 4);
    }

    public boolean isWarningSleep() {
        return sleep <= (maxSleep / 4);
    }

    public boolean isWarningFullness() {
        return fullness <= (maxFullness / 4);
    }

    public boolean isWarningHappiness() {
        return happiness <= (maxHappiness / 4);
    }

    public String getCurrentOutfit() {
        return currentOutfit;
    }

    public void setOutfit(String outfitName) {
        this.currentOutfit = outfitName;
    }

    public void resetState() {
            this.isDead = false;
            this.isSleeping = false;
            this.isHungry = false;
            this.isHappy = true;

            this.health = maxHealth;
            this.sleep = maxSleep;
            this.fullness = maxFullness;
            this.happiness = maxHappiness;
    }

    public void printStats() {
        System.out.println(name + " - Health: " + health + "/" + maxHealth +
                ", Sleep: " + sleep + "/" + maxSleep +
                ", Fullness: " + fullness + "/" + maxFullness +
                ", Happiness: " + happiness + "/" + maxHappiness);
    }
}

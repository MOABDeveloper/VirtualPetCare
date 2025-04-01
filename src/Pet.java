package src;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;


/**
 * The InGameScreen class represents the main gameplay screen in the virtual pet game.
 * It allows the player to interact with their pet through feeding, playing, exercising,
 * gifting outfits, putting the pet to sleep, and visiting the vet.
 *
 * This screen also shows live updates of the pet's stats through progress bars for
 * health, sleep, fullness, and happiness. These stats gradually decline over time
 * using a built-in timer, adding a real-time care element to the game.
 * Other features include:
 * - A coin display to track the player's in-game currency
 * - A working store button for in-game purchases
 * - Popup menus for selecting food and toys from the inventory
 * - Keyboard shortcut support
 * - Sound effects tied to pet actions
 * - Parental control tracking for play session duration
 *
 * @author Kamaldeep Ghotra
 * @author Mohammed Abdulnabi
 */
public class Pet {
    /* The name given to the pet by the player */
    @SerializedName("name")
    private String name;

    /* Map that holds different types of pets and their declinerate multipliers */
    private final Map<String, PetType> petTypeMap;

    /* Type of the pet */
    @SerializedName("petType")
    private String petType;

    /* Current stats */
    @SerializedName("health")
    private int health;
    @SerializedName("Sleep")
    private int sleep;
    @SerializedName("fullness")
    private int fullness;
    @SerializedName("happiness")
    private int happiness;

    /* Max values for each stat */
    @SerializedName("maxHealth")
    private final int maxHealth;
    @SerializedName("maxSleep")
    private final int maxSleep;
    @SerializedName("maxFullness")
    private final int maxFullness;
    @SerializedName("maxHappiness")
    private final int maxHappiness;

    /* Decline rates for each stat */
    @SerializedName("healthDeclineRate")
    private int healthDeclineRate;
    @SerializedName("fullnessDeclineRate")
    private int fullnessDeclineRate;
    @SerializedName("sleepDeclineRate")
    private int sleepDeclineRate;
    @SerializedName("happinessDeclineRate")
    private int happinessDeclineRate;

    /* State Flags */
    @SerializedName("isSleeping")
    private boolean isSleeping;
    @SerializedName("isHungry")
    private boolean isHungry;
    @SerializedName("isHappy")
    private boolean isHappy;
    @SerializedName("isDead")
    private boolean isDead;


    /* Vet Cooldown */
    @SerializedName("lastVetVisitTime")
    private int lastVetVisitTime;  // Last time the pet visited the vet
    @SerializedName("vetCooldownDuration")
    private int vetCooldownDuration;

    /* Play Cooldown */
    @SerializedName("lastPlayTime")  // The last time the pet played
    private int lastPlayTime;
    @SerializedName("playCooldownDuration")
    private int playCooldownDuration;

    /* Name of the outfit that the pet is wearing (currently) */
    @SerializedName("currentOutfit")
    private String currentOutfit;

    /* Used to track how often applyDecline() is called */
    private static int declineCounter = 0;

    /* Stores which outfits are allowed per pet type */
    private static final Map<String, String> allowedOutfits = new HashMap<>();

    /* Static block to initialize the corresponding outfits to the pet */
    static {
        allowedOutfits.put("PetOption1", "outfit1");
        allowedOutfits.put("PetOption2", "outfit2");
        allowedOutfits.put("PetOption3", "outfit3");
    }


    /**
     * Constructs a new Pet with its initial values and behavior types.
     *
     * This constructor initializes all the pet's stats, states, cooldowns,
     * and also sets up the predefined pet type behavior multipliers.
     * It calls {@code setPetType(petType)} at the end to update decline rates accordingly.
     *
     * @param name  The name of the pet.
     * @param petType  The selected pet type (e.g., PetOption1, PetOption2, PetOption3)
     * @param health  The current health of the pet
     * @param sleep  The current sleep value
     * @param fullness  The current fullness (hunger) value
     * @param happiness  The current happiness value
     * @param maxHealth  The max possible health value
     * @param maxSleep  The max possible sleep value
     * @param maxFullness  The max possible fullness value
     * @param maxHappiness  The max possible happiness value
     * @param healthDeclineRate  How quickly health should drop
     * @param fullnessDeclineRate  How quickly fullness should drop
     * @param sleepDeclineRate  How quickly sleep should drop
     * @param happinessDeclineRate  How quickly happiness should drop
     * @param isSleeping  Whether the pet starts in a sleeping state
     * @param isHungry  Whether the pet starts hungry
     * @param isHappy  Whether the pet starts happy
     * @param isDead  Whether the pet is dead
     * @param lastVetVisitTime  Time of last vet visit
     * @param vetCooldownDuration  Time required between vet visits
     * @param lastPlayTime  Time of last play session
     * @param playCooldownDuration  Time required between play sessions
     * @param currentOutfit  Name of the outfit currently worn (null if none)
     */
    public Pet(String name, String petType, int health, int sleep, int fullness, int happiness,
               int maxHealth, int maxSleep, int maxFullness, int maxHappiness,
               int healthDeclineRate, int fullnessDeclineRate, int sleepDeclineRate, int happinessDeclineRate,
               boolean isSleeping, boolean isHungry, boolean isHappy, boolean isDead,
               int lastVetVisitTime, int vetCooldownDuration, int lastPlayTime, int playCooldownDuration,
               String currentOutfit) {

        /* Store the pet's name and type */
        this.name = name;
        this.petType = petType;

        /*  Initialize map that links pet types with decline rate multipliers */
        this.petTypeMap = new HashMap<>();

        /* Decline behaviour for each pet */
        petTypeMap.put("PetOption1", new PetType(.5F,.9F,.5F,.5F));
        petTypeMap.put("PetOption2",  new PetType(.6F,.4F,.6F,.9F));
        petTypeMap.put("PetOption3",  new PetType(.5F,.5F,.9F,.6F));

        /* Initialize stat values */
        this.name = name;
        this.health = health;
        this.sleep = sleep;
        this.fullness = fullness;
        this.happiness = happiness;

        /* Initialize max stat values */
        this.maxHealth = maxHealth;
        this.maxSleep = maxSleep;
        this.maxFullness = maxFullness;
        this.maxHappiness = maxHappiness;

        /* Initialize decline rate for each stat */
        this.healthDeclineRate = healthDeclineRate;
        this.fullnessDeclineRate = fullnessDeclineRate;
        this.sleepDeclineRate = sleepDeclineRate;
        this.happinessDeclineRate = happinessDeclineRate;

        /* Initialize pets starting state */
        this.isSleeping = isSleeping;
        this.isHungry = isHungry;
        this.isHappy = isHappy;
        this.isDead = isDead;

        /* Initialize vet cooldown values */
        this.lastVetVisitTime = lastVetVisitTime;  // The last time the pet visited the vet
        this.vetCooldownDuration = vetCooldownDuration;

        /* Initialize play cooldown values */
        this.lastPlayTime = lastPlayTime;  // the last time the vet played
        this.playCooldownDuration = playCooldownDuration;

        /* Set the pets current outfit */
        this.currentOutfit = currentOutfit;

        /* Apply the specific pets decline rates*/
        setPetType(petType);
    }


    /**
     * Gets the time (in seconds) when the pet last visited the vet.
     *
     * @return The timestamp of the last vet visit.
     */
    public int getLastVetVisitTime() {
        return lastVetVisitTime;
    }


    /**
     * Updates the timestamp for the pet's last vet visit.
     *
     * @param lastVetVisitTime The new timestamp (in seconds) of the vet visit.
     */
    public void setLastVetVisitTime(int lastVetVisitTime) {
        this.lastVetVisitTime = lastVetVisitTime;
    }


    /**
     * Retrieves the cooldown duration after a vet visit.
     *
     * @return The cooldown duration in seconds.
     */
    public int getVetCooldownDuration() {
        return vetCooldownDuration;
    }


    /**
     * Sets the number of seconds the player must wait before the pet
     * can visit the vet again.
     *
     * @param vetCooldownDuration The cooldown duration in seconds.
     */
    public void setVetCooldownDuration(int vetCooldownDuration) {
        this.vetCooldownDuration = vetCooldownDuration;
    }


    /**
     * Returns the time (in seconds since the game started) when the pet was last played with.
     *
     * @return The timestamp of the pet's last playtime.
     */
    public int getLastPlayTime() {
        return lastPlayTime;
    }


    /**
     * Sets the timestamp of when the pet was last played with.
     *
     * @param lastPlayTime The new value representing the last play time in seconds.
     */
    public void setLastPlayTime(int lastPlayTime) {
        this.lastPlayTime = lastPlayTime;
    }


    /**
     * Retrieves the cooldown duration required between play sessions.
     *
     * @return The number of seconds the pet must wait before it can play again.
     */

    public int getPlayCooldownDuration() {
        return playCooldownDuration;
    }


    /**
     * Sets the cooldown duration required between play sessions.
     *
     * @param playCooldownDuration The number of seconds the pet must wait before playing again.
     */
    public void setPlayCooldownDuration(int playCooldownDuration) {
        this.playCooldownDuration = playCooldownDuration;
    }


    /**
     * Retrieves the name of the pet.
     *
     * @return The pet's name.
     */
    public String getName() {
        return name;
    }


    /**
     * Increases the pet's happiness by a given amount.
     * Ensures the happiness does not exceed the pet's maximum happiness.
     *
     * @param amount The amount to increase happiness by.
     * @return The updated happiness value after the increase.
     */

    public int increaseHappiness(int amount) {
        happiness = Math.min(happiness + amount, maxHappiness);
        return happiness;
    }


    /**
     * Decreases the pet's happiness by a given amount.
     * Ensures the happiness does not go below zero.
     *
     * @param amount The amount to decrease happiness by.
     * @return The updated happiness value after the decrease.
     */

    public int decreaseHappiness(int amount) {
        happiness = Math.max(happiness - amount, 0);
        return happiness;
    }


    /**
     * Increases the pet's fullness by a given amount.
     * Ensures the fullness does not exceed the pet's maximum fullness.
     *
     * @param amount The amount to increase fullness by.
     * @return The updated fullness value after the increase.
     */

    public int increaseFullness(int amount) {
        fullness = Math.min(fullness + amount, maxFullness);
        return fullness;
    }


    /**
     * Reduces the pet's fullness by a specified amount.
     * Ensures that fullness does not fall below 0.
     *
     * @param amount the amount to subtract from the pet's current fullness
     * @return the updated fullness value after the decrease
     */
    public int decreaseFullness(int amount) {
        fullness = Math.max(fullness - amount, 0);
        return fullness;
    }


    /**
     * Increases the pet's health by the specified amount.
     * Ensures that health does not exceed the maximum health value.
     *
     * @param amount the amount to increase the pet's health by
     * @return the updated health value after the increase
     */
    public int increaseHealth(int amount) {
        health = Math.min(health + amount, maxHealth);
        return health;
    }


    /**
     * Decreases the pet's health by the specified amount.
     * Ensures that health does not go below zero.
     *
     * @param amount the amount to decrease the pet's health by
     * @return the updated health value after the decrease
     */

    public int decreaseHealth(int amount) {
        health = Math.max(health - amount, 0);
        return health;
    }


    /**
     * Increases the pet's sleep value by the specified amount.
     * Ensures the sleep value does not exceed the maximum allowed.
     *
     * @param amount the amount to increase the pet's sleep by
     * @return the updated sleep value after the increase
     */

    public int increaseSleep(int amount) {
        sleep = Math.min(sleep + amount, maxSleep);
        return sleep;
    }


    /**
     * Decreases the pet's sleep value by the specified amount.
     * Ensures the sleep value does not fall below zero.
     *
     * @param amount the amount to reduce the pet's sleep by
     * @return the updated sleep value after the decrease
     */

    public int decreaseSleep(int amount) {
        sleep = Math.max(sleep - amount, 0);
        return sleep;
    }


    /**
     * Returns the current happiness value of the pet.
     *
     * @return the pet's current happiness level
     */

    public int getHappiness() {
        return this.happiness;
    }


    /**
     * Returns the current fullness value of the pet.
     *
     * @return the pet's current fullness level
     */
    public int getFullness() {
        return this.fullness;
    }


    /**
     * Returns the current health value of the pet.
     *
     * @return the pet's current health level
     */

    public int getHealth() {
        return this.health;
    }


    /**
     * Returns the current sleep value of the pet.
     *
     * @return the pet's current sleep level
     */
    public int getSleepiness() {
        return this.sleep;
    }


    /**
     * Returns the map that holds all defined pet types and their corresponding stat multipliers.
     *
     * @return a map of pet type names to their PetType configurations
     */

    public Map<String, PetType> getPetTypeMap() {
        return petTypeMap;
    }


    /**
     * Retrieves the pet's current sleep level.
     *
     * @return the current sleep value
     */

    public int getSleep() {
        return sleep;
    }


    /**
     * Returns the maximum health value the pet can have.
     *
     * @return the maximum health stat
     */
    public int getMaxHealth() {
        return maxHealth;
    }


    /**
     * Returns the maximum sleep value the pet can have.
     *
     * @return the maximum sleep stat
     */
    public int getMaxSleep() {
        return maxSleep;
    }


    /**
     * Returns the maximum fullness value the pet can reach.
     *
     * @return the maximum fullness stat
     */
    public int getMaxFullness() {
        return maxFullness;
    }


    /**
     * Returns the maximum happiness value the pet can have.
     *
     * @return the maximum happiness stat
     */
    public int getMaxHappiness() {
        return maxHappiness;
    }


    /**
     * Checks if the pet is currently in a happy state.
     *
     * @return true if the pet is happy; false otherwise
     */
    public boolean isHappy() {
        return isHappy;
    }


    /**
     * Updates the stat decline rates (health, fullness, sleep, happiness) based on the pet's type.
     *
     * Each pet type has predefined multipliers that affect how quickly each stat decays.
     * This method fetches the current pet's type and applies its corresponding multipliers
     * to base stat decline values.
     */
    public void updateRatesBasedOfType() {
        PetType type = petTypeMap.get(this.petType);  // Get PetType object for current pet from Map

        // If the pet type doesn't exist, leave
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
        if(getHappiness() == 0)
        {
            return true;
        }
        return false;
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

    public void setHappiness(int happiness) {
        this.happiness = happiness;
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
            System.out.println("Invalid pet type: " + petType);
        }
    }

    public void applyDecline() {
        declineCounter++;

        if(declineCounter % 15 == 0)
        {
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
    }

    public boolean isWearingOutfit() {
        return currentOutfit != null && !currentOutfit.isEmpty();
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


    /**
     * Gets the name of the currently equipped outfit.
     *
     * @return The name of the current outfit, or null if no outfit is equipped.
     */
    public String getCurrentOutfit() {
        return currentOutfit;
    }


    /**
     * Attempts to equip an outfit to the pet. The outfit must match the allowed outfit
     * for the pet's type, as defined in the `allowedOutfits` map.
     *
     * @param outfitName The name of the outfit to equip, or null/empty to remove the current outfit.
     * @return true if the outfit was successfully set or removed; false otherwise.
     *
     * @author
     * Mohammed Abdulnabi
     * Kamaldeep Singh Ghotra
     *
     */
    public boolean setOutfit(String outfitName) {
        if (outfitName == null || outfitName.isEmpty()) {
            this.currentOutfit = null;
            System.out.println("Outfit removed.");
            return true;
        }

        String allowedOutfit = allowedOutfits.get(this.petType);
        System.out.println("Setting outfit: " + outfitName);

        if (allowedOutfit == null) {
            System.out.println("ERROR: No outfit restrictions defined for this pet type.");
            return false;
        }

        if (!outfitName.equalsIgnoreCase(allowedOutfit)) {
            System.out.println("ERROR: " + this.petType + " can only wear " + allowedOutfit + "!");
            return false;
        }

        this.currentOutfit = outfitName;
        System.out.println(this.name + " is now wearing " + outfitName);
        return true;
    }


    /**
     * Removes the currently equipped outfit from the pet, if one exists.
     * Outputs the removed outfit's name to the console for logging or debugging.
     *
     * @author Mohammed Abdulnabi
     */
    public void removeOutfit() {
        if (this.currentOutfit != null && !this.currentOutfit.isEmpty()) {
            System.out.println("Removing outfit: " + this.currentOutfit);
            this.currentOutfit = null;
        }
    }


    /**
     * Resets the pet's state to its default.
     * This method is typically used when restarting the game or reviving the pet.
     *
     * @author Kamaldeep Ghotra
     */
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




//    private String getAllowedOutfit(Pet pet) {
//        switch (pet.getPetType()) {
//            case "PetOption1":
//                return "outfit1";
//            case "PetOption2":
//                return "outfit2";
//            case "PetOption3":
//                return "outfit3";
//            default:
//                return "None"; // In case of an unknown pet type
//        }
//    }
//
//    private boolean isOutfit(String itemName) {
//        return itemName.equals("outfit1") || itemName.equals("outfit2") || itemName.equals("outfit3");
//    }
//
//    public boolean canWearOutfit(String outfitName) {
//        // Each pet type is restricted to a specific outfit
//        switch (this.petType) {
//            case "PetOption1":
//                return outfitName.equals("outfit1");
//            case "PetOption2":
//                return outfitName.equals("outfit2");
//            case "PetOption3":
//                return outfitName.equals("outfit3");
//            default:
//                return false; // If petType is unknown, deny all outfits
//        }
//    }
}

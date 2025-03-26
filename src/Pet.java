package src;

public class Pet {
    // Pet name
    private String name;



    // Current stats
    private int health;
    private int sleep;
    private int fullness;
    private int happiness;

    // Max values for each stat
    private final int maxHealth;
    private final int maxSleep;
    private final int maxFullness;
    private final int maxHappiness;

    // Decline rates for each stat
    private int healthDeclineRate;
    private int fullnessDeclineRate;
    private int sleepDeclineRate;
    private int happinessDeclineRate;

    // State flags
    private boolean isSleeping;
    private boolean isHungry;
    private boolean isHappy;
    private boolean isDead;

    // Cooldowns
    private int lastVetVisitTime;
    private int vetCooldownDuration;
    private int lastPlayTime;
    private int playCooldownDuration;

    // Current outfit name; null means no outfit equipped
    private String currentOutfit;

    /**
     * Creates a new Pet instance with assigned max stats
     * Initializes all current stats to their respective max values
     * @param name the pets name
     * @param maxHealth maximum health of the pet
     * @param maxSleep maximum sleep of the pet
     * @param maxFullness maximum fullness of the pet
     * @param maxHappiness maximum happiness of the pet
     */
    public Pet(String name, int maxHealth, int maxSleep, int maxFullness, int maxHappiness) {
        this.name = name;

        this.maxHealth = maxHealth;
        this.maxSleep = maxSleep;
        this.maxFullness = maxFullness;
        this.maxHappiness = maxHappiness;

        this.health = maxHealth;
        this.sleep = maxSleep;
        this.fullness = maxFullness;
        this.happiness = maxHappiness;
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

    public void updateRatesBasedOfType() {
        // TO BE IMPLEMENTED **************************************************************
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
        return isDead;
    }

    public void applyDecline() {
        if (isDead || isSleeping) {
            return;
        }

        decreaseSleep(sleepDeclineRate);
        decreaseFullness(fullnessDeclineRate);
        decreaseHappiness(fullness <= 0 ? happinessDeclineRate * 2 : happinessDeclineRate);

        if (fullness <= 0) {
            decreaseHealth(Math.max(1, healthDeclineRate));
            isHungry = true;
        } else {
            isHungry = false;
        }

        isHappy = happiness > 0;

        if (sleep <= 0) {
            decreaseHealth(Math.max(1, healthDeclineRate));
            sleep = 0;
            isSleeping = true;
        }

        if (health <= 0) {
            health = 0;
            isDead = true;
        }
    }

    public boolean isWearingOutfit() {
        return currentOutfit != null;
    }

    public String getCurrentOutfit() {
        return currentOutfit;
    }

    /**
     * Sets the current outfit for the pet (package-private).
     * Can only be changed from within the same package (e.g., PlayerInventory).
     */
    public void setOutfit(String outfitName) {
        this.currentOutfit = outfitName;
    }

    public void printStats() {
        System.out.println(name + " - Health: " + health + "/" + maxHealth +
                ", Sleep: " + sleep + "/" + maxSleep +
                ", Fullness: " + fullness + "/" + maxFullness +
                ", Happiness: " + happiness + "/" + maxHappiness);
    }
}

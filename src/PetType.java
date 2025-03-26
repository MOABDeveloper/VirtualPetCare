package src;

public class PetType {
    private float healthDeclineMultiplier;
    private float fullnessDeclineMultiplier;
    private float sleepDeclineMultiplier;
    private float happinessDeclineMultiplier;

    public PetType(float healthDeclineMultiplier, float fullnessDeclineMultiplier, float sleepDeclineMultiplier, float happinessDeclineMultiplier) {
        this.healthDeclineMultiplier = healthDeclineMultiplier;
        this.fullnessDeclineMultiplier = fullnessDeclineMultiplier;
        this.sleepDeclineMultiplier = sleepDeclineMultiplier;
        this.happinessDeclineMultiplier = happinessDeclineMultiplier;
    }

    public float getHealthDeclineMultiplier() {
        return healthDeclineMultiplier;
    }

    public void setHealthDeclineMultiplier(float multiplier) {
        this.healthDeclineMultiplier = multiplier;
    }

    public float getFullnessDeclineMultiplier() {
        return fullnessDeclineMultiplier;
    }

    public void setFullnessDeclineMultiplier(float multiplier) {
        this.fullnessDeclineMultiplier = multiplier;
    }

    public float getSleepDeclineMultiplier() {
        return sleepDeclineMultiplier;
    }

    public void setSleepDeclineMultiplier(float multiplier) {
        this.sleepDeclineMultiplier = multiplier;
    }

    public float getHappinessDeclineMultiplier() {
        return happinessDeclineMultiplier;
    }

    public void setHappinessDeclineMultiplier(float multiplier) {
        this.happinessDeclineMultiplier = multiplier;
    }
}

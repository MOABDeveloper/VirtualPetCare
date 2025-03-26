package tests;

import src.*;

public class PetBehaviorTest {
    public static void main(String[] args) {
        Pet pet = new Pet("Buddy", 100, 100, 100, 100);

        // Set fast decline rates for testing
        pet.setHealthDeclineRate(10);
        pet.setSleepDeclineRate(30);
        pet.setFullnessDeclineRate(40);
        pet.setHappinessDeclineRate(20);

        System.out.println("🔍 Starting stat test...");

        // First decline cycle
        pet.applyDecline();
        printStats(pet);

        // Second decline cycle
        pet.applyDecline();
        printStats(pet);

        // Third cycle — expect fullness to be 0, hunger to kick in
        pet.applyDecline();
        printStats(pet);

        // Fourth — happiness drops to 0 (angry), sleep hits 0 (sleeping)
        pet.applyDecline();
        printStats(pet);

        // While sleeping, sleep should increase — simulate 4 more ticks
        for (int i = 0; i < 4; i++) {
            pet.applyDecline();
            printStats(pet);
        }

        // At this point, health should decline due to hunger, may hit 0
        while (!pet.isDead()) {
            pet.applyDecline();
            printStats(pet);
        }

        System.out.println("☠️ Pet has died. Final state:");
        printStats(pet);
    }

    private static void printStats(Pet pet) {
        System.out.println("\n---- Pet Status ----");
        System.out.println("Health:    " + pet.getHealth() + (pet.isWarningHealth() ? " ⚠️" : ""));
        System.out.println("Sleep:     " + pet.getSleepiness() + (pet.isWarningSleep() ? " ⚠️" : ""));
        System.out.println("Fullness:  " + pet.getFullness() + (pet.isWarningFullness() ? " ⚠️" : ""));
        System.out.println("Happiness: " + pet.getHappiness() + (pet.isWarningHappiness() ? " ⚠️" : ""));
        System.out.println("State: " +
                (pet.isDead() ? "☠️ DEAD" :
                        pet.isSleeping() ? "😴 SLEEPING" :
                                pet.isHungry() ? "🍽️ HUNGRY" :
                                        pet.isAngry() ? "😡 ANGRY" : "😊 NORMAL"));
    }
}

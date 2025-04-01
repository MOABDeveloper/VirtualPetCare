package tests;

import src.Pet;

public class PetTypeIntegrationTest {
    public static void main(String[] args) {
        // Step 1: Create the pet
        Pet pet = new Pet(
                "Shadow", "Petoption1", 100, 100, 100, 100,   // health, sleep, fullness, happiness
                100, 100, 100, 100,             // maxHealth, maxSleep, maxFullness, maxHappiness
                5, 5, 5, 5,                     // healthDeclineRate, fullnessDeclineRate, sleepDeclineRate, happinessDeclineRate
                false, false, true, false,      // isSleeping, isHungry, isHappy, isDead
                0, 30, 0, 20,                   // lastVetVisitTime, vetCooldownDuration, lastPlayTime, playCooldownDuration
                "default_outfit"                // currentOutfit
        );

        // Step 2: Set a specific pet type
        pet.setPetType("PetOption1");

        // Step 3: Display initial decline rates
        System.out.println("✅ Decline Rates After Setting Pet Type:");
        System.out.println("Health decline rate:    " + pet.getHealthDeclineRate());
        System.out.println("Fullness decline rate:  " + pet.getFullnessDeclineRate());
        System.out.println("Sleep decline rate:     " + pet.getSleepDeclineRate());
        System.out.println("Happiness decline rate: " + pet.getHappinessDeclineRate());

        // Step 4: Simulate time passing and apply declines
        System.out.println("\n🕒 Starting decline simulation...");
        for (int i = 1; i <= 10; i++) {
            System.out.println("\n--- Tick " + i + " ---");
            pet.applyDecline();
            printStats(pet);

            if (pet.isDead()) {
                System.out.println("☠️ Pet died during tick " + i + ". Ending test.");
                break;
            }
        }
    }

    private static void printStats(Pet pet) {
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

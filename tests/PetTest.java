package tests;
import src.Pet;

public class PetTest {
    public static void main(String[] args) {
        // Create a new pet with defined max stats
        Pet pet = new Pet(
                "Shadow", 100, 100, 100, 100,   // health, sleep, fullness, happiness
                100, 100, 100, 100,             // maxHealth, maxSleep, maxFullness, maxHappiness
                5, 5, 5, 5,                     // healthDeclineRate, fullnessDeclineRate, sleepDeclineRate, happinessDeclineRate
                false, false, true, false,      // isSleeping, isHungry, isHappy, isDead
                0, 30, 0, 20,                   // lastVetVisitTime, vetCooldownDuration, lastPlayTime, playCooldownDuration
                "default_outfit"                // currentOutfit
        );

        // Print starting stats
        System.out.println("Initial stats:");
        pet.printStats();

        // Simulate some stat changes
        System.out.println("\nDecreasing stats manually:");
        pet.decreaseHealth(10);
        pet.decreaseFullness(20);
        pet.decreaseSleep(15);
        pet.decreaseHappiness(5);
        pet.printStats();

        // Increase stats back
        System.out.println("\nIncreasing stats manually:");
        pet.increaseHealth(5);
        pet.increaseFullness(10);
        pet.increaseSleep(10);
        pet.increaseHappiness(15);
        pet.printStats();

        // Apply multiple declines and observe changes
        System.out.println("\nSimulating passive decline over 5 ticks:");
        // Set artificial decline rates for testing
        // You'd normally want these values exposed via methods, but for now we set them manually
        for (int i = 0; i < 5; i++) {
            System.out.println("Tick " + (i + 1));
            pet.applyDecline(); // apply decay logic
            pet.printStats();   // show current stats
            System.out.println();
        }

        // Check flags
        System.out.println("State checks:");
        System.out.println("Sleeping? " + pet.isSleeping());
        System.out.println("Hungry? " + pet.isHungry());
        System.out.println("Angry? " + pet.isAngry());
        System.out.println("Dead? " + pet.isDead());


        if (pet.isWearingOutfit()) {
            System.out.println("Pet is wearing: " + pet.getCurrentOutfit());
        } else {
            System.out.println("Pet is not wearing anything!");
        }


    }
}

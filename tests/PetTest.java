package tests;

import src.Pet;

public class PetTest {
    public static void main(String[] args) throws InterruptedException {
        // Create a new pet with max stats
        Pet pet = new Pet("Buddy", 100, 100, 100, 100);

        // Set stat decline rates for testing
        pet.setHealth(100);           // current health
        pet.setSleepiness(10);        // simulate already tired
        pet.setFullness(0);           // simulate already hungry
        pet.setHappiness(50);         // mid-level happiness

        // Simulate custom decline rates (this would normally go in PetOption1)
        // You could create setters later, but for now you can directly set fields or extend the Pet class
        // For this demo, just call applyDecline() and print

        // Apply decline a few times and print after each
        for (int i = 1; i <= 5; i++) {
            System.out.println("Tick " + i);
            pet.applyDecline();
            pet.printStats();
            System.out.println();
            Thread.sleep(1000); // pause for 1 sec between updates (just for readability)
        }

        // Check for state flags
        System.out.println("Pet is sleeping? " + pet.isSleeping());
        System.out.println("Pet is hungry? " + pet.isHungry());
        System.out.println("Pet is angry? " + pet.isAngry());
        System.out.println("Pet is dead? " + pet.isDead());
    }
}

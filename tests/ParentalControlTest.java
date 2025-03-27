package tests;

import src.*;

public class ParentalControlTest {
    public static void main(String[] args) {
        ParentalControl parent = new ParentalControl();

        System.out.println("🔐 AUTHENTICATION TEST");
        System.out.println("Correct password? " + parent.authenticate("1234"));   // true
        System.out.println("Wrong password?   " + parent.authenticate("0000"));   // false

        System.out.println("\n⏰ PARENTAL LIMITATION TEST");
        parent.setLimitationsEnabled(true);
        parent.setPlayTimeWindow(9, 17); // Only playable between 9am and 5pm
        System.out.println("Is play allowed now? " + parent.isPlayAllowedNow());

        System.out.println("\n📊 PLAY TIME TRACKING TEST");
        long session1 = 10_000; // simulate 10 seconds
        parent.updateAfterSession(session1);
        System.out.println("Total play time:   " + parent.getTotalPlayTime() + " ms");
        System.out.println("Average play time: " + parent.getAveragePlayTime() + " ms");

        long session2 = 20_000; // simulate another session
        parent.updateAfterSession(session2);
        System.out.println("Total play time:   " + parent.getTotalPlayTime() + " ms");
        System.out.println("Average play time: " + parent.getAveragePlayTime() + " ms");

        System.out.println("\n♻️ RESET STATS TEST");
        parent.resetStats();
        System.out.println("Total play time after reset: " + parent.getTotalPlayTime());
        System.out.println("Average play time after reset: " + parent.getAveragePlayTime());

        System.out.println("\n❤️ PET REVIVAL TEST");
        Pet pet = new Pet(
                "Shadow", 100, 100, 100, 100,   // health, sleep, fullness, happiness
                100, 100, 100, 100,             // maxHealth, maxSleep, maxFullness, maxHappiness
                5, 5, 5, 5,                     // healthDeclineRate, fullnessDeclineRate, sleepDeclineRate, happinessDeclineRate
                false, false, true, false,      // isSleeping, isHungry, isHappy, isDead
                0, 30, 0, 20,                   // lastVetVisitTime, vetCooldownDuration, lastPlayTime, playCooldownDuration
                "default_outfit"                // currentOutfit
        );
        pet.setPetType("PetOption2");

        // Simulate the pet is dead
        pet.decreaseHealth(100); // 0 health
        pet.setDead(true);
        pet.setHungry(true);
        pet.setSleeping(true);

        System.out.println("Before revival:");
        pet.printStats();
        System.out.println("Is dead? " + pet.isDead());

        parent.revivePet(pet);

        System.out.println("\nAfter revival:");
        pet.printStats();
        System.out.println("Is dead? " + pet.isDead());
        System.out.println("Is hungry? " + pet.isHungry());
        System.out.println("Is sleeping? " + pet.isSleeping());
    }
}

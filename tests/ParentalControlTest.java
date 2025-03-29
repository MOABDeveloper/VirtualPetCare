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
                "Shadow", "Petoption1", 100, 100, 100, 100,   // health, sleep, fullness, happiness
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

//JUnit tests 

package src;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalTime;

public class ParentalControlTest {

    @Test
    public void testAuthenticate() {
        ParentalControl pc = new ParentalControl();
        assertTrue(pc.authenticate("1234"), "Authentication should succeed with the correct password");
        assertFalse(pc.authenticate("wrong"), "Authentication should fail with an incorrect password");
    }

    @Test
    public void testIsPlayAllowedNowWithoutLimitations() {
        ParentalControl pc = new ParentalControl();
        assertTrue(pc.isPlayAllowedNow(), "Play should be allowed when limitations are disabled");
    }

    @Test
    public void testIsPlayAllowedNowWithLimitationsAllowed() {
        ParentalControl pc = new ParentalControl();
        pc.setLimitationsEnabled(true);
        int currentHour = LocalTime.now().getHour();
        int startHour = currentHour;
        int endHour = (currentHour == 23) ? 24 : currentHour + 1;
        pc.setPlayTimeWindow(startHour, endHour);
        assertTrue(pc.isPlayAllowedNow(), "Play should be allowed when current hour is within the allowed window");
    }

    @Test
    public void testIsPlayAllowedNowWithLimitationsNotAllowed() {
        ParentalControl pc = new ParentalControl();
        pc.setLimitationsEnabled(true);
        int currentHour = LocalTime.now().getHour();
        int startHour = (currentHour + 1) % 24;
        int endHour = (currentHour + 2) % 24;
        pc.setPlayTimeWindow(startHour, endHour);
        assertFalse(pc.isPlayAllowedNow(), "Play should not be allowed when current hour is outside the allowed window");
    }

    @Test
    public void testAveragePlayTimeAndUpdateAfterSession() {
        ParentalControl pc = new ParentalControl();
        assertEquals(99, pc.getAveragePlayTime(), "Initial average play time should be 99");

        pc.updateAfterSession(1000);
        assertEquals(1999, pc.getTotalPlayTime(), "Total play time should update correctly after a session");
        assertEquals(181, pc.getAveragePlayTime(), "Average play time should update correctly after a session");
    }

    @Test
    public void testResetStats() {
        ParentalControl pc = new ParentalControl();
        pc.updateAfterSession(1000);
        pc.resetStats();
        assertEquals(0, pc.getTotalPlayTime(), "Total play time should be reset to 0");
        // When sessionCount is 0, average play time should be 0.
        assertEquals(0, pc.getAveragePlayTime(), "Average play time should be 0 after reset when there are no sessions");
    }

    @Test
    public void testRevivePet() {
        ParentalControl pc = new ParentalControl();
        // Create a pet that is alive.
        Pet petAlive = new Pet("AlivePet", 100, 100, 100, 100, 
                               100, 100, 100, 100, 
                               5, 5, 5, 5, 
                               false, false, true, false, 
                               0, 10, 0, 10, 
                               null);
        // For an alive pet, revivePet should return false.
        assertFalse(pc.revivePet(petAlive), "Revive should return false for a living pet");

        // Create a pet and kill it by reducing its health below 0.
        Pet petDead = new Pet("DeadPet", 100, 100, 100, 100, 
                              100, 100, 100, 100, 
                              5, 5, 5, 5, 
                              false, false, true, false, 
                              0, 10, 0, 10, 
                              null);
        // mark it as dead.
        petDead.decreaseHealth(150);
        assertTrue(petDead.isDead(), "The pet should be dead after decreasing health below 0");

        // Revive the dead pet
        assertTrue(pc.revivePet(petDead), "Revive should return true for a dead pet");
        // After revival, the pet's stats should be reset
        assertEquals(100, petDead.getHealth(), "Pet health should be reset to max after revival");
        assertFalse(petDead.isDead(), "Pet should not be dead after revival");
    }
}


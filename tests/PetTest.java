package tests;
import src.Pet;

public class PetTest {
    public static void main(String[] args) {
        // Create a new pet with defined max stats
        Pet pet = new Pet(
                "Shadow", "Petoption1", 100, 100, 100, 100,   // health, sleep, fullness, happiness
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

//JUnit Tests
package src;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PetTest {

    // Utility method to create a default Pet instance
    private Pet createDefaultPet() {
        // Creates a pet with all stats at 100 and default decline rates set to 5.
        return new Pet("TestPet", 
                100, 100, 100, 100,   // current stats: health, sleep, fullness, happiness
                100, 100, 100, 100,   // max values for each stat
                5, 5, 5, 5,          // decline rates: health, fullness, sleep, happiness
                false, false, true, false, // flags: isSleeping, isHungry, isHappy, isDead
                0, 10, 0, 10,        // cooldown times: lastVetVisitTime, vetCooldownDuration, lastPlayTime, playCooldownDuration
                null);               // current outfit = null
    }

    @Test
    public void testIncreaseAndDecreaseStats() {
        Pet pet = createDefaultPet();

        // Increase methods: stats should not exceed max values
        pet.increaseHappiness(10);
        assertEquals(100, pet.getHappiness(), "Happiness should not exceed max");
        pet.increaseFullness(10);
        assertEquals(100, pet.getFullness(), "Fullness should not exceed max");
        pet.increaseHealth(10);
        assertEquals(100, pet.getHealth(), "Health should not exceed max");
        pet.increaseSleep(10);
        assertEquals(100, pet.getSleep(), "Sleep should not exceed max");

        // Decrease methods: stats should not go below 0
        pet.decreaseHappiness(150);
        assertEquals(0, pet.getHappiness(), "Happiness should not be less than 0");
        pet.decreaseFullness(150);
        assertEquals(0, pet.getFullness(), "Fullness should not be less than 0");
        pet.decreaseHealth(150);
        assertEquals(0, pet.getHealth(), "Health should not be less than 0");
        pet.decreaseSleep(150);
        assertEquals(0, pet.getSleep(), "Sleep should not be less than 0");
    }

    @Test
    public void testApplyDeclineWhenNotSleeping() {
        Pet pet = createDefaultPet();
        pet.decreaseFullness(95); // Fullness becomes 5.
        assertEquals(5, pet.getFullness(), "Fullness should be 5");

        pet.applyDecline();
        // decreaseFullness is called with rate 5 so fullness should now be 0.
        assertEquals(0, pet.getFullness(), "Fullness should decline to 0");

        assertEquals(95, pet.getSleep(), "Sleep should decrease by 5 from 100 to 95");

        // Since fullness is now 0, happiness decreases 
        // Starting from 100, happiness should be 90
        assertEquals(90, pet.getHappiness(), "Happiness should decrease by 10 due to hunger");

        // When fullness = 0 pet becomes hungry and loses health by 5.
        assertEquals(95, pet.getHealth(), "Health should decrease by 5 due to hunger");
        assertTrue(pet.isHungry(), "Pet should be hungry");
        assertTrue(pet.isHappy(), "Pet should be happy since happiness > 0");
        assertFalse(pet.isSleeping(), "Pet should not be sleeping if sleep > 0");
    }

    @Test
    public void testApplyDeclineWhenSleeping() {
        Pet pet = createDefaultPet();
        pet.setSleeping(true);
        pet.decreaseSleep(10); // Sleep becomes 90.
        int sleepBefore = pet.getSleep();
        pet.applyDecline();
        // When sleeping, sleep is increased but capped at maxSleep.
        int expectedSleep = Math.min(sleepBefore + pet.getSleepDeclineRate(), pet.getMaxSleep());
        assertEquals(expectedSleep, pet.getSleep(), "Sleep should increase while sleeping");
    }

    @Test
    public void testSetPetTypeValid() {
        Pet pet = createDefaultPet();
        pet.setPetType("PetOption2");
        assertEquals("PetOption2", pet.getPetType(), "Pet type should be set to PetOption2");

        // With base decline rates of 5 and multiplier 2
        assertEquals(10, pet.getHealthDeclineRate(), "Health decline rate should be 10");
        assertEquals(10, pet.getFullnessDeclineRate(), "Fullness decline rate should be 10");
        assertEquals(10, pet.getSleepDeclineRate(), "Sleep decline rate should be 10");
        assertEquals(10, pet.getHappinessDeclineRate(), "Happiness decline rate should be 10");
    }

    @Test
    public void testSetPetTypeInvalid() {
        Pet pet = createDefaultPet();
        pet.setPetType("InvalidPetType");
        // Since the type is invalid, petType should remain null.
        assertNull(pet.getPetType(), "Pet type should remain null for an invalid type");
    }

    @Test
    public void testResetState() {
        Pet pet = createDefaultPet();
        pet.decreaseHealth(50);
        pet.decreaseSleep(50);
        pet.decreaseFullness(50);
        pet.decreaseHappiness(50);
        pet.setSleeping(true);
        pet.setHungry(true);
        pet.setDead(true);

        // Call resetState to default values.
        pet.resetState();
        assertEquals(pet.getMaxHealth(), pet.getHealth(), "Health should be reset to max");
        assertEquals(pet.getMaxSleep(), pet.getSleep(), "Sleep should be reset to max");
        assertEquals(pet.getMaxFullness(), pet.getFullness(), "Fullness should be reset to max");
        assertEquals(pet.getMaxHappiness(), pet.getHappiness(), "Happiness should be reset to max");
        assertFalse(pet.isDead(), "Pet should not be dead after reset");
        assertFalse(pet.isSleeping(), "Pet should not be sleeping after reset");
        assertFalse(pet.isHungry(), "Pet should not be hungry after reset");
        assertTrue(pet.isHappy(), "Pet should be happy after reset");
    }

    @Test
    public void testWarnings() {
        Pet pet = createDefaultPet();
        pet.decreaseHealth(80);
        pet.decreaseSleep(80);
        pet.decreaseFullness(80);
        pet.decreaseHappiness(80);

        assertTrue(pet.isWarningHealth(), "Health warning should be triggered");
        assertTrue(pet.isWarningSleep(), "Sleep warning should be triggered");
        assertTrue(pet.isWarningFullness(), "Fullness warning should be triggered");
        assertTrue(pet.isWarningHappiness(), "Happiness warning should be triggered");
    }

    @Test
    public void testCooldownSettersAndGetters() {
        Pet pet = createDefaultPet();
        pet.setLastVetVisitTime(50);
        pet.setVetCooldownDuration(30);
        pet.setLastPlayTime(40);
        pet.setPlayCooldownDuration(20);

        assertEquals(50, pet.getLastVetVisitTime(), "Last vet visit time should be 50");
        assertEquals(30, pet.getVetCooldownDuration(), "Vet cooldown duration should be 30");
        assertEquals(40, pet.getLastPlayTime(), "Last play time should be 40");
        assertEquals(20, pet.getPlayCooldownDuration(), "Play cooldown duration should be 20");
    }

    @Test
    public void testIsAngry() {
        Pet pet = createDefaultPet();
        // When the pet is happy, it should not be angry.
        assertFalse(pet.isAngry(), "Pet should not be angry when happy");
        // Decrease happiness to zero; pet is not dead so it should be angry.
        pet.decreaseHappiness(150);
        assertTrue(pet.isAngry(), "Pet should be angry when happiness is 0 and pet is not dead");
    }
}

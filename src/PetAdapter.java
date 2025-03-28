//package src;
//
//import com.google.gson.*;
//
//import java.lang.reflect.Type;
//
//public class PetAdapter implements JsonSerializer<Pet>, JsonDeserializer<Pet> {
//
//    @Override
//    public JsonElement serialize(Pet pet, Type type, JsonSerializationContext context) {
//        JsonObject obj = new JsonObject();
//        obj.addProperty("name", pet.getName());
//        obj.addProperty("petType", pet.getPetType());  // ✅ Save pet type
//        obj.addProperty("health", pet.getHealth());
//        obj.addProperty("sleep", pet.getSleepiness());
//        obj.addProperty("fullness", pet.getFullness());
//        obj.addProperty("happiness", pet.getHappiness());
//        obj.addProperty("currentOutfit", pet.getCurrentOutfit());
//
//        return obj;
//    }
//
//    @Override
//    public Pet deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//        JsonObject obj = json.getAsJsonObject();
//
//        String name = obj.get("name").getAsString();
//
//        // ✅ Load pet type (or fallback if missing)
//        String petType = obj.has("petType") && !obj.get("petType").isJsonNull()
//                ? obj.get("petType").getAsString()
//                : "Dog";  // default type fallback if not saved
//
//        int health = obj.get("health").getAsInt();
//        int sleep = obj.get("sleep").getAsInt();
//        int fullness = obj.get("fullness").getAsInt();
//        int happiness = obj.get("happiness").getAsInt();
//
//        String outfit = obj.has("currentOutfit") && !obj.get("currentOutfit").isJsonNull()
//                ? obj.get("currentOutfit").getAsString()
//                : null;
//
//        // Recreate pet with default max stats
//        Pet pet = new Pet(name, 100, 100, 100, 100);
//        pet.setPetType(petType); // ✅ apply the loaded pet type
//
//        // Restore current stats
//        pet.decreaseHealth(pet.getHealth() - health);
//        pet.decreaseSleep(pet.getSleepiness() - sleep);
//        pet.decreaseFullness(pet.getFullness() - fullness);
//        pet.decreaseHappiness(pet.getHappiness() - happiness);
//
//        pet.setOutfit(outfit);
//
//        return pet;
//    }
//}
package src;

import com.google.gson.*;
import java.lang.reflect.Type;

public class PetAdapter implements JsonSerializer<Pet>, JsonDeserializer<Pet> {

    @Override
    public JsonElement serialize(Pet pet, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.addProperty("name", pet.getName());
        obj.addProperty("petType", pet.getPetType());
        obj.addProperty("health", pet.getHealth());
        obj.addProperty("sleep", pet.getSleepiness());
        obj.addProperty("fullness", pet.getFullness());
        obj.addProperty("happiness", pet.getHappiness());
        obj.addProperty("maxHealth", pet.getMaxHealth());
        obj.addProperty("maxSleep", pet.getMaxSleep());
        obj.addProperty("maxFullness", pet.getMaxFullness());
        obj.addProperty("maxHappiness", pet.getMaxHappiness());
        obj.addProperty("healthDeclineRate", pet.getHealthDeclineRate());
        obj.addProperty("fullnessDeclineRate", pet.getFullnessDeclineRate());
        obj.addProperty("sleepDeclineRate", pet.getSleepDeclineRate());
        obj.addProperty("happinessDeclineRate", pet.getHappinessDeclineRate());
        obj.addProperty("isSleeping", pet.isSleeping());
        obj.addProperty("isHungry", pet.isHungry());
        obj.addProperty("isHappy", pet.isHappy());
        obj.addProperty("isDead", pet.isDead());
        obj.addProperty("lastVetVisitTime", pet.getLastVetVisitTime());
        obj.addProperty("vetCooldownDuration", pet.getVetCooldownDuration());
        obj.addProperty("lastPlayTime", pet.getLastPlayTime());
        obj.addProperty("playCooldownDuration", pet.getPlayCooldownDuration());
        obj.addProperty("currentOutfit", pet.getCurrentOutfit());

        return obj;
    }

    @Override
    public Pet deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();

        // Read values from JSON
        String name = obj.get("name").getAsString();
        String petType = obj.has("petType") && !obj.get("petType").isJsonNull() ? obj.get("petType").getAsString() : "Dog";  // Default if missing

        int health = obj.get("health").getAsInt();
        int sleep = obj.get("sleep").getAsInt();
        int fullness = obj.get("fullness").getAsInt();
        int happiness = obj.get("happiness").getAsInt();
        int maxHealth = obj.get("maxHealth").getAsInt();
        int maxSleep = obj.get("maxSleep").getAsInt();
        int maxFullness = obj.get("maxFullness").getAsInt();
        int maxHappiness = obj.get("maxHappiness").getAsInt();
        int healthDeclineRate = obj.get("healthDeclineRate").getAsInt();
        int fullnessDeclineRate = obj.get("fullnessDeclineRate").getAsInt();
        int sleepDeclineRate = obj.get("sleepDeclineRate").getAsInt();
        int happinessDeclineRate = obj.get("happinessDeclineRate").getAsInt();
        boolean isSleeping = obj.get("isSleeping").getAsBoolean();
        boolean isHungry = obj.get("isHungry").getAsBoolean();
        boolean isHappy = obj.get("isHappy").getAsBoolean();
        boolean isDead = obj.get("isDead").getAsBoolean();
        int lastVetVisitTime = obj.get("lastVetVisitTime").getAsInt();
        int vetCooldownDuration = obj.get("vetCooldownDuration").getAsInt();
        int lastPlayTime = obj.get("lastPlayTime").getAsInt();
        int playCooldownDuration = obj.get("playCooldownDuration").getAsInt();
        String outfit = obj.has("currentOutfit") && !obj.get("currentOutfit").isJsonNull()
                ? obj.get("currentOutfit").getAsString()
                : null;

        // ✅ Correctly use the Pet constructor with all values
        Pet pet = new Pet(
                name, petType, health, sleep, fullness, happiness,
                maxHealth, maxSleep, maxFullness, maxHappiness,
                healthDeclineRate, fullnessDeclineRate, sleepDeclineRate, happinessDeclineRate,
                isSleeping, isHungry, isHappy, isDead,
                lastVetVisitTime, vetCooldownDuration, lastPlayTime, playCooldownDuration,
                outfit
        );

//        pet.setPetType(petType);
        return pet;
    }
}

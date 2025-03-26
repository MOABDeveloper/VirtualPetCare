package src;

import com.google.gson.*;

import java.lang.reflect.Type;

public class PetAdapter implements JsonSerializer<Pet>, JsonDeserializer<Pet> {

    @Override
    public JsonElement serialize(Pet pet, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.addProperty("name", pet.getName());
        obj.addProperty("health", pet.getHealth());
        obj.addProperty("happiness", pet.getHappiness());
        obj.addProperty("sleep", pet.getSleepiness());
        obj.addProperty("fullness", pet.getFullness());
        obj.addProperty("outfit", pet.getCurrentOutfit());

        // You can optionally add more fields here (e.g. decline rates, cooldowns)
        return obj;
    }

    @Override
    public Pet deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();

        String name = obj.get("name").getAsString();
        int health = obj.get("health").getAsInt();
        int happiness = obj.get("happiness").getAsInt();
        int sleep = obj.get("sleep").getAsInt();
        int fullness = obj.get("fullness").getAsInt();
        String outfit = obj.has("outfit") && !obj.get("outfit").isJsonNull()
                ? obj.get("outfit").getAsString()
                : null;

        // Create pet with max stats = current stats (you can adjust this logic)
        Pet pet = new Pet(name, health, sleep, fullness, happiness);
        pet.setOutfit(outfit);

        return pet;
    }
}

package src;

import com.google.gson.*;

import java.lang.reflect.Type;

public class PetAdapter implements JsonSerializer<Pet>, JsonDeserializer<Pet> {

    @Override
    public JsonElement serialize(Pet pet, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.addProperty("name", pet.getName());
        obj.addProperty("petType", pet.getPetType());  // ✅ Save pet type
        obj.addProperty("health", pet.getHealth());
        obj.addProperty("sleep", pet.getSleepiness());
        obj.addProperty("fullness", pet.getFullness());
        obj.addProperty("happiness", pet.getHappiness());
        obj.addProperty("currentOutfit", pet.getCurrentOutfit());

        return obj;
    }

    @Override
    public Pet deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();

        String name = obj.get("name").getAsString();

        // ✅ Load pet type (or fallback if missing)
        String petType = obj.has("petType") && !obj.get("petType").isJsonNull()
                ? obj.get("petType").getAsString()
                : "Dog";  // default type fallback if not saved

        int health = obj.get("health").getAsInt();
        int sleep = obj.get("sleep").getAsInt();
        int fullness = obj.get("fullness").getAsInt();
        int happiness = obj.get("happiness").getAsInt();

        String outfit = obj.has("currentOutfit") && !obj.get("currentOutfit").isJsonNull()
                ? obj.get("currentOutfit").getAsString()
                : null;

        // Recreate pet with default max stats
        Pet pet = new Pet(name, 100, 100, 100, 100);
        pet.setPetType(petType); // ✅ apply the loaded pet type

        // Restore current stats
        pet.decreaseHealth(pet.getHealth() - health);
        pet.decreaseSleep(pet.getSleepiness() - sleep);
        pet.decreaseFullness(pet.getFullness() - fullness);
        pet.decreaseHappiness(pet.getHappiness() - happiness);

        pet.setOutfit(outfit);

        return pet;
    }
}

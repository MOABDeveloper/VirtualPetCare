package group19;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class FoodInventoryAdapter implements JsonSerializer<Map<Food, Integer>>, JsonDeserializer<Map<Food, Integer>> {

    @Override
    public JsonElement serialize(Map<Food, Integer> foodMap, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        for (Map.Entry<Food, Integer> entry : foodMap.entrySet()) {
            obj.add(entry.getKey().getName(), new JsonPrimitive(entry.getValue()));
        }
        return obj;
    }

    @Override
    public Map<Food, Integer> deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        Map<Food, Integer> foodMap = new HashMap<>();
        Store store = new Store(); // used to rehydrate food by name

        JsonObject obj = json.getAsJsonObject();
        for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
            Food food = store.getFood(entry.getKey());
            if (food != null) {
                foodMap.put(food, entry.getValue().getAsInt());
            } else {
                System.out.println("⚠️ Unknown food item in save file: " + entry.getKey());
            }
        }
        return foodMap;
    }
}

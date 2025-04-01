package src;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class FoodInventoryAdapter implements JsonSerializer<Map<Food, Integer>>, JsonDeserializer<Map<Food, Integer>> {


    /**
     * Converts a Map of Food objects to a JSON object using their names as keys.
     * This allows food items to be saved in a clean, readable format.
     *
     * @param foodMap The food inventory map to serialize
     * @param type    The type of the object (ignored here)
     * @param context The serialization context
     * @return A JSON object representing the food inventory
     */
    @Override
    public JsonElement serialize(Map<Food, Integer> foodMap, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        for (Map.Entry<Food, Integer> entry : foodMap.entrySet()) {
            obj.add(entry.getKey().getName(), new JsonPrimitive(entry.getValue()));
        }
        return obj;
    }


    /**
     * Converts a JSON object back into a Map of Food objects and their counts.
     * It uses the Store class to look up actual Food instances based on names.
     *
     * @param json    The JSON data to parse
     * @param type    The expected type of the result (Map<Food, Integer>)
     * @param context The deserialization context
     * @return A reconstructed food inventory map
     * @throws JsonParseException if the JSON is malformed or missing data
     */
    @Override
    public Map<Food, Integer> deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        Map<Food, Integer> foodMap = new HashMap<>();
        Store store = new Store();

        JsonObject obj = json.getAsJsonObject();
        for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
            Food food = store.getFood(entry.getKey());
            if (food != null) {
                foodMap.put(food, entry.getValue().getAsInt());
            } else {
                System.out.println("Unknown food item in save file: " + entry.getKey());
            }
        }
        return foodMap;
    }
}

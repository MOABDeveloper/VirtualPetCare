package src;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ToyInventoryAdapter implements JsonSerializer<Map<Toys, Integer>>, JsonDeserializer<Map<Toys, Integer>> {

    @Override
    public JsonElement serialize(Map<Toys, Integer> toyMap, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        for (Map.Entry<Toys, Integer> entry : toyMap.entrySet()) {
            obj.add(entry.getKey().getName(), new JsonPrimitive(entry.getValue()));
        }
        return obj;
    }

    @Override
    public Map<Toys, Integer> deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        Map<Toys, Integer> toyMap = new HashMap<>();
        Store store = new Store(); // to retrieve Toys by name

        JsonObject obj = json.getAsJsonObject();
        for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
            Toys toy = store.getToy(entry.getKey());
            if (toy != null) {
                toyMap.put(toy, entry.getValue().getAsInt());
            } else {
                System.out.println("⚠️ Unknown toy in save file: " + entry.getKey());
            }
        }
        return toyMap;
    }
}

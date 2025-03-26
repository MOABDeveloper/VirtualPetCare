package src;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class GiftInventoryAdapter implements JsonSerializer<Map<Gifts, Integer>>, JsonDeserializer<Map<Gifts, Integer>> {

    @Override
    public JsonElement serialize(Map<Gifts, Integer> giftMap, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        for (Map.Entry<Gifts, Integer> entry : giftMap.entrySet()) {
            obj.add(entry.getKey().getName(), new JsonPrimitive(entry.getValue()));
        }
        return obj;
    }

    @Override
    public Map<Gifts, Integer> deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        Map<Gifts, Integer> giftMap = new HashMap<>();
        Store store = new Store(); // to retrieve Gifts by name

        JsonObject obj = json.getAsJsonObject();
        for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
            Gifts gift = store.getGift(entry.getKey());
            if (gift != null) {
                giftMap.put(gift, entry.getValue().getAsInt());
            } else {
                System.out.println("⚠️ Unknown gift in save file: " + entry.getKey());
            }
        }
        return giftMap;
    }
}

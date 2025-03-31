package src;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.util.Map;

public class PlayerInventoryAdapter implements JsonSerializer<PlayerInventory>, JsonDeserializer<PlayerInventory> {
    private final Store store;

    public PlayerInventoryAdapter(Store store) {
        this.store = store;
    }

    @Override
    public JsonElement serialize(PlayerInventory inventory, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.addProperty("playerCoins", inventory.getPlayerCoins());
        obj.add("foodInventory", new FoodInventoryAdapter().serialize(inventory.getFoodInventory(), null, context));
        obj.add("giftInventory", new GiftInventoryAdapter().serialize(inventory.getGiftInventory(), null, context));
        obj.add("toyInventory", new ToyInventoryAdapter().serialize(inventory.getToyInventory(), null, context));
        obj.add("outfitInventory", context.serialize(inventory.getOutfitInventory()));
        return obj;
    }

    @Override
    public PlayerInventory deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        PlayerInventory inventory = new PlayerInventory(store);

        if (obj.has("playerCoins")) {
            inventory.setPlayerCoins(obj.get("playerCoins").getAsInt());
        }

        Type foodType = new com.google.gson.reflect.TypeToken<Map<Food, Integer>>() {}.getType();
        Type giftType = new com.google.gson.reflect.TypeToken<Map<Gifts, Integer>>() {}.getType();
        Type toyType = new com.google.gson.reflect.TypeToken<Map<Toys, Integer>>() {}.getType();
        Type outfitType = new com.google.gson.reflect.TypeToken<Map<String, Boolean>>() {}.getType();

        Map<Food, Integer> foodMap = new FoodInventoryAdapter().deserialize(obj.get("foodInventory"), foodType, context);
        Map<Gifts, Integer> giftMap = new GiftInventoryAdapter().deserialize(obj.get("giftInventory"), giftType, context);
        Map<Toys, Integer> toyMap = new ToyInventoryAdapter().deserialize(obj.get("toyInventory"), toyType, context);
        Map<String, Boolean> outfitMap = context.deserialize(obj.get("outfitInventory"), outfitType);

        inventory.getFoodInventory().putAll(foodMap);
        inventory.getGiftInventory().putAll(giftMap);
        inventory.getToyInventory().putAll(toyMap);
        inventory.getOutfitInventory().putAll(outfitMap);

        return inventory;
    }
}

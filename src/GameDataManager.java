package src;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

public class GameDataManager {
    private static final String SAVE_FILE = "savegame.json";

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(
                    new TypeToken<Map<Food, Integer>>() {}.getType(),
                    new FoodInventoryAdapter()
            )
            .registerTypeAdapter(
                    new TypeToken<Map<Toys, Integer>>() {}.getType(),
                    new ToyInventoryAdapter()
            )
            .registerTypeAdapter(
                    new TypeToken<Map<Gifts, Integer>>() {}.getType(),
                    new GiftInventoryAdapter()
            )
            .setPrettyPrinting()
            .create();

    // Track the session start time in milliseconds
    private static long sessionStartTime = System.currentTimeMillis();

    public static void saveGame(Pet pet, PlayerInventory inventory, long previousPlayTime) {
        long sessionDuration = System.currentTimeMillis() - sessionStartTime;
        long updatedPlayTime = previousPlayTime + sessionDuration;

        GameData data = new GameData(pet, inventory, updatedPlayTime);
        try (FileWriter writer = new FileWriter(SAVE_FILE)) {
            gson.toJson(data, writer);
            System.out.println("✅ Game saved successfully. Total play time: " + (updatedPlayTime / 1000) + "s");
        } catch (IOException e) {
            System.out.println("❌ Failed to save game: " + e.getMessage());
        }

        // Reset session start time
        sessionStartTime = System.currentTimeMillis();
    }

    public static GameData loadGame() {
        try (FileReader reader = new FileReader(SAVE_FILE)) {
            GameData data = gson.fromJson(reader, GameData.class);
            System.out.println("✅ Game loaded successfully.");
            sessionStartTime = System.currentTimeMillis(); // reset for new session
            return data;
        } catch (IOException e) {
            System.out.println("❌ Failed to load game: " + e.getMessage());
            return null;
        }
    }
}

//package src;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.reflect.TypeToken;
//
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.lang.reflect.Type;
//import java.util.Map;
//
//public class GameDataManager {
//    private static final String SAVE_FILE = "savegame.json";
//
//    // Gson setup with all custom adapters
//    private static final Gson gson = new GsonBuilder()
//            .registerTypeAdapter(
//                    new TypeToken<Map<Food, Integer>>() {}.getType(),
//                    new FoodInventoryAdapter()
//            )
//            .registerTypeAdapter(
//                    new TypeToken<Map<Toys, Integer>>() {}.getType(),
//                    new ToyInventoryAdapter()
//            )
//            .registerTypeAdapter(
//                    new TypeToken<Map<Gifts, Integer>>() {}.getType(),
//                    new GiftInventoryAdapter()
//            )
//            .setPrettyPrinting()
//            .create();
//
//    public static void saveGame(Pet pet, PlayerInventory inventory) {
//        GameData data = new GameData(pet, inventory);
//        try (FileWriter writer = new FileWriter(SAVE_FILE)) {
//            gson.toJson(data, writer);
//            System.out.println("✅ Game saved successfully.");
//        } catch (IOException e) {
//            System.out.println("❌ Failed to save game: " + e.getMessage());
//        }
//    }
//
//    public static GameData loadGame() {
//        try (FileReader reader = new FileReader(SAVE_FILE)) {
//            GameData data = gson.fromJson(reader, GameData.class);
//            System.out.println("✅ Game loaded successfully.");
//            return data;
//        } catch (IOException e) {
//            System.out.println("❌ Failed to load game: " + e.getMessage());
//            return null;
//        }
//    }
//}

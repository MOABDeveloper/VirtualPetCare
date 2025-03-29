package src;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.io.File;


public class GameDataManager {
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
            .registerTypeAdapter(
                    Pet.class,
                    new PetAdapter()
            )
            .setPrettyPrinting()
            .create();

    // Track when this session started
    private static long sessionStartTime = System.currentTimeMillis();

    /**
     * Save game data to a custom-named file (e.g., one per pet or user).
     */
    public static void saveGame(String filename, Pet pet, PlayerInventory inventory, long previousPlayTime) {
        long sessionDuration = System.currentTimeMillis() - sessionStartTime;
        long updatedPlayTime = previousPlayTime + sessionDuration;

        GameData data = new GameData(pet, inventory, updatedPlayTime);
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(data, writer);
            System.out.println("✅ Game saved to '" + filename + "' with total play time: " + (updatedPlayTime / 1000) + "s");
        } catch (IOException e) {
            System.out.println("❌ Failed to save game: " + e.getMessage());
        }

        // Reset session start time
        sessionStartTime = System.currentTimeMillis();
    }

    /**
     * Load game data from a custom-named file.
     */
    public static GameData loadGame(String filename) {
        try (FileReader reader = new FileReader(filename)) {
            GameData data = gson.fromJson(reader, GameData.class);
            System.out.println("✅ Game loaded from '" + filename + "'");
            sessionStartTime = System.currentTimeMillis();
            return data;
        } catch (IOException e) {
            System.out.println("❌ Failed to load game from '" + filename + "': " + e.getMessage());
            return null;
        }
    }

    public static boolean canCreateNewGame() {
        File dir = new File("saves/");
        if (dir.exists() && dir.isDirectory()) {
            return dir.listFiles((dir1, name) -> name.endsWith(".json")).length < 3;
        }
        return true; // Allow if the directory doesn't exist
    }
//    public static String getPetTypeFromSave(String filename) {
//        GameData data = loadGame(filename);
//        if (data != null && data.getPet() != null) {
//            return data.getPet().getPetType(); // ✅ Return the pet type
//        }
//        return "Unknown"; // Default value if loading fails
//    }
    public static String getPetTypeFromSave(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            System.err.println("Error: Save file path is empty!");
            return "default";  // Default pet type if no file is provided
        }

        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.err.println("Error: Save file does not exist.");
                return "default";
            }

            // Load saved game data
            GameData loadedGame = loadGame(filePath);
            if (loadedGame != null && loadedGame.getPet() != null) {
                return loadedGame.getPet().getPetType(); // Retrieve pet type
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "default";  // Return default pet type in case of error
    }

}

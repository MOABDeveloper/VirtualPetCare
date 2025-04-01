package group19;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.io.File;

/**
 * The {@code GameDataManager} class provides functionality to save and load game data
 * using JSON serialization/deserialization with custom adapters for various types.
 * <p>
 * It uses the Gson library with registered adapters for:
 * <ul>
 *   <li>{@link Food} inventory</li>
 *   <li>{@link Toys} inventory</li>
 *   <li>{@link Gifts} inventory</li>
 *   <li>{@link Pet} data</li>
 * </ul>
 * The game data includes the pet, the player's inventory, and the total play time.
 * </p>
 */

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
     * Saves the current game data to a file with the given filename.
     * <p>
     * The method calculates the session duration since the last save and adds it
     * to the previous total play time. The game data (including the pet, inventory, and updated play time)
     * is then serialized to JSON and written to the file.
     * </p>
     *
     * @param filename         the file path to save the game data
     * @param pet              the pet instance representing the player's pet
     * @param inventory        the player's inventory instance
     * @param previousPlayTime the previous total play time in milliseconds
     */

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
     * Loads game data from a file with the specified filename.
     * <p>
     * The method deserializes the JSON content of the file into a {@code GameData} object.
     * If the file cannot be read, the method returns {@code null}.
     * </p>
     *
     * @param filename the file path from which to load the game data
     * @return the loaded {@code GameData} object, or {@code null} if loading fails
     */

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

       /**
     * Determines whether a new game can be created based on the number of existing save files.
     * <p>
     * This method checks if the "saves/" directory exists and counts the number of JSON files.
     * A new game can be created if there are fewer than 3 save files.
     * </p>
     *
     * @return {@code true} if a new game can be created; {@code false} otherwise
     */

    public static boolean canCreateNewGame() {
        File dir = new File("saves/");
        if (dir.exists() && dir.isDirectory()) {
            return dir.listFiles((dir1, name) -> name.endsWith(".json")).length < 3;
        }
        return true; // Allow if the directory doesn't exist
    }

    /**
     * Retrieves the pet type from a saved game file.
     * <p>
     * This method attempts to load the game data from the specified file and, if successful,
     * returns the pet's type. If the file path is empty, the file doesn't exist, or loading fails,
     * the method returns a default pet type ("default").
     * </p>
     *
     * @param filePath the file path of the saved game
     * @return the pet type from the saved game, or "default" if not available
     */

     
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

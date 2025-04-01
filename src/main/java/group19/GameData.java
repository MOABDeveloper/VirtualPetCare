package group19;

/**
 * Represents the game state data, including the current pet,
 * the player's inventory, and the total play time.
 * <p>
 * The total play time is represented in milliseconds.
 * </p>
 */

public class GameData {
    private Pet pet;
    private PlayerInventory inventory;
    private long totalPlayTime; // in milliseconds, or use int for seconds


    /**
     * Constructs a new GameData instance with the specified pet, inventory, and total play time.
     *
     * @param pet           the pet associated with this game data
     * @param inventory     the player's inventory
     * @param totalPlayTime the total play time in milliseconds
     */


    public GameData(Pet pet, PlayerInventory inventory, long totalPlayTime) {
        this.pet = pet;
        this.inventory = inventory;
        this.totalPlayTime = totalPlayTime;
    }
    /**
     * Returns the pet associated with this game data.
     *
     * @return the current pet
     */

    public Pet getPet() {
        return pet;
    }

    /**
     * Returns the player's inventory associated with this game data.
     *
     * @return the player's inventory
     */

    public PlayerInventory getInventory() {
        return inventory;
    }

    /**
     * Returns the total play time.
     *
     * @return the total play time in milliseconds
     */

    public long getTotalPlayTime() {
        return totalPlayTime;
    }

    /**
     * Sets the total play time.
     *
     * @param totalPlayTime the total play time in milliseconds to set
     */
    
    public void setTotalPlayTime(long totalPlayTime) {
        this.totalPlayTime = totalPlayTime;
    }

}

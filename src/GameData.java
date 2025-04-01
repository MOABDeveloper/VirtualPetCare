package src;

public class GameData {
    private Pet pet;
    private PlayerInventory inventory;
    private long totalPlayTime; // in milliseconds, or use int for seconds

    public GameData(Pet pet, PlayerInventory inventory, long totalPlayTime) {
        this.pet = pet;
        this.inventory = inventory;
        this.totalPlayTime = totalPlayTime;
    }


    public Pet getPet() {
        return pet;
    }

    public PlayerInventory getInventory() {
        return inventory;
    }

    public long getTotalPlayTime() {
        return totalPlayTime;
    }

    public void setTotalPlayTime(long totalPlayTime) {
        this.totalPlayTime = totalPlayTime;
    }

}

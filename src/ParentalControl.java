package src;

public class ParentalControl {
    private static final String PARENT_PASSWORD = "1234";

    private boolean limitationsEnabled;
    private int allowedStartHour; // 24-hr format (e.g. 9 for 9am)
    private int allowedEndHour;   // e.g. 21 for 9pm

    private long totalPlayTime = 999;  // milliseconds
    private int sessionCount = 10;



    public boolean authenticate(String input) {
        return PARENT_PASSWORD.equals(input);
    }

    // Parental Limitations (allowed time)
    public boolean isPlayAllowedNow() {
        if (!limitationsEnabled) {
            return true;
        }
        int currentHour = java.time.LocalTime.now().getHour();
        return currentHour >= allowedStartHour && currentHour < allowedEndHour;
    }

    public void setPlayTimeWindow(int startHour, int endHour) {
        this.allowedStartHour = startHour;
        this.allowedEndHour = endHour;
    }

    public void setLimitationsEnabled(boolean enabled) {
        this.limitationsEnabled = enabled;
    }

    // Parental Statistics (total & average play time)
    public long getAveragePlayTime() {
        return sessionCount == 0 ? 0 : totalPlayTime / sessionCount;
    }

    public long getTotalPlayTime() {
        return totalPlayTime;
    }

    public void updateAfterSession(long sessionDuration) {
        totalPlayTime += sessionDuration;
        sessionCount++;
    }

    public void resetStats() {
        totalPlayTime = 0;
        sessionCount = 0;
    }

    public boolean revivePet(Pet pet) {
        if (pet.isDead()) {
            pet.resetState();
            return true;
        } else {
            return false;
        }
    }

    public void resetPlayTimeRestrictions() {
        this.limitationsEnabled = false;
        this.allowedStartHour = 0;
        this.allowedEndHour = 24;
    }

    public boolean isLimitationsEnabled() {
        return limitationsEnabled;
    }

    public int getAllowedStartHour() {
        return allowedStartHour;
    }

    public int getAllowedEndHour() {
        return allowedEndHour;
    }

}

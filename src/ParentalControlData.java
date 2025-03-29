package src;

public class ParentalControlData {
    public boolean limitationsEnabled;
    public int allowedStartHour;
    public int allowedEndHour;

    public ParentalControlData() {} // Default constructor

    public ParentalControlData(boolean enabled, int start, int end) {
        this.limitationsEnabled = enabled;
        this.allowedStartHour = start;
        this.allowedEndHour = end;
    }
}

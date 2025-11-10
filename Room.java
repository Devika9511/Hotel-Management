import java.io.Serializable;

public class Room implements Serializable {
    private static final long serialVersionUID = 1L;
    private int roomNumber;
    private String type; // e.g., Single, Double
    private double pricePerNight;
    private boolean occupied;

    public Room(int roomNumber, String type, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.pricePerNight = pricePerNight;
        this.occupied = false;
    }

    public int getRoomNumber() { return roomNumber; }
    public String getType() { return type; }
    public double getPricePerNight() { return pricePerNight; }
    public boolean isOccupied() { return occupied; }
    public void setOccupied(boolean occupied) { this.occupied = occupied; }

    @Override
    public String toString() {
        return "Room " + roomNumber + " (" + type + ") - " + (occupied ? "Occupied" : "Available") + " - $" + pricePerNight;
    }
}

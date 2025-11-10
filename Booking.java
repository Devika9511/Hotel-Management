import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Booking implements Serializable {
    private static final long serialVersionUID = 1L;
    private int roomNumber;
    private int nights;
    private List<Food> foodOrders = new ArrayList<>();
    private boolean checkedOut = false;

    public Booking(int roomNumber, int nights) {
        this.roomNumber = roomNumber;
        this.nights = nights;
    }

    public int getRoomNumber() { return roomNumber; }
    public int getNights() { return nights; }
    public List<Food> getFoodOrders() { return foodOrders; }
    public void addFood(Food f) { foodOrders.add(f); }
    public boolean isCheckedOut() { return checkedOut; }
    public void setCheckedOut(boolean checkedOut) { this.checkedOut = checkedOut; }

    public double calculateTotal(double roomPricePerNight) {
        double total = roomPricePerNight * nights;
        for (Food f : foodOrders) total += f.getPrice();
        return total;
    }

    @Override
    public String toString() {
        return "Room " + roomNumber + " for " + nights + " night(s) - Foods: " + foodOrders.size();
    }
}

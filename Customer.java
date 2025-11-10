import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id; // unique id (e.g., CUST-1)
    private String name;
    private List<Booking> bookings = new ArrayList<>();

    public Customer(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public List<Booking> getBookings() { return bookings; }

    public void addBooking(Booking b) { bookings.add(b); }

    @Override
    public String toString() {
        return id + " - " + name;
    }
}

import java.io.*;
import java.util.*;

public class Hotel implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Room> rooms = new ArrayList<>();
    private List<Food> menu = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private int nextCustomerId = 1;

    public Hotel() {
        // default sample data
        rooms.add(new Room(101, "Single", 50));
        rooms.add(new Room(102, "Double", 80));
        rooms.add(new Room(201, "Single", 55));
        rooms.add(new Room(202, "Double", 90));

        menu.add(new Food("Sandwich", 5.0));
        menu.add(new Food("Pasta", 8.5));
        menu.add(new Food("Coffee", 2.5));
        menu.add(new Food("Juice", 3.0));
    }

    public List<Room> getRooms() { return rooms; }
    public List<Food> getMenu() { return menu; }
    public List<Customer> getCustomers() { return customers; }

    public Customer addCustomer(String name) {
        String id = "CUST-" + (nextCustomerId++);
        Customer c = new Customer(id, name);
        customers.add(c);
        return c;
    }

    public Room findRoom(int roomNumber) {
        for (Room r : rooms) if (r.getRoomNumber() == roomNumber) return r;
        return null;
    }

    public Customer findCustomerById(String id) {
        for (Customer c : customers) if (c.getId().equals(id)) return c;
        return null;
    }

    // persistence
    public void saveToFile(String path) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path))) {
            out.writeObject(this);
        }
    }

    public static Hotel loadFromFile(String path) throws IOException, ClassNotFoundException {
        File f = new File(path);
        if (!f.exists()) return new Hotel();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(path))) {
            return (Hotel) in.readObject();
        }
    }
}

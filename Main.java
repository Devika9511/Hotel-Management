import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;

public class Main {
    private static final String DATA_FILE = "hotel_data.ser";
    private Hotel hotel;

    private JFrame frame;
    private DefaultListModel<Customer> customerListModel;
    private JList<Customer> customerJList;
    private DefaultListModel<Room> roomListModel;
    private JList<Room> roomJList;
    private DefaultListModel<Food> menuListModel;
    private JList<Food> menuJList;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new Main().start();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to start: " + e.getMessage());
            }
        });
    }

    private void start() throws Exception {
        hotel = Hotel.loadFromFile(DATA_FILE);
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        frame = new JFrame("Hotel Management - Swing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();

        tabs.add("Customers", createCustomersPanel());
        tabs.add("Rooms", createRoomsPanel());
        tabs.add("Menu / Food", createMenuPanel());
        tabs.add("Bookings", createBookingsPanel());

        frame.getContentPane().add(tabs, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        JButton saveBtn = new JButton("Save Data");
        saveBtn.addActionListener(e -> saveData());
        bottom.add(saveBtn);
        frame.getContentPane().add(bottom, BorderLayout.SOUTH);

        frame.setVisible(true);
        refreshAll();
    }

    private JPanel createCustomersPanel() {
        JPanel p = new JPanel(new BorderLayout());
        customerListModel = new DefaultListModel<>();
        customerJList = new JList<>(customerListModel);
        p.add(new JScrollPane(customerJList), BorderLayout.CENTER);

        JPanel controls = new JPanel();
        JButton addCustomer = new JButton("Add Customer");
        addCustomer.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(frame, "Customer name:");
            if (name != null && !name.trim().isEmpty()) {
                hotel.addCustomer(name.trim());
                refreshAll();
                saveData();
            }
        });
        JButton view = new JButton("View Bookings");
        view.addActionListener(e -> {
            Customer c = customerJList.getSelectedValue();
            if (c == null) { JOptionPane.showMessageDialog(frame, "Select a customer."); return; }
            StringBuilder sb = new StringBuilder();
            for (Booking b : c.getBookings()) {
                sb.append(b.toString()).append("\\n");
            }
            JOptionPane.showMessageDialog(frame, sb.length()==0 ? "No bookings." : sb.toString());
        });

        controls.add(addCustomer);
        controls.add(view);
        p.add(controls, BorderLayout.SOUTH);
        return p;
    }

    private JPanel createRoomsPanel() {
        JPanel p = new JPanel(new BorderLayout());
        roomListModel = new DefaultListModel<>();
        roomJList = new JList<>(roomListModel);
        p.add(new JScrollPane(roomJList), BorderLayout.CENTER);

        JPanel controls = new JPanel();
        JButton book = new JButton("Book Room for Customer");
        book.addActionListener(e -> {
            Customer c = customerJList.getSelectedValue();
            Room r = roomJList.getSelectedValue();
            if (c==null) { JOptionPane.showMessageDialog(frame, "Select a customer from Customers tab."); return; }
            if (r==null) { JOptionPane.showMessageDialog(frame, "Select a room."); return; }
            if (r.isOccupied()) { JOptionPane.showMessageDialog(frame, "Room already occupied."); return; }
            String nightsStr = JOptionPane.showInputDialog(frame, "Number of nights:");
            try {
                int nights = Integer.parseInt(nightsStr);
                Booking b = new Booking(r.getRoomNumber(), nights);
                c.addBooking(b);
                r.setOccupied(true);
                refreshAll();
                saveData();
                JOptionPane.showMessageDialog(frame, "Booked " + r);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid number.");
            }
        });
        JButton checkout = new JButton("Check-out (generate bill)");
        checkout.addActionListener(e -> {
            Customer c = customerJList.getSelectedValue();
            if (c==null) { JOptionPane.showMessageDialog(frame, "Select a customer."); return; }
            List<Booking> list = c.getBookings();
            if (list.isEmpty()) { JOptionPane.showMessageDialog(frame, "Customer has no bookings."); return; }
            Booking b = list.get(list.size()-1); // last booking
            if (b.isCheckedOut()) { JOptionPane.showMessageDialog(frame, "Already checked out."); return; }
            Room room = hotel.findRoom(b.getRoomNumber());
            double total = b.calculateTotal(room.getPricePerNight());
            b.setCheckedOut(true);
            room.setOccupied(false);
            saveData();
            JOptionPane.showMessageDialog(frame, "Bill for " + c.getName() + ": $" + total);
        });

        controls.add(book);
        controls.add(checkout);
        p.add(controls, BorderLayout.SOUTH);
        return p;
    }

    private JPanel createMenuPanel() {
        JPanel p = new JPanel(new BorderLayout());
        menuListModel = new DefaultListModel<>();
        menuJList = new JList<>(menuListModel);
        p.add(new JScrollPane(menuJList), BorderLayout.CENTER);

        JPanel controls = new JPanel();
        JButton order = new JButton("Order Food for Customer");
        order.addActionListener(e -> {
            Customer c = customerJList.getSelectedValue();
            Food f = menuJList.getSelectedValue();
            if (c==null) { JOptionPane.showMessageDialog(frame, "Select a customer."); return; }
            if (f==null) { JOptionPane.showMessageDialog(frame, "Select a food item."); return; }
            List<Booking> list = c.getBookings();
            if (list.isEmpty()) { JOptionPane.showMessageDialog(frame, "Customer has no booking to attach food to."); return; }
            Booking b = list.get(list.size()-1);
            b.addFood(f);
            saveData();
            refreshAll();
            JOptionPane.showMessageDialog(frame, "Added " + f.getName() + " to booking.");
        });

        JButton addMenu = new JButton("Add Menu Item");
        addMenu.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(frame, "Food name:");
            if (name==null || name.trim().isEmpty()) return;
            String priceStr = JOptionPane.showInputDialog(frame, "Price:");
            try {
                double price = Double.parseDouble(priceStr);
                hotel.getMenu().add(new Food(name.trim(), price));
                saveData();
                refreshAll();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid price.");
            }
        });

        controls.add(order);
        controls.add(addMenu);
        p.add(controls, BorderLayout.SOUTH);
        return p;
    }

    private JPanel createBookingsPanel() {
        JPanel p = new JPanel(new BorderLayout());
        JTextArea area = new JTextArea();
        area.setEditable(false);
        p.add(new JScrollPane(area), BorderLayout.CENTER);

        JButton refresh = new JButton("Refresh Bookings List");
        refresh.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            for (Customer c : hotel.getCustomers()) {
                sb.append(c.toString()).append("\\n");
                for (Booking b : c.getBookings()) {
                    Room r = hotel.findRoom(b.getRoomNumber());
                    sb.append("   ").append(b.toString());
                    if (r!=null) sb.append(" - room price: $").append(r.getPricePerNight());
                    sb.append(" - checkedOut: ").append(b.isCheckedOut()).append("\\n");
                }
            }
            area.setText(sb.toString());
        });

        p.add(refresh, BorderLayout.SOUTH);
        return p;
    }

    private void refreshAll() {
        customerListModel.clear();
        for (Customer c : hotel.getCustomers()) customerListModel.addElement(c);

        roomListModel.clear();
        for (Room r : hotel.getRooms()) roomListModel.addElement(r);

        menuListModel.clear();
        for (Food f : hotel.getMenu()) menuListModel.addElement(f);
    }

    private void saveData() {
        try {
            hotel.saveToFile(DATA_FILE);
            JOptionPane.showMessageDialog(frame, "Data saved to " + DATA_FILE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Failed saving: " + e.getMessage());
        }
    }
}

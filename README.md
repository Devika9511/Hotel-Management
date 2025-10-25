# 🏨 Hotel Management System

**Hotel Management System** is a desktop application developed in **Java** with a **Swing-based GUI**. It is designed to make hotel operations like room management, booking, food ordering, and billing **convenient and user-friendly**.

---

## ✨ Major Features

### 🛏 Room Management
- Stores various room types (Single, Double, Suite) with rates.
- Displays room status and information (Available / Booked).

### 📅 Booking System
- Enables users to reserve available rooms.
- Dynamically updates room status after booking.

### 🍽 Food Ordering System
- Pre-defined food menu with prices.
- Allows ordering food during a stay.

### 💰 Billing / Checkout
- Computes total bill including room charges and food orders.
- Displays total bill at checkout.

### 🖥 User-Friendly GUI
- Master menu with buttons for each operation.
- Pop-up dialogs for input, messages, and confirmations.
- Easy navigation without command-line interaction.

---

## 📝 Use Case
This system is ideal for **guesthouses or small hotels** to:
- Manage room availability
- Reserve rooms efficiently
- Take food orders
- Generate bills automatically  

It **minimizes manual labor** and ensures **orderly record keeping** of hotel operations.

---

## 🛠 Tech Stack Used

### 💻 Java
- Base programming language for **business logic** and **data management**.

### 🎨 Java Swing
- Used for designing the **Graphical User Interface (GUI)**.
- Provides windows, buttons, input dialogs, and tables for **interactive operations**.

### 📦 Object-Oriented Programming (OOP)
- **Classes:** `Room`, `Food`, `Hotel`, `Main`
- Encapsulation of room, food, billing, and GUI interactions.
- Modular implementation for easy maintenance and future updates.

### 📚 Collections
- **ArrayList:** Holds rooms and manages bookings.
- **HashMap:** Stores the food menu with prices for quick retrieval.

---

## ⚙ How the Project Functions

1. **Main Class**  
   Launches the Swing GUI with buttons for every operation.

2. **Display Room Details / Availability**  
   - Shows room information in a pop-up dialog.

3. **Book Room**  
   - User inputs room number to book.
   - Room status changes to `Booked`.

4. **Order Food**  
   - Displays menu with prices.
   - Adds selected food charges to the bill.

5. **Checkout**  
   - Displays **total bill** including room and food charges.
   - Resets bill for next customer.

---

## 🗂 Project Structure
Room.java – Represents hotel rooms (type, price, availability)

Food.java – Represents menu items (name, price)

Customer.java – Customer details and their bookings

Booking.java – Stores room booking info, nights, and ordered food

Hotel.java – Central management class for rooms, menu, and customers

Main.java – Swing-based graphical interface to interact with the system


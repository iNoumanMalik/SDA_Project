// src/main/java/model/Room.java
package model;

public class Room {
    private int roomId;
    private String roomNumber;
    private int capacity;
    private int currentOccupancy;
    private String status;
    
    // Constructors, getters, setters
    public Room(int roomId, String roomNumber, int capacity, int currentOccupancy, String status) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.currentOccupancy = currentOccupancy;
        this.status = status;
    }
    
    // Getters and setters
    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }
    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public int getCurrentOccupancy() { return currentOccupancy; }
    public void setCurrentOccupancy(int currentOccupancy) { this.currentOccupancy = currentOccupancy; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
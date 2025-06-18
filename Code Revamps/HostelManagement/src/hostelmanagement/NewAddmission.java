/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hostelmanagement;

/**
 *
 * @author SP23-BSE-168
 */

import java.util.UUID;

public class NewAddmission {
    private String id;
    private String name;
    private String email;
    private boolean admitted;
    private boolean documentsSubmitted;
    private String assignedRoomNumber; // Changed to String to match Room class

    public NewAddmission (String name, String email, boolean admitted, boolean documentsSubmitted, String assignedRoomNumber) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.admitted = admitted;
        this.documentsSubmitted = documentsSubmitted;
        this.assignedRoomNumber = assignedRoomNumber;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public boolean isAdmitted() {
        return admitted;
    }

    public boolean areDocumentsSubmitted() {
        return documentsSubmitted;
    }

    public String getAssignedRoomNumber() {
        return assignedRoomNumber;
    }

    // Setters (if needed for modification, though for this example, they aren't strictly used beyond initial creation)
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAdmitted(boolean admitted) {
        this.admitted = admitted;
    }

    public void setDocumentsSubmitted(boolean documentsSubmitted) {
        this.documentsSubmitted = documentsSubmitted;
    }

    public void setAssignedRoomNumber(String assignedRoomNumber) {
        this.assignedRoomNumber = assignedRoomNumber;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Room: " + (assignedRoomNumber != null && !assignedRoomNumber.isEmpty() ? assignedRoomNumber : "N/A") + ", Admitted: " + admitted + ", Docs: " + documentsSubmitted;
    }
}

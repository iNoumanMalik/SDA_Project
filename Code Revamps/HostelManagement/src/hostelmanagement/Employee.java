/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hostelmanagement;

/**
 *
 * @author SP23-BSE-014
 */
public class Employee {
    private String name;
    private String email;
    private String phone;
    private String experience;

    public Employee(String name, String email, String phone, String experience) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.experience = experience;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getExperience() {
        return experience;
    }

    @Override
    public String toString() {
        return name + " (" + email + ", " + phone + ", " + experience + ")";
    }

    // Equals and hashCode based on name (to consider employees equal if names are same)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Employee)) return false;
        Employee other = (Employee) obj;
        return name != null && name.equalsIgnoreCase(other.name);
    }

    @Override
    public int hashCode() {
        return name == null ? 0 : name.toLowerCase().hashCode();
    }
}


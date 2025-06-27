package hostelmanagement;

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
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getExperience() { return experience; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setExperience(String experience) { this.experience = experience; }

    @Override
    public String toString() {
        return name + " (" + email + ", " + phone + ", " + experience + ")";
    }

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

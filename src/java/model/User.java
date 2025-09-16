package model;

/**
 *
 * @author Narathip
 */
public class User {

    private int id;
    private String username;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private String password;
    private boolean isAdmin;

    public User() {
    }

    public User(int id, String username, String email, String phone, String firstName, String lastName, String password, boolean isAdmin) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password; // เก็บ plain text ตามที่คุณระบุ
        this.isAdmin = isAdmin;
    }

    // Getter และ Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password; // เก็บ plain text
    }

     public boolean getIsAdmin() {
        return isAdmin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    
     public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    // Method พิเศษ
    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return "User{username=" + username + ", email=" + email + ", phone=" + phone + "}";
    }

    public boolean isValid() {
        // ตรวจสอบแค่ที่จำเป็น (ลดความซับซ้อน)
        if (username == null || username.trim().isEmpty() || username.length() < 4 || username.length() > 50) {
            return false;
        }
        if (email == null || !email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            return false;
        }
        if (password == null || password.length() < 8) {
            return false;
        }
        return true; // ไม่ตรวจสอบ phone, firstName, lastName เพื่อลดความซับซ้อน
    }
}

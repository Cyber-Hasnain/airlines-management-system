package model;

public class Admin extends User {
    public Admin() {
        super();
        setRole("ADMIN");
    }

    public Admin(int id, String username, String password) {
        super(id, username, password, "ADMIN");
    }

    public Admin(String username, String password) {
        super(username, password, "ADMIN");
    }
}

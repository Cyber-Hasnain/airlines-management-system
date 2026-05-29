package controller;

import dao.UserDAO;
import dao.impl.UserDAOImpl;
import model.User;

public class LoginController {
    private final UserDAO userDAO;
    private static User currentUser;

    public LoginController() {
        this.userDAO = new UserDAOImpl();
    }

    public boolean login(String username, String password) {
        User user = userDAO.authenticate(username, password);
        if (user != null) {
            currentUser = user;
            return true;
        }
        return false;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void logout() {
        currentUser = null;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }
}

package dao;

import model.User;
import java.util.List;

public interface UserDAO {
    boolean add(User user);
    boolean update(User user);
    boolean delete(int id);
    User getById(int id);
    List<User> getAll();
    List<User> search(String keyword);
    User authenticate(String username, String password);
}

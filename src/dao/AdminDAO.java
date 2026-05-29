package dao;

import model.Admin;
import java.util.List;

public interface AdminDAO {
    boolean add(Admin admin);
    boolean update(Admin admin);
    boolean delete(int id);
    Admin getById(int id);
    List<Admin> getAll();
    List<Admin> search(String keyword);
}

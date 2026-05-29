package dao;

import model.Passenger;
import java.util.List;

public interface PassengerDAO {
    boolean add(Passenger passenger);
    boolean update(Passenger passenger);
    boolean delete(int id);
    Passenger getById(int id);
    List<Passenger> getAll();
    List<Passenger> search(String keyword);
}

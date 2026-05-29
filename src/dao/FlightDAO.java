package dao;

import model.Flight;
import java.util.List;

public interface FlightDAO {
    boolean add(Flight flight);
    boolean update(Flight flight);
    boolean delete(int id);
    Flight getById(int id);
    List<Flight> getAll();
    List<Flight> search(String keyword);
}

package dao;

import model.Booking;
import java.util.List;

public interface BookingDAO {
    boolean add(Booking booking);
    boolean update(Booking booking);
    boolean delete(int id);
    Booking getById(int id);
    List<Booking> getAll();
    List<Booking> search(String keyword);
    boolean isSeatOccupied(int flightId, int seatNumber);
}

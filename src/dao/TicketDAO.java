package dao;

import model.Ticket;
import java.util.List;

public interface TicketDAO {
    boolean add(Ticket ticket);
    boolean update(Ticket ticket);
    boolean delete(int id);
    Ticket getById(int id);
    List<Ticket> getAll();
    List<Ticket> search(String keyword);
    Ticket getByBookingId(int bookingId);
}

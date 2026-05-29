package dao;

import model.Payment;
import java.util.List;

public interface PaymentDAO {
    boolean add(Payment payment);
    boolean update(Payment payment);
    boolean delete(int id);
    Payment getById(int id);
    List<Payment> getAll();
    List<Payment> search(String keyword);
    Payment getByBookingId(int bookingId);
}

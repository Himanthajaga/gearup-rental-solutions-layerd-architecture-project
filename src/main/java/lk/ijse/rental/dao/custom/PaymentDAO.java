package lk.ijse.rental.dao.custom;

import lk.ijse.rental.dao.CrudDAO;
import lk.ijse.rental.entity.Payment;

import java.sql.SQLException;

public interface PaymentDAO extends CrudDAO<Payment> {
    public String currentId() throws SQLException, ClassNotFoundException;
}

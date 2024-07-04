package lk.ijse.rental.bo.custom;

import lk.ijse.rental.bo.SuperBO;
import lk.ijse.rental.dto.MechanicDTO;
import lk.ijse.rental.dto.PaymentDTO;
import lk.ijse.rental.entity.Bokking;
import lk.ijse.rental.entity.Payment;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PaymentBO extends SuperBO{
    public ArrayList<PaymentDTO> getAllPayments() throws SQLException, ClassNotFoundException;
    public boolean addPayment(PaymentDTO dto) throws SQLException, ClassNotFoundException;
    public boolean updatePayment(PaymentDTO dto) throws SQLException, ClassNotFoundException;
    public boolean existPayment(String id) throws SQLException, ClassNotFoundException;
    public String generateNewPaymentID() throws SQLException, ClassNotFoundException;
    public boolean deletePayment(String id) throws SQLException, ClassNotFoundException;
    String getLastPaymentId() throws SQLException, ClassNotFoundException;
    public Payment search(String id) throws SQLException, ClassNotFoundException;
    public String currentId() throws SQLException, ClassNotFoundException;
}

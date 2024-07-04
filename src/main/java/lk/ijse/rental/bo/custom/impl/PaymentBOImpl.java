package lk.ijse.rental.bo.custom.impl;

import lk.ijse.rental.bo.custom.PaymentBO;
import lk.ijse.rental.dao.DAOFactory;
import lk.ijse.rental.dao.custom.MechanicDAO;
import lk.ijse.rental.dao.custom.PaymentDAO;
import lk.ijse.rental.dto.PaymentDTO;
import lk.ijse.rental.entity.Payment;

import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentBOImpl implements PaymentBO {
    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.Payment);

    @Override
    public ArrayList<PaymentDTO> getAllPayments() throws SQLException, ClassNotFoundException {
        ArrayList<PaymentDTO> allPayments = new ArrayList<>();
        ArrayList<Payment> all = paymentDAO.getAll();
        for (Payment c : all) {
            allPayments.add(new PaymentDTO(c.getPaymentId(),c.getPaymentType(),c.getS_email(),c.getPaymentAmount()));
        }
        return allPayments;
    }

    @Override
    public boolean addPayment(PaymentDTO dto) throws SQLException, ClassNotFoundException {
        return paymentDAO.add(new Payment(dto.getPaymentId(),dto.getPaymentType(),dto.getS_email(),dto.getPaymentAmount()));
    }

    @Override
    public boolean updatePayment(PaymentDTO dto) throws SQLException, ClassNotFoundException {
        return paymentDAO.update(new Payment(dto.getPaymentId(),dto.getPaymentType(),dto.getS_email(),dto.getPaymentAmount()));
    }

    @Override
    public boolean existPayment(String id) throws SQLException, ClassNotFoundException {
        return paymentDAO.exist(id);
    }

    @Override
    public String generateNewPaymentID() throws SQLException, ClassNotFoundException {
        return paymentDAO.generateNewID();
    }

    @Override
    public boolean deletePayment(String id) throws SQLException, ClassNotFoundException {
        return paymentDAO.delete(id);
    }

    @Override
    public String getLastPaymentId() throws SQLException, ClassNotFoundException {
        return paymentDAO.getLastId();
    }

    @Override
    public Payment search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String currentId() throws SQLException, ClassNotFoundException {
        return paymentDAO.currentId();
    }

}

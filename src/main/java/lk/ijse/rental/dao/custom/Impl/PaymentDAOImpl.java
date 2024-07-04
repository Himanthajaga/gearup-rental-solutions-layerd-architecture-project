package lk.ijse.rental.dao.custom.Impl;

import lk.ijse.rental.dao.SQLUtil;
import lk.ijse.rental.dao.custom.PaymentDAO;
import lk.ijse.rental.entity.Mechanic;
import lk.ijse.rental.entity.Payment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentDAOImpl implements PaymentDAO {

    @Override
    public ArrayList<Payment> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Payment> allPayments = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM payment");
        while (rst.next()) {
            Payment payment = new Payment(rst.getString("p_id"), rst.getString("p_type"), rst.getString("s_email"), rst.getDouble("p_amount"));
            allPayments.add(payment);
        }
        return allPayments;
    }

    @Override
    public boolean add(Payment entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO payment (p_id,p_type,s_email,p_amount) VALUES (?,?,?,?)", entity.getPaymentId(), entity.getPaymentType(), entity.getS_email(), entity.getPaymentAmount());
    }

    @Override
    public boolean update(Payment entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE payment SET p_type = ?,s_email = ?,p_amount = ? WHERE p_id = ?", entity.getPaymentType(), entity.getS_email(), entity.getPaymentAmount(), entity.getPaymentId());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT p_id FROM payment WHERE p_id=?", id);
        return rst.next();
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT p_id FROM payment ORDER BY p_id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("p_id");
            int newPaymentId = Integer.parseInt(id.replace("P00-", "")) + 1;
            return String.format("P00-%03d", newPaymentId);
        } else {
            return "P00-001";
        }
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM payment WHERE p_id=?", id);
    }

    @Override
    public Payment search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM payment WHERE p_id=?", id + "");
        if (rst.next()) {
            return new Payment(rst.getString("p_id"), rst.getString("p_type"), rst.getString("s_email"), rst.getDouble("p_amount"));
        }
        return null;
    }

    @Override
    public <T> T searchByEmail(String name) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public <T> ArrayList<T> getEmails(String email) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public <T> T searchById(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public <T> T searchByName(String name) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String getLastId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT p_id FROM payment ORDER BY p_id DESC LIMIT 1;");
        if (rst.next()) {
            return rst.getString("p_id");
        } else {
            return null;
        }
    }

    @Override
    public String currentId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT p_id FROM payment ORDER BY CAST(SUBSTRING(p_id, 2) AS UNSIGNED) desc LIMIT 1;");
        if (rst.next()) {
            return rst.getString("p_id");
        }
        return null;
    }
}

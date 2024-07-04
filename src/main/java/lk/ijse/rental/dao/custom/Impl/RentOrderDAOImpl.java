package lk.ijse.rental.dao.custom.Impl;

import lk.ijse.rental.dao.SQLUtil;
import lk.ijse.rental.dao.custom.RentOrderDAO;
import lk.ijse.rental.entity.RentOrder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RentOrderDAOImpl implements RentOrderDAO {

    @Override
    public ArrayList<RentOrder> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<RentOrder> allRentOrders = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM rent_order");
        while (rst.next()) {
            RentOrder rentOrder = new RentOrder(rst.getString("o_id"), rst.getDate("o_date"), rst.getDate("r_date"), rst.getString("c_email"),rst.getDouble("r_total"));
            allRentOrders.add(rentOrder);
        }
        return allRentOrders;
    }

    @Override
    public boolean add(RentOrder entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO rent_order (o_id,o_date,r_date,c_email,r_total) VALUES (?,?,?,?,?)", entity.getOrderId(), entity.getDate(), entity.getReturnDate(), entity.getCustomerEmail(), entity.getTotal());
    }

    @Override
    public boolean update(RentOrder entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE rent_order SET o_date = ?,r_date = ?,c_email = ?,r_total = ? WHERE o_id = ?", entity.getDate(), entity.getReturnDate(), entity.getCustomerEmail(), entity.getTotal(), entity.getOrderId());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT o_id FROM rent_order WHERE o_id=?", id);
        return rst.next();
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT o_id FROM rent_order ORDER BY o_id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("o_id");
            int newRentOrderId = Integer.parseInt(id.replace("O00-", "")) + 1;
            return String.format("O00-%03d", newRentOrderId);
        } else {
            return "O00-001";
        }
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM rent_order WHERE o_id=?", id);
    }

    @Override
    public RentOrder search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM rent_order WHERE o_id=?", id + "");
        if (rst.next()) {
            return new RentOrder(rst.getString("o_id"), rst.getDate("o_date"), rst.getDate("r_date"), rst.getString("c_email"),rst.getDouble("r_total"));
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
        ResultSet rst = SQLUtil.execute("SELECT o_id FROM rent_order ORDER BY o_id DESC LIMIT 1;");
        if (rst.next()) {
            return rst.getString("o_id");
        } else {
            return null;
        }
    }
}

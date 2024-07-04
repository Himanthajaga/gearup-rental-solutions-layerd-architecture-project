package lk.ijse.rental.dao.custom.Impl;

import lk.ijse.rental.dao.SQLUtil;
import lk.ijse.rental.dao.custom.OrderDetailDAO;
import lk.ijse.rental.entity.Mechanic;
import lk.ijse.rental.entity.OrderDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAOImpl implements OrderDetailDAO {

    @Override
    public ArrayList<OrderDetail> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetail> allOrderDetails = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM order_detail");
        while (rst.next()) {
           OrderDetail orderDetail = new OrderDetail(rst.getString("o_id"),rst.getString("m_id"));
            allOrderDetails.add(orderDetail);
        }
        return allOrderDetails;
    }

    @Override
    public boolean add(OrderDetail entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO order_detail (o_id,m_id) VALUES (?,?)", entity.getOrderId(),entity.getMachineId());
    }

    @Override
    public boolean update(OrderDetail entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE order_detail SET m_id = ? WHERE o_id = ?",entity.getMachineId(),entity.getOrderId());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT o_id FROM order_detail WHERE o_id=?",id);
        return rst.next();
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT o_id FROM order_detail ORDER BY o_id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("o_id");
            int newOrderDetailId = Integer.parseInt(id.replace("O00-", "")) + 1;
            return String.format("O00-%03d", newOrderDetailId);
        } else {
            return "O00-001";
        }
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM order_detail WHERE o_id=?", id);
    }

    @Override
    public OrderDetail search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM order_detail WHERE o_id=?", id + "");
        if (rst.next()) {
            return new OrderDetail(rst.getString("o_id"),rst.getString("m_id"));
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
        ResultSet rst = SQLUtil.execute("SELECT o_id FROM order_detail ORDER BY o_id DESC LIMIT 1");
        if (rst.next()) {
            return rst.getString(1);
        } else {
            return null;
        }
    }

    @Override
    public boolean saved(List<OrderDetail> odList) throws SQLException, ClassNotFoundException {
        for (OrderDetail od : odList) {
            if (!add(od)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean save(OrderDetail od) throws SQLException, ClassNotFoundException {
        return add(od);
    }
}


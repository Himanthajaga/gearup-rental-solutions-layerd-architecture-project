package lk.ijse.rental.dao.custom.Impl;

import lk.ijse.rental.dao.SQLUtil;
import lk.ijse.rental.dao.custom.BokkingDAO;
import lk.ijse.rental.entity.Bokking;
import lk.ijse.rental.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BokkingDAOImpl implements BokkingDAO {
    @Override
    public ArrayList<Bokking> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Bokking> allBokkings = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM bokking");
        while (rst.next()) {
            Bokking bokking = new Bokking(rst.getString("b_id"), rst.getString("b_date"), rst.getString("c_email"), rst.getString("m_id"));
            allBokkings.add(bokking);
        }
        return allBokkings;
    }

    @Override
    public boolean add(Bokking entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO bokking (b_id,b_date,c_email,m_id) VALUES (?,?,?,?)", entity.getBokkingId(), entity.getBokkingDate(), entity.getCustomerEmail(), entity.getMachineId());

    }

    @Override
    public boolean update(Bokking entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE bokking SET b_date=?,c_email = ?,m_id = ? WHERE b_id = ?", entity.getBokkingDate(), entity.getCustomerEmail(), entity.getMachineId(), entity.getBokkingId());

    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT FROM bokking WHERE b_id=?", id);
        return rst.next();
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT b_id FROM bokking ORDER BY b_id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("b_id");
            int newBokkingId = Integer.parseInt(id.replace("B00-", "")) + 1;
            return String.format("B00-%03d", newBokkingId);
        } else {
            return "B00-001";
        }
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM bokking WHERE b_id=?", id);
    }

    @Override
    public Bokking search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM bokking WHERE b_id=?", id + "");
        rst.next();
        return new Bokking(rst.getString("b_id"), rst.getString("b_date"), rst.getString("c_email"), rst.getString("m_id"));
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
        ResultSet rst = SQLUtil.execute("SELECT b_id FROM bokking ORDER BY b_id DESC LIMIT 1");
        if (rst.next()) {
            return rst.getString(1);
        } else {
            return null;
        }
    }
    }

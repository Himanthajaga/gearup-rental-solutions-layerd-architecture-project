package lk.ijse.rental.dao.custom.Impl;

import lk.ijse.rental.dao.SQLUtil;
import lk.ijse.rental.dao.custom.SellDAO;
import lk.ijse.rental.entity.Reservation;
import lk.ijse.rental.entity.Sell;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SellDAOImpl implements SellDAO {

    @Override
    public ArrayList<Sell> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Sell> allSells = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM sell");
        while (rst.next()) {
          Sell sell   = new Sell(rst.getString("se_id"), rst.getDate("se_date"), rst.getString("c_email"),rst.getDouble("s_total"));
           allSells.add(sell);
        }
        return allSells;
    }

    @Override
    public boolean add(Sell entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO sell (se_id,se_date,c_email,s_total) VALUES (?,?,?,?)", entity.getSellId(), entity.getSellDate(), entity.getC_email(), entity.getTotal());
    }

    @Override
    public boolean update(Sell entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE sell SET se_date = ?,c_email = ?,s_total = ? WHERE se_id = ?", entity.getSellDate(), entity.getC_email(), entity.getTotal(), entity.getSellId());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT se_id FROM sell WHERE se_id=?", id);
        return rst.next();
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT se_id FROM sell ORDER BY se_id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("se_id");
            int newSellId = Integer.parseInt(id.replace("SE00-", "")) + 1;
            return String.format("SE00-%03d", newSellId);
        } else {
            return "SE00-001";
        }
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM sell WHERE se_id=?", id);
    }

    @Override
    public Sell search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM sell WHERE se_id=?", id + "");
        if (rst.next()) {
            return new Sell(rst.getString("se_id"), rst.getDate("se_date"), rst.getString("c_email"),rst.getDouble("s_total"));
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
        ResultSet rst = SQLUtil.execute("SELECT se_id FROM sell ORDER BY se_id DESC LIMIT 1;");
        if (rst.next()) {
            return rst.getString("se_id");
        }
        return null;
    }
}

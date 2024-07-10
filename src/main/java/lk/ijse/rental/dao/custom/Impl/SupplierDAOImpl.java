package lk.ijse.rental.dao.custom.Impl;

import lk.ijse.rental.dao.SQLUtil;
import lk.ijse.rental.dao.custom.SupplierDAO;
import lk.ijse.rental.entity.BuildingMaterial;
import lk.ijse.rental.entity.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAOImpl implements SupplierDAO {

    @Override
    public ArrayList<Supplier> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Supplier> allSuppliers = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM supplier");
        while (rst.next()) {
            Supplier supplier = new Supplier(rst.getString("s_id"), rst.getString("s_name"), rst.getString("s_address"), rst.getString("s_tel"),rst.getString("s_email"));
            allSuppliers.add(supplier);
        }
        return allSuppliers;
    }

    @Override
    public boolean add(Supplier entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO supplier (s_email,s_name,s_address,s_tel,s_id) VALUES (?,?,?,?,?)", entity.getS_email(), entity.getS_name(), entity.getS_address(), entity.getS_tel(),entity.getS_id());
    }

    @Override
    public boolean update(Supplier entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE supplier SET s_email=?,s_name=?, s_address=?, s_tel=? WHERE s_id=?",  entity.getS_email(),entity.getS_name(), entity.getS_address(), entity.getS_tel(),entity.getS_id());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT s_email FROM supplier WHERE s_email=?", id);
        return rst.next();
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT s_id FROM supplier ORDER BY s_id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("s_id");
            int newSupplierId = Integer.parseInt(id.replace("S00-", "")) + 1;
            return String.format("S00-%03d", newSupplierId);
        } else {
            return "S00-001";
        }
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM supplier WHERE s_id=?", id);
    }

    @Override
    public Supplier search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM supplier WHERE s_email=?", id + "");
        if (rst.next()) {
            return new Supplier(rst.getString("s_email"), rst.getString("s_name"), rst.getString("s_address"), rst.getString("s_tel"),rst.getString("s_id"));
        }
        return null;
    }

    @Override
    public <T> T searchByEmail(String name) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM supplier WHERE s_email=?", name + "");
        rst.next();
        return (T) new Supplier(rst.getString("s_email"), rst.getString("s_name"), rst.getString("s_address"), rst.getString("s_tel"),rst.getString("s_id"));
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
        ResultSet rst = SQLUtil.execute("SELECT s_id FROM supplier ORDER BY s_id DESC LIMIT 1");
        if (rst.next()) {
            return rst.getString(1);
        } else {
            return null;
        }
    }

    @Override
    public List<String> getIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT s_email FROM supplier");
        List<String> idList = new ArrayList<>();
        while (rst.next()) {
            idList.add(rst.getString("s_email"));
        }
        return idList;
    }
}

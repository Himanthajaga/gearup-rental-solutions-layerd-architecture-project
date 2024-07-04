package lk.ijse.rental.dao.custom.Impl;

import lk.ijse.rental.dao.SQLUtil;
import lk.ijse.rental.dao.custom.CustomerDAO;
import lk.ijse.rental.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> allCustomers = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM customer");
        while (rst.next()) {
            Customer customer = new Customer(rst.getString("c_email"), rst.getString("c_name"), rst.getString("c_address"),rst.getString("c_tel"),rst.getString("c_id"));
            allCustomers.add(customer);
        }
        return allCustomers;
    }

    @Override
    public boolean add(Customer entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO customer (c_email,c_name,c_address,c_tel,c_id) VALUES (?,?,?,?,?)", entity.getC_mail(),entity.getC_name(),entity.getC_address(),entity.getC_tel(),entity.getC_id());

    }

    @Override
    public boolean update(Customer entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE customer SET c_email = ?,c_name=?,c_address = ?,c_tel = ? WHERE c_id = ?",entity.getC_mail(),entity.getC_name(),entity.getC_address(),entity.getC_tel(),entity.getC_id());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT id FROM customer WHERE c_id=?",id);
        return rst.next();
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT c_id FROM customer ORDER BY c_id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("c_id");
            int newCustomerId = Integer.parseInt(id.replace("C00-", "")) + 1;
            return String.format("C00-%03d", newCustomerId);
        } else {
            return "C00-001";
        }
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM customer WHERE c_id=?", id);
    }

    @Override
    public Customer search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM customer WHERE c_id=?", id + "");
        rst.next();
        return new Customer(id + "", rst.getString("c_email"), rst.getString("c_name"),rst.getString("c_address"),rst.getString("c_tel"));
    }

    @Override
    public String getLastId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT c_id FROM customer ORDER BY c_id DESC LIMIT 1;");
        if (rst.next()) {
            return rst.getString("c_id");
        }
        return null;
    }

    @Override
    public ArrayList<Customer> getEmails(String email) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public Customer searchById(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM customer WHERE c_id=?", id + "");
        rst.next();
        return new Customer(id + "", rst.getString("c_email"), rst.getString("c_name"),rst.getString("c_address"),rst.getString("c_tel"));
    }

    @Override
    public Customer searchByName(String name) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM customer WHERE c_name=?", name + "");
        rst.next();
        return new Customer(name + "", rst.getString("c_email"),rst.getString("c_address"),rst.getString("c_tel"),rst.getString("c_id"));
    }

    @Override
    public String currentID() throws SQLException, ClassNotFoundException {

        return null;
    }

    @Override
    public List<String> getIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT c_email FROM customer");
        List<String> idList = new ArrayList<>();
        while (rst.next()) {
            idList.add(rst.getString("c_email"));
        }
        return idList;
    }

    @Override
    public Customer searchByEmail(String email) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM customer WHERE c_email=?", email + "");
        rst.next();
        return new Customer(email + "", rst.getString("c_name"),rst.getString("c_address"),rst.getString("c_tel"),rst.getString("c_id"));

    }

}

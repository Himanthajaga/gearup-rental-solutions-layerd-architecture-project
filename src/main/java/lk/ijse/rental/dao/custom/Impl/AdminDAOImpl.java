package lk.ijse.rental.dao.custom.Impl;

import javafx.scene.layout.AnchorPane;
import lk.ijse.rental.dao.SQLUtil;
import lk.ijse.rental.dao.custom.AdminDAO;
import lk.ijse.rental.entity.Admin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminDAOImpl implements AdminDAO {
    private AnchorPane rootNode;

    @Override
    public ArrayList<Admin> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Admin> allAdmins = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM admin");
        while (rst.next()) {
            Admin admin = new Admin(rst.getString("a_id"), rst.getString("a_name"), rst.getString("a_password"), rst.getString("a_confirmPasword"), rst.getString("a_email"));
            allAdmins.add(admin);
        }
        return allAdmins;
    }

    @Override
    public boolean add(Admin entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO admin (a_id,a_name,a_password,a_confirmPasword,a_email) VALUES (?,?,?,?,?)", entity.getA_id(), entity.getA_name(), entity.getA_password(), entity.getA_confirmPassword(), entity.getA_email());

    }

    @Override
    public boolean update(Admin entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE admin SET a_name = ?,a_password = ?,a_confirmPasword = ?,a_email = ? WHERE a_id = ?", entity.getA_name(), entity.getA_password(), entity.getA_confirmPassword(), entity.getA_email(), entity.getA_id());

    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT a_id FROM admin WHERE a_id=?", id);
        return rst.next();
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT a_id FROM admin ORDER BY a_id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("a_id");
            int newAdminId = Integer.parseInt(id.replace("A00-", "")) + 1;
            return String.format("B00-%03d", newAdminId);
        } else {
            return "A00-001";
        }
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM admin WHERE a_id=?", id);

    }

    @Override
    public Admin search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM admin WHERE a_id=?", id + "");
        rst.next();
        return new Admin(rst.getString("a_id"), rst.getString("a_name"), rst.getString("a_password"), rst.getString("a_confirmPassword"), rst.getString("a_email"));

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
        ResultSet rst = SQLUtil.execute("SELECT a_id FROM admin ORDER BY a_id DESC LIMIT 1");
        if (rst.next()) {
            return rst.getString(1);
        } else {
            return null;
        }
    }
@Override
    public boolean getUser(String adminUser, String adminPass) throws Exception {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM admin WHERE a_name =? AND a_password= ?", adminUser, adminPass);
        if (resultSet.next()) {
            return true;
        }
        return false;
    }

    @Override
    public String searchUser(String user, String pass) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT a_name FROM admin WHERE a_name = ? AND a_password = ?", user, pass);
        String name = "null";
        if (resultSet.next()) {
            String names = resultSet.getString(1);
            name = names;
        }
        return name;
    }
}

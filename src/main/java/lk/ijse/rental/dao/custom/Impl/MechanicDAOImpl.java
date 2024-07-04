package lk.ijse.rental.dao.custom.Impl;

import lk.ijse.rental.dao.SQLUtil;
import lk.ijse.rental.dao.custom.MechanicDAO;
import lk.ijse.rental.entity.Admin;
import lk.ijse.rental.entity.Machine;
import lk.ijse.rental.entity.Mechanic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MechanicDAOImpl implements MechanicDAO {

    @Override
        public ArrayList<Mechanic> getAll() throws SQLException, ClassNotFoundException {
            ArrayList<Mechanic> allMechanics = new ArrayList<>();
            ResultSet rst = SQLUtil.execute("SELECT * FROM mechanic");
            while (rst.next()) {
                Mechanic mechanic = new Mechanic(rst.getString("mec_id"), rst.getString("mec_name"), rst.getString("mec_address"), rst.getString("mec_tel"), rst.getString("mec_desc"), rst.getString("mec_salary"));
              allMechanics.add(mechanic);
            }
            return allMechanics;
        }



    @Override
    public boolean add(Mechanic entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO mechanic (mec_id,mec_name,mec_address,mec_tel,mec_desc,mec_salary) VALUES (?,?,?,?,?,?)", entity.getMec_id(), entity.getMec_name(), entity.getMec_address(), entity.getMec_tel(), entity.getMec_desc(), entity.getMec_salary());
    }

    @Override
    public boolean update(Mechanic entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE mechanic SET mec_name = ?,mec_address = ?,mec_tel = ?,mec_desc = ?,mec_salary = ? WHERE mec_id = ?", entity.getMec_name(), entity.getMec_address(), entity.getMec_tel(), entity.getMec_desc(), entity.getMec_salary(), entity.getMec_id());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT mec_id FROM mechanic WHERE mec_id=?", id);
        return rst.next();
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT mec_id FROM mechanic ORDER BY mec_id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("mec_id");
            int newMechanicId = Integer.parseInt(id.replace("MEC00-", "")) + 1;
            return String.format("MEC00-%03d", newMechanicId);
        } else {
            return "MEC00-001";
        }
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM mechanic WHERE mec_id=?", id);
    }

    @Override
    public Mechanic search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM mechanic WHERE mec_id=?", id + "");
        if (rst.next()) {
            return new Mechanic(rst.getString("mec_id"), rst.getString("mec_name"), rst.getString("mec_address"), rst.getString("mec_tel"), rst.getString("mec_desc"), rst.getString("mec_salary"));
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
        ResultSet rst = SQLUtil.execute("SELECT mec_id FROM mechanic ORDER BY mec_id DESC LIMIT 1;");
        if (rst.next()) {
            return rst.getString("mec_id");
        }
        return null;
    }
}

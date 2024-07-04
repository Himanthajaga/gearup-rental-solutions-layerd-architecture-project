package lk.ijse.rental.dao.custom.Impl;

import lk.ijse.rental.dao.SQLUtil;
import lk.ijse.rental.dao.custom.MachineDAO;
import lk.ijse.rental.entity.Machine;
import lk.ijse.rental.entity.OrderDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MachineDAOImpl implements MachineDAO {

    @Override
    public ArrayList<Machine> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Machine> allMachines = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM machine");
        while (rst.next()) {
            Machine machine = new Machine(rst.getString("m_id"), rst.getString("m_name"), rst.getString("m_desc"), rst.getString("m_rental_price"), rst.getString("isAvailable"),rst.getString("c_email"));
            allMachines.add(machine);
        }
        return allMachines;
    }

    @Override
    public boolean add(Machine entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO machine (m_id,m_name,m_desc,m_rental_price,isAvailable) VALUES (?,?,?,?,?)", entity.getM_Id(), entity.getM_Name(), entity.getM_desc(), entity.getM_rental_price(), entity.getIsAvaiable());
    }

    @Override
    public boolean update(Machine entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE machine SET m_name=?,m_desc=?,m_rental_price=?,isAvailable=? WHERE m_id=?", entity.getM_Name(), entity.getM_desc(), entity.getM_rental_price(), entity.getIsAvaiable(), entity.getM_Id());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT m_id FROM machine WHERE m_id=?", id);
        return rst.next();
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT m_id FROM machine ORDER BY m_id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("m_id");
            int newMachineId = Integer.parseInt(id.replace("M00-", "")) + 1;
            return String.format("M00-%03d", newMachineId);
        } else {
            return "M00-001";
        }
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM machine WHERE m_id=?", id);
    }

    @Override
    public Machine search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM machine WHERE m_id=?", id + "");
        if (rst.next()) {
            return new Machine(rst.getString("m_id"), rst.getString("m_name"), rst.getString("m_desc"), rst.getString("m_rental_price"), rst.getString("isAvailable"),rst.getString("c_email"));
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
        ResultSet rst = SQLUtil.execute("SELECT m_id FROM machine ORDER BY m_id DESC LIMIT 1;");
        if (rst.next()) {
            return rst.getString("m_id");
        }
        return null;
    }

    @Override
    public boolean checkAvailability(String machineId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT isAvailable FROM machine WHERE m_id=?", machineId);
        if (rst.next()) {
            return rst.getString("isAvailable").equals("1");
        }
        return false;
    }

    @Override
    public boolean isAvailable(List<OrderDetail> odList) throws SQLException, ClassNotFoundException {
        for (OrderDetail orderDetail : odList) {
            if (checkAvailability(orderDetail.getMachineId())) {
                updateAvailable(orderDetail.getMachineId());
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean updateAvailable(String machine) throws SQLException, ClassNotFoundException {
        SQLUtil.execute("UPDATE machine SET isAvailable = ? WHERE m_id = ?", "0", machine);
        return true;
    }

    @Override
    public List<String> getIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT m_id FROM machine");
        List<String> idList = new ArrayList<>();
        while (rst.next()) {
            idList.add(rst.getString("m_id"));
        }
        return idList;
    }

    @Override
    public boolean updateIsAvailable(String code, String number) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE machine SET isAvailable = ? WHERE m_id = ?", number, code);
    }

    @Override
    public String getLastMachineId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT m_id FROM machine ORDER BY m_id DESC LIMIT 1;");
        if (rst.next()) {
            return rst.getString("m_id");
        }
        return null;
    }
}

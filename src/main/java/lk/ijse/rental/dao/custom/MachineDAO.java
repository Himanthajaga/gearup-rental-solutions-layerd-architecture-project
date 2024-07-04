package lk.ijse.rental.dao.custom;

import lk.ijse.rental.dao.CrudDAO;
import lk.ijse.rental.entity.Machine;
import lk.ijse.rental.entity.OrderDetail;

import java.sql.SQLException;
import java.util.List;

public interface MachineDAO extends CrudDAO<Machine> {
    boolean checkAvailability(String machineId) throws SQLException, ClassNotFoundException;
     boolean isAvailable(List<OrderDetail> odList) throws SQLException, ClassNotFoundException;
     boolean updateAvailable(String machine) throws SQLException, ClassNotFoundException;
     List<String> getIds() throws SQLException, ClassNotFoundException;
     boolean updateIsAvailable(String code, String number) throws SQLException, ClassNotFoundException;
     String getLastMachineId() throws SQLException, ClassNotFoundException;
}

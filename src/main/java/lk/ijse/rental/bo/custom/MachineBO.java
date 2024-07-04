package lk.ijse.rental.bo.custom;

import lk.ijse.rental.bo.SuperBO;
import lk.ijse.rental.dto.MachineDTO;
import lk.ijse.rental.entity.Machine;
import lk.ijse.rental.entity.OrderDetail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface MachineBO extends SuperBO {
    public ArrayList<MachineDTO> getAllMachines() throws SQLException, ClassNotFoundException;
    public boolean addMachine(MachineDTO dto) throws SQLException, ClassNotFoundException;
    public boolean updateMachine(MachineDTO dto) throws SQLException, ClassNotFoundException;
    public boolean existMachine(String id) throws SQLException, ClassNotFoundException;
    public String generateNewMachineID() throws SQLException, ClassNotFoundException;
    public boolean deleteMachine(String id) throws SQLException, ClassNotFoundException;
    String getLastMachineId() throws SQLException, ClassNotFoundException;
    List<String> getIds() throws SQLException, ClassNotFoundException;
    boolean checkAvailability(String machineId) throws SQLException, ClassNotFoundException;
    boolean isAvailable(List<OrderDetail> odList) throws SQLException, ClassNotFoundException;
    void updateAvailable(String machine) throws SQLException, ClassNotFoundException;
    boolean updateIsAvailable(String code, String number) throws SQLException, ClassNotFoundException;
    public Machine search(String id) throws SQLException, ClassNotFoundException;
}

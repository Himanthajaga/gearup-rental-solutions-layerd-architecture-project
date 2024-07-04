package lk.ijse.rental.bo.custom;

import lk.ijse.rental.bo.SuperBO;
import lk.ijse.rental.dto.MechanicDTO;
import lk.ijse.rental.entity.Bokking;
import lk.ijse.rental.entity.Mechanic;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MechanicBO extends SuperBO {
    public ArrayList<MechanicDTO> getAllMechanics() throws SQLException, ClassNotFoundException;
    public boolean addMechanic(MechanicDTO dto) throws SQLException, ClassNotFoundException;
    public boolean updateMechanic(MechanicDTO dto) throws SQLException, ClassNotFoundException;
    public boolean existMechanic(String id) throws SQLException, ClassNotFoundException;
    public String generateNewMechanicID() throws SQLException, ClassNotFoundException;
    public boolean deleteMechanic(String id) throws SQLException, ClassNotFoundException;
    String getLastMechanicId() throws SQLException, ClassNotFoundException;
    public Mechanic search(String id) throws SQLException, ClassNotFoundException;
}

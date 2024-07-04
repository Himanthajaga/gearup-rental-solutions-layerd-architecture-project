package lk.ijse.rental.bo.custom.impl;

import lk.ijse.rental.bo.custom.MachineBO;
import lk.ijse.rental.dao.DAOFactory;
import lk.ijse.rental.dao.custom.MachineDAO;
import lk.ijse.rental.dto.MachineDTO;
import lk.ijse.rental.entity.Machine;
import lk.ijse.rental.entity.OrderDetail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MachineBOImpl implements MachineBO {
    MachineDAO machineDAO = (MachineDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.Machine);

    @Override
    public ArrayList<MachineDTO> getAllMachines() throws SQLException, ClassNotFoundException {
        ArrayList<MachineDTO> allMachines = new ArrayList<>();
        ArrayList<Machine> all = machineDAO.getAll();
        for (Machine c : all) {
            allMachines.add(new MachineDTO(c.getM_Id(),c.getM_Name(),c.getM_desc(),c.getM_rental_price(),c.getIsAvaiable(),c.getC_email()));
        }
        return allMachines;
    }

    @Override
    public boolean addMachine(MachineDTO dto) throws SQLException, ClassNotFoundException {
        return machineDAO.add(new Machine(dto.getM_Id(),dto.getM_Name(),dto.getM_desc(),dto.getM_rental_price(),dto.getIsAvaiable(),dto.getC_email()));
    }

    @Override
    public boolean updateMachine(MachineDTO dto) throws SQLException, ClassNotFoundException {
        return machineDAO.update(new Machine(dto.getM_Id(),dto.getM_Name(),dto.getM_desc(),dto.getM_rental_price(),dto.getIsAvaiable(),dto.getC_email()));
    }

    @Override
    public boolean existMachine(String id) throws SQLException, ClassNotFoundException {
        return machineDAO.exist(id);
    }

    @Override
    public String generateNewMachineID() throws SQLException, ClassNotFoundException {
        return machineDAO.generateNewID();
    }

    @Override
    public boolean deleteMachine(String id) throws SQLException, ClassNotFoundException {
        return machineDAO.delete(id);
    }

    @Override
    public String getLastMachineId() throws SQLException, ClassNotFoundException {
        return machineDAO.getLastMachineId();
    }

    @Override
    public List<String> getIds() throws SQLException, ClassNotFoundException {
        return machineDAO.getIds();
    }

    @Override
    public boolean checkAvailability(String machineId) throws SQLException, ClassNotFoundException {
        return machineDAO.checkAvailability(machineId);
    }

    @Override
    public boolean isAvailable(List<OrderDetail> odList) throws SQLException, ClassNotFoundException {
        return machineDAO.isAvailable(odList);
    }

    @Override
    public void updateAvailable(String machine) throws SQLException, ClassNotFoundException {
        machineDAO.updateAvailable(machine);

    }

    @Override
    public boolean updateIsAvailable(String code, String number) throws SQLException, ClassNotFoundException {
        return machineDAO.updateIsAvailable(code,number);
    }

    @Override
    public Machine search(String id) throws SQLException, ClassNotFoundException {
        return machineDAO.search(id);
    }
}

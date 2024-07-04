package lk.ijse.rental.bo.custom.impl;

import lk.ijse.rental.bo.custom.MechanicBO;
import lk.ijse.rental.dao.DAOFactory;
import lk.ijse.rental.dao.custom.MechanicDAO;
import lk.ijse.rental.dto.MachineDTO;
import lk.ijse.rental.dto.MechanicDTO;
import lk.ijse.rental.entity.Machine;
import lk.ijse.rental.entity.Mechanic;

import java.sql.SQLException;
import java.util.ArrayList;

public class MechanicBOImpl implements MechanicBO {
    MechanicDAO mechanicDAO = (MechanicDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.Mechanic);

    @Override
    public ArrayList<MechanicDTO> getAllMechanics() throws SQLException, ClassNotFoundException {
        ArrayList<MechanicDTO> allMechanics = new ArrayList<>();
        ArrayList<Mechanic> all = mechanicDAO.getAll();
        for (Mechanic c : all) {
            allMechanics.add(new MechanicDTO(c.getMec_id(),c.getMec_name(),c.getMec_address(),c.getMec_tel(),c.getMec_desc(),c.getMec_salary()));
        }
        return allMechanics;
    }

    @Override
    public boolean addMechanic(MechanicDTO dto) throws SQLException, ClassNotFoundException {
        return mechanicDAO.add(new Mechanic(dto.getMec_id(),dto.getMec_name(),dto.getMec_address(),dto.getMec_tel(),dto.getMec_desc(),dto.getMec_salary()));
    }

    @Override
    public boolean updateMechanic(MechanicDTO dto) throws SQLException, ClassNotFoundException {
        return mechanicDAO.update(new Mechanic(dto.getMec_id(),dto.getMec_name(),dto.getMec_address(),dto.getMec_tel(),dto.getMec_desc(),dto.getMec_salary()));
    }

    @Override
    public boolean existMechanic(String id) throws SQLException, ClassNotFoundException {
        return mechanicDAO.exist(id);
    }

    @Override
    public String generateNewMechanicID() throws SQLException, ClassNotFoundException {
        return mechanicDAO.generateNewID();
    }

    @Override
    public boolean deleteMechanic(String id) throws SQLException, ClassNotFoundException {
        return mechanicDAO.delete(id);
    }

    @Override
    public String getLastMechanicId() throws SQLException, ClassNotFoundException {
        return mechanicDAO.getLastId();
    }

    @Override
    public Mechanic search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}

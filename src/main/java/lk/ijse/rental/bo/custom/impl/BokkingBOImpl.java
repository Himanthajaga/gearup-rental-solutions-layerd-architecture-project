package lk.ijse.rental.bo.custom.impl;

import lk.ijse.rental.bo.custom.BokkingBO;
import lk.ijse.rental.dao.DAOFactory;
import lk.ijse.rental.dao.custom.BokkingDAO;
import lk.ijse.rental.dto.AdminDTO;
import lk.ijse.rental.dto.BokkingDTO;
import lk.ijse.rental.entity.Admin;
import lk.ijse.rental.entity.Bokking;

import java.sql.SQLException;
import java.util.ArrayList;

public class BokkingBOImpl implements BokkingBO {
    BokkingDAO bokkingDAO = (BokkingDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.Bokking);

    @Override
    public ArrayList<BokkingDTO> getAllBokkings() throws SQLException, ClassNotFoundException {
        ArrayList<BokkingDTO> allbokkings= new ArrayList<>();
        ArrayList<Bokking> all = bokkingDAO.getAll();
        for (Bokking c : all) {
            allbokkings.add(new BokkingDTO(c.getBokkingId(),c.getBokkingDate(),c.getCustomerEmail(),c.getMachineId()));
        }
        return allbokkings;
    }

    @Override
    public boolean addBokking(BokkingDTO dto) throws SQLException, ClassNotFoundException {
        return bokkingDAO.add(new Bokking(dto.getBokkingId(),dto.getBokkingDate(),dto.getCustomerEmail(),dto.getMachineId()));
    }

    @Override
    public boolean updateBokking(BokkingDTO dto) throws SQLException, ClassNotFoundException {
        return bokkingDAO.update(new Bokking(dto.getBokkingId(),dto.getBokkingDate(),dto.getCustomerEmail(),dto.getMachineId()));
    }

    @Override
    public boolean existBokking(String id) throws SQLException, ClassNotFoundException {
        return bokkingDAO.exist(id);
    }

    @Override
    public String generateNewBokkingID() throws SQLException, ClassNotFoundException {
        return bokkingDAO.generateNewID();
    }

    @Override
    public boolean deleteBokking(String id) throws SQLException, ClassNotFoundException {
        return bokkingDAO.delete(id);
    }

    @Override
    public String getLastBokkingId() throws SQLException, ClassNotFoundException {
        return bokkingDAO.getLastId();
    }

    @Override
    public Bokking search(String id) throws SQLException, ClassNotFoundException {
        return bokkingDAO.search(id);
    }
}

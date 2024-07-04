package lk.ijse.rental.bo.custom.impl;

import lk.ijse.rental.bo.custom.AdminBO;
import lk.ijse.rental.dao.DAOFactory;
import lk.ijse.rental.dao.custom.AdminDAO;
import lk.ijse.rental.dto.AdminDTO;
import lk.ijse.rental.entity.Admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminBOImpl implements AdminBO {
    public static String adminId;
    AdminDAO adminDAO = (AdminDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.Admin);

    @Override
    public ArrayList<AdminDTO> getAllAdmins() throws SQLException, ClassNotFoundException {
        ArrayList<AdminDTO> allaAdmins= new ArrayList<>();
        ArrayList<Admin> all = adminDAO.getAll();
        for (Admin c : all) {
            allaAdmins.add(new AdminDTO(c.getA_id(), c.getA_name(),c.getA_password(),c.getA_confirmPassword(),c.getA_email()));
        }
        return allaAdmins;
    }

    @Override
    public boolean addAdmin(AdminDTO dto) throws SQLException, ClassNotFoundException {
        return adminDAO.add(new Admin(dto.getA_id(), dto.getA_name(), dto.getA_password(), dto.getA_confirmPassword(),dto.getA_email()));
    }

    @Override
    public boolean updateAdmin(AdminDTO dto) throws SQLException, ClassNotFoundException {
        return adminDAO.update(new Admin(dto.getA_id(), dto.getA_name(), dto.getA_password(), dto.getA_confirmPassword(),dto.getA_email()));
    }

    @Override
    public boolean existAdmin(String id) throws SQLException, ClassNotFoundException {
        return adminDAO.exist(id);
    }

    @Override
    public String generateNewAdminID() throws SQLException, ClassNotFoundException {
        return adminDAO.generateNewID();
    }

    @Override
    public boolean deleteAdmin(String id) throws SQLException, ClassNotFoundException {
        return adminDAO.delete(id);
    }

    @Override
    public String getLastAdminId() throws SQLException, ClassNotFoundException {
        return adminDAO.getLastId();
    }

    @Override
    public boolean getUser(String adminUser, String adminPass) throws Exception {
        return adminDAO.getUser(adminUser,adminPass);
    }

    @Override
    public String searchUser(String user, String pass) throws SQLException, ClassNotFoundException {
        return adminDAO.searchUser(user,pass);
    }


}

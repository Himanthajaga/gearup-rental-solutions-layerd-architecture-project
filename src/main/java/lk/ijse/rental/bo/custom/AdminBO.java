package lk.ijse.rental.bo.custom;

import lk.ijse.rental.bo.SuperBO;
import lk.ijse.rental.dto.AdminDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface AdminBO extends SuperBO {
    public ArrayList<AdminDTO> getAllAdmins() throws SQLException, ClassNotFoundException;
    public boolean addAdmin(AdminDTO dto) throws SQLException, ClassNotFoundException;
    public boolean updateAdmin(AdminDTO dto) throws SQLException, ClassNotFoundException;
    public boolean existAdmin(String id) throws SQLException, ClassNotFoundException;
    public String generateNewAdminID() throws SQLException, ClassNotFoundException;
    public boolean deleteAdmin(String id) throws SQLException, ClassNotFoundException;

    String getLastAdminId() throws SQLException, ClassNotFoundException;
    boolean getUser(String adminUser, String adminPass) throws Exception;

    String searchUser(String user, String pass) throws SQLException, ClassNotFoundException;
}

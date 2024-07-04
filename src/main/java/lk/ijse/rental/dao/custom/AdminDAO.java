package lk.ijse.rental.dao.custom;

import lk.ijse.rental.dao.CrudDAO;
import lk.ijse.rental.entity.Admin;

import java.io.IOException;
import java.sql.SQLException;

public interface AdminDAO extends CrudDAO<Admin> {

    boolean getUser(String adminUser, String adminPass) throws Exception;

    String searchUser(String user, String pass) throws SQLException, ClassNotFoundException;
}

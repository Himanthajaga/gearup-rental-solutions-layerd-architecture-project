package lk.ijse.rental.dao.custom;

import lk.ijse.rental.dao.CrudDAO;
import lk.ijse.rental.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CustomerDAO extends CrudDAO<Customer> {
    String currentID() throws SQLException, ClassNotFoundException;
    List<String> getIds() throws SQLException, ClassNotFoundException;

}

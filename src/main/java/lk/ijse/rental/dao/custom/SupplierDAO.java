package lk.ijse.rental.dao.custom;

import lk.ijse.rental.dao.CrudDAO;
import lk.ijse.rental.entity.Supplier;

import java.sql.SQLException;
import java.util.List;

public interface SupplierDAO extends CrudDAO<Supplier> {
    List<String> getIds() throws SQLException, ClassNotFoundException;
}

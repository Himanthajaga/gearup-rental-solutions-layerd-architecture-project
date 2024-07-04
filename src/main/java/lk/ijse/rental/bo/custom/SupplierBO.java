package lk.ijse.rental.bo.custom;

import lk.ijse.rental.bo.SuperBO;
import lk.ijse.rental.dto.SupplierDTO;
import lk.ijse.rental.entity.Bokking;
import lk.ijse.rental.entity.Customer;
import lk.ijse.rental.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface SupplierBO extends SuperBO {
    public ArrayList<SupplierDTO> getAllSuppliers() throws SQLException, ClassNotFoundException;
    public boolean addSupplier(SupplierDTO dto) throws SQLException, ClassNotFoundException;
    public boolean updateSupplier(SupplierDTO dto) throws SQLException, ClassNotFoundException;
    public boolean existSupplier(String id) throws SQLException, ClassNotFoundException;
    public String generateNewSupplierID() throws SQLException, ClassNotFoundException;
    public boolean deleteSupplier(String id) throws SQLException, ClassNotFoundException;
    String getLastSupplierId() throws SQLException, ClassNotFoundException;
    public Supplier search(String id) throws SQLException, ClassNotFoundException;
    List<String> getIds() throws SQLException, ClassNotFoundException;
    public Supplier searchByEmail(String email) throws SQLException, ClassNotFoundException;
}

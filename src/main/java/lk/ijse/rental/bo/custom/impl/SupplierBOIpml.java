package lk.ijse.rental.bo.custom.impl;

import lk.ijse.rental.bo.custom.SupplierBO;
import lk.ijse.rental.dao.DAOFactory;
import lk.ijse.rental.dao.custom.SupplierDAO;
import lk.ijse.rental.dto.SupplierDTO;
import lk.ijse.rental.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierBOIpml implements SupplierBO {
    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.Supplier);

    @Override
    public ArrayList<SupplierDTO> getAllSuppliers() throws SQLException, ClassNotFoundException {
        ArrayList<SupplierDTO> allSuppliers = new ArrayList<>();
        ArrayList<Supplier> all = supplierDAO.getAll();
        for (Supplier c : all) {
            allSuppliers.add(new SupplierDTO(c.getS_email(),c.getS_name(),c.getS_address(),c.getS_tel(),c.getS_id()));
        }
        return allSuppliers;
    }

    @Override
    public boolean addSupplier(SupplierDTO dto) throws SQLException, ClassNotFoundException {
        return supplierDAO.add(new Supplier(dto.getS_email(),dto.getS_name(),dto.getS_address(),dto.getS_tel(),dto.getS_id()));
    }

    @Override
    public boolean updateSupplier(SupplierDTO dto) throws SQLException, ClassNotFoundException {
        return supplierDAO.update(new Supplier(dto.getS_email(),dto.getS_name(),dto.getS_address(),dto.getS_tel(),dto.getS_id()));
    }

    @Override
    public boolean existSupplier(String id) throws SQLException, ClassNotFoundException {
        return supplierDAO.exist(id);
    }

    @Override
    public String generateNewSupplierID() throws SQLException, ClassNotFoundException {
        return supplierDAO.generateNewID();
    }

    @Override
    public boolean deleteSupplier(String id) throws SQLException, ClassNotFoundException {
        return supplierDAO.delete(id);
    }

    @Override
    public String getLastSupplierId() throws SQLException, ClassNotFoundException {
        return supplierDAO.getLastId();
    }

    @Override
    public Supplier search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<String> getIds() throws SQLException, ClassNotFoundException {
        return supplierDAO.getIds();
    }

    @Override
    public Supplier searchByEmail(String email) throws SQLException, ClassNotFoundException {
        return supplierDAO.searchByEmail(email);
    }
}

package lk.ijse.rental.bo.custom.impl;

import lk.ijse.rental.bo.custom.SupplierBuildingMaterialBO;
import lk.ijse.rental.dao.DAOFactory;
import lk.ijse.rental.dao.custom.SupplierBuildingMaterialDAO;
import lk.ijse.rental.dto.SupplierBuildingmaterialDTO;
import lk.ijse.rental.entity.SupplierBuildingmaterial;

import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierBuildingMaterialBOImpl implements SupplierBuildingMaterialBO {
    SupplierBuildingMaterialDAO supplierBuildingMaterialDAO = (SupplierBuildingMaterialDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SupplierBuildingMaterial);

    @Override
    public ArrayList<SupplierBuildingmaterialDTO> getAllSupplierBuildingMaterials() throws SQLException, ClassNotFoundException {
        ArrayList<SupplierBuildingmaterialDTO> allSupplierBuildingMaterials = new ArrayList<>();
        ArrayList<SupplierBuildingmaterial> all = supplierBuildingMaterialDAO.getAll();
        for (SupplierBuildingmaterial c : all) {
            allSupplierBuildingMaterials.add(new SupplierBuildingmaterialDTO(c.getBm_id(),c.getS_email(),c.getAmount(),c.getBm_desc()));
        }
        return allSupplierBuildingMaterials;
    }

    @Override
    public boolean addSupplierBuildingMaterial(SupplierBuildingmaterialDTO dto) throws SQLException, ClassNotFoundException {
        return supplierBuildingMaterialDAO.add(new SupplierBuildingmaterial(dto.getBm_id(),dto.getS_email(),dto.getAmount(),dto.getBm_desc()));
    }

    @Override
    public boolean updateSupplierBuildingMaterial(SupplierBuildingmaterialDTO dto) throws SQLException, ClassNotFoundException {
        return supplierBuildingMaterialDAO.update(new SupplierBuildingmaterial(dto.getBm_id(),dto.getS_email(),dto.getAmount(),dto.getBm_desc()));
    }

    @Override
    public boolean existSupplierBuildingMaterial(String id) throws SQLException, ClassNotFoundException {
        return supplierBuildingMaterialDAO.exist(id);
    }

    @Override
    public String generateNewSupplierBuildingMaterialID() throws SQLException, ClassNotFoundException {
        return supplierBuildingMaterialDAO.generateNewID();
    }

    @Override
    public boolean deleteSupplierBuildingMaterial(String id) throws SQLException, ClassNotFoundException {
        return supplierBuildingMaterialDAO.delete(id);
    }

    @Override
    public String getLastSupplierBuildingMaterialId() throws SQLException, ClassNotFoundException {
        return supplierBuildingMaterialDAO.getLastId();
    }

    @Override
    public SupplierBuildingmaterial search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}

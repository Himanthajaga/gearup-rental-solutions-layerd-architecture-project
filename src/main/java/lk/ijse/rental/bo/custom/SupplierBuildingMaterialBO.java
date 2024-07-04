package lk.ijse.rental.bo.custom;

import lk.ijse.rental.bo.SuperBO;
import lk.ijse.rental.dto.ReservationDTO;
import lk.ijse.rental.dto.SupplierBuildingmaterialDTO;
import lk.ijse.rental.entity.Bokking;
import lk.ijse.rental.entity.SupplierBuildingmaterial;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierBuildingMaterialBO extends SuperBO {
    public ArrayList<SupplierBuildingmaterialDTO> getAllSupplierBuildingMaterials() throws SQLException, ClassNotFoundException;
    public boolean addSupplierBuildingMaterial(SupplierBuildingmaterialDTO dto) throws SQLException, ClassNotFoundException;
    public boolean updateSupplierBuildingMaterial(SupplierBuildingmaterialDTO dto) throws SQLException, ClassNotFoundException;
    public boolean existSupplierBuildingMaterial(String id) throws SQLException, ClassNotFoundException;
    public String generateNewSupplierBuildingMaterialID() throws SQLException, ClassNotFoundException;
    public boolean deleteSupplierBuildingMaterial(String id) throws SQLException, ClassNotFoundException;
    String getLastSupplierBuildingMaterialId() throws SQLException, ClassNotFoundException;
    public SupplierBuildingmaterial search(String id) throws SQLException, ClassNotFoundException;
}

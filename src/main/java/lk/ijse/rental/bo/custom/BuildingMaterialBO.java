package lk.ijse.rental.bo.custom;

import lk.ijse.rental.bo.SuperBO;
import lk.ijse.rental.dto.BuildingMaterialDTO;
import lk.ijse.rental.entity.Bokking;
import lk.ijse.rental.entity.BuildingMaterial;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface BuildingMaterialBO extends SuperBO {
    public ArrayList<BuildingMaterialDTO> getAllBuildingMaterials() throws SQLException, ClassNotFoundException;
    public boolean addBuildingMaterial(BuildingMaterialDTO dto) throws SQLException, ClassNotFoundException;
    public boolean updateBuildingMaterial(BuildingMaterialDTO dto) throws SQLException, ClassNotFoundException;
    public boolean existBuildingMaterial(String id) throws SQLException, ClassNotFoundException;
    public String generateNewBuildingMaterialID() throws SQLException, ClassNotFoundException;
    public boolean deleteBuildingMaterial(String id) throws SQLException, ClassNotFoundException;
    String getLastBuildingMaterialId() throws SQLException, ClassNotFoundException;
    public List<String> getIds() throws SQLException, ClassNotFoundException;
    public BuildingMaterial search(String id) throws SQLException, ClassNotFoundException;
    public <T> T searchByEmail(String name) throws SQLException, ClassNotFoundException;
}

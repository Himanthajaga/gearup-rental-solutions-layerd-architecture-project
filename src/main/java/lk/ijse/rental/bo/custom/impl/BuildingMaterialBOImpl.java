package lk.ijse.rental.bo.custom.impl;

import lk.ijse.rental.bo.custom.BuildingMaterialBO;
import lk.ijse.rental.dao.DAOFactory;
import lk.ijse.rental.dao.custom.BuildingMaterialDAO;
import lk.ijse.rental.dto.BuildingMaterialDTO;
import lk.ijse.rental.entity.Bokking;
import lk.ijse.rental.entity.BuildingMaterial;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BuildingMaterialBOImpl implements BuildingMaterialBO {
    BuildingMaterialDAO buildingMaterialDAO = (BuildingMaterialDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BuildingMaterial);

    @Override
    public ArrayList<BuildingMaterialDTO> getAllBuildingMaterials() throws SQLException, ClassNotFoundException {
        ArrayList<BuildingMaterialDTO> allBuildingMaterials = new ArrayList<>();
        ArrayList<BuildingMaterial> all = buildingMaterialDAO.getAll();
        for (BuildingMaterial c : all) {
            allBuildingMaterials.add(new BuildingMaterialDTO(c.getBm_id(),c.getBm_desc(),c.getBm_type(),c.getBm_price(),c.getBm_qty(),c.getS_email()));
        }

        return allBuildingMaterials;
    }

    @Override
    public boolean addBuildingMaterial(BuildingMaterialDTO dto) throws SQLException, ClassNotFoundException {
        return buildingMaterialDAO.add(new BuildingMaterial(dto.getBm_id(),dto.getBm_desc(),dto.getBm_type(),dto.getBm_price(),dto.getBm_qty(),dto.getS_email()));
    }

    @Override
    public boolean updateBuildingMaterial(BuildingMaterialDTO dto) throws SQLException, ClassNotFoundException {
        return buildingMaterialDAO.update(new BuildingMaterial(dto.getBm_id(),dto.getBm_desc(),dto.getBm_type(),dto.getBm_price(),dto.getBm_qty(),dto.getS_email()));
    }

    @Override
    public boolean existBuildingMaterial(String id) throws SQLException, ClassNotFoundException {
        return buildingMaterialDAO.exist(id);
    }

    @Override
    public String generateNewBuildingMaterialID() throws SQLException, ClassNotFoundException {
        return buildingMaterialDAO.generateNewID();
    }

    @Override
    public boolean deleteBuildingMaterial(String id) throws SQLException, ClassNotFoundException {
        return buildingMaterialDAO.delete(id);
    }

    @Override
    public String getLastBuildingMaterialId() throws SQLException, ClassNotFoundException {
        return buildingMaterialDAO.getLastId();
    }

    @Override
    public List<String> getIds() throws SQLException, ClassNotFoundException {
        return buildingMaterialDAO.getIds();
    }

    @Override
    public BuildingMaterial search(String id) throws SQLException, ClassNotFoundException {
        return buildingMaterialDAO.search(id);
    }

    @Override
    public <T> T searchByEmail(String name) throws SQLException, ClassNotFoundException {
        return buildingMaterialDAO.searchByEmail(name);
    }
}

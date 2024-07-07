package lk.ijse.rental.dao.custom;

import lk.ijse.rental.dao.CrudDAO;
import lk.ijse.rental.dto.BuildingMaterialDTO;
import lk.ijse.rental.dto.SellMaterialDTO;
import lk.ijse.rental.entity.BuildingMaterial;
import lk.ijse.rental.entity.SellMaterial;

import java.sql.SQLException;
import java.util.List;

public interface BuildingMaterialDAO extends CrudDAO<BuildingMaterial> {
    public List<String> getIds() throws SQLException, ClassNotFoundException;
    public boolean updateQtys(List<SellMaterialDTO> odList) throws SQLException, ClassNotFoundException;
    boolean updateQty(BuildingMaterial od) throws SQLException, ClassNotFoundException;
}

package lk.ijse.rental.dao.custom;

import lk.ijse.rental.dao.CrudDAO;
import lk.ijse.rental.entity.SellMaterial;

import java.sql.SQLException;
import java.util.List;

public interface SellMaterialDAO extends CrudDAO<SellMaterial> {
     boolean saved(List<SellMaterial> odList) throws SQLException, ClassNotFoundException;
     boolean save(SellMaterial od) throws SQLException, ClassNotFoundException;
}

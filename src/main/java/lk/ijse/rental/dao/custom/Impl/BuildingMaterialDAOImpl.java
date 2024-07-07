package lk.ijse.rental.dao.custom.Impl;

import lk.ijse.rental.dao.SQLUtil;
import lk.ijse.rental.dao.custom.BuildingMaterialDAO;
import lk.ijse.rental.db.DBConnection;
import lk.ijse.rental.dto.SellMaterialDTO;
import lk.ijse.rental.entity.BuildingMaterial;
import lk.ijse.rental.entity.SellMaterial;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BuildingMaterialDAOImpl implements BuildingMaterialDAO {

    @Override
    public ArrayList<BuildingMaterial> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<BuildingMaterial> allBuildingmaterials = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM building_material");
        while (rst.next()) {
            BuildingMaterial buildingMaterial = new BuildingMaterial(rst.getString("bm_id"), rst.getString("bm_desc"), rst.getString("bm_type"), rst.getString("bm_price"), rst.getInt("bm_amount"), rst.getString("s_email"));
            allBuildingmaterials.add(buildingMaterial);
        }
        return allBuildingmaterials;
    }

    @Override
    public boolean add(BuildingMaterial entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO building_material (bm_id,bm_desc,bm_type,bm_price,bm_amount,s_email) VALUES (?,?,?,?,?,?)", entity.getBm_id(), entity.getBm_desc(), entity.getBm_type(), entity.getBm_price(), entity.getBm_qty(), entity.getS_email());

    }

    @Override
    public boolean update(BuildingMaterial entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE building_material SET bm_desc = ?,bm_type = ?,bm_price = ?,bm_amount = ?,s_email = ? WHERE bm_id = ?", entity.getBm_desc(), entity.getBm_type(), entity.getBm_price(), entity.getBm_qty(), entity.getS_email(), entity.getBm_id());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT bm_id FROM building_material WHERE bm_id=?", id);
        return rst.next();
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT bm_id FROM building_material ORDER BY bm_id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("bm_id");
            int newBuildingMaterialId = Integer.parseInt(id.replace("BM00-", "")) + 1;
            return String.format("BM00-%03d", newBuildingMaterialId);
        } else {
            return "BM00-001";
        }
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM building_material WHERE bm_id=?", id);
    }

    @Override
    public BuildingMaterial search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM building_material WHERE bm_id=?", id + "");
        rst.next();
        return new BuildingMaterial(rst.getString("bm_id"), rst.getString("bm_desc"), rst.getString("bm_type"), rst.getString("bm_price"), rst.getInt("bm_amount"), rst.getString("s_email"));

    }

    @Override
    public <T> T searchByEmail(String name) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM building_material WHERE s_email=?", name + "");
        rst.next();
        return (T) new BuildingMaterial(rst.getString("bm_id"), rst.getString("bm_desc"), rst.getString("bm_type"), rst.getString("bm_price"), rst.getInt("bm_amount"), rst.getString("s_email"));

    }

    @Override
    public <T> ArrayList<T> getEmails(String email) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public <T> T searchById(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public <T> T searchByName(String name) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String getLastId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT bm_id FROM building_material ORDER BY bm_id DESC LIMIT 1");
        if (rst.next()) {
            return rst.getString(1);
        } else {
            return null;
        }
    }

    @Override
    public List<String> getIds() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean updateQtys(List<SellMaterialDTO> odList) throws SQLException, ClassNotFoundException {
//            if (odList == null) {
//                // Log that odList is null
//                System.out.println("updateQtys was called with a null odList.");
//                return false;
//            }
//            for (SellMaterialDTO od : odList) {
//                if (!updateQty(od)) {
//                    // Log which specific update failed, if possible
//                    System.out.println("Failed to update quantity for SellMaterial with ID: " + od.getBmId());
//                    return false;
//                }
//            }
            return true;
        }

    @Override
    public boolean updateQty( BuildingMaterial  bm) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE building_material SET bm_amount = ? WHERE bm_id = ?", bm.getBm_qty(), bm.getBm_id());
    }
}

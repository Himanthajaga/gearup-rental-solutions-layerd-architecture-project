package lk.ijse.rental.dao.custom.Impl;

import lk.ijse.rental.dao.SQLUtil;
import lk.ijse.rental.dao.custom.SupplierBuildingMaterialDAO;
import lk.ijse.rental.entity.SellMaterial;
import lk.ijse.rental.entity.SupplierBuildingmaterial;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierBuildingMaterialDAOImpl implements SupplierBuildingMaterialDAO {

    @Override
    public ArrayList<SupplierBuildingmaterial> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<SupplierBuildingmaterial> allSupplierBuildingMaterials = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM supplier_building_material");
        while (rst.next()) {
           SupplierBuildingmaterial supplierBuildingmaterial = new SupplierBuildingmaterial(rst.getString("bm_id"),rst.getString("s_email"),rst.getString("amount"),rst.getString("bm_desc"));
           allSupplierBuildingMaterials.add(supplierBuildingmaterial);
        }
        return allSupplierBuildingMaterials;
    }

    @Override
    public boolean add(SupplierBuildingmaterial entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO supplier_building_material (bm_id,s_email,amount,bm_desc) VALUES (?,?,?,?)", entity.getBm_id(), entity.getS_email(), entity.getAmount(), entity.getBm_desc());
    }

    @Override
    public boolean update(SupplierBuildingmaterial entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE supplier_building_material SET s_email = ?,amount = ?,bm_desc = ? WHERE bm_id = ?", entity.getS_email(), entity.getAmount(), entity.getBm_desc(), entity.getBm_id());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT bm_id FROM supplier_building_material WHERE bm_id=?", id);
        return rst.next();
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT bm_id FROM supplier_building_material ORDER BY bm_id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("bm_id");
            int newSupplierBuildingMaterialId = Integer.parseInt(id.replace("BM00-", "")) + 1;
            return String.format("BM00-%03d", newSupplierBuildingMaterialId);
        } else {
            return "BM00-001";
        }
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM supplier_building_material WHERE bm_id=?", id);
    }

    @Override
    public SupplierBuildingmaterial search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM supplier_building_material WHERE bm_id=?", id + "");
        if (rst.next()) {
            return new SupplierBuildingmaterial(rst.getString("bm_id"),rst.getString("s_email"),rst.getString("amount"),rst.getString("bm_desc"));
        }
        return null;
    }

    @Override
    public <T> T searchByEmail(String name) throws SQLException, ClassNotFoundException {
        return null;
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
        ResultSet rst = SQLUtil.execute("SELECT bm_id FROM supplier_building_material ORDER BY bm_id DESC LIMIT 1;");
        if (rst.next()) {
            return rst.getString("bm_id");
        } else {
            return null;
        }
    }
}

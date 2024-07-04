package lk.ijse.rental.dao.custom.Impl;

import lk.ijse.rental.dao.SQLUtil;
import lk.ijse.rental.dao.custom.SellMaterialDAO;
import lk.ijse.rental.entity.SellMaterial;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SellMaterialDAOImpl implements SellMaterialDAO {


    @Override
    public ArrayList<SellMaterial> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<SellMaterial> allSellMaterials = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM sell_material");
        while (rst.next()) {
            SellMaterial sellMaterial   = new SellMaterial(rst.getString("se_id"), rst.getString("bm_id"),rst.getDouble("bm_unit_price"), rst.getInt("bm_qty"));
            allSellMaterials.add(sellMaterial);
        }
        return allSellMaterials;
    }

    @Override
    public boolean add(SellMaterial entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO sell_material (se_id,bm_id,bm_unit_price,bm_qty) VALUES (?,?,?,?)", entity.getSellId(), entity.getBmId(), entity.getBm_unit_price(), entity.getBm_qty());
    }

    @Override
    public boolean update(SellMaterial entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE sell_material SET bm_id = ?,bm_unit_price = ?,bm_qty = ? WHERE se_id = ?", entity.getBmId(), entity.getBm_unit_price(), entity.getBm_qty(), entity.getSellId());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT se_id FROM sell_material WHERE se_id=?", id);
        return rst.next();
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT se_id FROM sell_material ORDER BY se_id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("se_id");
            int newSellMaterialId = Integer.parseInt(id.replace("SE00-", "")) + 1;
            return String.format("SE00-%03d", newSellMaterialId);
        } else {
            return "SE00-001";
        }
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM sell_material WHERE se_id=?", id);
    }

    @Override
    public SellMaterial search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM sell_material WHERE se_id=?", id + "");
        if (rst.next()) {
            return new SellMaterial(rst.getString("se_id"), rst.getString("bm_id"), rst.getDouble("bm_unit_price"), rst.getInt("bm_qty"));
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
        ResultSet rst = SQLUtil.execute("SELECT se_id FROM sell_material ORDER BY se_id DESC LIMIT 1");
        if (rst.next()) {
            return rst.getString(1);
        } else {
            return null;
        }
    }

    @Override
    public boolean saved(List<SellMaterial> odList) throws SQLException, ClassNotFoundException {
        for (SellMaterial od : odList) {
            if(!save(od)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean save(SellMaterial od) throws SQLException, ClassNotFoundException {
    return add(od);
    }
}


package lk.ijse.rental.bo.custom;

import lk.ijse.rental.bo.SuperBO;
import lk.ijse.rental.dto.*;
import lk.ijse.rental.entity.BuildingMaterial;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PlaceSellBO extends SuperBO {
    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException ;

    public BuildingMaterialDTO searchBuildingmaterial(String code) throws SQLException, ClassNotFoundException ;

    public boolean existBuildingMaterial(String code) throws SQLException, ClassNotFoundException;

    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException ;

    public String generateSellID() throws SQLException, ClassNotFoundException ;

    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException;

    public ArrayList<BuildingMaterialDTO> getAllBuildingmaterials() throws SQLException, ClassNotFoundException;

    public boolean placeSell(SellDTO dto)throws SQLException, ClassNotFoundException;

    public BuildingMaterialDTO findBuildingmaterial(String code)throws SQLException, ClassNotFoundException;
    public String getLastId() throws SQLException, ClassNotFoundException;

}

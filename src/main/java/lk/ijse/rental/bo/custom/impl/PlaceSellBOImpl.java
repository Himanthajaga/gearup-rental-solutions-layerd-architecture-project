package lk.ijse.rental.bo.custom.impl;

import lk.ijse.rental.bo.custom.PlaceSellBO;
import lk.ijse.rental.dao.DAOFactory;
import lk.ijse.rental.dao.custom.BuildingMaterialDAO;
import lk.ijse.rental.dao.custom.CustomerDAO;
import lk.ijse.rental.dao.custom.SellDAO;
import lk.ijse.rental.dao.custom.SellMaterialDAO;
import lk.ijse.rental.db.DBConnection;
import lk.ijse.rental.dto.BuildingMaterialDTO;
import lk.ijse.rental.dto.CustomerDTO;
import lk.ijse.rental.dto.SellDTO;
import lk.ijse.rental.dto.SellMaterialDTO;
import lk.ijse.rental.entity.BuildingMaterial;
import lk.ijse.rental.entity.Customer;
import lk.ijse.rental.entity.Sell;
import lk.ijse.rental.entity.SellMaterial;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaceSellBOImpl implements PlaceSellBO {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.Customer);
    BuildingMaterialDAO buildingMaterialDAO = (BuildingMaterialDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BuildingMaterial);
    SellMaterialDAO sellMaterialDAO = (SellMaterialDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SellMaterial);
    SellDAO sellDAO = (SellDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.Sell);


    @Override
    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException {
        Customer c = customerDAO.search(id);
        return new CustomerDTO(c.getC_mail(), c.getC_name(), c.getC_address(), c.getC_tel(), c.getC_id());
    }

    @Override
    public BuildingMaterialDTO searchBuildingmaterial(String code) throws SQLException, ClassNotFoundException {
        BuildingMaterial c = buildingMaterialDAO.search(code);
        return new BuildingMaterialDTO(c.getBm_id(), c.getBm_desc(), c.getBm_type(), c.getBm_price(), c.getBm_qty(), c.getS_email());
    }

    @Override
    public boolean existBuildingMaterial(String code) throws SQLException, ClassNotFoundException {
        return sellMaterialDAO.exist(code);
    }

    @Override
    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.exist(id);
    }

    @Override
    public String generateSellID() throws SQLException, ClassNotFoundException {
        return sellDAO.generateNewID();
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> customerEntityData = customerDAO.getAll();
        ArrayList<CustomerDTO> customers = new ArrayList<>();
        for (Customer c : customerEntityData) {
            customers.add(new CustomerDTO(c.getC_mail(), c.getC_name(), c.getC_address(), c.getC_tel(), c.getC_id()));
        }
        return customers;
    }

    @Override
    public ArrayList<BuildingMaterialDTO> getAllBuildingmaterials() throws SQLException, ClassNotFoundException {
        ArrayList<BuildingMaterial> buildingMaterialEntityData = buildingMaterialDAO.getAll();
        ArrayList<BuildingMaterialDTO> buildingMaterials = new ArrayList<>();
        for (BuildingMaterial c : buildingMaterialEntityData) {
            buildingMaterials.add(new BuildingMaterialDTO(c.getBm_id(), c.getBm_desc(), c.getBm_type(), c.getBm_price(), c.getBm_qty(), c.getS_email()));
        }
        return buildingMaterials;
    }

    @Override
    public boolean placeSell(SellDTO dto) throws SQLException, ClassNotFoundException {
            Connection connection = null;
            try {
                connection = DBConnection.getDbConnection().getConnection();
                connection.setAutoCommit(false);

                boolean sellExists = sellDAO.exist(dto.getSellId());
                if (sellExists) {
                    return false;
                }

                Sell sell = new Sell(dto.getSellId(), dto.getSellDate(), dto.getC_email(), dto.getTotal());
                boolean sellAdded = sellDAO.add(sell);
                if (!sellAdded) {
                    connection.rollback();
                    return false;
                }

                for (SellMaterialDTO d : dto.getSellMaterials()) {
                    SellMaterial sellMaterial = new SellMaterial(d.getSellId(), d.getBmId(), d.getBm_unit_price(), d.getBm_qty());
                    boolean sellMaterialAdded = sellMaterialDAO.add(sellMaterial);
                    if (!sellMaterialAdded) {
                        connection.rollback();
                        return false;
                    }

                    BuildingMaterial buildingMaterial = buildingMaterialDAO.search(d.getBmId());

                    int newQuantity = (int) (buildingMaterial.getBm_qty() - d.getBm_qty());

                    buildingMaterial.setBm_qty(newQuantity);
                    boolean materialUpdated = buildingMaterialDAO.updateQty(buildingMaterial);

                    if (!materialUpdated) {
                        connection.rollback();
                        return false;
                    }
                }


                connection.commit();
                return true;
            } catch (SQLException | ClassNotFoundException e) {
                if (connection != null) {
                    connection.rollback();
                }
                e.printStackTrace();
                return false;
            } finally {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            }
        }
        /* Connection connection = null;
        try {
            connection = DBConnection.getDbConnection().getConnection();
            boolean b1 = sellDAO.exist(dto.getSellId());
            System.out.println("exist baluwa");
           // if order id already exist
            if (b1) {
                return false;
            }
            connection.setAutoCommit(false);
            //Save the Order to the sell table
            System.out.println(dto.getC_email());
            boolean b2 = sellDAO.add(new Sell(dto.getSellId(), dto.getSellDate(), dto.getC_email(), dto.getTotal()));
            System.out.println("order ek add una");



            if (!b2) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
            for (SellMaterialDTO d : dto.getSellMaterials()) {
                SellMaterial sellMaterial = new SellMaterial(d.getSellId(),d.getBmId(),d.getBm_unit_price(),d.getBm_qty());
                boolean b3 = sellMaterialDAO.add(sellMaterial);
                if (!b3) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                    //Search & Update Item
                }
                BuildingMaterialDTO buildingMaterial = findBuildingmaterial(d.getBmId());
                    buildingMaterial.setBm_qty((int) (buildingMaterial.getBm_qty() - d.getBm_qty()));

                    //update item
                    boolean b = buildingMaterialDAO.updateQtys((List<SellMaterial>) new BuildingMaterial(buildingMaterial.getBm_id(), buildingMaterial.getBm_desc(), buildingMaterial.getBm_type(), buildingMaterial.getBm_price(), buildingMaterial.getBm_qty(), buildingMaterial.getS_email()));

                if (!b) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }*/
/*
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {

            boolean isSellSaved = sellDAO.add(new Sell(dto.getSellId(), dto.getSellDate(), dto.getC_email(), dto.getTotal()));

            if (isSellSaved) {
                boolean isSellMaterialSaved = sellMaterialDAO.add((SellMaterial) dto.getSellMaterials());


                if (isSellMaterialSaved) {
                    List<SellMaterial> sellMaterials = new ArrayList<>();
                    for (SellMaterialDTO dto : dto.getSellMaterials()) {
                        sellMaterials.add(new SellMaterial(dto.getSellId(), dto.getBmId(), dto.getBm_unit_price(), dto.getBm_qty()));
                    }
                    boolean isItemQtyUpdate = buildingMaterialDAO.updateQtys(dto.getSellMaterials(),List<SellMaterial> sellMaterials);

                    System.out.println(isItemQtyUpdate);

                    if (isItemQtyUpdate) {

                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        } catch (Exception e) {
            connection.rollback();
            e.printStackTrace();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }*/

    @Override
    public BuildingMaterialDTO findBuildingmaterial(String code) throws SQLException, ClassNotFoundException {
        try {
            BuildingMaterial i = buildingMaterialDAO.search(code);
            return new BuildingMaterialDTO(i.getBm_id(), i.getBm_desc(), i.getBm_type(), i.getBm_price(), i.getBm_qty(), i.getS_email());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the Item " + code, e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getLastId() throws SQLException, ClassNotFoundException {
        return sellDAO.getLastId();
    }
}
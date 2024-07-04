package lk.ijse.rental.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.rental.dao.DAOFactory;
import lk.ijse.rental.dao.custom.RentOrderDAO;
import lk.ijse.rental.dao.custom.SellDAO;
import lk.ijse.rental.entity.RentOrder;
import lk.ijse.rental.entity.Sell;
import lk.ijse.rental.tdm.Rent_orderTm;
import lk.ijse.rental.tdm.SellTm;

import java.sql.SQLException;
import java.util.ArrayList;

public class DashbordFormController {
    @FXML
    private TableColumn<?, ?> colCEmail1;
    @FXML
    private TableColumn<?, ?> ColReturnDate;

    @FXML
    private TableColumn<?, ?> colCEmail;

    @FXML
    private TableColumn<?, ?> colOrderDate;

    @FXML
    private TableColumn<?, ?> colOrderId;

    @FXML
    private TableColumn<?, ?> colSellDate;

    @FXML
    private TableColumn<?, ?> colSellId;

    @FXML
    private TableColumn<?, ?> colSellTotal;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private Label lblTotalRentOrders;

    @FXML
    private Label lblTotalSellorders;

    @FXML
    private TableView<SellTm> tblSell;

    @FXML
    private TableView<Rent_orderTm> tblrent_order;
    private ArrayList<RentOrder> rentOrderList = new ArrayList<>();
    private ArrayList<Sell> sellList = new ArrayList<>();
    SellDAO sellDAO  = (SellDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.Sell);
    RentOrderDAO rentOrderDAO = (RentOrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.RentOrder);

    public void initialize() {
        this.rentOrderList = getAllRentorders();
        this.sellList = getAllSellOrders();
        setCellValueFactory();
        loadrent_orderTable();
        loadSellTable();
        loadTotalRentOrders();
        loadTotalSellOrders();

    }

    private void loadTotalRentOrders() {
        lblTotalRentOrders.setText(rentOrderList.size() + "");

    }

    private void loadTotalSellOrders() {
        lblTotalSellorders.setText(sellList.size() + "");

    }

    private ArrayList<Sell> getAllSellOrders() {
        ArrayList<Sell> sellList = null;
        try {
            sellList = sellDAO.getAll();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return sellList;
    }

    private ArrayList<RentOrder> getAllRentorders() {
        ArrayList<RentOrder> bmList = null;
        try {
            bmList = rentOrderDAO.getAll();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return bmList;
    }

    private void loadSellTable() {
        ObservableList<SellTm> tmList = FXCollections.observableArrayList();

        for (Sell sell : sellList) {
            SellTm sellTm= new SellTm(
                    sell.getSellId(),
                    sell.getSellDate(),
                    sell.getC_email(),
                    sell.getTotal()
            );
            tmList.add(sellTm);
    }
        tblSell.setItems(tmList);
        SellTm selectedItem = (SellTm) tblSell.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }

    private void setCellValueFactory() {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("colOrderId"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("colOrderDate"));
        ColReturnDate.setCellValueFactory(new PropertyValueFactory<>("ColReturnDate"));
        colCEmail1.setCellValueFactory(new PropertyValueFactory<>("colCEmail1"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("colTotal"));
        colSellId.setCellValueFactory(new PropertyValueFactory<>("colSellId"));
        colSellDate.setCellValueFactory(new PropertyValueFactory<>("colSellDate"));
        colCEmail.setCellValueFactory(new PropertyValueFactory<>("colCEmail"));
        colSellTotal.setCellValueFactory(new PropertyValueFactory<>("colSellTotal"));


    }
    private void  loadrent_orderTable() {

        ObservableList<Rent_orderTm> tmList = FXCollections.observableArrayList();

        for (RentOrder rentOrder : rentOrderList) {
           Rent_orderTm rentOrderTm= new Rent_orderTm(
                    rentOrder.getOrderId(),
                    rentOrder.getDate(),
                    rentOrder.getReturnDate(),
                    rentOrder.getCustomerEmail(),
                    rentOrder.getTotal()


            );
            // System.out.println("machineTm = " + machineTm);
            tmList.add(rentOrderTm);
        }
        tblrent_order.setItems(tmList);
        Rent_orderTm selectedItem = (Rent_orderTm) tblrent_order.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }

}

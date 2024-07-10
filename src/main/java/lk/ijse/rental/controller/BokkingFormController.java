package lk.ijse.rental.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.rental.bo.BOFactory;
import lk.ijse.rental.bo.custom.BokkingBO;
import lk.ijse.rental.bo.custom.CustomerBO;
import lk.ijse.rental.bo.custom.MachineBO;
import lk.ijse.rental.dto.AdminDTO;
import lk.ijse.rental.dto.BokkingDTO;
import lk.ijse.rental.entity.Bokking;
import lk.ijse.rental.entity.Customer;
import lk.ijse.rental.entity.Machine;
import lk.ijse.rental.tdm.BokkingTm;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BokkingFormController {
    private List<BokkingDTO> bokkingList = new ArrayList<>();

    @FXML
    private Label txtBokkingId;
    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDeleteBokking;

    @FXML
    private JFXButton btnSaveBokking;

    @FXML
    private JFXButton btnUpdateBokking;

    @FXML
    private JFXComboBox<String> cmbCustomerID;

    @FXML
    private JFXComboBox<String > cmbMachineId;

    @FXML
    private TableColumn<?, ?> colBokkingDate;

    @FXML
    private TableColumn<?, ?> colBokkingId;

    @FXML
    private TableColumn<?, ?> colCustomerEmail;

    @FXML
    private TableColumn<?, ?> colMachineId;

    @FXML
    private Label lblCustomerId;

    @FXML
    private Label lblMachineId;

    @FXML
    private TableView<BokkingTm> tblBokking;
    BokkingBO bokkingBO  = (BokkingBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Bokking);
    MachineBO machineBO  = (MachineBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Machine);
    CustomerBO customerBO  = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Customer);


    @FXML
    private DatePicker txtBokkkingDate;




    @FXML
    void btnLogoutOnAction(ActionEvent event) {
        System.exit(0);

    }
    public void initialize() throws SQLException, ClassNotFoundException {

        this.bokkingList=getAllBokkings();
        setCellValueFactory();
        loadBokkingTable();
        getCustomerIds();
        getMachineIds();
        loadNextBokkingId();
        setListener();
    }
    private void setListener() {
        tblBokking.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    BokkingDTO dto = new BokkingDTO(
                            newValue.getColBokkingId(),
                            newValue.getColBokkingDate(),
                            newValue.getColCustomerEmail(),
                            newValue.getColMachineId()
                    );
                    setFields(dto);
                });
    }

    private void setFields(BokkingDTO dto) {
        txtBokkingId.setText(dto.getBokkingId());
        txtBokkkingDate.setValue(LocalDate.parse(dto.getBokkingDate()));
        cmbCustomerID.setValue(dto.getCustomerEmail());
        cmbMachineId.setValue(dto.getMachineId());
    }

    private void loadNextBokkingId() throws SQLException, ClassNotFoundException {
        String lastBokkingId = bokkingBO.getLastBokkingId();
        if (lastBokkingId != null) {
            int id = Integer.parseInt(lastBokkingId.replace("B", ""));
            id++;
            if (id < 10) {
                txtBokkingId.setText("B00" + id);
            } else if (id < 100) {
                txtBokkingId.setText("B0" + id);
            } else {
                txtBokkingId.setText("B" + id);
            }
        } else {
            txtBokkingId.setText("B001");
        }
    }

    private void getMachineIds() {

        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = machineBO.getIds();

            for (String id : idList) {
                obList.add(String.valueOf(id));
            }
            cmbMachineId.setItems(obList);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);

        }
    }

    private void getCustomerIds() {

        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = customerBO.getIds();

            for (String id : idList) {
                obList.add(id);
            }
            cmbCustomerID.setItems(obList);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);

        }
    }

    private void setCellValueFactory() {
        colBokkingId.setCellValueFactory(new PropertyValueFactory<>("colBokkingId"));
        colBokkingDate.setCellValueFactory(new PropertyValueFactory<>("colBokkingDate"));
       colCustomerEmail.setCellValueFactory(new PropertyValueFactory<>("colCustomerEmail"));
        colMachineId.setCellValueFactory(new PropertyValueFactory<>("colMachineId"));

    }
    private void loadBokkingTable() {

        ObservableList<BokkingTm> tmList = FXCollections.observableArrayList();

        for (BokkingDTO bokking : bokkingList) {
            BokkingTm bokkingTm = new BokkingTm(
                    bokking.getBokkingId(),
                    bokking.getBokkingDate(),
                    bokking.getCustomerEmail(),
                    bokking.getMachineId()

            );

            tmList.add(bokkingTm);
        }
        tblBokking.setItems(tmList);
        BokkingTm selectedItem = (BokkingTm) tblBokking.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }


    private List<BokkingDTO> getAllBokkings() {
        ArrayList<BokkingDTO> bokkingList = null;
        try {
            bokkingList = bokkingBO.getAllBokkings();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return bokkingList;
    }

    @FXML
    void IconBackOnAction(MouseEvent event) {

    }


    @FXML
    void btnDeleteBokkingOnAction(ActionEvent event) {
        String id = txtBokkingId.getText();

        try {
            boolean isDeleted =bokkingBO.deleteBokking(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Bokking deleted!").show();
                loadBokkingTable();
                clearFields();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String id = txtBokkingId.getText();

        try {
            BokkingBO  bokking = (BokkingBO) bokkingBO.search(id);

            if (bokking != null) {
                txtBokkingId.setText(bokking.generateNewBokkingID());
                txtBokkkingDate.getValue().toString();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void cmbCustomerIdOnAction(ActionEvent event) {
        String customerId = (String) cmbCustomerID.getValue();
        try {
            Customer customer= customerBO.searchByEmail(customerId);
            lblCustomerId.setText(customer.getC_name());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
//    @FXML
//    void txtBokkingOnReleasedOnAction(KeyEvent event) {
//        Pattern idPattern = Pattern.compile("^(B)[0-9]{1,}$");
//        if (!idPattern.matcher(txtBokkingId.getText()).matches()) {
//            addError(txtBokkingId);
//
//        }else{
//            removeError(txtBokkingId);
//        }
//    }

    private void removeError(TextField textField) {
        textField.setStyle("-fx-border-color: green; -fx-border-width: 5");
    }

    private void addError(TextField textField) {
        textField.setStyle("-fx-border-color: red; -fx-border-width: 5");
    }

    @FXML
    void cmbMachineIdOnaction(ActionEvent event) {
        String machineId = (String) cmbMachineId.getValue();
        try {
            Machine machine= machineBO.search(machineId);
            lblMachineId.setText(((Machine) machine).getM_Name());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveBokkingOnAction(ActionEvent event) {
        String bokkingId = txtBokkingId.getText();
        String bokkingDate = txtBokkkingDate.getValue().toString();
        String customerId = cmbCustomerID.getValue().toString();
        String machineId = cmbMachineId.getValue().toString();
        if (customerId == null || machineId == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a customer and machine").show();
            return;
        }
        Bokking bokking = new Bokking(bokkingId, bokkingDate,customerId,machineId);
        try {
            boolean isAdded = bokkingBO.addBokking(new BokkingDTO(bokking.getBokkingId(),bokking.getBokkingDate(),bokking.getCustomerEmail(),bokking.getMachineId()));
            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
                bokkingList.add(new BokkingDTO(bokking.getBokkingId(),bokking.getBokkingDate(),bokking.getCustomerEmail(),bokking.getMachineId()));
                loadBokkingTable();
                clearFields();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void btnUpdateBokkingOnAction(ActionEvent event) {
        String bokkingId = txtBokkingId.getText();
        String bokkingDate = txtBokkkingDate.getValue().toString();
        String customerId = lblCustomerId.getText();
        String machineId = lblMachineId.getText();
        if (customerId == null || machineId == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a customer and machine").show();
            return;
        }
        Bokking bokking = new Bokking(bokkingId,bokkingDate,customerId,machineId);
        try {
            boolean isUpdated = bokkingBO.updateBokking(new BokkingDTO(bokking.getBokkingId(),bokking.getBokkingDate(),bokking.getCustomerEmail(),bokking.getMachineId()));
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
                bokkingList.add(new BokkingDTO(bokking.getBokkingId(),bokking.getBokkingDate(),bokking.getCustomerEmail(),bokking.getMachineId()));
                loadBokkingTable();
                clearFields();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void btnClearBokkingOnAction(ActionEvent event) {
    clearFields();
    }

    private void  clearFields() {
        txtBokkingId.setText("");
        txtBokkkingDate.getEditor().clear();
        cmbCustomerID.getEditor().clear();
        cmbMachineId.getEditor().clear();
        lblCustomerId.setText("");
        lblMachineId.setText("");
    }

}

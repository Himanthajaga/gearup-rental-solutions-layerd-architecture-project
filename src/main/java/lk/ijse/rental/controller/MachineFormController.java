package lk.ijse.rental.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.rental.bo.BOFactory;
import lk.ijse.rental.bo.custom.MachineBO;
import lk.ijse.rental.dto.CustomerDTO;
import lk.ijse.rental.dto.MachineDTO;
import lk.ijse.rental.entity.Machine;
import lk.ijse.rental.qrGenerate.QrcodeForMachine;
import lk.ijse.rental.tdm.MachineTm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MachineFormController {
    @FXML
    private JFXButton btnReturn;
    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDeleteMachine;

    @FXML
    private JFXButton btnSaveMachine;

    @FXML
    private JFXButton btnUpdateMachine;

    @FXML
    private TableColumn<?, ?> colMachineDescription;

    @FXML
    private TableColumn<?, ?> colMachineId;

    @FXML
    private TableColumn<?, ?> colMachineIsAvailable;

    @FXML
    private TableColumn<?, ?> colMachineName;


    @FXML
    private TableColumn<?, ?> colMachineRentalPrice;

    @FXML
    private TableView<MachineTm> tblMachine;

    @FXML
    private TextField txtDesc;
    @FXML
    private Label txtMachineId;

    @FXML
    private TextField txtMachineName;

    @FXML
    private TextField txtMachineIdNew;
    @FXML
    private TextField txtRentalprice;

    @FXML
    private TextField txtisavailable;
    @FXML
    private AnchorPane paneHolder;
    private QrcodeForMachine qrcodeForUser = new QrcodeForMachine();

    private List<MachineDTO> machineList = new ArrayList<>();
    MachineBO machineBO  = (MachineBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Machine);

    public void initialize() throws SQLException, ClassNotFoundException {

        this.machineList=getAllMachines();
        setCellValueFactory();
        loadMachineTable();
        loadNextMachineId();
        setListener();
    }
    private void setListener() {
        tblMachine.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    MachineDTO dto = new MachineDTO(
                            newValue.getColMachineId(),
                            newValue.getColMachineName(),
                            newValue.getColMachineDescription(),
                            newValue.getColMachineRentalPrice(),
                            newValue.getColMachineIsAvailable(),
                            "0");
                    setFields(dto);
                });
    }

    private void setFields(MachineDTO dto) {

        txtMachineIdNew.setText(dto.getM_Id());
        txtMachineName.setText(dto.getM_Name());
        txtDesc.setText(dto.getM_desc());
        txtRentalprice.setText(dto.getM_rental_price());
        txtisavailable.setText(dto.getIsAvaiable());
    }

    private void loadNextMachineId() throws SQLException, ClassNotFoundException {
        String lastMachineId = machineBO.getLastMachineId();
        if (lastMachineId != null) {
            int id = Integer.parseInt(lastMachineId.replace("M", ""));
            id++;
            if (id < 10) {
                txtMachineId.setText("M00" + id);
            } else if (id < 100) {
                txtMachineId.setText("M0" + id);
            } else {
                txtMachineId.setText("M" + id);
            }
        } else {
            txtMachineId.setText("M001");
        }
    }


    private void setCellValueFactory() {
        colMachineId.setCellValueFactory(new PropertyValueFactory<>("colMachineId"));
        colMachineName.setCellValueFactory(new PropertyValueFactory<>("colMachineName"));
        colMachineDescription.setCellValueFactory(new PropertyValueFactory<>("colMachineDescription"));
        colMachineRentalPrice.setCellValueFactory(new PropertyValueFactory<>("colMachineRentalPrice"));
        colMachineIsAvailable.setCellValueFactory(new PropertyValueFactory<>("colMachineIsAvailable"));



    }
    private void loadMachineTable() {

        ObservableList<MachineTm> tmList = FXCollections.observableArrayList();

        for (MachineDTO machine : machineList) {
            MachineTm machineTm= new MachineTm(
                    machine.getM_Id(),
                    machine.getM_Name(),
                    machine.getM_desc(),
                    machine.getM_rental_price(),
                    machine.getIsAvaiable()


            );
           // System.out.println("machineTm = " + machineTm);
            tmList.add(machineTm);
        }
        tblMachine.setItems(tmList);
        MachineTm selectedItem = (MachineTm) tblMachine.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }


    private List<MachineDTO> getAllMachines() {
        List<MachineDTO> machineList = null;
        try {
           machineList = machineBO.getAllMachines();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return machineList;
    }


    @FXML
    void btnDeleteMachineOnAction(ActionEvent event) {
        String id = txtMachineIdNew.getText();

        try {
            boolean isDeleted = machineBO.deleteMachine(id);

            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted", ButtonType.OK).show();
                machineList.removeIf(machine -> machine.getM_Id().equals(id));
                loadMachineTable();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Try Again", ButtonType.OK).show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }



    @FXML
    void btnSaveMachinerOnAction(ActionEvent event) {
        String id = txtMachineId.getText();
        String name = txtMachineName.getText();
        String desc = txtDesc.getText();
        String rentalPrice = txtRentalprice.getText();
        String isAvailable = txtisavailable.getText();
        if (id.trim().isEmpty() || name.trim().isEmpty() || desc.trim().isEmpty() || rentalPrice.trim().isEmpty() || isAvailable.trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill all the fields", ButtonType.OK).show();
            return;
        }

        Machine machine = new Machine(id, name, desc, rentalPrice,isAvailable, "0");

        try {
            boolean isAdded = machineBO.addMachine(new MachineDTO(machine.getM_Id(),machine.getM_Name(),machine.getM_desc(),machine.getM_rental_price(),machine.getIsAvaiable(),"0"));

            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved", ButtonType.OK).show();
                qrcodeForUser.CreateQr(id);
                machineList.add(new MachineDTO(machine.getM_Id(),machine.getM_Name(),machine.getM_desc(),machine.getM_rental_price(),machine.getIsAvaiable(),"0"));
                loadMachineTable();
                clearFields();
                loadNextMachineId();
            } else {
                new Alert(Alert.AlertType.ERROR, "Try Again", ButtonType.OK).show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    void btnUpdateMachineOnAction(ActionEvent event) {
        String id = txtMachineIdNew.getText();
        String name = txtMachineName.getText();
        String desc = txtDesc.getText();
        String rentalPrice = txtRentalprice.getText();
        String isAvailable = txtisavailable.getText();


        Machine machine = new Machine(id, name, desc, rentalPrice,isAvailable, "0");

        try {
            boolean isUpdated = machineBO.updateMachine(new MachineDTO(machine.getM_Id(),machine.getM_Name(),machine.getM_desc(),machine.getM_rental_price(),machine.getIsAvaiable(),"0"));

            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated", ButtonType.OK).show();
                machineList.add(new MachineDTO(machine.getM_Id(),machine.getM_Name(),machine.getM_desc(),machine.getM_rental_price(),machine.getIsAvaiable(),"0"));
                loadMachineTable();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Try Again", ButtonType.OK).show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    @FXML
    void btnClearCustomerOnAction(ActionEvent event) {
    clearFields();
    }
    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String id = txtMachineName.getText();

        try {
            Machine machine = machineBO.search(id);

            if (machine != null) {
                txtMachineId.setText(machine.getM_Id());
                txtMachineName.setText(machine.getM_Name());
                txtDesc.setText(machine.getM_desc());
                txtRentalprice.setText(machine.getM_rental_price());
                txtisavailable.setText(machine.getIsAvaiable());


            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }
    @FXML
    void txtMachineDescOnReleased(KeyEvent event) {
        Pattern idPattern = Pattern.compile("^[a-zA-Z ]*$");
        if (!idPattern.matcher(txtDesc.getText()).matches()) {
            addError(txtDesc);

        }else{
            removeError(txtDesc);
        }
    }

    private void addError(TextField txtDesc) {
        txtDesc.setStyle("-fx-border-color: red; -fx-border-width: 5");
    }

    private void removeError(TextField txtDesc) {
        txtDesc.setStyle("-fx-border-color: green; -fx-border-width: 5");
    }

    @FXML
    void txtMachineIsAvailableOnReleased(KeyEvent event) {
        Pattern idPattern = Pattern.compile("[0-1]{1}");
        if (!idPattern.matcher(txtisavailable.getText()).matches()) {
            addError(txtisavailable);

        }else{
            removeError(txtisavailable);
        }
    }

    @FXML
    void txtMachineRentalOnReleased(KeyEvent event) {
        Pattern idPattern = Pattern.compile("^([0-9]){1,}[.]([0-9]){1,}$");
        if (!idPattern.matcher(txtRentalprice.getText()).matches()) {
            addError(txtRentalprice);

        }else{
            removeError(txtRentalprice);
        }
    }

//    @FXML
//    void txtMachineidOnReleased(KeyEvent event) {
//        Pattern idPattern = Pattern.compile("^(M)[0-9]{1,}$");
//        if (!idPattern.matcher(txtMachineId.getText()).matches()) {
//            addError(txtMachineId);
//
//        }else{
//            removeError(txtMachineId);
//        }
//    }

    @FXML
    void txtMachinenameOnReleased(KeyEvent event) {
        Pattern idPattern = Pattern.compile("^[a-zA-Z ]*$");
        if (!idPattern.matcher(txtMachineName.getText()).matches()) {
            addError(txtMachineName);

        }else{
            removeError(txtMachineName);
        }
    }

    private void clearFields() {
        txtDesc.clear();
        txtMachineId.setText("");
        txtMachineName.clear();
        txtRentalprice.clear();
        txtisavailable.clear();


    }

    public void idreleased(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^(M)[0-9]{1,}$");
        if (!idPattern.matcher(txtMachineIdNew.getText()).matches()) {
            addError(txtMachineIdNew);

        }else{
            removeError(txtMachineIdNew);
        }
    }
}

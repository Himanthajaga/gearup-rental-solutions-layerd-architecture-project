package lk.ijse.rental.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.util.Duration;
import lk.ijse.rental.bo.BOFactory;
import lk.ijse.rental.bo.custom.CustomerBO;
import lk.ijse.rental.bo.custom.MachineBO;
import lk.ijse.rental.dto.MachineDTO;
import lk.ijse.rental.entity.Customer;
import lk.ijse.rental.entity.Machine;
import lk.ijse.rental.util.DateTimeUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class ReturnMachineFormController {
    @FXML
    private Label lblDate;
    @FXML
    private Label lblMachineName;
    @FXML
    private JFXButton btnReturn;

    @FXML
    private ComboBox<String> cmbmachineId;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblcusId;

    @FXML
    private Label lblcusname;
    CustomerBO customerBO  = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Customer);
    MachineBO machineBO  = (MachineBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Machine);
    public void initialize() {
        //getIsavailableMachines();
        getItemCodes();
        realTime();
    }

    private void realTime() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> lblDate.setText(DateTimeUtil.timenow())));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
       lblDate.setText(DateTimeUtil.dateNow());
    }

    @FXML
    void btnReturnOnActionOnAction(ActionEvent event) {
        String code = cmbmachineId.getValue();
        try {
            boolean isUpdated = machineBO.updateIsAvailable(code, "1");
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Machine Returned", ButtonType.OK).show();
                getItemCodes();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to return the machine", ButtonType.OK).show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void cmbmachineIdOnAction(ActionEvent event) {
        String code = cmbmachineId.getValue();
        try {
           // Customer customer = customerBO.searchByEmail(code);
            Machine machine = machineBO.search(code);
            if (machine != null) {
                System.out.println(machine.getM_desc());
                lblDescription.setText(machine.getM_desc());
                lblMachineName.setText(machine.getM_Name());
                //lblcusname.setText(customer.getC_name());
                //lblcusId.setText(customer.getC_id());

            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
//    private void getIsavailableMachines(){
//
//        ObservableList<String> obList = FXCollections.observableArrayList();
//        try {
//            List<Machine> machineList = MachineRepo.searchIsavailable();
//
//            for (Machine machine : machineList) {
//                if (machine.getIsAvaiable().equals("0")){
//                    obList.add(machine.getM_Id());
//                }
//            }
//
//            cmbmachineId.setItems(obList);
//
//        } catch (SQLException | ClassNotFoundException e) {
//            throw new RuntimeException(e);
//
//        }
//    }


    private void getItemCodes() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            ArrayList<MachineDTO> machineList = machineBO.getAllMachines();

            for (MachineDTO machine : machineList) {
                if (machine.getIsAvaiable().equals("0")){
                    obList.add(machine.getM_Id());
                }
            }

          cmbmachineId.setItems(obList);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);

        }
    }
}


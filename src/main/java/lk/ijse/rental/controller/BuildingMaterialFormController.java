package lk.ijse.rental.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.rental.bo.BOFactory;
import lk.ijse.rental.bo.custom.BuildingMaterialBO;
import lk.ijse.rental.dto.BuildingMaterialDTO;
import lk.ijse.rental.entity.BuildingMaterial;
import lk.ijse.rental.qrGenerate.QrcodeForMachine;
import lk.ijse.rental.tdm.BuildingMaterialTm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class BuildingMaterialFormController {
    @FXML
    private Label txtMaterialId;
    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDeleteMaterial;

    @FXML
    private JFXButton btnSaveMaterial;

    @FXML
    private JFXButton btnUpdateMaterial;

    @FXML
    private TableColumn<?, ?> colMaterialId;

    @FXML
    private TableColumn<?, ?> colMaterialPrice;
    @FXML
    private TextField txtMaterialIdIn;
    @FXML
    private TableColumn<?, ?> colMaterialQty;
    @FXML
    private TextField txtSupplierEmail;
    @FXML
    private TableColumn<?, ?> colMaterialType;

    @FXML
    private TableColumn<?, ?> colMaterialdescription;

    @FXML
    private TableView<BuildingMaterialTm> tblMaterial;

    @FXML
    private TextField txtMateriaDescription;


    @FXML
    private TextField txtMaterialPrice;

    @FXML
    private TextField txtMaterialQty;

    @FXML
    private TextField txtMaterialType;
    private List<BuildingMaterialDTO> bmList = new ArrayList<>();
    private QrcodeForMachine qrcodeForUser = new QrcodeForMachine();
    BuildingMaterialBO buildingMaterialBO  = (BuildingMaterialBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.BuildingMaterial);

    public void initialize() throws SQLException, ClassNotFoundException {

        this.bmList = getAllBuildingMaterials();
        setCellValueFactory();
        loadBuildingMaterialTable();
        loadNextBuildingMaterialId();
    }

    private void loadNextBuildingMaterialId() throws SQLException, ClassNotFoundException {
        String lastBuildingMaterialId = buildingMaterialBO.getLastBuildingMaterialId();
        if (lastBuildingMaterialId != null) {
            int id = Integer.parseInt(lastBuildingMaterialId.replace("BM", ""));
            id++;
            if (id < 10) {
                txtMaterialId.setText("BM00" + id);
            } else if (id < 100) {
                txtMaterialId.setText("BM0" + id);
            } else {
                txtMaterialId.setText("BM" + id);
            }
        } else {
            txtMaterialId.setText("BM001");
        }
    }

    private void loadBuildingMaterialTable() {
        ObservableList<BuildingMaterialTm> tmList = FXCollections.observableArrayList();

        for (BuildingMaterialDTO buildingMaterial : bmList) {
            BuildingMaterialTm buildingMaterialTm = new BuildingMaterialTm(
                    buildingMaterial.getBm_id(),
                    buildingMaterial.getBm_desc(),
                    buildingMaterial.getBm_type(),
                    buildingMaterial.getBm_price(),
                    buildingMaterial.getBm_qty()

            );

            tmList.add(buildingMaterialTm);
        }
        tblMaterial.setItems(tmList);
        BuildingMaterialTm selectedItem = tblMaterial.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }

    private void setCellValueFactory() {
        colMaterialId.setCellValueFactory(new PropertyValueFactory<>("colMaterialId"));
        colMaterialdescription.setCellValueFactory(new PropertyValueFactory<>("colMaterialdescription"));
        colMaterialType.setCellValueFactory(new PropertyValueFactory<>("colMaterialType"));
        colMaterialPrice.setCellValueFactory(new PropertyValueFactory<>("colMaterialPrice"));
        colMaterialQty.setCellValueFactory(new PropertyValueFactory<>("colMaterialQty"));

    }


    private List<BuildingMaterialDTO> getAllBuildingMaterials() {
        List<BuildingMaterialDTO> bmList = null;
        try {
            bmList = buildingMaterialBO.getAllBuildingMaterials();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return bmList;
    }


    @FXML
    void btnDeleteMaterialOnAction(ActionEvent event) {
        String id = txtMaterialIdIn.getText();

        try {
            boolean isDeleted = buildingMaterialBO.deleteBuildingMaterial(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Material deleted!").show();
                loadBuildingMaterialTable();
                clearFields();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    void btnSaveMaterialOnAction(ActionEvent event) {
        String MaterialId = txtMaterialId.getText();
        String MaterialDesc = txtMateriaDescription.getText();
        String MaterialType = txtMaterialType.getText();
        String MaterialPrice = txtMaterialPrice.getText();
        int MaterialQty = Integer.parseInt(txtMaterialQty.getText());
        String SupplierEmail = txtSupplierEmail.getText();

        if (MaterialId.trim().isEmpty() || MaterialDesc.trim().isEmpty() || MaterialType.trim().isEmpty() || MaterialPrice.trim().isEmpty() || txtMaterialQty.getText().trim().isEmpty() || SupplierEmail.trim().isEmpty()){
            new Alert(Alert.AlertType.WARNING, "Please fill all the fields").show();
            return;
        }
        BuildingMaterial buildingMaterial = new BuildingMaterial(MaterialId, MaterialDesc, MaterialType, MaterialPrice, MaterialQty, SupplierEmail);
        try {
            boolean isAdded = buildingMaterialBO.addBuildingMaterial(new BuildingMaterialDTO(MaterialId, MaterialDesc, MaterialType, MaterialPrice, MaterialQty, SupplierEmail));
            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
                qrcodeForUser.CreateQr(MaterialId);
                bmList.add(new BuildingMaterialDTO(MaterialId, MaterialDesc, MaterialType, MaterialPrice, MaterialQty, SupplierEmail));
                loadBuildingMaterialTable();
                clearFields();
                loadNextBuildingMaterialId();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void btnUpdateMaterialOnAction(ActionEvent event) {
        String MaterialId = txtMaterialIdIn.getText();
        String MaterialDesc = txtMateriaDescription.getText();
        String MaterialType = txtMaterialType.getText();
        String MaterialPrice = txtMaterialPrice.getText();
        int MaterialQty = Integer.parseInt(txtMaterialQty.getText());
        String SupplierEmail = txtSupplierEmail.getText();

        BuildingMaterial buildingMaterial = new BuildingMaterial(MaterialId, MaterialDesc, MaterialType, MaterialPrice, MaterialQty, "null");
        try {
            boolean isUpdated = buildingMaterialBO.updateBuildingMaterial(new BuildingMaterialDTO(MaterialId, MaterialDesc, MaterialType, MaterialPrice, MaterialQty, SupplierEmail));
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
                loadBuildingMaterialTable();
                clearFields();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void txtMaterialDescriptionOnReleasedOnAction(KeyEvent event) {
        Pattern idPattern = Pattern.compile("^[a-zA-Z ]*$");
        if (!idPattern.matcher(txtMateriaDescription.getText()).matches()) {
            addError(txtMateriaDescription);

        }else{
            removeError(txtMateriaDescription);
        }
    }

//    @FXML
//    void txtMaterialIdOnReleasedOnAction(KeyEvent event) {
//        Pattern idPattern = Pattern.compile("^(BM)[0-9]{1,}$");
//        if (!idPattern.matcher(txtMaterialId.getText()).matches()) {
//            addError(txtMaterialId);
//
//        }else{
//            removeError(txtMaterialId);
//        }
//    }

    @FXML
    void txtMaterialPriceOnReleasedOnAction(KeyEvent event) {
        Pattern idPattern = Pattern.compile("^([0-9]){1,}[.]([0-9]){1,}$");
        if (!idPattern.matcher(txtMaterialPrice.getText()).matches()) {
            addError(txtMaterialPrice);

        }else{
            removeError(txtMaterialPrice);
        }
    }

    @FXML
    void txtMaterialQtyOnReleasedOnAction(KeyEvent event) {
        Pattern idPattern = Pattern.compile("^[0-9]{1,}$");
        if (!idPattern.matcher(txtMaterialQty.getText()).matches()) {
            addError(txtMaterialQty);

        }else{
            removeError(txtMaterialQty);
        }
    }

    @FXML
    void txtMaterialTypeOnReleasedOnAction(KeyEvent event) {
        Pattern idPattern = Pattern.compile("^[a-zA-Z ]*$");
        if (!idPattern.matcher(txtMaterialType.getText()).matches()) {
            addError(txtMaterialType);

        }else{
            removeError(txtMaterialType);
        }
    }

    private void removeError(TextField txtMaterialType) {
        txtMaterialType.setStyle("-fx-border-color: green; -fx-border-width: 5");
    }

    private void addError(TextField txtMaterialType) {
        txtMaterialType.setStyle("-fx-border-color: red; -fx-border-width: 5");
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String id = txtMaterialIdIn.getText();

        try {
            BuildingMaterial buildingMaterial = buildingMaterialBO.search(id);

            if (buildingMaterial != null) {
                txtMaterialId.setText(buildingMaterial.getBm_id());
                txtMateriaDescription.setText(buildingMaterial.getBm_desc());
                txtMaterialType.setText(buildingMaterial.getBm_type());
                txtMaterialPrice.setText(buildingMaterial.getBm_price());
                txtMaterialQty.setText(String.valueOf(buildingMaterial.getBm_qty()));
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnClearAdminOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtMaterialId.setText("");
        txtMaterialType.clear();
        txtMateriaDescription.clear();
        txtMaterialPrice.clear();
        txtMaterialQty.clear();

    }


    public void IdReleased(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^(BM)[0-9]{1,}$");
        if (!idPattern.matcher(txtMaterialIdIn.getText()).matches()) {
            addError(txtMaterialIdIn);

        }else{
            removeError(txtMaterialIdIn);
        }
    }

    public void emailreleased(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[a-zA-Z0-9]{1,}[@][a-zA-Z]{1,}[.][a-zA-Z]{1,}$");
        if (!idPattern.matcher(txtSupplierEmail.getText()).matches()) {
            addError(txtSupplierEmail);

        }else{
            removeError(txtSupplierEmail);
        }
    }
}

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
import lk.ijse.rental.bo.custom.AdminBO;
import lk.ijse.rental.dto.AdminDTO;
import lk.ijse.rental.dto.CustomerDTO;
import lk.ijse.rental.entity.Admin;
import lk.ijse.rental.qrGenerate.QrcodeForMachine;
import lk.ijse.rental.tdm.AdminTm;
import lk.ijse.rental.util.Regex;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AdminFormController {
    @FXML
    private Label txtId;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDeleteAdmin;

    @FXML
    private JFXButton btnSaveAdmin;

    @FXML
    private JFXButton btnUpdateAdmin;

    @FXML
    private TableColumn<?, ?> cola_ConfirmPasswors;

    @FXML
    private TableColumn<?, ?> cola_Email;

    @FXML
    private TableColumn<?, ?> cola_id;

    @FXML
    private TableColumn<?, ?> cola_name;

    @FXML
    private TableColumn<?, ?> cola_password;

    @FXML
    private TableView<AdminTm> tblAdmin;

    @FXML
    private TextField txtAid;

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtConfirmPassword;
    @FXML
    private TextField txtPassword;
    private List<AdminDTO> adminList = new ArrayList<>();
    AdminBO adminBO  = (AdminBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Admin);
    private QrcodeForMachine qrcodeForUser = new QrcodeForMachine();
    //AdminRepo adminRepo = new AdminRepo();

    @FXML
    void btnLogoutOnAction(ActionEvent event) {
    System.exit(0);
    }

    public void initialize() throws SQLException, ClassNotFoundException {
        this.adminList = getAllAdmins();
        setCellValueFactory();
        loadAdminTable();
        loadNextAdminId();
        setListener();
    }
    private void setListener() {
        tblAdmin.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    AdminDTO dto = new AdminDTO(
                            newValue.getCola_id(),
                            newValue.getCola_name(),
                            newValue.getCola_password(),
                            newValue.getCola_ConfirmPasswors(),
                            newValue.getCola_Email());
                    setFields(dto);
                });
    }

    private void setFields(AdminDTO dto) {
        txtAid.setText(dto.getA_id());
        txtName.setText(dto.getA_name());
        txtPassword.setText(dto.getA_password());
        txtConfirmPassword.setText(dto.getA_confirmPassword());
        txtEmail.setText(dto.getA_email());
    }

    private void loadNextAdminId() throws SQLException, ClassNotFoundException {
        String lastAdminId = adminBO.getLastAdminId();
        if (lastAdminId != null) {
            int id = Integer.parseInt(lastAdminId.replace("A", ""));
            id++;
            if (id < 10) {
                txtId.setText("A00" + id);
            } else if (id < 100) {
                txtId.setText("A0" + id);
            } else {
                txtId.setText("A" + id);
            }
        } else {
            txtId.setText("A001");
        }
    }

    private void setCellValueFactory() {
        cola_id.setCellValueFactory(new PropertyValueFactory<>("cola_id"));
        cola_name.setCellValueFactory(new PropertyValueFactory<>("cola_name"));
        cola_password.setCellValueFactory(new PropertyValueFactory<>("cola_password"));
        cola_ConfirmPasswors.setCellValueFactory(new PropertyValueFactory<>("cola_ConfirmPasswors"));
        cola_Email.setCellValueFactory(new PropertyValueFactory<>("cola_Email"));


    }
    private List<AdminDTO> getAllAdmins() {
        ArrayList<AdminDTO> adminsList = null;
        try {
            adminsList = adminBO.getAllAdmins();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return adminsList;
    }
    private void loadAdminTable() {

        ObservableList<AdminTm> tmList = FXCollections.observableArrayList();

        for (AdminDTO admin : adminList) {
            AdminTm adminTm = new AdminTm(
                    admin.getA_id(),
                    admin.getA_name(),
                    admin.getA_password(),
                    admin.getA_confirmPassword(),
                    admin.getA_email()

            );
            tmList.add(adminTm);
        }
        tblAdmin.setItems(tmList);
        AdminTm selectedItem = (AdminTm) tblAdmin.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }
    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtPassword.setText("");
    }
    @FXML
    void btnClearAdminOnAction(ActionEvent event) {
        clearFields();
    }


    @FXML
    void btnDeleteAdminOnAction(ActionEvent event) {
        String adminId = txtAid.getText();
        try {
            boolean isDeleted =adminBO.deleteAdmin(adminId);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted..").show();
                adminList.removeIf(temp -> temp.getA_id().equals(adminId));
                loadAdminTable();
                clearFields();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnSaveAdminOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String password = txtPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();
        String email = txtEmail.getText();

        if (!Regex.idValidation(id)) {
            new Alert(Alert.AlertType.WARNING, "Invalid Admin ID").show();
            txtId.requestFocus();
            return;
        }
       Admin admin = new Admin(id, name, password,confirmPassword,email);
        try {
            boolean isAdded = adminBO.addAdmin(new AdminDTO(id, name, password,confirmPassword,email));
            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
                qrcodeForUser.CreateQr(id);
                adminList.add(new AdminDTO(id, name, password,confirmPassword,email));
                loadAdminTable();
                loadNextAdminId();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void btnUpdateAdminOnAction(ActionEvent event) {
        String adminId = txtAid.getText();
        String adminName = txtName.getText();
        String adminPassword = txtPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();
        String adminEmail = txtPassword.getText();


        Admin admin = new Admin(adminId, adminName, adminPassword,confirmPassword,adminEmail);
        try {
            boolean isUpdated = adminBO.updateAdmin(new AdminDTO(admin.getA_id(), admin.getA_name(), admin.getA_password(), admin.getA_confirmPassword(), admin.getA_email()));
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
               adminList.add(new AdminDTO(admin.getA_id(), admin.getA_name(), admin.getA_password(), admin.getA_confirmPassword(), admin.getA_email()));
                loadAdminTable();
                clearFields();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void txtAdminConfirmPasswordOnreleasedOnAction(KeyEvent event) {
        Pattern idPattern = Pattern.compile("^[0-9]{1,}$");
        if (!idPattern.matcher(txtConfirmPassword.getText()).matches()) {
            addError(txtConfirmPassword);

        }else{
            removeError(txtConfirmPassword);
        }
    }

    private void removeError(TextField textField) {
        textField.setStyle("-fx-border-color: green; -fx-border-width: 5");
    }

    private void addError(TextField textField) {
        textField.setStyle("-fx-border-color: red; -fx-border-width: 5");
    }

    @FXML
    void txtAdminEmailOnreleasedOnAction(KeyEvent event) {
        Pattern idPattern = Pattern.compile("(^[a-zA-Z0-9_.]+[@]{1}[a-z0-9]+[\\.][a-z]+$)");
        if (!idPattern.matcher(txtEmail.getText()).matches()) {
            addError(txtEmail);

        }else{
            removeError(txtEmail);
        }
    }

//    @FXML
//    void txtAdminIdOnreleasedOnAction(KeyEvent event) {
//        Pattern idPattern = Pattern.compile("^(A)[0-9]{1,}$");
//        if (!idPattern.matcher(txtId.getText()).matches()) {
//            addError(txtId);
//
//        }else{
//            removeError(txtId);
//        }
//    }

    @FXML
    void txtAdminnameOnreleasedOnAction(KeyEvent event) {
        Pattern idPattern = Pattern.compile("^[A-z|\\\\s]{3,}$");
        if (!idPattern.matcher(txtName.getText()).matches()) {
            addError(txtName);

        }else{
            removeError(txtName);
        }
    }

    @FXML
    void txtAdminpasswordOnreleasedOnAction(KeyEvent event) {
        Pattern idPattern = Pattern.compile("^[0-9]{1,}$");
        if (!idPattern.matcher(txtPassword.getText()).matches()) {
            addError(txtPassword);

        }else{
            removeError(txtPassword);
        }
    }

    public void idReleased(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^(A)[0-9]{1,}$");
        if (!idPattern.matcher(txtAid.getText()).matches()) {
            addError(txtAid);

        }else{
            removeError(txtAid);
        }
    }
}



package lk.ijse.rental.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.rental.bo.BOFactory;
import lk.ijse.rental.bo.custom.AdminBO;
import lk.ijse.rental.db.DBConnection;
import lk.ijse.rental.dto.AdminDTO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.regex.Pattern;

public class LoginFormController {

    @FXML
    private Text ForgotPassword;

    @FXML
    private Text Signup;

    @FXML
    private JFXButton btnlogin;

    @FXML
    private TextField txtpassword;

    @FXML
    private TextField txtuserId;
    @FXML
    private AnchorPane rootNode;
    @FXML
    private Text txtGreetings;
    AdminBO adminBO = (AdminBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Admin);

    public void initialize() {
        setGreetings();
    }

    public static String adminId;

    private void setGreetings() {
        LocalTime currentTime = LocalTime.now();
        String greeting = (currentTime.getHour() < 12) ? "Good Morning" : "Good Evening";
        txtGreetings.setText(greeting);
    }

    @FXML
    void btnForgotPwOnAction(MouseEvent event) throws IOException {
        Stage stage1 = (Stage) Signup.getScene().getWindow();
        stage1.close();
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/otp_form.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.setTitle("Sign up Form");

        stage.show();
    }

    @FXML
    void btnSignupOnAction(MouseEvent event) throws IOException {
        Stage stage1 = (Stage) Signup.getScene().getWindow();
        stage1.close();
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/signup_form.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.setTitle("Sign up Form");

        stage.show();
    }

    @FXML
    void btnloginOnAction(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {


        boolean isValid = valid();
        if (isValid==true) {
            String adminUser = txtuserId.getText();
            String adminPass = txtpassword.getText();

            try {
                boolean isIn =adminBO.getUser(adminUser, adminPass);
                if (isIn == true) {
                    AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_main_form.fxml"));

                    Scene scene = new Scene(rootNode);

                    Stage stage = (Stage) this.rootNode.getScene().getWindow();
                    stage.setScene(scene);
                    stage.centerOnScreen();
                    stage.setTitle("Dashboard Form");

                } else if (adminUser.equals("admin") && adminPass.equals("1234")) {
                    Parent borderPane = FXMLLoader.load(this.getClass().getResource("/view/adminDashBoard_form.fxml"));
                    Scene scene = new Scene(borderPane);
                    Stage stage = (Stage) this.rootNode.getScene().getWindow();
                    stage.setScene(scene);
                    stage.centerOnScreen();
                    stage.setTitle("ADMIN DASHBOARD");

                } else if (adminUser.equals("admin")) {
                    txtuserId.setStyle("-fx-border-color: green");
                    txtpassword.setStyle("-fx-border-color: red");
                    txtpassword.requestFocus();

                } else {
                    txtuserId.setStyle("-fx-border-color: red");
                    txtpassword.setStyle("-fx-border-color: red");
                    txtuserId.requestFocus();
                    new Alert(Alert.AlertType.WARNING, "Invalid UserName or Password").show();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    private boolean valid(){
        String username = txtuserId.getText();
        String password = txtpassword.getText();

        boolean isUserNameValid = Pattern.matches("^[a-zA-Z ]*$",username);
        boolean isUserPassvalid = Pattern.matches("^[0-9]{1,}$",password);

        if(!isUserNameValid){
            new Alert(Alert.AlertType.ERROR,"Invalid UserName").show();
            return false;
        }
        if(!isUserPassvalid){
            new Alert(Alert.AlertType.ERROR,"Invalid User Password").show();
            return false;
        }
        return true;
    }

    private void removeError(TextField textField) {
        textField.setStyle("-fx-border-color: green; -fx-border-width: 5");
    }
    @FXML
    void txtPasswordOnReleased(KeyEvent event) {
        Pattern idPattern = Pattern.compile("^[0-9]{1,}$");
        if (!idPattern.matcher(txtpassword.getText()).matches()) {
            addError(txtpassword);

        }else{
            removeError(txtpassword);
        }
    }

    @FXML
    void txtUsernameOnReleased(KeyEvent event) {
        Pattern idPattern = Pattern.compile("^[a-zA-Z ]*$");
        if (!idPattern.matcher(txtuserId.getText()).matches()) {
            addError(txtuserId);

        }else{
            removeError(txtuserId);
        }



    }

    private void addError(TextField txtuserId) {
        txtuserId.setStyle("-fx-border-color: red; -fx-border-width: 5");
    }

    private void navigateToTheDashboard() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_main_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");

    }
    @FXML
    void btnForgotPasswordOnAction(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(this.getClass().getResource("/view/forgotPassword_form.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) this.rootNode.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setTitle("OTP Form");
        } catch (IOException e) {
            e.printStackTrace();
    }}
    @FXML
    void txtpasswordOnAction(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {

        btnloginOnAction(event);
    }

    @FXML


    public void btnuserIdOnAction(ActionEvent actionEvent) {
    }
}

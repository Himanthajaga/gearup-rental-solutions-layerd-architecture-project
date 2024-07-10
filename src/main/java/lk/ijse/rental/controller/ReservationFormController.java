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
import lk.ijse.rental.bo.custom.ReservationBO;
import lk.ijse.rental.dto.PaymentDTO;
import lk.ijse.rental.dto.ReservationDTO;
import lk.ijse.rental.entity.Reservation;
import lk.ijse.rental.tdm.ReservationTm;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ReservationFormController {
    private JFXButton btnClear;
    @FXML
    private TextField txtReservationIdnew;
    @FXML
    private JFXButton btnDeleteReservation;

    @FXML
    private JFXButton btnSaveReservation;

    @FXML
    private JFXButton btnUpdateReservation;

    @FXML
    private TableColumn<?, ?> colReservationId;

    @FXML
    private TableColumn<?, ?> colReservationType;

    @FXML
    private TableView<ReservationTm> tblReservation;
    @FXML
    private TableColumn<?, ?> colReservationDate;
    @FXML
    private DatePicker reseerDate;
    @FXML
    private TextField txtReservationType;
    @FXML
    private Label txtReservationId;
    private List<ReservationDTO> reservationList = new ArrayList<>();
   ReservationBO reservationBO  = (ReservationBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Reservation);
    public void initialize() throws SQLException, ClassNotFoundException {

        this.reservationList=getAllReservations();
        setCellValueFactory();
        loadReservationTable();
        loadNextReservationid();
        setListener();
    }
    private void setListener() {
        tblReservation.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    ReservationDTO dto = new ReservationDTO(
                            newValue.getColReservationId(),
                            newValue.getColReservationType(),
                            newValue.getColReservationDate()
                    );
                    setFields(dto);
                });
    }

    private void setFields(ReservationDTO dto) {
        txtReservationIdnew.setText(dto.getR_id());
        txtReservationType.setText(dto.getR_type());
        reseerDate.setValue(LocalDate.parse(dto.getR_date()));
    }

    private void loadNextReservationid() throws SQLException, ClassNotFoundException {
        String lastCustomerId = reservationBO.getLastReservationId();
        if (lastCustomerId != null) {
            int id = Integer.parseInt(lastCustomerId.replace("R", ""));
            id++;
            if (id < 10) {
                txtReservationId.setText("R00" + id);
            } else if (id < 100) {
                txtReservationId.setText("R0" + id);
            } else {
                txtReservationId.setText("R" + id);
            }
        } else {
            txtReservationId.setText("R001");
        }
    }


    private void setCellValueFactory() {
        colReservationId.setCellValueFactory(new PropertyValueFactory<>("colReservationId"));
        colReservationType.setCellValueFactory(new PropertyValueFactory<>("colReservationType"));
        colReservationDate.setCellValueFactory(new PropertyValueFactory<>("colReservationDate"));




    }
    private void loadReservationTable() {

        ObservableList<ReservationTm> tmList = FXCollections.observableArrayList();

        for (ReservationDTO reservation : reservationList) {
            ReservationTm reservationTm = new ReservationTm(
                   reservation.getR_id(),
                    reservation.getR_type(),
                    reservation.getR_date()


            );
            System.out.println("machineTm = " + reservationTm);
            tmList.add(reservationTm);
        }
        tblReservation.setItems(tmList);
        ReservationTm selectedItem = (ReservationTm) tblReservation.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }


    private ArrayList<ReservationDTO> getAllReservations() {
        ArrayList<ReservationDTO> reservationList = null;
        try {
            reservationList = reservationBO.getAllReservations();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return reservationList;
    }


    @FXML
    void btnDeleteReservationOnAction(ActionEvent event) {
        String id = txtReservationIdnew.getText();
        try {
            boolean isDeleted = reservationBO.deleteReservation(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Deleted..").show();
                reservationList.removeIf(reservation -> reservation.getR_id().equals(id));
                loadReservationTable();
                clearFields();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



    @FXML
    void btnSaveReservationOnAction(ActionEvent event) {
        String id = txtReservationId.getText();
        String name = txtReservationType.getText();
        String date = reseerDate.getValue().toString();
        if (id.trim().length() == 0 || name.trim().length() == 0) {
            new Alert(Alert.AlertType.WARNING, "Empty Fields").show();
            return;
        }
        Reservation reservation = new Reservation(id, name, date);
        try {
            boolean isSaved = reservationBO.addReservation(new ReservationDTO(id, name, date));
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Saved..").show();
                reservationList.add(new ReservationDTO(reservation.getR_id(),reservation.getR_type(),reservation.getR_date()));
                loadReservationTable();
                clearFields();
                loadNextReservationid();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



    @FXML
    void btnUpdateReservationOnAction(ActionEvent event) {
        String id = txtReservationIdnew.getText();
        String name = txtReservationType.getText();


        Reservation reservation = new Reservation(id, name, reseerDate.getValue().toString());
        try {
            boolean isUpdated = reservationBO.updateReservation(new ReservationDTO(reservation.getR_id(), reservation.getR_type(), reservation.getR_date()));
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
                reservationList.add(new ReservationDTO(id, name, reseerDate.getValue().toString()));
                loadReservationTable();
                clearFields();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void btnClearCustomerOnAction(ActionEvent event) {
    clearFields();
    }

    private void clearFields() {
    txtReservationId.setText("");
    txtReservationType.clear();

    }
//    @FXML
//    void txtReserReleasedOnAction(KeyEvent event) {
//        Pattern idPattern = Pattern.compile("^(R)[0-9]{1,}$");
//        if (!idPattern.matcher(txtReservationId.getText()).matches()) {
//            addError(txtReservationId);
//
//        }else{
//            removeError(txtReservationId);
//        }
//    }

    private void removeError(TextField txtReservationId) {
        txtReservationId.setStyle("-fx-border-color: green; -fx-border-width: 5");
    }

    private void addError(TextField txtReservationId) {
        txtReservationId.setStyle("-fx-border-color: red; -fx-border-width: 5");
    }

    @FXML
    void txtReserTypeReleasedOnAction(KeyEvent event) {
        Pattern idPattern = Pattern.compile("^[a-zA-Z ]*$");
        if (!idPattern.matcher(txtReservationType.getText()).matches()) {
            addError(txtReservationType);

        }else{
            removeError(txtReservationType);
        }
    }

    public void relesed(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^(R)[0-9]{1,}$");
        if (!idPattern.matcher(txtReservationIdnew.getText()).matches()) {
            addError(txtReservationIdnew);

        }else{
            removeError(txtReservationIdnew);
        }
    }
}

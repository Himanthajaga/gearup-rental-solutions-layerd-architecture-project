package lk.ijse.rental.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.rental.bo.BOFactory;
import lk.ijse.rental.bo.custom.BuildingMaterialBO;
import lk.ijse.rental.bo.custom.PaymentBO;
import lk.ijse.rental.bo.custom.SupplierBO;
import lk.ijse.rental.dto.MechanicDTO;
import lk.ijse.rental.dto.PaymentDTO;
import lk.ijse.rental.dto.SupplierDTO;
import lk.ijse.rental.entity.BuildingMaterial;
import lk.ijse.rental.entity.Payment;
import lk.ijse.rental.entity.Supplier;
import lk.ijse.rental.tdm.PaymentTm;
import lk.ijse.rental.tdm.TblPaymentCartTm;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class PaymentFormController {
    private List<PaymentDTO> paymentList = new ArrayList<>();

    @FXML
    private JFXButton btnPaySupplier;

    @FXML
    private JFXComboBox<String> cmbCustomerEmail;

    @FXML
    private TableColumn<?, ?> colMaterialName;

    @FXML
    private TableColumn<?, ?> colPaymentAmount;

    @FXML
    private TableColumn<?, ?> colPaymentId;

    @FXML
    private TableColumn<?, ?> colPaymentType;

    @FXML
    private TableColumn<?, ?> colSEmail;

    @FXML
    private TableColumn<?, ?> colSName;

    @FXML
    private Label lblMaterialDescription;

    @FXML
    private Label lblMaterialId;

    @FXML
    private Label lblOrderDate;

    @FXML
    private TextField lblPayType;

    @FXML
    private Label lblSellId;

    @FXML
    private Label lblSupplierName;
    @FXML
    private JFXButton btnClear;
    @FXML
    private TableView<PaymentTm> tblPayment;

    @FXML
    private TextField txtPaymentAmount;
    @FXML
    private JFXButton btnPrintBill;


    private ObservableList<TblPaymentCartTm> TblPaymentcartList = FXCollections.observableArrayList();
    SupplierBO supplierBO  = (SupplierBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Supplier);
    PaymentBO paymentBO  = (PaymentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Payment);
    BuildingMaterialBO buildingMaterialBO  = (BuildingMaterialBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.BuildingMaterial);


    public void initialize() {
        this.paymentList = getALLPayments();
        setCellValueFactory();
        loadPaymentTable();
        loadNextOrderId();
        setDate();
        getSupplierEmails();
        setListener();
    }
    private void setListener() {
        tblPayment.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    PaymentDTO dto = new PaymentDTO(
                            newValue.getColPaymentId(),
                            newValue.getColPaymentType(),
                            newValue.getColSEmail(),
                            newValue.getColPaymentAmount()
                    );
                    setFields(dto);
                });
    }

    private void setFields(PaymentDTO dto) {
        lblSellId.setText(dto.getPaymentId());
        lblPayType.setText(dto.getPaymentType());
        cmbCustomerEmail.setValue(dto.getS_email());
        txtPaymentAmount.setText(String.valueOf(dto.getPaymentAmount()));
    }

    @FXML
    void btnClearSellBuildingMaterialOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        lblSellId.setText("");
        lblOrderDate.setText("");
        lblMaterialId.setText("");
        lblMaterialDescription.setText("");
        lblSupplierName.setText("");
        txtPaymentAmount.clear();
        cmbCustomerEmail.setValue(null);
    }

    private void getSupplierEmails() {

        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = supplierBO.getIds();

            for (String id : idList) {
                obList.add(id);
            }
            cmbCustomerEmail.setItems(obList);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);

        }
    }

    @FXML
    void btnPrintBillOnAction(ActionEvent event) throws JRException, SQLException, ClassNotFoundException {
        HashMap hashMap = new HashMap();
        hashMap.put("paymentID", lblSellId.getText());
        hashMap.put("paymentTYPE", lblPayType.getText());
        hashMap.put("supplierEmail", cmbCustomerEmail.getValue());
        hashMap.put("totaL", txtPaymentAmount.getText());


        InputStream resourceAsStream = this.getClass().getResourceAsStream("/reports/payments.jrxml");
        JasperDesign jasperDesign = JRXmlLoader.load(resourceAsStream);

        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperReport,
                hashMap,
                new JREmptyDataSource()
        );

        JasperViewer.viewReport(jasperPrint, false);
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        lblOrderDate.setText(String.valueOf(now));
    }

    private void loadNextOrderId() {
        try {
            String currentId = paymentBO.currentId();
            String nextId = nextId(currentId);

            lblSellId.setText(nextId);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnPaySupplierOnAction(ActionEvent event) {
        String paymentId = lblSellId.getText();
        String paymentType = lblPayType.getText();
        String s_email = cmbCustomerEmail.getValue();
        String paymentAmount = txtPaymentAmount.getText();
        Payment payment = new Payment(paymentId, paymentType, s_email, Double.parseDouble(paymentAmount));
        try {
            boolean isAdded = paymentBO.addPayment(new PaymentDTO(paymentId, paymentType, s_email, Double.parseDouble(paymentAmount)));
            if (isAdded) {
                paymentList.add(new PaymentDTO(paymentId, paymentType, s_email, Double.parseDouble(paymentAmount)));
                loadPaymentTable();
                loadNextOrderId();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private String nextId(String currentId) {
        int currentIdNum = Integer.parseInt(currentId.replace("P", ""));
        currentIdNum = currentIdNum + 1;
        if (currentIdNum < 10) {
            return "P00" + currentIdNum;
        } else if (currentIdNum < 100) {
            return "P0" + currentIdNum;
        } else {
            return "P" + currentIdNum;
        }
    }


    private List<PaymentDTO> getALLPayments() {
        List<PaymentDTO> paymentList = null;
        try {
            paymentList = paymentBO.getAllPayments();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return paymentList;
    }

    private void setCellValueFactory() {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("colPaymentId"));
        colPaymentType.setCellValueFactory(new PropertyValueFactory<>("colPaymentType"));
        colSEmail.setCellValueFactory(new PropertyValueFactory<>("colSEmail"));
        colPaymentAmount.setCellValueFactory(new PropertyValueFactory<>("colPaymentAmount"));

    }

    private void loadPaymentTable() {

        ObservableList<PaymentTm> tmList = FXCollections.observableArrayList();

        for (PaymentDTO payment : paymentList) {
            PaymentTm paymentTm = new PaymentTm(
                    payment.getPaymentId(),
                    payment.getPaymentType(),
                    payment.getS_email(),
                    payment.getPaymentAmount()


            );
            System.out.println("machineTm = " + paymentTm);
            tmList.add(paymentTm);
        }
        tblPayment.setItems(tmList);
        PaymentTm selectedItem = (PaymentTm) tblPayment.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }

    @FXML
    void cmbCustomerEmailOnAction(ActionEvent event) {
        String code = cmbCustomerEmail.getValue();
        try {
            BuildingMaterial buildingMaterial = buildingMaterialBO.searchByEmail(code) ;
            Supplier supplier = supplierBO.searchByEmail(code);
            if (buildingMaterial != null) {
                lblMaterialId.setText(buildingMaterial.getBm_id());
                lblMaterialDescription.setText(buildingMaterial.getBm_desc());
                lblSupplierName.setText(supplier.getS_name());

            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void paymentAmountOnReleased(KeyEvent event) {
        Pattern idPattern = Pattern.compile("^([0-9]){1,}[.]([0-9]){1,}$");
        if (!idPattern.matcher(txtPaymentAmount.getText()).matches()) {
            addError(txtPaymentAmount);

        } else {
            removeError(txtPaymentAmount);
        }
    }

    private void removeError(TextField txtPaymentAmount) {
        txtPaymentAmount.setStyle("-fx-border-color: green; -fx-border-width: 5");
    }

    private void addError(TextField txtPaymentAmount) {
        txtPaymentAmount.setStyle("-fx-border-color: red; -fx-border-width: 5");
    }

    public void txtPaymentTypeOnReleasedOnAction(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[a-zA-Z ]*$");
        if (!idPattern.matcher(lblPayType.getText()).matches()) {
            addError(lblPayType);

        } else {
            removeError(lblPayType);
        }
    }
}

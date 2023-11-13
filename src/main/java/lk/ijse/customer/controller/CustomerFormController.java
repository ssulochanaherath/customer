package lk.ijse.customer.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.customer.dto.CustomerDto;
import lk.ijse.customer.dto.tm.CustomerTm;
import lk.ijse.customer.model.CustomerModel;

import java.sql.SQLException;
import java.util.List;

public class CustomerFormController {
    public TextField txtID;
    public TextField txtTele;
    public TextField txtName;
    public TextField txtAddress;
    public TableView<CustomerTm> tblCustomer;
    public TableColumn colID;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colTele;
    public JFXComboBox<String> comboBox;
    public JFXComboBox<String> cmbCustomersIds;
    private CustomerModel cusModel = new CustomerModel();

    public void initialize() {
        setCellValueFctory();
        loadAllCustomer();
        try {
            cmbLoadCustomerIds(null); // Pass a dummy ActionEvent or adjust your method to not require an event
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAllCustomer() {
        var model = new CustomerModel();

        ObservableList<CustomerTm> obList = FXCollections.observableArrayList();

        try {
            List<CustomerDto> dtoList = model.getAllCustomer();

            for (CustomerDto dto : dtoList) {
                obList.add(
                        new CustomerTm(
                                dto.getId(),
                                dto.getName(),
                                dto.getAddress(),
                                dto.getTele()
                        )
                );
            }

            tblCustomer.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFctory() {
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colTele.setCellValueFactory(new PropertyValueFactory<>("tele"));
    }

    public void btnSaveOnAction(ActionEvent event) {
        String id = txtID.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String tele = txtTele.getText();

        var dto = new CustomerDto(id, name, address, tele);

        try {
            boolean isSaved =  cusModel.saveCustomer(dto);

            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION,"Customer Saved!").show();
                clearFields();
            }
        } catch(SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        initialize();
    }

    private void clearFields() {
        txtID.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtTele.setText("");
    }

    public void btnDeleteOnAction(ActionEvent event) {
        String id = txtID.getText();

        try {
            boolean isDeleted = cusModel.deleteCustomer(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
                loadAllCustomer();
            } else {
                new Alert(Alert.AlertType.CONFIRMATION, "customer not deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnUpdateOnAction(ActionEvent event) {
        String id = txtID.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String tel = txtTele.getText();

        var dto = new CustomerDto(id, name, address, tel);

//        var model = new CustomerModel();
        try {
            boolean isUpdated = cusModel.updateCustomer(dto);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnSearchOnAction(ActionEvent event) {
        String id = txtID.getText();

//        var model = new CustomerModel();
        try {
            CustomerDto customerDto = cusModel.searchCustomer(id);
//            System.out.println(customerDto);
            if (customerDto != null) {
                txtID.setText(customerDto.getId());
                txtName.setText(customerDto.getName());
                txtAddress.setText(customerDto.getAddress());
                txtTele.setText(customerDto.getTele());
            } else {
                new Alert(Alert.AlertType.INFORMATION, "customer not found").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    public void cmbLoadCustomerIds(ActionEvent event) throws Exception {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<CustomerDto> idList = cusModel.loadAllCustomerIds();

            for (CustomerDto dto : idList) {
                obList.add(dto.getId());
            }

            cmbCustomersIds.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


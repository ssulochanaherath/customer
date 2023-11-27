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

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

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
    public TextField txtEmail;
    public TableColumn colEmail;
    private CustomerModel cusModel = new CustomerModel();

    public void initialize() {
        setCellValueFctory();    // Set cell value factory
        loadAllCustomer();
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
                                dto.getTele(),
                                dto.getEmail()
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
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    public void btnSaveOnAction(ActionEvent event) {
        String id = txtID.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String tele = txtTele.getText();
        String email = txtEmail.getText();

        var dto = new CustomerDto(id, name, address, tele, email);

        try {
            boolean isSaved =  cusModel.saveCustomer(dto);

            if (isSaved) {
                sendEmail(email, "Customer Saved!", "Thank you for being our customer. Your details have been saved successfully.");

                new Alert(Alert.AlertType.CONFIRMATION,"Customer Saved!").show();
                clearFields();
            }
        } catch(SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        initialize();
    }

    private void sendEmail(String to, String subject, String content) {
        final String username = "hallwembley@gmail.com"; // Replace with your Gmail username
        final String password = "nopvwkcbxpxhvjji"; // Replace with your Gmail password

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


    private void clearFields() {
        txtID.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtTele.setText("");
        txtEmail.setText("");
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
        String email = txtEmail.getText();

        var dto = new CustomerDto(id, name, address, tel, email);

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
                txtEmail.setText(customerDto.getEmail());
            } else {
                new Alert(Alert.AlertType.INFORMATION, "customer not found").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}


package com.customer.desktop.ui;

import com.customer.desktop.model.Customer;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class CustomerFormDialog extends Dialog<Customer> {

    private final TextField nameField = new TextField();
    private final TextField emailField = new TextField();
    private final TextField phoneField = new TextField();

    public CustomerFormDialog(Customer customer) {
        boolean isEdit = customer != null;

        setTitle(isEdit ? "Edit Customer" : "Add Customer");
        setHeaderText(isEdit ? "Edit customer details" : "Enter new customer details");

        // Buttons
        ButtonType saveBtn = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(saveBtn, ButtonType.CANCEL);

        // Layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        nameField.setPromptText("Full name");
        emailField.setPromptText("Email address");
        phoneField.setPromptText("01xxxxxxxxx");

        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Email:"), 0, 1);
        grid.add(emailField, 1, 1);
        grid.add(new Label("Phone:"), 0, 2);
        grid.add(phoneField, 1, 2);

        getDialogPane().setContent(grid);

        // لو Edit، حط البيانات الموجودة
        if (isEdit) {
            nameField.setText(customer.getName());
            emailField.setText(customer.getEmail());
            phoneField.setText(customer.getPhone());
        }

        // Validation قبل ما يحفظ
        getDialogPane().lookupButton(saveBtn).addEventFilter(
            javafx.event.ActionEvent.ACTION, event -> {
                if (!validate()) {
                    event.consume();
                }
            }
        );

        // Convert result
        setResultConverter(button -> {
            if (button == saveBtn) {
                Customer result = new Customer(
                    nameField.getText().trim(),
                    emailField.getText().trim(),
                    phoneField.getText().trim()
                );
                if (isEdit) result.setId(customer.getId());
                return result;
            }
            return null;
        });
    }

    private boolean validate() {
        StringBuilder errors = new StringBuilder();

        if (nameField.getText().trim().isEmpty())
            errors.append("• Name is required\n");

        if (emailField.getText().trim().isEmpty())
            errors.append("• Email is required\n");
        else if (!emailField.getText().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$"))
            errors.append("• Email format is invalid\n");

        if (phoneField.getText().trim().isEmpty())
            errors.append("• Phone is required\n");
        else if (!phoneField.getText().matches("^(\\+20|0020|0)(10|11|12|15)[0-9]{8}$"))
            errors.append("• Phone must be a valid Egyptian number\n");

        if (!errors.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Please fix the following:");
            alert.setContentText(errors.toString());
            alert.showAndWait();
            return false;
        }

        return true;
    }
}
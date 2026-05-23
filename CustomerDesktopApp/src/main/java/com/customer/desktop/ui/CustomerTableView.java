package com.customer.desktop.ui;

import com.customer.desktop.model.Customer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CustomerTableView extends TableView<Customer> {

    private final ObservableList<Customer> customerList = FXCollections.observableArrayList();

    public CustomerTableView() {
        buildColumns();
        setItems(customerList);
        setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void buildColumns() {
        TableColumn<Customer, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setMaxWidth(60);

        TableColumn<Customer, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Customer, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Customer, String> phoneCol = new TableColumn<>("Phone");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

        TableColumn<Customer, String> createdCol = new TableColumn<>("Created At");
        createdCol.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        getColumns().addAll(idCol, nameCol, emailCol, phoneCol, createdCol);
    }

    public void setCustomers(java.util.List<Customer> customers) {
        customerList.setAll(customers);
    }

    public void clear() {
        customerList.clear();
    }
}
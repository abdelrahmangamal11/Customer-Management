package com.customer.desktop.ui;

import java.util.List;

import com.customer.desktop.model.Customer;
import com.customer.desktop.service.CustomerApiService;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainWindow {

    private final CustomerApiService apiService = new CustomerApiService();
    private TableView<Customer> tableView;
    private ObservableList<Customer> customerList;
    private ProgressIndicator loadingIndicator;

    // Pagination
    private int currentPage = 0;
    private final int pageSize = 10;
    private Label pageLabel;

    // Search fields
    private TextField searchName;
    private TextField searchEmail;
    private TextField searchPhone;

    public void show(Stage stage) {
        stage.setTitle("Customer Management");

        BorderPane root = new BorderPane();
        root.setTop(buildTopBar());
        root.setCenter(buildTable());
        root.setBottom(buildBottomBar());

        Scene scene = new Scene(root, 900, 600);
        stage.setScene(scene);
        stage.show();

        loadCustomers();
    }

    // ── Top Bar ─────────────────────────────────────────────────
    private VBox buildTopBar() {
        searchName = new TextField();
        searchName.setPromptText("Search by name...");

        searchEmail = new TextField();
        searchEmail.setPromptText("Search by email...");

        searchPhone = new TextField();
        searchPhone.setPromptText("Search by phone...");

        Button searchBtn = new Button("Search");
        searchBtn.setOnAction(e -> {
            currentPage = 0;
            loadCustomers();
        });

        Button clearBtn = new Button("Clear");
        clearBtn.setOnAction(e -> {
            searchName.clear();
            searchEmail.clear();
            searchPhone.clear();
            currentPage = 0;
            loadCustomers();
        });

        HBox searchBar = new HBox(10, searchName, searchEmail, searchPhone, searchBtn, clearBtn);
        searchBar.setPadding(new Insets(10));
        searchBar.setAlignment(Pos.CENTER_LEFT);

        Button addBtn = new Button("Add Customer");
        addBtn.setOnAction(e -> openForm(null));

        Button editBtn = new Button("Edit");
        editBtn.setOnAction(e -> {
            Customer selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) openForm(selected);
            else showAlert("Please select a customer to edit.");
        });

        Button deleteBtn = new Button("Delete");
        deleteBtn.setOnAction(e -> deleteSelected());

        Button refreshBtn = new Button("Refresh");
        refreshBtn.setOnAction(e -> loadCustomers());

        HBox actionBar = new HBox(10, addBtn, editBtn, deleteBtn, refreshBtn);
        actionBar.setPadding(new Insets(0, 10, 10, 10));
        actionBar.setAlignment(Pos.CENTER_LEFT);

        return new VBox(searchBar, actionBar);
    }

    // ── Table ────────────────────────────────────────────────────
    private StackPane buildTable() {
        tableView = new TableView<>();
        customerList = FXCollections.observableArrayList();
        tableView.setItems(customerList);

        TableColumn<Customer, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(60);

        TableColumn<Customer, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(200);

        TableColumn<Customer, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailCol.setPrefWidth(220);

        TableColumn<Customer, String> phoneCol = new TableColumn<>("Phone");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        phoneCol.setPrefWidth(150);

        TableColumn<Customer, String> createdCol = new TableColumn<>("Created At");
        createdCol.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        createdCol.setPrefWidth(180);

        tableView.getColumns().addAll(idCol, nameCol, emailCol, phoneCol, createdCol);

        loadingIndicator = new ProgressIndicator();
        loadingIndicator.setMaxSize(80, 80);
        loadingIndicator.setVisible(false);

        return new StackPane(tableView, loadingIndicator);
    }

    // ── Bottom Bar ───────────────────────────────────────────────
    private HBox buildBottomBar() {
        Button prevBtn = new Button("◀ Prev");
        prevBtn.setOnAction(e -> {
            if (currentPage > 0) {
                currentPage--;
                loadCustomers();
            }
        });

        Button nextBtn = new Button("Next ▶");
        nextBtn.setOnAction(e -> {
            currentPage++;
            loadCustomers();
        });

        pageLabel = new Label("Page: 1");

        HBox bottomBar = new HBox(10, prevBtn, pageLabel, nextBtn);
        bottomBar.setPadding(new Insets(10));
        bottomBar.setAlignment(Pos.CENTER);
        return bottomBar;
    }

    // ── Load Customers ───────────────────────────────────────────
    private void loadCustomers() {
        showLoading(true);
        new Thread(() -> {
            try {
                List<Customer> customers = apiService.searchCustomers(
                        searchName.getText(),
                        searchEmail.getText(),
                        searchPhone.getText(),
                        currentPage,
                        pageSize
                );
                Platform.runLater(() -> {
                    customerList.setAll(customers);
                    pageLabel.setText("Page: " + (currentPage + 1));
                    showLoading(false);
                });
            } catch (Exception ex) {
                Platform.runLater(() -> {
                    showLoading(false);
                    showAlert("Error loading customers: " + ex.getMessage());
                });
            }
        }).start();
    }

    // ── Open Add/Edit Form ───────────────────────────────────────
    private void openForm(Customer customer) {
        CustomerFormDialog dialog = new CustomerFormDialog(customer);
        dialog.showAndWait().ifPresent(result -> {
            showLoading(true);
            new Thread(() -> {
                try {
                    if (customer == null) {
                        apiService.createCustomer(result);
                    } else {
                        apiService.updateCustomer(customer.getId(), result);
                    }
                    Platform.runLater(() -> {
                        showLoading(false);
                        loadCustomers();
                    });
                } catch (Exception ex) {
                    Platform.runLater(() -> {
                        showLoading(false);
                        showAlert("Error saving customer: " + ex.getMessage());
                    });
                }
            }).start();
        });
    }

    // ── Delete ───────────────────────────────────────────────────
    private void deleteSelected() {
        Customer selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Please select a customer to delete.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Delete " + selected.getName() + "?",
                ButtonType.YES, ButtonType.NO);
        confirm.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.YES) {
                showLoading(true);
                new Thread(() -> {
                    try {
                        apiService.deleteCustomer(selected.getId());
                        Platform.runLater(() -> {
                            showLoading(false);
                            loadCustomers();
                        });
                    } catch (Exception ex) {
                        Platform.runLater(() -> {
                            showLoading(false);
                            showAlert("Error deleting: " + ex.getMessage());
                        });
                    }
                }).start();
            }
        });
    }

    // ── Helpers ──────────────────────────────────────────────────
    private void showLoading(boolean visible) {
        loadingIndicator.setVisible(visible);
        tableView.setDisable(visible);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.showAndWait();
    }
}
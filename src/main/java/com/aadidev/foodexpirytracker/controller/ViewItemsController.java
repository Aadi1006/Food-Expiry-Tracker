package com.aadidev.foodexpirytracker.controller;

import com.aadidev.foodexpirytracker.model.FoodItem;
import com.aadidev.foodexpirytracker.repository.FoodItemRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ViewItemsController {

    @FXML private TableView<FoodItem> tableView;
    @FXML private TableColumn<FoodItem, String> nameColumn;
    @FXML private TableColumn<FoodItem, LocalDate> expiryColumn;
    @FXML private TableColumn<FoodItem, Void> actionColumn;
    @FXML private TextField nameInput;
    @FXML private DatePicker expiryInput;
    @FXML private TextField filterDaysInput;

    private final FoodItemRepository repo = new FoodItemRepository();
    private ObservableList<FoodItem> foodItems;
    private int userId;

    public void setUserId(int userId) {
        this.userId = userId;
        loadItems();
    }

    public void initialize() {
        nameColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));
        expiryColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getExpiryDate()));
        foodItems = FXCollections.observableArrayList();
        tableView.setItems(foodItems);

        // Delete buttons
        actionColumn.setCellFactory(col -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(e -> {
                    FoodItem item = getTableView().getItems().get(getIndex());
                    repo.deleteItem(item.getId());
                    foodItems.remove(item);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : deleteButton);
            }
        });
    }

    private void loadItems() {
        List<FoodItem> items = repo.getItemsForUser(userId);
        foodItems.setAll(items);
    }

    @FXML
    public void onAdd() {
        if (userId == 0) {
            new Alert(Alert.AlertType.ERROR, "User not set. Please log in again.").show();
            return;
        }
        String name = nameInput.getText();
        LocalDate date = expiryInput.getValue();
        if (name.isBlank() || date == null) return;

        FoodItem newItem = new FoodItem(0, name, date, userId);
        repo.addItem(newItem);
        loadItems();
        nameInput.clear();
        expiryInput.setValue(null);
    }

    @FXML
    public void onFilter() {
        String daysText = filterDaysInput.getText();
        if (daysText.isBlank()) {
            loadItems();
            return;
        }

        try {
            int days = Integer.parseInt(daysText);
            LocalDate today = LocalDate.now();
            LocalDate limit = today.plusDays(days);

            List<FoodItem> filtered = repo.getItemsForUser(userId).stream()
                    .filter(item -> !item.getExpiryDate().isAfter(limit))
                    .collect(Collectors.toList());

            foodItems.setAll(filtered);
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid number of days").show();
        }
    }
    @FXML
    public void onLogout(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene loginScene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/login.fxml")));
        stage.setScene(loginScene);
    }
}
package com.aadidev.foodexpirytracker.controller;

import com.aadidev.foodexpirytracker.model.FoodItem;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;
import com.aadidev.foodexpirytracker.repository.FoodItemRepository;

public class DashboardController {
    @FXML private TextField foodNameInput;
    @FXML private DatePicker expiryDateInput;
    @FXML private TableView<FoodItem> tableView;
    @FXML private TableColumn<FoodItem, String> nameColumn;
    @FXML private TableColumn<FoodItem, LocalDate> dateColumn;

    private final ObservableList<FoodItem> foodItems = FXCollections.observableArrayList();
    private final FoodItemRepository repository = new FoodItemRepository();

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        dateColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getExpiryDate()));
        foodItems.addAll(repository.loadAll());
        tableView.setItems(foodItems);
    }
    @FXML
    public void onAddItem() {
        String name = foodNameInput.getText();
        LocalDate date = expiryDateInput.getValue();

        if (name.isBlank() || date == null) {
            showAlert("Both name and date must be provided.");
            return;
        }

        FoodItem item = new FoodItem(name, date);
        foodItems.add(item);
        repository.saveItem(item);  // Save to SQLite
        foodNameInput.clear();
        expiryDateInput.setValue(null);
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.show();
    }
}
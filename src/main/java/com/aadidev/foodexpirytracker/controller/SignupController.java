package com.aadidev.foodexpirytracker.controller;

import com.aadidev.foodexpirytracker.repository.UserRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SignupController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    private final UserRepository repo = new UserRepository();

    @FXML
    public void handleSignup(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isBlank() || password.isBlank()) {
            showError("Username and password cannot be empty.");
            return;
        }

        if (repo.userExists(username)) {
            showError("User already exists. Please log in.");
            return;
        }

        repo.registerUser(username, password);
        showInfo("Signup successful. You can now log in.");
    }

    @FXML
    public void goToLogin(ActionEvent event) throws IOException {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        Scene loginScene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/login.fxml")));
        stage.setScene(loginScene);
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error");
        alert.setContentText(msg);
        alert.show();
    }

    private void showInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Success");
        alert.setContentText(msg);
        alert.show();
    }
}
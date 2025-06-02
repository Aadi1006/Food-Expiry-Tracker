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
import com.aadidev.foodexpirytracker.controller.ViewItemsController;

import java.io.IOException;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    private final UserRepository repo = new UserRepository();

    @FXML
    public void handleLogin(ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (repo.loginUser(username, password)) {
            int userId = repo.getUserId(username);
            if (userId == -1) {
                showError("User not found in database.");
                return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/view_items.fxml"));
            Scene scene = new Scene(loader.load());

            ViewItemsController controller = loader.getController();
            controller.setUserId(userId);

            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(scene);
        } else {
            showError("Invalid username or password.");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Login Failed");
        alert.setContentText(message);
        alert.show();
    }
    @FXML
    public void goToSignup(ActionEvent event) throws IOException {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        Scene signupScene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/signup.fxml")));
        stage.setScene(signupScene);
    }
}
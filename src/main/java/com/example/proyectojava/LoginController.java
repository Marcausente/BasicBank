package com.example.proyectojava;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.util.Optional;
import java.io.IOException;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    
    private static String savedPassword = "12345";

    @FXML
    protected void onLoginClick() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.equals("usuario") && password.equals(savedPassword)) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 700, 600);
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Aplicación Bancaria");
                stage.setResizable(true);
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Error al cargar la aplicación: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Error", "Usuario o contraseña incorrectos", Alert.AlertType.ERROR);
        }
    }

    @FXML
    protected void onResetPasswordClick() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Restablecer Contraseña");
        dialog.setHeaderText("Introduce la nueva contraseña");
        dialog.setContentText("Nueva contraseña:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newPassword -> {
            if (newPassword.length() > 0) {
                savedPassword = newPassword;
                showAlert("Éxito", "Contraseña actualizada correctamente", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "La contraseña no puede estar vacía", Alert.AlertType.ERROR);
            }
        });
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
} 
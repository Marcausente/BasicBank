package com.example.proyectojava;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.collections.FXCollections;

public class HelloController {
    @FXML private Text balanceText;
    @FXML private PasswordField passwordField;
    @FXML private ListView<String> transactionList;
    @FXML private ComboBox<String> currencySelector;
    
    private double balance = 1000.0;
    private String currentCurrency = "€";
    private boolean isLoggedIn = false;
    
    @FXML
    public void initialize() {
        currencySelector.setItems(FXCollections.observableArrayList("€", "$", "£"));
        currencySelector.setValue("€");
        updateBalance();
        
        // Añadir algunas transacciones de ejemplo
        transactionList.setItems(FXCollections.observableArrayList(
            "2024-03-20 10:30 - Depósito de 100€",
            "2024-03-19 15:45 - Retiro de 50€"
        ));
    }
    
    @FXML
    protected void onLoginClick() {
        if (passwordField.getText().equals("1234")) { // Contraseña de ejemplo
            isLoggedIn = true;
            showAlert("Éxito", "Inicio de sesión correcto", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Error", "Contraseña incorrecta", Alert.AlertType.ERROR);
        }
    }
    
    private void updateBalance() {
        balanceText.setText(String.format("Saldo: %.2f%s", balance, currentCurrency));
    }
    
    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    @FXML
    protected void onWithdrawClick() {
        if (!isLoggedIn) {
            showAlert("Error", "Debe iniciar sesión primero", Alert.AlertType.ERROR);
            return;
        }
        // Aquí implementarías la lógica de retiro
    }
    
    @FXML
    protected void onDepositClick() {
        if (!isLoggedIn) {
            showAlert("Error", "Debe iniciar sesión primero", Alert.AlertType.ERROR);
            return;
        }
        // Aquí implementarías la lógica de depósito
    }
    
    @FXML
    protected void onResetPasswordClick() {
        // Aquí implementarías la lógica de restablecimiento de contraseña
    }
}
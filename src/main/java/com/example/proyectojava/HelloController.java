package com.example.proyectojava;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.collections.FXCollections;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import javafx.collections.ObservableList;

public class HelloController {
    @FXML private Text balanceText;
    @FXML private ListView<String> transactionList;
    @FXML private ComboBox<String> currencySelector;
    
    private double balance = 1000.0;
    private String currentCurrency = "€";
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    
    // Tasas de conversión (en una aplicación real, estas vendrían de una API)
    private final double EUR_TO_USD = 1.09;
    private final double EUR_TO_GBP = 0.86;
    
    @FXML
    public void initialize() {
        currencySelector.setItems(FXCollections.observableArrayList("€", "$", "£"));
        currencySelector.setValue("€");
        
        // Añadir listener para el cambio de moneda
        currencySelector.setOnAction(event -> {
            currentCurrency = currencySelector.getValue();
            updateBalance();
        });
        
        updateBalance();
        
        // Inicializar la lista de transacciones si está vacía
        if (transactionList.getItems() == null) {
            transactionList.setItems(FXCollections.observableArrayList());
        }
    }
    
    @FXML
    protected void onDepositClick() {
        Dialog<Double> dialog = new Dialog<>();
        dialog.setTitle("Ingresar Dinero");
        dialog.setHeaderText("Introduce la cantidad a ingresar");

        // Crear los campos del diálogo
        TextField amountField = new TextField();
        ComboBox<String> currencyChoice = new ComboBox<>();
        currencyChoice.setItems(FXCollections.observableArrayList("€", "$", "£"));
        currencyChoice.setValue(currentCurrency);

        // Crear el layout del diálogo
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Cantidad:"), 0, 0);
        grid.add(amountField, 1, 0);
        grid.add(new Label("Moneda:"), 0, 1);
        grid.add(currencyChoice, 1, 1);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Convertir el resultado
        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                try {
                    return Double.parseDouble(amountField.getText());
                } catch (NumberFormatException e) {
                    return null;
                }
            }
            return null;
        });

        Optional<Double> result = dialog.showAndWait();
        
        result.ifPresent(amount -> {
            if (amount <= 0) {
                showAlert("Error", "La cantidad debe ser mayor que 0", Alert.AlertType.ERROR);
                return;
            }

            // Convertir a euros (moneda base)
            double amountInEuros = convertToEuros(amount, currencyChoice.getValue());
            
            // Actualizar el balance
            balance += amountInEuros;
            
            // Registrar la transacción
            String transaction = String.format("%s - Ingreso de %.2f%s", 
                LocalDateTime.now().format(formatter),
                amount,
                currencyChoice.getValue());
            
            transactionList.getItems().add(0, transaction);
            
            updateBalance();
            showAlert("Éxito", "Ingreso realizado correctamente", Alert.AlertType.INFORMATION);
        });
    }
    
    private double convertToEuros(double amount, String fromCurrency) {
        switch (fromCurrency) {
            case "€":
                return amount;
            case "$":
                return amount / EUR_TO_USD;
            case "£":
                return amount / EUR_TO_GBP;
            default:
                return amount;
        }
    }
    
    private void updateBalance() {
        double displayBalance = balance;
        
        // Convertir el balance según la moneda seleccionada
        switch (currentCurrency) {
            case "$":
                displayBalance = balance * EUR_TO_USD;
                break;
            case "£":
                displayBalance = balance * EUR_TO_GBP;
                break;
        }
        
        balanceText.setText(String.format("Saldo: %.2f%s", displayBalance, currentCurrency));
    }
    
    @FXML
    protected void onShowAllBalancesClick() {
        // Calcular saldos en todas las monedas
        double balanceEUR = balance;
        double balanceUSD = balance * EUR_TO_USD;
        double balanceGBP = balance * EUR_TO_GBP;

        // Crear el contenido del diálogo
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Saldo en todas las divisas");
        alert.setHeaderText("Tu saldo actual en diferentes monedas:");
        
        String content = String.format("""
            Euros (€): %.2f€
            Dólares ($): %.2f$
            Libras (£): %.2f£""", 
            balanceEUR, balanceUSD, balanceGBP);
            
        alert.setContentText(content);
        
        // Hacer el diálogo redimensionable
        alert.getDialogPane().setMinHeight(200);
        alert.getDialogPane().setPrefWidth(300);
        
        alert.showAndWait();
    }
    
    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    @FXML
    protected void onWithdrawClick() {
        // Implementaremos esto después
    }
    
    @FXML
    protected void onResetPasswordClick() {
        // Aquí implementarías la lógica de restablecimiento de contraseña
    }
}
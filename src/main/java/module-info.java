module com.example.proyectojava {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.proyectojava to javafx.fxml;
    exports com.example.proyectojava;
}
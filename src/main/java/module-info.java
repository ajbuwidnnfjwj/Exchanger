module com.example.exchanger {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;


    opens com.example.exchanger to javafx.fxml;
    exports com.example.exchanger;
}
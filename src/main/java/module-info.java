module williammathias.colors {
    requires javafx.controls;
    requires javafx.fxml;


    opens williammathias.colors to javafx.fxml;
    exports williammathias.colors;
}
module williammathias.colors {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;


    opens williammathias.colors to javafx.fxml;
    exports williammathias.colors;
}
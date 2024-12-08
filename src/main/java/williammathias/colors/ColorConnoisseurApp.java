package williammathias.colors;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ColorConnoisseurApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ColorConnoisseurApp.class.getResource("/williammathias/colors/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750, 750);
        scene.getStylesheets().add(ColorConnoisseurApp.class.getResource("/williammathias/colors/styles.css").toExternalForm());
        stage.setTitle("Epic game");
        stage.setScene(scene);

        ColorConnoisseurController controller = fxmlLoader.getController();
        controller.setStage(stage);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
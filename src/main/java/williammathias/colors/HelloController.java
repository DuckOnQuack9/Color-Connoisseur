package williammathias.colors;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class HelloController {
//    private final Timer timer = new Timer();
    private int level = 1;
    @FXML
    private BorderPane root;

    @FXML
    private Button startButton;

    @FXML
    private Button levelButton;

    @FXML
    void initialize() {
        startButton.setOnAction((event) -> {
            System.out.println("Start button clicked");
            runGame();
        });
    }

    private String toRgbString(javafx.scene.paint.Color color) {
        int red = (int) (color.getRed() * 255);
        int green = (int) (color.getGreen() * 255);
        int blue = (int) (color.getBlue() * 255);
        return String.format("rgb(%d, %d, %d)", red, green, blue);
    }

    private void gameOver() {
        VBox vbox = new VBox();
        Label gameOverLabel = new Label("Game Over!");
        gameOverLabel.getStyleClass().add("big-red-text");

        Label levelLabel = new Label("Level: " + level);
        levelLabel.getStyleClass().add("big-red-text");

        Button button = new Button("Try again");
        button.getStyleClass().add("start");

        button.setOnAction((event) -> runGame());
        vbox.getChildren().addAll(gameOverLabel, levelLabel, button);
        vbox.setAlignment(javafx.geometry.Pos.CENTER);
        root.setTop(null);
        root.setCenter(vbox);
        level = 1;
    }

    @FXML
    private void runGame() {
        GridPane mainGrid = new GridPane();
        mainGrid.setAlignment(javafx.geometry.Pos.CENTER);

        int rowNum = 3;
        int colNum = 3;
        int diffColorRow = (int) (Math.random() * 3);
        int diffColorCol = (int) (Math.random() * 3);
        int red = (int) (Math.random() * 255);
        int green = (int) (Math.random() * 255);
        int blue = (int) (Math.random() * 255);
        int difference = Math.max(1, 255 / (level + 5));

        javafx.scene.paint.Color sameColor = javafx.scene.paint.Color.rgb(red, green, blue);
        javafx.scene.paint.Color diffColor = javafx.scene.paint.Color.rgb(
                Math.min(255, red + difference),
                Math.min(255, green + difference),
                Math.min(255, blue + difference)
        );

        // Create grid of buttons
        for (int r = 0; r < rowNum; r++) {
            HBox hbox = new HBox();
            hbox.setSpacing(10);

            for (int c = 0; c < colNum; c++) {
                Button button = new Button();
                button.setMinSize(100, 100);
                button.getStyleClass().add("color-button");

                // Determine correct button color
                if (r == diffColorRow && c == diffColorCol) {
                    button.setStyle("-fx-background-color: " + toRgbString(diffColor) + ";");
                } else {
                    button.setStyle("-fx-background-color: " + toRgbString(sameColor) + ";");
                }

                // Set button action for color checking
                button.setOnAction((event) -> {
                    Button thisButton = (Button) event.getSource();
                    // Checks if color is guessed correctly
                    if (Objects.equals(thisButton.getStyle(), "-fx-background-color: " + toRgbString(diffColor) + ";")) {
                        level += 1;
                        root.getChildren().remove(mainGrid);
                        runGame();
                    } else {
                        gameOver();
                    }
                });

                hbox.getChildren().add(button);
            }

            mainGrid.setVgap(10);
            mainGrid.addRow(r, hbox);
        }

        VBox levelBox = new VBox();
        levelBox.setAlignment(javafx.geometry.Pos.CENTER);
        Label levelLabel = new Label("Level: " + level);
//        Label timerLabel = new Label("Time: 30");
        levelLabel.getStyleClass().add("big-red-text");
        levelBox.getChildren().add(levelLabel);

        root.setTop(levelBox);
        root.setCenter(mainGrid);
    }

    TimerTask task = new TimerTask()
    {
        public void run()
        {
            // TODO add stuff for timer
        }

    };
}
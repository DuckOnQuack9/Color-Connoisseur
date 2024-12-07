package williammathias.colors;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class HelloController {
    private Stage stage;
    private Timer timer = new Timer();
    private int level = 1;
    private int time;
    private int rowNum = 3;
    private int colNum = 3;

    @FXML
    private BorderPane root;
    @FXML
    private Button startButton;
    @FXML
    private Button levelButton;

    @FXML
    void initialize() throws FileNotFoundException {
        root.setCenter(new ImageView(new Image(new FileInputStream("src/main/resources/williammathias/colors/startScreen.png"))));
        root.setTop(getMenuContainer());

        startButton.setOnAction((event) -> {
            onStartButtonClick();
        });
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private HBox getMenuContainer() {
        MenuButton menuButton = getMenuButton();
        menuButton.getStyleClass().add("cool-button");
        HBox container = new HBox();
        container.setAlignment(javafx.geometry.Pos.CENTER);
        container.getChildren().add(menuButton);
        return container;
    }

    private MenuButton getMenuButton() {
        MenuItem threeByThree = new MenuItem("3X3");
        threeByThree.setOnAction((event) -> {
            rowNum = 3;
            colNum = 3;
        });
        MenuItem fourByFour = new MenuItem("4X4");
        fourByFour.setOnAction((event) -> {
            rowNum = 4;
            colNum = 4;
        });
        MenuItem fiveByFive = new MenuItem("5X5");
        fiveByFive.setOnAction((event) -> {
            rowNum = 5;
            colNum = 5;
        });
        MenuItem sixBySix = new MenuItem("6X6");
        sixBySix.setOnAction((event) -> {
            rowNum = 6;
            colNum = 6;
        });

        return new MenuButton("Grid Size", null, threeByThree, fourByFour, fiveByFive, sixBySix);
    }

    private void timerFunction() {
        timer.cancel();
        timer = new Timer();
        time = 10;

        TimerTask task = new TimerTask()
        {
            public void run() {
                if (time > 0) {
                Platform.runLater(() -> {
                    HBox textBox = new HBox();
                    textBox.setAlignment(javafx.geometry.Pos.CENTER);
                    Label textLabel = new Label("Level: " + level + "  |  " + "Time: " + time);
                    textLabel.setAlignment(javafx.geometry.Pos.CENTER);
                    textLabel.getStyleClass().add("big-red-text");
                    textBox.getChildren().add(textLabel);
                    root.setTop(textBox);
                    time--;
                });
            }
                else { Platform.runLater(() -> {gameOver();}); }
            }

        };

        timer.schedule(task, 0, 1000);
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

        Button tryAgainButton = new Button("Play Again");
        tryAgainButton.setPrefSize(200, 75);
        tryAgainButton.getStyleClass().add("cool-button");
        tryAgainButton.setOnAction((event) -> runGame());
        vbox.getChildren().addAll(gameOverLabel, levelLabel, tryAgainButton, getMenuContainer());
        vbox.setAlignment(javafx.geometry.Pos.CENTER);
        root.setTop(null);
        root.setCenter(vbox);
        timer.cancel();
        level = 1;
    }

    @FXML
    private void onStartButtonClick() {
        runGame();
        root.setBottom(null);
    }

    private void runGame() {
        final int threshold = 25;
        int red = (int) (Math.random() * 255);
        int green = (int) (Math.random() * 255);
        int blue = (int) (Math.random() * 255);
        int diffColorRow = (int) (Math.random() * 3);
        int diffColorCol = (int) (Math.random() * 3);

        if (red < threshold && green < threshold && blue < threshold) {
            while (red < threshold && green < threshold && blue < threshold) {
                red = (int) (Math.random() * 255);
                green = (int) (Math.random() * 255);
                blue = (int) (Math.random() * 255);
            }
        }

        int difference = Math.max(1, 255 / (level + 5));

        javafx.scene.paint.Color sameColor = javafx.scene.paint.Color.rgb(red, green, blue);
        javafx.scene.paint.Color diffColor = javafx.scene.paint.Color.rgb(
                Math.min(255, red + difference),
                Math.min(255, green + difference),
                Math.min(255, blue + difference)
        );

        // Create grid of buttons
        GridPane mainGrid = new GridPane();
        mainGrid.setAlignment(javafx.geometry.Pos.CENTER);
        for (int r = 0; r < rowNum; r++) {
            HBox hbox = new HBox();
            hbox.setSpacing(10);

            for (int c = 0; c < colNum; c++) {
                Button button = new Button();
                button.setMinSize(100, 100);
                button.getStyleClass().add("color-button");

                // Determine correct button color
                if (r == diffColorRow && c == diffColorCol) { button.setStyle("-fx-background-color: " + toRgbString(diffColor) + ";");}
                else { button.setStyle("-fx-background-color: " + toRgbString(sameColor) + ";");}

                // Set button action for color checking
                button.setOnAction((event) -> {
                    Button thisButton = (Button) event.getSource();
                    // Checks if color is guessed correctly
                    if (Objects.equals(thisButton.getStyle(), "-fx-background-color: " + toRgbString(diffColor) + ";")) {
                        level += 1;
                        root.getChildren().remove(mainGrid);
                        runGame();
                    }
                    else { gameOver();}
                });

                hbox.getChildren().add(button);
            }

            mainGrid.setVgap(10);
            mainGrid.addRow(r, hbox);
        }

        root.setCenter(mainGrid);
        timerFunction();
    }
}
package williammathias.colors;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.Objects;

public class HelloController {
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
            onStartButtonClick();
        });

        levelButton.setOnAction((event) -> {
            System.out.println("Level button clicked");
            level++;
        });
    }

    @FXML
    protected void onStartButtonClick() {
        GridPane mainGrid = new GridPane();
        mainGrid.setAlignment(javafx.geometry.Pos.CENTER);
        int rowNum = 3;
        int colNum = 3;
        int diffColorRow = (int)(Math.random() * 3);
        int diffColorCol = (int)(Math.random() * 3);
        int red = (int) (Math.random() * 255);
        int green = (int) (Math.random() * 255);
        int blue = (int) (Math.random() * 255);

        int redDifference = Math.max(1, 255 - level * 2);
        javafx.scene.paint.Color sameColor = javafx.scene.paint.Color.rgb(red, green, blue);
        javafx.scene.paint.Color diffColor = javafx.scene.paint.Color.rgb(Math.min(255, red + redDifference), green, blue);

        // Create grid of buttons
        for (int r = 0; r < rowNum; r++) {
            HBox hbox = new HBox();
            hbox.setSpacing(10);

            for (int c = 0; c < colNum; c++) {
                Button button = new Button();
                // Determine correct button color
                if(r == diffColorRow && c == diffColorCol) {
                    button.setBackground(Background.fill(diffColor));
                }
                else {
                    button.setBackground(Background.fill(sameColor));
                }

                // Set button action for color checking
                button.setOnAction((event) -> {
                    Button thisButton = (Button) event.getSource();
                    // Checks if color is guessed correctly
                    if (Objects.equals(thisButton.getBackground().getFills().getFirst().getFill(), diffColor)) {
                        System.out.println("Correct!");
                    } else {
                        System.out.println("Incorrect!");
                    }
                });

                hbox.getChildren().add(button);
            }

            mainGrid.setVgap(10);
            mainGrid.addRow(r, hbox);
        }

        root.setCenter(mainGrid);
    }
}
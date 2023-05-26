
package connect4game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Author: zuhmalik
 * Connect4Game is a JavaFX application for playing a Connect4 game.
 */

public class Connect4Game extends Application {
    private static final int ROWS = 6;
    private static final int COLUMNS = 7;
    private static final int WINNING_CONDITION = 4;

    private Color[][] board;
    private Button[][] buttons;

    private Stage primaryStage;
    private Stage gameScreenStage;
    private String player1Name;
    private String player2Name;
    private Color player1Color;
    private Color player2Color;
    private int player1Wins;
    private int player2Wins;
    private boolean player1Turn;
    private Label player1Label;
    private Label player2Label;
    private Label scoreLabel;
    private GridPane gameGrid;
    private Timeline player1Timer;
    private Timeline player2Timer;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showStartScreen();
    }

    private void showStartScreen() {
        Stage startScreenStage = new Stage();
        
        
        primaryStage.setTitle("Connect4 - Start Screen");
        
        Label titleLabel = new Label("Connect 4");
        titleLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
        titleLabel.setFont(Font.font("Tahoma")); // Change the font
        titleLabel.setTextFill(Color.BLACK); // Change the color

        Label player1Label = new Label("Player 1 Name:");
        player1Label.setFont(Font.font("Tahoma", FontWeight.BOLD, 20)); // Change the font size
        player1Label.setTextFill(Color.RED); // Change the color of lable

        Label player2Label = new Label("Player 2 Name:");
        player2Label.setFont(Font.font("Tahoma", FontWeight.BOLD, 20)); // Change the font size and make it bold
        player2Label.setTextFill(Color.YELLOW); // Change the color

        TextField player1TextField = new TextField();
        TextField player2TextField = new TextField();

        Button startButton = new Button("Start Game");
        startButton.setOnAction(e -> {
            player1Name = player1TextField.getText();
            player2Name = player2TextField.getText();

            if (!player1Name.isEmpty() && !player2Name.isEmpty()) {
                startScreenStage.close();
                showGameScreen();
            } else {
                showAlert("Error", "Please enter names for both players.");
            }
        });
        
        ImageView imageView = new ImageView(new Image("c4.jpg"));
        imageView.setFitWidth(700);
        imageView.setFitHeight(600);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(imageView, titleLabel, player1Label, player1TextField,
        player2Label, player2TextField, startButton);
        StackPane.setAlignment(titleLabel, Pos.TOP_CENTER);
        StackPane.setAlignment(player1Label, Pos.CENTER_LEFT);
        StackPane.setAlignment(player1TextField, Pos.CENTER_LEFT);
        StackPane.setAlignment(player2Label, Pos.CENTER_RIGHT);
        StackPane.setAlignment(player2TextField, Pos.CENTER_RIGHT);
        StackPane.setAlignment(startButton, Pos.BOTTOM_CENTER);
        StackPane.setMargin(titleLabel, new Insets(20));
        StackPane.setMargin(player1Label, new Insets(0, 20, 110, 5));
        StackPane.setMargin(player1TextField, new Insets(140, 100, 0, 50));
        StackPane.setMargin(player2Label, new Insets(80, 540, 0, 0));
        StackPane.setMargin(player2TextField, new Insets(0, 100, 50, 50));
        StackPane.setMargin(startButton, new Insets(0, 0, 20, 0));

        Scene startScreenScene = new Scene(stackPane, 700, 600);
        primaryStage.setScene(startScreenScene);
        primaryStage.show();
    }
    

    private void showGameScreen() {
        gameScreenStage = new Stage();
        gameScreenStage.setTitle("Connect4 - Game Screen");

        player1Color = Color.RED;
        player2Color = Color.YELLOW;
        player1Wins = 0;
        player2Wins = 0;
        player1Turn = true;

        player1Label = new Label(player1Name + "'s Turn");
        player2Label = new Label(player2Name);
        scoreLabel = new Label("Score: " + player1Name + " " + player1Wins + " - " + player2Wins + " " + player2Name);

        gameGrid = new GridPane();
        gameGrid.setHgap(10);
        gameGrid.setVgap(10);
        gameGrid.setPadding(new Insets(10));

        buttons = new Button[ROWS][COLUMNS];
        board = new Color[ROWS][COLUMNS];

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                Button button = createButton(row, col);
                buttons[row][col] = button;
                gameGrid.add(button, col, row);
            }
        }

        VBox gameScreenLayout = new VBox(20);
        gameScreenLayout.setAlignment(Pos.CENTER);
        gameScreenLayout.setPadding(new Insets(20));
        gameScreenLayout.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        gameScreenLayout.getChildren().addAll(player1Label, player2Label, scoreLabel, gameGrid);

        Scene gameScreenScene = new Scene(gameScreenLayout);
        gameScreenStage.setScene(gameScreenScene);
        gameScreenStage.show();
    }

    private Button createButton(int row, int col) {
        Button button = new Button();
        button.setPrefSize(50, 50);
        button.setShape(new Circle(25));
        button.setStyle("-fx-background-color: white; -fx-border-color: black;");

        button.setOnAction(e -> {
            if (player1Turn) {
                button.setDisable(true);
                button.setGraphic(new Circle(20, player1Color));
                board[row][col] = player1Color;

                if (checkWin(row, col, player1Color)) {
                    showAlert("Game Over", player1Name + " wins!");
                    updateScoreboard(player1Name);
                    resetGame();
                } else if (isBoardFull()) {
                    showAlert("Game Over", "It's a tie!");
                    resetGame();
                } else {
                    player1Turn = false;
                    player1Label.setText(player2Name + "'s Turn");
                    startPlayerTurnTimer();
                }
            } else {
                button.setDisable(true);
                button.setGraphic(new Circle(20, player2Color));
                board[row][col] = player2Color;

                if (checkWin(row, col, player2Color)) {
                    showAlert("Game Over", player2Name + " wins!");
                    updateScoreboard(player2Name);
                    resetGame();
                } else if (isBoardFull()) {
                    showAlert("Game Over", "It's a tie!");
                    resetGame();
                } else {
                    player1Turn = true;
                    player1Label.setText(player1Name + "'s Turn");
                    startPlayerTurnTimer();
                }
            }
        });

        return button;
    }

    private boolean checkWin(int row, int col, Color color) {
        // Check horizontal
        int count = 1;
        int i = 1;
        while (col - i >= 0 && board[row][col - i] == color) {
            count++;
            i++;
        }
        i = 1;
        while (col + i < COLUMNS && board[row][col + i] == color) {
            count++;
            i++;
        }
        if (count >= WINNING_CONDITION) {
            return true;
        }

        // Check vertical
        count = 1;
        i = 1;
        while (row - i >= 0 && board[row - i][col] == color) {
            count++;
            i++;
        }
        i = 1;
        while (row + i < ROWS && board[row + i][col] == color) {
            count++;
            i++;
        }
        if (count >= WINNING_CONDITION) {
            return true;
        }

        // Check diagonal (top-left to bottom-right)
        count = 1;
        i = 1;
        while (row - i >= 0 && col - i >= 0 && board[row - i][col - i] == color) {
            count++;
            i++;
        }
        i = 1;
        while (row + i < ROWS && col + i < COLUMNS && board[row + i][col + i] == color) {
            count++;
            i++;
        }
        if (count >= WINNING_CONDITION) {
            return true;
        }

        // Check diagonal (top-right to bottom-left)
        count = 1;
        i = 1;
        while (row - i >= 0 && col + i < COLUMNS && board[row - i][col + i] == color) {
            count++;
            i++;
        }
        i = 1;
        while (row + i < ROWS && col - i >= 0 && board[row + i][col - i] == color) {
            count++;
            i++;
        }
        if (count >= WINNING_CONDITION) {
            return true;
        }

        return false;
    }

    private boolean isBoardFull() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                if (board[row][col] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void updateScoreboard(String winner) {
        if (winner.equals(player1Name)) {
            player1Wins++;
        } else if (winner.equals(player2Name)) {
            player2Wins++;
        }
        scoreLabel.setText("Score: " + player1Name + " " + player1Wins + " - " + player2Wins + " " + player2Name);
    }

    private void resetGame() {
        gameGrid.getChildren().clear();
        buttons = new Button[ROWS][COLUMNS];
        board = new Color[ROWS][COLUMNS];

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                Button button = createButton(row, col);
                buttons[row][col] = button;
                gameGrid.add(button, col, row);
            }
        }
    }

    private void startPlayerTurnTimer() {
        if (player1Turn) {
            if (player1Timer != null) {
                player1Timer.stop();
            }
            player1Timer = new Timeline(new KeyFrame(Duration.seconds(10), e -> {
                showAlert("Time's Up", player1Name + " ran out of time!");
                updateScoreboard(player2Name);
                resetGame();
                player1Turn = false;
                player1Label.setText(player2Name + "'s Turn");
                startPlayerTurnTimer();
            }));
            player1Timer.play();
        } else {
            if (player2Timer != null) {
                player2Timer.stop();
            }
            player2Timer = new Timeline(new KeyFrame(Duration.seconds(10), e -> {
                showAlert("Time's Up", player2Name + " ran out of time!");
                updateScoreboard(player1Name);
                resetGame();
                player1Turn = true;
                player1Label.setText(player1Name + "'s Turn");
                startPlayerTurnTimer();
            }));
            player2Timer.play();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

package TicTacToe;
// Made by watching the following tutorial:
// https://www.youtube.com/watch?v=Uj8rPV6JbCE
//https://github.com/jclement92/TicTacToeFX/blob/master/src/sample/Main.java
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;


public class TicTacToe extends Application {
    private Stage closeStage;
    private boolean playable = true;
    private Tile[][] board = new Tile[3][3];
    private List<Combo> combos = new ArrayList<>();
    private String name = "";
    private Random rand = new Random();
    private Line line;
    private Text text;

    Pane root = new Pane();

    private Parent createContent() {
        root.setPrefSize(405,450);
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Welcome");
        dialog.setHeaderText(null);
        dialog.setContentText("Welcome to the TicTacToe!\nEnter your name: ");

        Optional<String> result = dialog.showAndWait();

        if(result.isPresent()) {
            name = result.get();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Welcome");
            alert.setHeaderText(null);
            alert.setContentText("Welcome, " + name + "!");
            alert.showAndWait();
        }

        text = new Text(name);
        text.setX(25);
        text.setY(35);
        text.setFont(Font.font(36));
        root.getChildren().add(text);

        Text computer = new Text("Computer");
        computer.setX(225);
        computer.setY(35);
        computer.setFont(Font.font(36));
        root.getChildren().add(computer);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Tile tile = new Tile();
                tile.setTranslateX(i*125);
                tile.setTranslateY(j*125);

                root.getChildren().add(tile);
                board[i][j] = tile;
            }
        }

        // Horizontal
        for(int y = 0; y < 3; y++) {
            combos.add(new Combo(board[0][y], board[1][y], board[2][y]));
        }

        // Vertical
        for(int x = 0; x < 3; x++) {
            combos.add(new Combo(board[x][0], board[x][1], board[x][2]));
        }

        // Diagonals
        combos.add(new Combo(board[0][0], board[1][1], board[2][2]));
        combos.add(new Combo(board[2][0], board[1][1], board[0][2]));

        return root;
    }

    private class Tile extends StackPane {
        private Text text = new Text();

        public Tile() {
            Rectangle border = new Rectangle(125,125); // Create rectangle
            border.setFill(null);           // Transparency
            border.setStroke(Color.BLACK);  // Border color
            border.setTranslateX(14);
            border.setTranslateY(50);      // Start at 14,50

            text.setTranslateX(14);
            text.setTranslateY(50);
            text.setFont(Font.font(72));    // Set the size of the text

            setAlignment(Pos.CENTER);       // Set alignment of elements within Pane
            getChildren().addAll(border, text);   // Add list of objects to stack pane

            setOnMouseClicked(event -> {
                if(!playable) return; // Checks whether the game is playable

                if(event.getButton() == MouseButton.PRIMARY) { // If left clicked
                    if(!text.getText().isEmpty()) return;
                    drawX();
                    checkState();
                    if(!playable) return;
                    drawO();
                }
            });
        }

        public double getCenterX() {
            return getTranslateX() + 62.5; // 125/2 = 62.5
        }

        public double getCenterY() {
            return getTranslateY() + 62.5;
        }

        public String getValue() {
            return text.getText();
        }

        /**
         * Create an X.
         */
        private void drawX() {
            text.setText("X");
        }

        /**
         * Create an O.
         */
        private void drawO() {
            boolean oTurn = true;

            do {
                int row = rand.nextInt(3);
                int col = rand.nextInt(3);
                if(board[row][col].getValue().isEmpty()) {
                    board[row][col].text.setText("O");
                    oTurn = false;
                    checkState();
                }
            } while(oTurn);
        }
    }

    @Override
    public void start(Stage primaryStage) {
        closeStage = primaryStage;
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.show();
    }

    private void checkState() {
        for (Combo combo: combos) {
            if (combo.isComplete()) {
                playable = false;
                playWinAnimation(combo);
                break;
            }
        }
    }

    private void playWinAnimation(Combo combo) {
        line = new Line();
        line.setTranslateX(14);
        line.setTranslateY(50);
        line.setStartX(combo.tiles[0].getCenterX());
        line.setStartY(combo.tiles[0].getCenterY());
        line.setEndX(combo.tiles[0].getCenterX());
        line.setEndY(combo.tiles[0].getCenterY());
        line.setStrokeWidth(10.0);

        root.getChildren().add(line);

        // https://docs.oracle.com/javase/8/javafx/api/javafx/animation/Timeline.html
        // Creates the line animation through the winning combo
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.1),
                new KeyValue(line.endXProperty(), combo.tiles[2].getCenterX()),
                new KeyValue(line.endYProperty(), combo.tiles[2].getCenterY())));
        timeline.play();

        playAgain();
    }

    private void playAgain() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText("Play again?");

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");

        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent()) {
            if (result.get() == yesButton){
                resetBoard();
                getNames();
                playable = true;
            } else if (result.get() == noButton) {
                Alert thankYouAlert = new Alert(Alert.AlertType.CONFIRMATION);
                thankYouAlert.setTitle("Thank you");
                thankYouAlert.setHeaderText(null);
                thankYouAlert.setContentText("Thank you for playing!");
                thankYouAlert.showAndWait();
                closeStage.close();
            }
        }
    }

    private void resetBoard() {
        root.getChildren().remove(line);
        root.getChildren().remove(text);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j].text.setText("");
            }
        }
    }

    private void getNames() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Info");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter your name: ");

        Optional<String> result = dialog.showAndWait();

        if(result.isPresent()) {
            name = result.get();
            text = new Text(name);
            text.setX(25);
            text.setY(35);
            text.setFont(Font.font(36));
            root.getChildren().add(text);
        }
    }

    // https://youtu.be/Uj8rPV6JbCE?t=921
    private class Combo {
        private Tile[] tiles;
        public Combo(Tile... tiles) {
            this.tiles = tiles;
        }

        public boolean isComplete() {
            if (tiles[0].getValue().isEmpty()) return false;

            return tiles[0].getValue().equals(tiles[1].getValue())
                    && tiles[0].getValue().equals(tiles[2].getValue());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
package com.example.battleship.controller;

import com.example.battleship.model.Board;
import com.example.battleship.model.Ship;
import com.example.battleship.model.Board.Cell;
import com.example.battleship.view.GameView;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.geometry.Pos;

import java.util.Random;

/**
 * BattleshipController controls the game logic for Battleship.
 */
public class BattleshipController {
    private boolean running = false;
    private Board enemyBoard, playerBoard;
    private int shipsToPlace = 5;
    private boolean enemyTurn = false;
    private Random random = new Random();
    private GameView view;

    /**
     * Constructor for BattleshipController.
     *
     * @param view The GameView associated with this controller.
     */
    public BattleshipController(GameView view) {
        this.view = view;
    }

    /**
     * Creates the content for the Battleship game.
     *
     * @return The root node of the game UI.
     */
    public Parent createContent() {
        view.initRoot();
        resetGame();
        return view.getRoot();
    }

    /**
     * Resets the game to its initial state.
     */
    private void resetGame() {
        running = false;
        shipsToPlace = 5;
        enemyTurn = false;

        view.setRightPanel(new Text("DESTROY THE ENEMIES"));

        // Initialize the enemy board
        enemyBoard = new Board(true, event -> {
            if (!running) return;
            Cell cell = (Cell) event.getSource();
            if (cell.wasShot) {
                showAlert("Invalid Action", "This cell has already been shot.");
                return;
            }
            enemyTurn = !cell.shoot();
            if (enemyBoard.ships == 0) {
                showEndGameAlert("CONGRATULATIONS, YOU WIN");
            }
            if (enemyTurn) enemyMove();
        });

        // Initialize the player board
        playerBoard = new Board(false, event -> {
            if (running) return;
            Cell cell = (Cell) event.getSource();
            if (cell.ship != null) {
                showAlert("Invalid Action", "A ship is already placed here.");
                return;
            }
            if (playerBoard.placeShip(new Ship(shipsToPlace, event.getButton() == MouseButton.PRIMARY), cell.x, cell.y)) {
                if (--shipsToPlace == 0) {
                    startGame();
                }
            }
        });

        VBox vbox = new VBox(50, enemyBoard, playerBoard);
        vbox.setAlignment(Pos.CENTER);

        view.setCenterPanel(vbox);
    }

    /**
     * Handles the enemy's move in the game.
     */
    private void enemyMove() {
        while (enemyTurn) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);
            Cell cell = playerBoard.getCell(x, y);
            if (cell.wasShot) continue;
            enemyTurn = cell.shoot();
            if (playerBoard.ships == 0) {
                showEndGameAlert("SORRY, YOU LOSE");
            }
        }
    }

    /**
     * Starts the game by placing ships on the enemy board.
     */
    private void startGame() {
        int[] shipCounts = {1, 2, 3, 4}; // Counter for each ship type
        int[] shipSizes = {4, 3, 2, 1}; // Sizes of each ship type

        for (int i = 0; i < shipCounts.length; i++) {
            int type = shipSizes[i];
            int count = shipCounts[i];
            while (count > 0) {
                int x = random.nextInt(10);
                int y = random.nextInt(10);
                if (enemyBoard.placeShip(new Ship(type, Math.random() < 0.5), x, y)) {
                    count--;
                }
            }
        }

        running = true;
    }

    /**
     * Shows an end game alert with the given message.
     *
     * @param message The message to display in the alert.
     */
    private void showEndGameAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                resetGame();
            }
        });
    }

    /**
     * Shows a generic alert with the given title and message.
     *
     * @param title   The title of the alert.
     * @param message The message to display in the alert.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

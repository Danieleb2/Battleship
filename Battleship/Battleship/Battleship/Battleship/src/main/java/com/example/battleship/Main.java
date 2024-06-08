package com.example.battleship;

import com.example.battleship.controller.BattleshipController;
import com.example.battleship.view.GameView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class that starts the Battleship game application.
 */
public class Main extends Application {
    /**
     * Start method overridden from Application class.
     *
     * @param primaryStage The primary stage of the application.
     */
    @Override
    public void start(Stage primaryStage) {
        // Create the GameView and BattleshipController
        GameView view = new GameView(primaryStage);
        BattleshipController controller = new BattleshipController(view);

        // Create the game content
        controller.createContent();

        // Initialize the stage with the game view
        view.initializeStage();

        // Display the stage
        view.display();
    }

    /**
     * Main method that launches the application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}

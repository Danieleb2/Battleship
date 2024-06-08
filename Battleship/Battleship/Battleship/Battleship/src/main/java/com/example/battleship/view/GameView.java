package com.example.battleship.view;

import javafx.scene.layout.BorderPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Scene;

/**
 * GameView represents the main view of the Battleship game.
 */
public class GameView {
    /** The root BorderPane for the game view. */
    private BorderPane root;

    /** The stage for displaying the game view. */
    private Stage stage;

    /**
     * Constructs a GameView object with the specified stage.
     *
     * @param stage The stage to display the game view.
     */
    public GameView(Stage stage) {
        this.stage = stage;
        initRoot();
    }

    /**
     * Initializes the root BorderPane and sets up the background image.
     */
    public void initRoot() {
        root = new BorderPane();
        root.setPrefSize(600, 800);

        // Set background image
        BackgroundPanel backgroundPanel = new BackgroundPanel("/com/example/battleship/view/images/background.png");
        root.getChildren().add(backgroundPanel);
    }

    /**
     * Sets the right panel of the BorderPane.
     *
     * @param node The node to set in the right panel.
     */
    public void setRightPanel(javafx.scene.Node node) {
        root.setRight(node);
    }

    /**
     * Sets the center panel of the BorderPane.
     *
     * @param node The node to set in the center panel.
     */
    public void setCenterPanel(javafx.scene.Node node) {
        root.setCenter(node);
    }

    /**
     * Returns the root BorderPane of the game view.
     *
     * @return The root BorderPane.
     */
    public BorderPane getRoot() {
        return root;
    }

    /**
     * Initializes the stage with the game scene and sets the window title and icon.
     */
    public void initializeStage() {
        Scene scene = new Scene(root);
        stage.setScene(scene);

        // Set window icon
        Image icon = new Image(getClass().getResourceAsStream("/com/example/battleship/view/images/icon.png"));
        stage.getIcons().add(icon);

        stage.setTitle("Battleship");
    }

    /**
     * Displays the stage.
     */
    public void display() {
        stage.show();
    }
}

package com.example.battleship.view;

import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * BackgroundPanel represents a pane with a background image.
 */
public class BackgroundPanel extends Pane {
    /** The image view for the background image. */
    private ImageView backgroundImage;

    /**
     * Constructs a BackgroundPanel object with the specified background image.
     *
     * @param fileName The file name of the background image.
     */
    public BackgroundPanel(String fileName) {
        Image image = new Image(getClass().getResourceAsStream(fileName));
        backgroundImage = new ImageView(image);
        backgroundImage.setFitWidth(600); // Adjust according to your window size
        backgroundImage.setFitHeight(800); // Adjust according to your window size
        getChildren().add(backgroundImage);
    }
}

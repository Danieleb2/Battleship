package com.example.battleship.model;

import javafx.scene.Parent;

/**
 * Ship represents a ship in the Battleship game.
 */
public class Ship extends Parent {
    /** The type of the ship, indicating its size. */
    public int type;

    /** Indicates if the ship is placed vertically or horizontally. */
    public boolean vertical = true;

    /** The health of the ship, determined by its type. */
    private int health;

    /**
     * Constructs a Ship object.
     *
     * @param type     The type of the ship.
     * @param vertical Whether the ship is placed vertically (true) or horizontally (false).
     */
    public Ship(int type, boolean vertical) {
        this.type = type;
        this.vertical = vertical;
        this.health = type;
    }

    /**
     * Decreases the health of the ship when hit.
     */
    public void hit() {
        health--;
    }

    /**
     * Checks if the ship is still alive.
     *
     * @return true if the ship's health is greater than 0, false otherwise.
     */
    public boolean isAlive() {
        return health > 0;
    }
}

package com.example.battleship.model;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * Board represents the game board for Battleship.
 */
public class Board extends Parent {
    private VBox rows = new VBox();
    private boolean enemy = false;
    public int ships = 5;
    private double cellSize = 30; // Default cell size

    /**
     * Constructs a Board object.
     *
     * @param enemy   Indicates if this board belongs to the enemy.
     * @param handler EventHandler for handling mouse clicks on cells.
     */
    public Board(boolean enemy, EventHandler<? super MouseEvent> handler) {
        this.enemy = enemy;
        for (int y = 0; y < 10; y++) {
            HBox row = new HBox();
            for (int x = 0; x < 10; x++) {
                Cell c = new Cell(x, y, this);
                c.setOnMouseClicked(handler);
                row.getChildren().add(c);
            }
            rows.getChildren().add(row);
        }
        getChildren().add(rows);
    }

    /**
     * Places a ship on the board.
     *
     * @param ship The ship to be placed.
     * @param x    X-coordinate of the starting position.
     * @param y    Y-coordinate of the starting position.
     * @return true if the ship was successfully placed, false otherwise.
     */
    public boolean placeShip(Ship ship, int x, int y) {
        if (canPlaceShip(ship, x, y)) {
            int length = ship.type;
            if (ship.vertical) {
                for (int i = y; i < y + length; i++) {
                    Cell cell = getCell(x, i);
                    cell.ship = ship;
                    if (!enemy) {
                        cell.setFill(Color.WHITE);
                        cell.setStroke(Color.GREEN);
                        drawShip(cell, ship);
                    }
                }
            } else {
                for (int i = x; i < x + length; i++) {
                    Cell cell = getCell(i, y);
                    cell.ship = ship;
                    if (!enemy) {
                        cell.setFill(Color.WHITE);
                        cell.setStroke(Color.GREEN);
                        drawShip(cell, ship);
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Draws the ship on the cells of the board.
     *
     * @param cell The starting cell of the ship.
     * @param ship The ship to be drawn.
     */
    private void drawShip(Cell cell, Ship ship) {
        cell.setFill(Color.GRAY);
        cell.setStroke(Color.DARKVIOLET);
    }

    /**
     * Retrieves the cell at the specified coordinates.
     *
     * @param x X-coordinate of the cell.
     * @param y Y-coordinate of the cell.
     * @return The cell object at the specified coordinates.
     */
    public Cell getCell(int x, int y) {
        return (Cell) ((HBox) rows.getChildren().get(y)).getChildren().get(x);
    }

    /**
     * Retrieves the neighboring cells of the specified cell.
     *
     * @param x X-coordinate of the cell.
     * @param y Y-coordinate of the cell.
     * @return An array of neighboring cells.
     */
    private Cell[] getNeighbors(int x, int y) {
        Point2D[] points = new Point2D[]{
                new Point2D(x - 1, y),
                new Point2D(x + 1, y),
                new Point2D(x, y - 1),
                new Point2D(x, y + 1)
        };
        List<Cell> neighbors = new ArrayList<>();
        for (Point2D p : points) {
            if (isValidPoint(p)) {
                neighbors.add(getCell((int) p.getX(), (int) p.getY()));
            }
        }
        return neighbors.toArray(new Cell[0]);
    }

    /**
     * Checks if a ship can be placed at the specified coordinates.
     *
     * @param ship The ship to be placed.
     * @param x    X-coordinate of the starting position.
     * @param y    Y-coordinate of the starting position.
     * @return true if the ship can be placed, false otherwise.
     */
    private boolean canPlaceShip(Ship ship, int x, int y) {
        int length = ship.type;
        if (ship.vertical) {
            for (int i = y; i < y + length; i++) {
                if (!isValidPoint(x, i)) return false;
                Cell cell = getCell(x, i);
                if (cell.ship != null) return false;
                for (Cell neighbor : getNeighbors(x, i)) {
                    if (!isValidPoint(x, i)) return false;
                    if (neighbor.ship != null) return false;
                }
            }
        } else {
            for (int i = x; i < x + length; i++) {
                if (!isValidPoint(i, y)) return false;
                Cell cell = getCell(i, y);
                if (cell.ship != null) return false;
                for (Cell neighbor : getNeighbors(i, y)) {
                    if (!isValidPoint(i, y)) return false;
                    if (neighbor.ship != null) return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if the given point is a valid coordinate on the board.
     *
     * @param point The point to be checked.
     * @return true if the point is valid, false otherwise.
     */
    private boolean isValidPoint(Point2D point) {
        return isValidPoint(point.getX(), point.getY());
    }

    /**
     * Checks if the given coordinates are valid on the board.
     *
     * @param x X-coordinate to be checked.
     * @param y Y-coordinate to be checked.
     * @return true if the coordinates are valid, false otherwise.
     */
    private boolean isValidPoint(double x, double y) {
        return x >= 0 && x < 10 && y >= 0 && y < 10;
    }

    /**
     * Sets the size of the cells on the board.
     *
     * @param size The size to be set for the cells.
     */
    public void setCellSize(double size) {
        this.cellSize = size;
        for (int y = 0; y < 10; y++) {
            HBox row = (HBox) rows.getChildren().get(y);
            for (int x = 0; x < 10; x++) {
                Cell cell = (Cell) row.getChildren().get(x);
                cell.setSize(size);
            }
        }
    }

    /**
     * Represents a single cell on the board.
     */
    public class Cell extends Rectangle {
        public int x, y;
        public Ship ship = null;
        public boolean wasShot = false;
        private Board board;

        /**
         * Constructs a Cell object.
         *
         * @param x     X-coordinate of the cell.
         * @param y     Y-coordinate of the cell.
         * @param board The board to which the cell belongs.
         */
        public Cell(int x, int y, Board board) {
            super(30, 30);
            this.x = x;
            this.y = y;
            this.board = board;
            setFill(Color.LIGHTGRAY);
            setStroke(Color.BLACK);
        }

        /**
         * Sets the size of the cell.
         *
         * @param size The size to be set for the cell.
         */
        public void setSize(double size) {
            setWidth(size);
            setHeight(size);
        }

        /**
         * Simulates shooting at the cell.
         *
         * @return true if the shot hits a ship, false otherwise.
         */
        public boolean shoot() {
            wasShot = true;
            setFill(Color.BLUE);
            if (ship != null) {
                ship.hit();
                setFill(Color.RED);
                if (!ship.isAlive()) {
                    board.ships--;
                }
                return true;
            }
            return false;
        }
    }
}

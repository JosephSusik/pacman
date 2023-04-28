package com.example.pacman;

import javafx.fxml.FXML;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.application.Platform;
import javafx.scene.paint.Color;

import java.util.Timer;
import java.util.TimerTask;

public class GameController extends Group implements EventHandler<KeyEvent> {
    @FXML
    private int rows;
    @FXML
    private int cols;

    @FXML
    private ImageView[][] cellViews;

    @FXML
    private GameController gameController;



    private Maze maze;

    private double CellSize = 30.0;

    public void giveMaze(Maze maze) {
        this.maze = maze;
    }


    public void useMaze(Maze maze) {
        this.maze = maze;
        gameController.giveMaze(maze);
        gameController.initializeGrid();
        gameController.update_map();
    }

    public void initialize1() {
        gameController.initializeGrid();
        gameController.update_map();
    }

    /**
     * Constructs an empty grid of ImageViews
     */
    private void initializeGrid() {
        if (this.maze.numRows() > 0 && this.maze.numCols() > 0) {
            this.cellViews = new ImageView[this.maze.numRows()][this.maze.numCols()];
            for (int row = 0; row < this.maze.numRows(); row++) {
                for (int column = 0; column < this.maze.numCols(); column++) {
                    ImageView imageView = new ImageView();
                    imageView.setX((double)column * this.CellSize);
                    imageView.setY((double)row * this.CellSize);
                    imageView.setFitWidth(this.CellSize);
                    imageView.setFitHeight(this.CellSize);
                    this.cellViews[row][column] = imageView;
                    this.getChildren().add(imageView);
                }
            }
        }
    }

    public void update_map(){
        Image wallImage =  new Image(getClass().getResourceAsStream("wall.png"));
        Image pacmanUpImage =  new Image(getClass().getResourceAsStream("pacman-up.gif"));
        Image pacmanDownImage =  new Image(getClass().getResourceAsStream("pacman-down.gif"));
        Image pacmanRightImage =  new Image(getClass().getResourceAsStream("pacman-right.gif"));
        Image pacmanLeftImage =  new Image(getClass().getResourceAsStream("pacman-left.gif"));
        for (int row = 0; row < this.maze.numRows(); row++) {
            for (int col = 0; col < this.maze.numCols(); col++) {
                Field cell = maze.getField(row, col);
                if (cell instanceof WallField) {
                    this.cellViews[row][col].setImage(wallImage);
                } else if (cell instanceof PathField && !cell.isEmpty() && cell.get() instanceof PacmanObject) {
                    this.cellViews[row][col].setImage(pacmanRightImage);
                }
            }
        }
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();
        if (code == KeyCode.LEFT) {
            System.out.print(this.maze.numRows());
        } else if (code == KeyCode.RIGHT) {
            System.out.print(this.maze.numCols());
        } else if (code == KeyCode.UP) {
            System.out.print(this.maze.numCols());
        } else if (code == KeyCode.DOWN) {
            System.out.print(this.maze.numCols());
        }
    }
}

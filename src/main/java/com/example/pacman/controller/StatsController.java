package com.example.pacman.controller;

import com.example.pacman.common.Field;
import com.example.pacman.common.Maze;
import com.example.pacman.common.MazeObject;
import com.example.pacman.game.GhostObject;
import com.example.pacman.game.PacmanObject;
import com.example.pacman.game.PathField;
import com.example.pacman.game.WallField;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

public class StatsController extends Group {

    private ImageView[][] cellViews;

    private Maze maze;

    private List<MazeObject> PoleGhostu;

    private PacmanObject pacman;

    private int backup_lives;

    private double CellSize = 40.0;

    private Image pacmanHeartImage;


    public void setStats(Maze maze) {
        this.maze = maze;
        this.pacman = maze.getPacman();
        this.PoleGhostu = maze.getGhosts();
        this.backup_lives = pacman.lives;
        this.pacmanHeartImage =  new Image(getClass().getResourceAsStream("full-heart-2.png"));
    }

    public void initializeGrid() {
        this.cellViews = new ImageView[1][pacman.lives];
        for (int row = 0; row < 1; row++) {
            for (int column = 0; column < pacman.lives; column++) {
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

    public void reset_stats() {
        pacman.lives = backup_lives;
    }

    public void update_stats(){
        for (int row = 0; row < 1; row++) {
            for (int col = 0; col < backup_lives; col++) {
                if (col < pacman.lives){
                    this.cellViews[row][col].setImage(pacmanHeartImage);
                } else {
                    this.cellViews[row][col].setImage(null);
                }
            }
        }
    }
}

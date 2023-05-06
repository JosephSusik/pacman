package com.example.pacman.controller;
/*
 * Author: Marek Putala
 * File: StatsReplayController.java
 */

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class StatsReplayController extends Group {

    private ImageView[][] cellViews;

    private double CellSize = 40.0;

    private Image pacmanHeartImage;

    public void initializeGrid() {
        this.pacmanHeartImage =  new Image(getClass().getResourceAsStream("image/full-heart-2.png"));
        this.cellViews = new ImageView[1][3];
        for (int row = 0; row < 1; row++) {
            for (int column = 0; column < 3; column++) {
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

    public void update_stats(String[] splitMap){
        int lives = Integer.parseInt(splitMap[splitMap.length-3]);
        for (int row = 0; row < 1; row++) {
            for (int col = 0; col < 3; col++) {
                if (col < lives){
                    this.cellViews[row][col].setImage(pacmanHeartImage);
                } else {
                    this.cellViews[row][col].setImage(null);
                }
            }
        }
    }
}

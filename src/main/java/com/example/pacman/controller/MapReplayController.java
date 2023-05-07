/*
 * Author: Marek Putala
 * File: MapReplayController.java
 */

package com.example.pacman.controller;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MapReplayController extends Group {

    private ImageView[][] cellViews;

    private double CellSize = 30.0;

    private Image wallImage;
    private Image pacmanUpImage;
    private Image pacmanDownImage;
    private Image pacmanRightImage;
    private Image pacmanLeftImage;
    private Image pacmanGhostImage;
    private Image keyImage;
    private Image doorOpenImage;
    private Image doorClosedImage;
    private Image pointImage;

    /**
     * Initializes grid
     * @param rows number of rows
     * @param cols number of cols
     */
    public void initializeGrid(int rows, int cols) {
        this.wallImage =  new Image(getClass().getResourceAsStream("image/wall.png"));
        this.pacmanUpImage =  new Image(getClass().getResourceAsStream("gif/pacman-up.gif"));
        this.pacmanDownImage =  new Image(getClass().getResourceAsStream("gif/pacman-down.gif"));
        this.pacmanRightImage =  new Image(getClass().getResourceAsStream("gif/pacman-right.gif"));
        this.pacmanLeftImage =  new Image(getClass().getResourceAsStream("gif/pacman-left.gif"));
        this.pacmanGhostImage =  new Image(getClass().getResourceAsStream("gif/ghost-yellow.gif"));
        this.keyImage = new Image(getClass().getResourceAsStream("image/key22.png"));
        this.doorClosedImage  = new Image(getClass().getResourceAsStream("image/door-closed2.png"));
        this.doorOpenImage  = new Image(getClass().getResourceAsStream("image/door-open2.png"));
        this.pointImage  = new Image(getClass().getResourceAsStream("image/point2.png"));

        if (rows > 0 && cols > 0) {
            this.cellViews = new ImageView[rows][cols];
            for (int row = 0; row < rows; row++) {
                for (int column = 0; column < cols; column++) {
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

    /**
     * Updates map
     * @param splitMap Map to load
     */
    public void update_map(String[] splitMap){
        for (int row = 0; row < splitMap.length-3; row++) {
            int col = 0;
            for (char ch: splitMap[row].toCharArray()) {
                if (ch == '.'){
                    this.cellViews[row][col].setImage(pointImage);
                }
                else if (ch == ',') {
                    this.cellViews[row][col].setImage(null);
                }
                else if (ch == 'G') {
                    this.cellViews[row][col].setImage(pacmanGhostImage);
                }
                else if (ch == 'P') {
                    this.cellViews[row][col].setImage(pacmanRightImage);
                }
                else if (ch == 'K') {
                    this.cellViews[row][col].setImage(keyImage);
                }
                else if (ch == 'D') {
                    this.cellViews[row][col].setImage(doorOpenImage);
                }
                else if (ch == 'X') {
                    this.cellViews[row][col].setImage(wallImage);
                }
                else {
                    this.cellViews[row][col].setImage(doorClosedImage);
                }
                col++;
            }
        }
    }
}

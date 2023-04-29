package com.example.pacman.controller;

import com.example.pacman.common.Field;
import com.example.pacman.common.Maze;
import com.example.pacman.common.MazeObject;
import com.example.pacman.game.GhostObject;
import com.example.pacman.game.PacmanObject;
import com.example.pacman.game.PathField;
import com.example.pacman.game.WallField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class GameController implements EventHandler<KeyEvent> {


    @FXML
    private MapController mapController;

    @FXML
    private StatsController statsController;

    @FXML
    private Label steps;

    @FXML
    private Label score;

    @FXML
    private Label logMessages;

    private int messages;

    private Font pixel_font;

    private Timer timer;

    private PacmanObject pacman;

    public void startGame(Maze maze) throws FileNotFoundException {

        pacman = maze.getPacman();

        mapController.setMaze(maze);
        mapController.initializeGrid();
        mapController.update_map();

        statsController.setStats(maze);
        statsController.initializeGrid();
        statsController.update_stats();

        pixel_font = Font.loadFont(getClass().getResourceAsStream("fxml/style/ArcadeFont.ttf"), 12);

        score.setText(String.format("Score 0"));
        steps.setText(String.format("Steps: %d", pacman.steps));
        messages = 0;

        setTimer();
    }

    public void restartGame() {

        mapController.restart_maze();
        statsController.reset_stats();
        mapController.update_map();
        statsController.update_stats();

        score.setText(String.format("Score: 0"));
        steps.setText(String.format("Steps: %d", pacman.steps));

        setTimer();
    }

    private void printLabel() {
        if (messages == 0){
            logMessages.setText(String.format("Use arrows to navigate pacman"));
            messages++;
        } else if (messages == 3){
            logMessages.setText(String.format("Press N key to restart game"));
            messages++;
        } else if (messages == 6){
            logMessages.setText(String.format("Press P key to pause game"));
            messages++;
        } else if (messages == 9){
            messages = 0;
        }
    }

    public void setTimer() {
        this.timer = new Timer();

        TimerTask task = new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    mapController.process_objects();
                    mapController.update_map();
                    statsController.update_stats();
                    score.setText(String.format("Score 0"));
                    steps.setText(String.format("Steps: %d", pacman.steps));
                    printLabel();
                });
            }
        };

        this.timer.schedule(task, 0, 500);
    }


    @Override
    public void handle(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();
        if (code == KeyCode.LEFT) {
            mapController.set_next_direction(PacmanObject.Direction.L);
        } else if (code == KeyCode.RIGHT) {
            mapController.set_next_direction(PacmanObject.Direction.R);
        } else if (code == KeyCode.UP) {
            mapController.set_next_direction(PacmanObject.Direction.U);
        } else if (code == KeyCode.DOWN) {
            mapController.set_next_direction(PacmanObject.Direction.D);
        } else if (code == KeyCode.N) {
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
            restartGame();
        } else if (code == KeyCode.P) {
            if (timer == null) {
                setTimer();
            } else {
                timer.cancel();
                timer = null;
            }
        }
    }
}

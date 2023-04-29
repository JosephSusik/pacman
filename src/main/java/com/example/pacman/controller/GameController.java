package com.example.pacman.controller;

import com.example.pacman.common.Field;
import com.example.pacman.common.Maze;
import com.example.pacman.common.MazeObject;
import com.example.pacman.game.GhostObject;
import com.example.pacman.game.PacmanObject;
import com.example.pacman.game.PathField;
import com.example.pacman.game.WallField;
import javafx.fxml.FXML;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GameController implements EventHandler<KeyEvent> {


    @FXML
    private MapController mapController;

    @FXML
    private StatsController statsController;


    private Timer timer;


    public void startGame(Maze maze) {

        mapController.setMaze(maze);
        mapController.initializeGrid();
        mapController.update_map();

        statsController.setStats(maze);
        statsController.initializeGrid();
        statsController.update_stats();

        setTimer();
    }

    public void setTimer() {
        this.timer = new Timer();

        TimerTask task = new TimerTask() {
            public void run() {
                mapController.process_objects();
                mapController.update_map();
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
        }
    }
}

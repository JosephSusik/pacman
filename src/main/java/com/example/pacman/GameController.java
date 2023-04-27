package com.example.pacman;

import javafx.fxml.FXML;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.application.Platform;
import java.util.Timer;
import java.util.TimerTask;

public class GameController implements EventHandler<KeyEvent>{

    private Maze maze;

    public void useMaze(Maze maze) {
        this.maze = maze;
    }


    @Override
    public void handle(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();
        if (code == KeyCode.LEFT) {
            System.out.print(this.maze.numRows());
        } else if (code == KeyCode.RIGHT) {
            System.out.print(this.maze.numCols());
        }
    }
}

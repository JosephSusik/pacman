/*
 * Author: Marek Putala, Josef Susík
 * File: GameController.java
 */

package com.example.pacman.controller;

import com.example.pacman.common.Field;
import com.example.pacman.common.Maze;
import com.example.pacman.common.MazeObject;
import com.example.pacman.game.*;
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
import java.text.SimpleDateFormat;
import java.util.*;


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

    private Timer timerMessage;

    private PacmanObject pacman;

    private List<String> panelMessages = new ArrayList<>();

    private String logFile;

    private Maze maze;

    public void createFile(){
        try {
            File myObj = new File(logFile);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void writeFile() {
        try {
            BufferedWriter out = new BufferedWriter(
                    new FileWriter(logFile, true));
            for (int x = 0; x < maze.numCols(); x++) {
                for (int y = 0; y < maze.numRows(); y++) {
                    Field tmp = maze.getField(x,y);
                    if (tmp instanceof WallField) {
                        out.write("X");
                    } else if (tmp instanceof PathField) {
                        if (tmp.isEmpty() && ((PathField) tmp).point == true){
                            out.write(".");
                        }
                        else if (tmp.isEmpty() && ((PathField) tmp).point == false) {
                            out.write(",");
                        }
                        else if (((PathField) tmp).get() instanceof GhostObject) {
                            out.write("G");
                        }
                        else if (((PathField) tmp).get() instanceof PacmanObject) {
                            out.write("P");
                        }
                        else if (((PathField) tmp).get() instanceof KeyObject) {
                            out.write("K");
                        }
                        else if (((PathField) tmp).get() instanceof DoorObject && ((DoorObject) ((PathField) tmp).get()).open == true) {
                            out.write("D");
                        }
                        else {
                            out.write("T");
                        }
                    }
                }
                out.write("|");
            }
            out.write(pacman.lives + "|" + pacman.score + "|" + pacman.steps);
            out.write("\n");
            out.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void setupFile() {
        try {
            BufferedWriter out = new BufferedWriter(
                    new FileWriter(logFile, true));
            out.write(maze.numRows()+" "+ maze.numCols()+" "+"\n");
            out.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void startGame(Maze maze) throws FileNotFoundException {

        pacman = maze.getPacman();
        this.maze = maze;

        logFile = new SimpleDateFormat("yyyyMMddHHmmSS'.txt'").format(new Date());
        createFile();


        mapController.setMaze(maze);
        mapController.initializeGrid();
        mapController.update_map();
        setupFile();
        writeFile();

        statsController.setStats(maze);
        statsController.initializeGrid();
        statsController.update_stats();

        pixel_font = Font.loadFont(getClass().getResourceAsStream("fxml/style/ArcadeFont.ttf"), 12);

        score.setText(String.format("Score 0"));
        steps.setText(String.format("Steps: %d", pacman.steps));
        panelMessages.add("Use arrows to navigate pacman");
        panelMessages.add("Press N key to restart game");
        panelMessages.add("Press P key to pause game");

        setTimer();
    }

    public void restartGame() {

        mapController.restart_maze();
        statsController.reset_stats();
        mapController.update_map();
        statsController.update_stats();

        score.setText(String.format("Score: %d", pacman.score));
        steps.setText(String.format("Steps: %d", pacman.steps));

        setTimer();
    }

    private void rotateLabel() {
        Collections.rotate(panelMessages, 1);
        logMessages.setText(String.format(panelMessages.get(0)));
    }

    public void setTimer() {
        this.timer = new Timer();
        if (this.timerMessage == null) {
            this.timerMessage = new Timer();
            TimerTask taskMessage = new TimerTask() {
                public void run() {
                    Platform.runLater(() -> {
                        rotateLabel();
                    });
                }
            };
            this.timerMessage.schedule(taskMessage, 0, 2500);
        }

        this.timerMessage = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    mapController.process_objects();
                    mapController.update_map();
                    if (pacman.won == true) {
                        timer.cancel();
                        timer = null;
                        panelMessages.removeAll(panelMessages);
                        panelMessages.add("You Won! Press N to restart");
                        rotateLabel();
                    }
                    if(pacman.lives == 0){
                        timer.cancel();
                        timer = null;
                        panelMessages.removeAll(panelMessages);
                        panelMessages.add("Game Over! Press N to restart");
                        rotateLabel();
                    }
                    statsController.update_stats();
                    score.setText(String.format("Score: %d", pacman.score));
                    steps.setText(String.format("Steps: %d", pacman.steps));
                    writeFile();
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
            if (panelMessages.size() != 3) {
                panelMessages.removeAll(panelMessages);
                panelMessages.add("Use arrows to navigate pacman");
                panelMessages.add("Press N key to restart game");
                panelMessages.add("Press P key to pause game");
                rotateLabel();
            }
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
            restartGame();
            pacman.score = 0;
            pacman.steps = 0;

            for (PathField path : mapController.PolePolicek) {
                path.point = true;
            }

            logFile = new SimpleDateFormat("yyyyMMddHHmmSS'.txt'").format(new Date());
            createFile();
            setupFile();
            writeFile();
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

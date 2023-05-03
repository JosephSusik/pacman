package com.example.pacman.controller;

import com.example.pacman.common.Field;
import com.example.pacman.game.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ReplayController implements EventHandler<KeyEvent> {

    @FXML
    private MapReplayController mapReplayController;

    @FXML
    private StatsReplayController statsReplayController;

    @FXML
    private Label steps;

    @FXML
    private Label score;

    @FXML
    private Label logMessages;

    private int rows;

    private int cols;

    private int numLines;

    private int actual_line;

    private String[] string_parts;


    public void startReplay(String content_replay) {
        String[] parts = content_replay.split("\n");
        this.string_parts = parts;
        this.actual_line = 1;
        this.numLines = parts.length - 1;
        String[] row_col = parts[0].split(" ");
        this.rows = Integer.parseInt(row_col[0]);
        this.cols = Integer.parseInt(row_col[1]);
        String[] splitMap = string_parts[actual_line].split("\\|");
        mapReplayController.initializeGrid(this.rows, this.cols);
        mapReplayController.update_map(splitMap);
        statsReplayController.initializeGrid();
        statsReplayController.update_stats(splitMap);
        logMessages.setText(String.format("Use LEFT and RIGHT arrows"));
        score.setText(String.format("Score: " + splitMap[splitMap.length-2]));
        steps.setText(String.format("Steps: " + splitMap[splitMap.length-1]));
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();
        if (code == KeyCode.LEFT) {
            if(actual_line > 1){
                actual_line--;
                String[] splitMap = string_parts[actual_line].split("\\|");
                mapReplayController.update_map(splitMap);
                statsReplayController.update_stats(splitMap);
                score.setText(String.format("Score: " + splitMap[splitMap.length-2]));
                steps.setText(String.format("Steps: " + splitMap[splitMap.length-1]));
                logMessages.setText(String.format("Frame "+Integer.toString(actual_line)+ " of "+Integer.toString(numLines)));
            }
        } else if (code == KeyCode.RIGHT) {
            if(actual_line < numLines){
                actual_line++;
                String[] splitMap = string_parts[actual_line].split("\\|");
                mapReplayController.update_map(splitMap);
                statsReplayController.update_stats(splitMap);
                score.setText(String.format("Score: " + splitMap[splitMap.length-2]));
                steps.setText(String.format("Steps: " + splitMap[splitMap.length-1]));
                logMessages.setText(String.format("Frame "+Integer.toString(actual_line)+ " of "+Integer.toString(numLines)));
            }
        }
    }
}

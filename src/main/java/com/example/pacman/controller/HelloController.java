/*
 * Author: Josef Susík
 * File: HelloController.java
 */

package com.example.pacman.controller;

import com.example.pacman.PacmanGame;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private Button btn_startGame;

    @FXML
    private Button btn_replayGame;

    @FXML
    private Button btn_quitGame;

    @FXML
    private Label pacmanName;

    /**
     * Initializes main controller
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_startGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Utils.changeScene(actionEvent, "fxml/start-game.fxml", "Start Game");
            }
        });

        btn_replayGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Utils.changeScene(actionEvent, "fxml/replay-game.fxml", "Replay Mode");
            }
        });

        btn_quitGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Platform.exit();
            }
        });
    }
}
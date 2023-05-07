/*
 * Author: Marek Putala, Josef Susík
 * File: LoadReplayController.java
 */

package com.example.pacman.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class LoadReplayController implements Initializable {
    @FXML
    private Button btn_selectReplay;

    @FXML
    private Button btn_startReplay;

    @FXML
    private Label label;

    @FXML
    private Label Replay_label;

    @FXML
    private Button btn_back;

    public File selectedFile;

    /**
     * Initializes replay controller
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_selectReplay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser fil_chooser = new FileChooser();
                FileChooser.ExtensionFilter extFilter =
                        new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
                fil_chooser.getExtensionFilters().add(extFilter);

                Node node = (Node) actionEvent.getSource();
                Stage st = (Stage) node.getScene().getWindow();
                File file = fil_chooser.showOpenDialog(st);
                if (file != null) {
                    selectedFile = file;
                    //label.setText(selectedFile.getAbsolutePath() + " selected");
                    label.setText("File: " + selectedFile.getName());
                    Replay_label.setText("");
                }
            }
        });

        btn_startReplay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (selectedFile == null) {
                    Replay_label.setText("No replay selected");
                } else {
                    BufferedReader reader;

                    try {
                        Path filePath = Path.of(selectedFile.getAbsolutePath());
                        String content_replay = Files.readString(filePath);

                        String[] result = content_replay.split(" ");

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/running-replay.fxml"));
                        Parent root = loader.load();

                        ReplayController replayController = loader.getController();
                        root.setOnKeyPressed(replayController);
                        replayController.startReplay(content_replay);

                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        stage.setTitle("Pac-Man Replay");
                        Scene scene = new Scene(root, Integer.parseInt(result[0]) * 30 + 130, Integer.parseInt(result[1]) * 30 + 130);
                        scene.getStylesheets().add(getClass().getResource("fxml/style/styling.css").toExternalForm());
                        stage.setScene(scene);
                        stage.show();
                        root.requestFocus();

                        //Utils.changeScene(actionEvent, "running-game.fxml", "Pac-Man");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        Replay_label.setText("Not valid map");

                    }
                }
            }
        });

        btn_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Utils.changeScene(actionEvent, "fxml/menu-view.fxml", "Pac-Man");
            }
        });
    }
}

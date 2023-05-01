package com.example.pacman.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_selectReplay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser fil_chooser = new FileChooser();
                FileChooser.ExtensionFilter extFilter =
                        new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
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

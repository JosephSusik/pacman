package com.example.pacman;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class LoadMapController implements Initializable{

    @FXML
    private Button btn_selectMap;

    @FXML
    private Button btn_back;

    @FXML
    private Label label;

    public File selectedFile;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FileChooser fil_chooser = new FileChooser();

        btn_selectMap.setOnAction(new EventHandler<ActionEvent>() {
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
                }
            }
        });

        btn_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Utils.changeScene(actionEvent,"hello-view.fxml", "Pac-Man");
            }
        });
    }
}

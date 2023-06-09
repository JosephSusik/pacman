package com.example.pacman.controller;
/*
 * Author: Marek Putala, Josef Susík
 * File: LoadMapController.java
 */

import com.example.pacman.common.Maze;
import com.example.pacman.game.MazeConfigure;
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
import java.io.FileReader;
import java.io.IOException;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class LoadMapController implements Initializable{

    @FXML
    private Button btn_selectMap;

    @FXML
    private Button btn_startGame;

    @FXML
    private Button btn_back;

    @FXML
    private Label label;

    @FXML
    private Label start_label;

    public File selectedFile;

    /**
     * Initializes map controller
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btn_selectMap.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                FileChooser fil_chooser = new FileChooser();

                FileChooser.ExtensionFilter extFilter =
                        new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
                fil_chooser.getExtensionFilters().add(extFilter);
                String currentPath = Paths.get("./data/").toAbsolutePath().normalize().toString();
                fil_chooser.setInitialDirectory(new File(currentPath));

                Node node = (Node) actionEvent.getSource();
                Stage st = (Stage) node.getScene().getWindow();
                File file = fil_chooser.showOpenDialog(st);
                if (file != null) {
                    selectedFile = file;
                    //label.setText(selectedFile.getAbsolutePath() + " selected");
                    label.setText("File: " + selectedFile.getName());
                    start_label.setText("");
                }
            }
        });

        btn_startGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (selectedFile == null) {
                    start_label.setText("No map selected");
                }
                else {
                    BufferedReader reader;

                    try {
                        reader = new BufferedReader(new FileReader(selectedFile));
                        String line = reader.readLine();
                        String[] parts = line.split(" ");
                        MazeConfigure cfg = new MazeConfigure();
                        int rows = Integer.parseInt(parts[0]);
                        int cols = Integer.parseInt(parts[1]);
                        cfg.startReading(rows, cols);
                        line = reader.readLine();
                        while (line != null) {
                            cfg.processLine(line);
                            line = reader.readLine();
                        }
                        reader.close();
                        cfg.stopReading();
                        Maze maze = cfg.createMaze();

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/running-game.fxml"));
                        Parent root = loader.load();

                        GameController gameController = loader.getController();
                        root.setOnKeyPressed(gameController);
                        gameController.startGame(maze);

                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        stage.setTitle("Pac-Man");
                        Scene scene = new Scene(root, maze.numRows()*30+130, maze.numCols()*30+130);
                        scene.getStylesheets().add(getClass().getResource("fxml/style/styling.css").toExternalForm());
                        stage.setScene(scene);
                        stage.show();
                        root.requestFocus();

                        //Utils.changeScene(actionEvent, "running-game.fxml", "Pac-Man");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        start_label.setText("Not valid map");
                    }
                }
            }
        });

        btn_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Utils.changeScene(actionEvent,"fxml/menu-view.fxml", "Pac-Man");
            }
        });
    }
}

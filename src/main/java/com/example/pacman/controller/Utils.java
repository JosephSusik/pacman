package com.example.pacman.controller;
/*
 * Author: Josef Susík
 * File: Utils.java
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class Utils {
    /**
     * Changes scene
     * @param event event for scene
     * @param fxmlFile fxml file to load
     * @param title title to set to scene
     */
    public static void changeScene(ActionEvent event, String fxmlFile, String title) {
        Parent root = null;

        try {
            root = FXMLLoader.load(Utils.class.getResource(fxmlFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);

        Scene scene = new Scene(root, 320, 240);
        scene.getStylesheets().add(Utils.class.getResource("fxml/style/styling.css").toExternalForm());
        stage.setScene(scene);
        stage.show();

        //stage.setScene(new Scene(root, 320, 240));
        //stage.show();
    }
}

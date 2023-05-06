/*
 * Author: Marek Putala, Josef Susík
 * File: PacmanGame.java
 */

package com.example.pacman;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class PacmanGame extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("controller/fxml/menu-view.fxml"));
        stage.setTitle("Pac-Man");
/*
        stage.setScene(new Scene(root, 320,240));
        stage.show();
*/
        Scene scene = new Scene(root, 320, 240);
        scene.getStylesheets().add(getClass().getResource("controller/fxml/style/styling.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
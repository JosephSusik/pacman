module com.example.pacman {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.pacman to javafx.fxml;
    exports com.example.pacman;
    exports com.example.pacman.common;
    opens com.example.pacman.common to javafx.fxml;
    exports com.example.pacman.game;
    opens com.example.pacman.game to javafx.fxml;
    exports com.example.pacman.controller;
    opens com.example.pacman.controller to javafx.fxml;
}
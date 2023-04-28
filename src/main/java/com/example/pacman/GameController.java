package com.example.pacman;

import javafx.fxml.FXML;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.application.Platform;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GameController extends Group implements EventHandler<KeyEvent> {

    @FXML
    private ImageView[][] cellViews;

    @FXML
    private GameController gameController;

    private List<MazeObject> PoleGhostu = new ArrayList<MazeObject>();

    private PacmanObject pacman;

    private Maze maze;

    private Timer timer;

    private double CellSize = 30.0;

    private Image wallImage;
    private Image pacmanUpImage;
    private Image pacmanDownImage;
    private Image pacmanRightImage;
    private Image pacmanLeftImage;
    private Image pacmanGhostImage;
    private MazeObject ghost;
    private MazeObject ghost1;

    public void giveMaze(Maze maze) {
        this.maze = maze;
        this.pacman = maze.getPacman();
        this.PoleGhostu = maze.getGhosts();
        System.out.println(this.PoleGhostu);
        this.wallImage =  new Image(getClass().getResourceAsStream("wall.png"));
        this.pacmanUpImage =  new Image(getClass().getResourceAsStream("pacman-up.gif"));
        this.pacmanDownImage =  new Image(getClass().getResourceAsStream("pacman-down.gif"));
        this.pacmanRightImage =  new Image(getClass().getResourceAsStream("pacman-right.gif"));
        this.pacmanLeftImage =  new Image(getClass().getResourceAsStream("pacman-left.gif"));
        this.pacmanGhostImage =  new Image(getClass().getResourceAsStream("ghost-yellow.gif"));
    }


    public void useMaze(Maze maze) {
        this.maze = maze;
        gameController.giveMaze(maze);
        gameController.initializeGrid();
        gameController.update_map();
        gameController.setTimer();
    }

    public void setTimer() {
        this.timer = new Timer();

        TimerTask task = new TimerTask() {
            public void run() {
                process_objects();
                update_map();
            }
        };

        this.timer.schedule(task, 0, 500);
    }

    public void initialize1() {
        gameController.initializeGrid();
        gameController.update_map();
    }

    /**
     * Constructs an empty grid of ImageViews
     */
    private void initializeGrid() {
        if (this.maze.numRows() > 0 && this.maze.numCols() > 0) {
            this.cellViews = new ImageView[this.maze.numRows()][this.maze.numCols()];
            for (int row = 0; row < this.maze.numRows(); row++) {
                for (int column = 0; column < this.maze.numCols(); column++) {
                    ImageView imageView = new ImageView();
                    imageView.setX((double)column * this.CellSize);
                    imageView.setY((double)row * this.CellSize);
                    imageView.setFitWidth(this.CellSize);
                    imageView.setFitHeight(this.CellSize);
                    this.cellViews[row][column] = imageView;
                    this.getChildren().add(imageView);
                }
            }
        }
    }

    public Field.Direction pacmanDirToField(PacmanObject.Direction pdir) {
        if (pdir == PacmanObject.Direction.U){
            return Field.Direction.U;
        } else if (pdir == PacmanObject.Direction.D) {
            return Field.Direction.D;
        } else if (pdir == PacmanObject.Direction.L) {
            return Field.Direction.L;
        } else {
            return Field.Direction.R;
        }
    }

    public void restart_maze() {
        pacman.reset();
        for(MazeObject ghost : PoleGhostu) {
            ghost.reset();
        }
    }

    public void process_objects(){
        boolean restart = false;
        //pohyb pacman
        if (pacman.next_direction != PacmanObject.Direction.NONE) {
            Field.Direction ndir = pacmanDirToField(pacman.next_direction);
            Field.Direction cdir = pacmanDirToField(pacman.current_direction);
            if (pacman.current_direction == pacman.next_direction) {
                if (pacman.canMove(ndir)) {
                    pacman.move(ndir);
                }
            } else if (pacman.current_direction != pacman.next_direction) {
                if (pacman.canMove(ndir)) {
                    pacman.current_direction = pacman.next_direction;
                    pacman.move(ndir);
                } else if (pacman.canMove(cdir)) {
                    pacman.move(cdir);
                }
            }
        }
        Field pac_field = pacman.getField();
        for(MazeObject ghost : PoleGhostu) {
            if (ghost.getField() == pac_field) {
                restart = true;
            }
            ghost.move(Field.Direction.R);
        }

        if (restart) {
            restart_maze();
        }
    }

    public void update_map(){
        for (int row = 0; row < this.maze.numRows(); row++) {
            for (int col = 0; col < this.maze.numCols(); col++) {
                Field cell = maze.getField(row, col);
                if (cell instanceof WallField) {
                    this.cellViews[row][col].setImage(wallImage);
                } else if (cell instanceof PathField && !cell.isEmpty() && cell.get() instanceof PacmanObject) {
                    if (pacman.current_direction == PacmanObject.Direction.L) {
                        this.cellViews[row][col].setImage(pacmanLeftImage);
                    } else if (pacman.current_direction == PacmanObject.Direction.R) {
                        this.cellViews[row][col].setImage(pacmanRightImage);
                    } else if (pacman.current_direction == PacmanObject.Direction.D) {
                        this.cellViews[row][col].setImage(pacmanDownImage);
                    } else if (pacman.current_direction == PacmanObject.Direction.U) {
                        this.cellViews[row][col].setImage(pacmanUpImage);
                    } else {
                        this.cellViews[row][col].setImage(pacmanRightImage);
                    }
                } else if (cell instanceof PathField && !cell.isEmpty() && cell.get() instanceof GhostObject) {
                    this.cellViews[row][col].setImage(pacmanGhostImage);
                } else if (cell instanceof PathField && cell.isEmpty()) {
                    this.cellViews[row][col].setImage(null);
                }
            }
        }
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();
        if (code == KeyCode.LEFT) {
            gameController.pacman.next_direction = PacmanObject.Direction.L;
        } else if (code == KeyCode.RIGHT) {
            gameController.pacman.next_direction = PacmanObject.Direction.R;
        } else if (code == KeyCode.UP) {
            gameController.pacman.next_direction = PacmanObject.Direction.U;
        } else if (code == KeyCode.DOWN) {
            gameController.pacman.next_direction = PacmanObject.Direction.D;
        }
    }
}

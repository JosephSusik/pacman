package com.example.pacman.controller;

import com.example.pacman.common.Field;
import com.example.pacman.common.Maze;
import com.example.pacman.common.MazeObject;
import com.example.pacman.game.GhostObject;
import com.example.pacman.game.PacmanObject;
import com.example.pacman.game.PathField;
import com.example.pacman.game.WallField;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MapController extends Group {

    private ImageView[][] cellViews;

    private Maze maze;

    private List<MazeObject> PoleGhostu;

    private PacmanObject pacman;

    private double CellSize = 30.0;

    private Image wallImage;
    private Image pacmanUpImage;
    private Image pacmanDownImage;
    private Image pacmanRightImage;
    private Image pacmanLeftImage;
    private Image pacmanGhostImage;
    private MazeObject ghost;
    private MazeObject ghost1;


    public void setMaze(Maze maze) {
        this.maze = maze;
        this.pacman = maze.getPacman();
        this.PoleGhostu = maze.getGhosts();
        System.out.println(this.PoleGhostu);
        this.wallImage =  new Image(getClass().getResourceAsStream("image/wall.png"));
        this.pacmanUpImage =  new Image(getClass().getResourceAsStream("gif/pacman-up.gif"));
        this.pacmanDownImage =  new Image(getClass().getResourceAsStream("gif/pacman-down.gif"));
        this.pacmanRightImage =  new Image(getClass().getResourceAsStream("gif/pacman-right.gif"));
        this.pacmanLeftImage =  new Image(getClass().getResourceAsStream("gif/pacman-left.gif"));
        this.pacmanGhostImage =  new Image(getClass().getResourceAsStream("gif/ghost-yellow.gif"));
    }

    public void initializeGrid() {
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
            //Ghost move
            int ghostX = ghost.ghostX();
            int ghostY = ghost.ghostY();
            int pacY = pacman.row;
            int pacX = pacman.col;

            //Ghost 1
            ghost.move(move_ghost(ghostX, ghostY, pacX, pacY, ghost));

            /* Ghost 2
            * targetX = targetX-pacX
            * targetY = targetY-pacY
            */

            if (ghost.getField() == pac_field) {
                pacman.lives--;
                restart = true;
            }
            //ghost.move(Field.Direction.R);
        }

        if (restart) {
            restart_maze();
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

    public void set_next_direction(PacmanObject.Direction dir) {
        pacman.next_direction = dir;
    }

    //currentX, currentY = X,Y coordinates of ghost
    //targetX, targetY = X,Y coordinates of pacman
    public Field.Direction move_ghost(int currentX, int currentY, int targetX, int targetY, MazeObject ghost) {
        int horizonDiff = currentX - targetX;
        int verticalDiff = currentY - targetY;
        Field.Direction currentDir;
        //left/right?
        Field.Direction horizonDir = horizonDiff > 0? Field.Direction.L : Field.Direction.R;
        //up/down?
        Field.Direction verticalDir = verticalDiff > 0? Field.Direction.U : Field.Direction.D;

        if(Math.abs(verticalDiff) > Math.abs(horizonDiff)) {
            currentDir = verticalDir;
        } else {
            currentDir = horizonDir;
        }
        if(!ghost.canMove(currentDir)) {
            if(currentDir == verticalDir) {
                if (ghost.GetLastDir() == Field.Direction.L || ghost.GetLastDir() == Field.Direction.R) {
                    ghost.setCurrDir(ghost.GetLastDir());
                    currentDir = ghost.GetLastDir();
                    if(!ghost.canMove(currentDir)) {
                        currentDir = currentDir == Field.Direction.L ? Field.Direction.R : Field.Direction.L;
                    }
                } else {
                    currentDir = horizonDir;
                    if(!ghost.canMove(currentDir)) {
                        currentDir = horizonDir == Field.Direction.L ? Field.Direction.R : Field.Direction.L;
                        if(!ghost.canMove(currentDir)) {
                            currentDir = verticalDir == Field.Direction.U ? Field.Direction.D : Field.Direction.U;
                        }
                    }
                }
            } else {
                if (ghost.GetLastDir() == Field.Direction.U || ghost.GetLastDir() == Field.Direction.D) {
                    ghost.setCurrDir(ghost.GetLastDir());
                    currentDir = ghost.GetLastDir();
                    if(!ghost.canMove(currentDir)) {
                        currentDir = currentDir == Field.Direction.U ? Field.Direction.D : Field.Direction.U;
                    }
                } else {
                    currentDir = verticalDir;
                    if(!ghost.canMove(currentDir)) {
                        currentDir = verticalDir == Field.Direction.U ? Field.Direction.D : Field.Direction.U;
                        if(!ghost.canMove(currentDir)) {
                            currentDir = horizonDir == Field.Direction.L ? Field.Direction.R : Field.Direction.L;
                        }
                    }
                }
            }
        }
        //set lastdir as currdir
        ghost.setLastDir(currentDir);
        return currentDir;
    }
}

package com.example.pacman.controller;

import com.example.pacman.common.Field;
import com.example.pacman.common.Maze;
import com.example.pacman.common.MazeObject;
import com.example.pacman.game.*;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MapController extends Group {

    private ImageView[][] cellViews;

    private Maze maze;

    private List<MazeObject> PoleGhostu;

    private List<KeyObject> PoleKlicu;

    private List<DoorObject> PoleDveri;

    public List<PathField> PolePolicek;

    private PacmanObject pacman;

    private double CellSize = 30.0;

    private Image wallImage;
    private Image pacmanUpImage;
    private Image pacmanDownImage;
    private Image pacmanRightImage;
    private Image pacmanLeftImage;
    private Image pacmanGhostImage;
    private Image keyImage;
    private Image doorOpenImage;
    private Image doorClosedImage;
    private Image pointImage;
    private MazeObject ghost;
    private MazeObject ghost1;


    public void setMaze(Maze maze) {
        this.maze = maze;
        this.pacman = maze.getPacman();
        this.PoleGhostu = maze.getGhosts();
        this.PoleKlicu = maze.getKeys();
        this.PoleDveri = maze.getDoors();
        this.PolePolicek = maze.getPaths();
        this.wallImage =  new Image(getClass().getResourceAsStream("image/wall.png"));
        this.pacmanUpImage =  new Image(getClass().getResourceAsStream("gif/pacman-up.gif"));
        this.pacmanDownImage =  new Image(getClass().getResourceAsStream("gif/pacman-down.gif"));
        this.pacmanRightImage =  new Image(getClass().getResourceAsStream("gif/pacman-right.gif"));
        this.pacmanLeftImage =  new Image(getClass().getResourceAsStream("gif/pacman-left.gif"));
        this.pacmanGhostImage =  new Image(getClass().getResourceAsStream("gif/ghost-yellow.gif"));
        this.keyImage = new Image(getClass().getResourceAsStream("image/key22.png"));
        this.doorClosedImage  = new Image(getClass().getResourceAsStream("image/door-closed2.png"));
        this.doorOpenImage  = new Image(getClass().getResourceAsStream("image/door-open2.png"));
        this.pointImage  = new Image(getClass().getResourceAsStream("image/point2.png"));
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
                } else if (cell instanceof PathField && !cell.isEmpty() && cell.get() instanceof GhostObject) {
                    this.cellViews[row][col].setImage(pacmanGhostImage);
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
                } else if (cell instanceof PathField && !cell.isEmpty() && cell.get() instanceof KeyObject){
                    this.cellViews[row][col].setImage(keyImage);
                } else if (cell instanceof PathField && !cell.isEmpty() && cell.get() instanceof DoorObject){
                    if (((DoorObject) cell.get()).open == true) {
                        this.cellViews[row][col].setImage(doorOpenImage);
                    } else if (((DoorObject) cell.get()).open == false) {
                        this.cellViews[row][col].setImage(doorClosedImage);
                    }
                }
                else if (cell instanceof PathField && cell.isEmpty() && ((PathField) cell).point == true) {
                    this.cellViews[row][col].setImage(pointImage);
                }
                else if (cell instanceof PathField && cell.isEmpty()) {
                    this.cellViews[row][col].setImage(null);
                }
            }
        }
    }

    public void process_point() {
        if (pacman.field.point == true) {
            pacman.field.point = false;
            pacman.score += 10;
        }
    }

    public void process_keys() {
        boolean open = true;
        for (KeyObject key : PoleKlicu) {
            if (pacman.getField() == key.getField()) {
                key.used = true;
            }
            if (key.used == false) {
                open = false;
                break;
            }
        }
        for (DoorObject door : PoleDveri) {
            if (open == true) {
                door.open = true;
            }
            if (pacman.getField() != door.getField()) {
                door.getField().put(door);
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
                    process_keys();
                    for (DoorObject door : PoleDveri) {
                        if (door.getField() == pacman.getField() && door.open == true) {
                            pacman.won = true;
                        }
                    }
                    process_point();
                }
            } else if (pacman.current_direction != pacman.next_direction) {
                if (pacman.canMove(ndir)) {
                    pacman.current_direction = pacman.next_direction;
                    pacman.move(ndir);
                    process_keys();
                    for (DoorObject door : PoleDveri) {
                        if (door.getField() == pacman.getField() && door.open == true) {
                            pacman.won = true;
                        }
                    }
                    process_point();
                } else if (pacman.canMove(cdir)) {
                    pacman.move(cdir);
                    process_keys();
                    for (DoorObject door : PoleDveri) {
                        if (door.getField() == pacman.getField() && door.open == true) {
                            pacman.won = true;
                        }
                    }
                    process_point();
                }
            }
        }
        Field pac_field = pacman.getField();
        for (KeyObject tmpKey : PoleKlicu) {
            if (tmpKey.used == false && tmpKey.getField() == pac_field) {
                tmpKey.used = true;
            } else if (tmpKey.used == false) {
                tmpKey.getField().put(tmpKey);
            }
        }
        for(MazeObject ghost : PoleGhostu) {
            /*
            první - PoleGhostu.get(0)
            MazeObject gh1 = PoleGhostu.get(0);
            MazeObject gh2 = PoleGhostu.get(1);

            poslední - PoleGhostu.get(PoleGhostu.size()-1)
            */

            if (ghost.getField() == pac_field) {
                pacman.lives--;
                restart = true;
                break;
            }
            //Ghost move
            int ghostX = ghost.ghostX();
            int ghostY = ghost.ghostY();
            int pacY = pacman.row;
            int pacX = pacman.col;

            //Ghost 1
            ghost.move(move_ghost(ghostX, ghostY, pacX, pacY, ghost));
            /* Ghost 2 - Pinky
            if(pacman.current_direction == PacmanObject.Direction.L) {
                targetX = pacman.col-4;
                targetY = pacman.row;
            }
            if(pacman.current_direction == PacmanObject.Direction.R) {
                targetX = pacman.col+4;
                targetY = pacman.row;
            }
            if(pacman.current_direction == PacmanObject.Direction.D) {
                targetX = pacman.col;
                targetY = pacman.row-4;
            }
            if(pacman.current_direction == PacmanObject.Direction.U) {
                targetX = pacman.col-4;
                targetY = pacman.row+4;
            }
            ghost.move(move_ghost(ghostX, ghostY, targetX, targetY, ghost));
            */

            /* Ghost 3 - Clyde
            //Distance from pacman
            distX = Math.abs(ghostX - targetX)
            distY = Math.abs(ghostY - targetY)
            if(distX > 8 || distY > 8) {
                ghost.move(move_ghost(ghostX, ghostY, pacX, pacY, ghost));
            } else {
                ghost.move(move_ghost(ghostX, ghostY, 0, 0, ghost));
            }
            */

            if (ghost.getField() == pac_field) {
                pacman.lives--;
                restart = true;
                break;
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
        for(KeyObject key : PoleKlicu) {
            key.reset();
        }
        for(MazeObject ghost : PoleGhostu) {
            ghost.reset();
        }
        for(DoorObject door : PoleDveri) {
            door.reset();
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

package com.example.pacman.game;
/*
 * Author: Marek Putala, Josef Susík
 * File: MazeConfigure.java
 */

import java.util.ArrayList;
import java.util.List;

import com.example.pacman.common.Field;
import com.example.pacman.common.Maze;
import com.example.pacman.common.MazeObject;

public class MazeConfigure {
    private int rows, cols;
    private int actual_rows;
    private boolean reading = false;
    private boolean fail = false;
    private Field[][] fields;
    private List<MazeObject> PoleGhostu = new ArrayList<MazeObject>();
    private List<KeyObject> PoleKlicu = new ArrayList<KeyObject>();
    private List<DoorObject> PoleDveri = new ArrayList<DoorObject>();
    private List<PathField> PolePolicek = new ArrayList<PathField>();
    private PacmanObject pacman;

    /**
     * Konstruktor
     */
    public MazeConfigure() {}

    /**
     * Start reading
     * @param rows rows
     * @param cols  cols
     */
    public void startReading(int rows,int cols){
        // kvůli okrajovým zdím musí být bludiště větěí o jedno v každém směru
        this.rows = rows+2;
        this.cols = cols+2;
        this.reading = true;
        this.actual_rows = 0;
        // vytvoření nového pole pro políčka
        this.fields = new Field[rows+2][cols+2];
    }

    /**
     * process line
     * @param line line of map
     * @return true on success
     */
    public boolean processLine(String line){
        this.actual_rows++;
        // kontrola korektnosti vstupního řetězce
        if((this.actual_rows > this.rows-2)
                || (!line.matches("^[X.SGKT]+$"))
                || (line.length() != this.cols-2)
                || (!this.reading)) {
            this.fail = true;
            // pokud string neodpovídá, tak se navrací false a nastaví se fail na true
            return false;
        }
        // postupné zpracování řetězce na jednotlivé symboly a vytvoření políček, případně objektů
        for(int counter = 0; counter < this.cols-2; counter++){
            Character ltr = line.charAt(counter);
            // zde se jedná o zed, vytvoří se Wallfield a vloží se do pole bludiště
            if(ltr.equals('X')){
                WallField wallField = new WallField(this.actual_rows, counter+1);
                this.fields[this.actual_rows][counter+1] = wallField;
            } // zde se jedná o cestu, vytvoří se Pathfield a vloží se do pole bludiště
            else if (ltr.equals('.')) {
                PathField pathField = new PathField(this.actual_rows, counter+1);
                this.fields[this.actual_rows][counter+1] = pathField;
                pathField.point = true;
                PolePolicek.add(pathField);
            } // zde se jedná o cestu, vytvoří se Pathfield a vloží se do pole bludiště a vytvoří se objekt pacman
            // nově vytvořený objekt se položí na políčko
            else if (ltr.equals('S')) {
                PathField pathField = new PathField(this.actual_rows, counter+1);
                PacmanObject pacman = new PacmanObject(this.actual_rows, counter+1);
                pacman.field = pathField;
                pathField.objectOnField = pacman;
                if (this.pacman != null) {
                    return false;
                }
                this.pacman = pacman;
                this.fields[this.actual_rows][counter+1] = pathField;
            } // zde se jedná o cestu, vytvoří se Pathfield a vloží se do pole bludiště a vytvoří se objekt duch
            // nově vytvořený objekt se položí na políčko
            else if (ltr.equals('G')) {
                PathField pathField = new PathField(this.actual_rows, counter+1);
                GhostObject ghost = new GhostObject(this.actual_rows, counter+1);
                ghost.field = pathField;
                pathField.objectOnField = ghost;
                this.fields[this.actual_rows][counter+1] = pathField;
                if (PoleGhostu.size() == 3) {
                    return false;
                }
                PoleGhostu.add(ghost);
            }
            else if (ltr.equals('K')) {
                PathField pathField = new PathField(this.actual_rows, counter+1);
                KeyObject key = new KeyObject(this.actual_rows, counter+1);
                key.field = pathField;
                pathField.objectOnField = key;
                this.fields[this.actual_rows][counter+1] = pathField;
                PoleKlicu.add(key);
            }
            else if (ltr.equals('T')) {
                PathField pathField = new PathField(this.actual_rows, counter+1);
                DoorObject door = new DoorObject(this.actual_rows, counter+1);
                door.field = pathField;
                pathField.objectOnField = door;
                this.fields[this.actual_rows][counter+1] = pathField;
                PoleDveri.add(door);
            }
            else {
                return false;
            }
        }
        return true;
    }

    /**
     * Stop read
     * @return true success
     */
    public boolean stopReading(){
        this.reading = false;
        // vytvoření zdí na prvním a posledním řádku
        for (int counter = 0; counter < this.cols; counter++){
            WallField fwallField = new WallField(0, counter);
            WallField lwallField = new WallField(this.rows-1, counter);
            this.fields[0][counter] = fwallField;
            this.fields[this.rows-1][counter] = lwallField;
        }
        // vytvoření zdí na krajích všech řádků s vyjímkou prvního a poslední
        for (int counter = 1; counter < this.rows; counter++){
            WallField lwallField = new WallField(counter, 0);
            this.fields[counter][0] = lwallField;
            WallField rwallField = new WallField(counter, this.cols-1);
            this.fields[counter][this.cols-1] = rwallField;
        }
        if (this.PoleGhostu.size() == 0) {
            this.fail = true;
            return false;
        }
        return true;
    }

    /**
     * Create maze
     * @return returns Maze
     */
    public Maze createMaze(){
        if(this.fail == true) {
            return null;
        }
        Maze maze = new Maze(){
            public int numRows(){
                return MazeConfigure.this.rows;
            }
            public int numCols(){
                return MazeConfigure.this.cols;
            }
            public Field getField(int row,
                                  int col){
                return MazeConfigure.this.fields[row][col];
            }
            public PacmanObject getPacman() {
                return MazeConfigure.this.pacman;
            }
            public List<MazeObject> getGhosts() {
                List<MazeObject> cloned_list
                        = new ArrayList<MazeObject>(PoleGhostu);
                return cloned_list;
            }
            public List<KeyObject> getKeys() {
                List<KeyObject> cloned_list
                        = new ArrayList<KeyObject>(PoleKlicu);
                return cloned_list;
            }
            public List<DoorObject> getDoors() {
                List<DoorObject> cloned_list
                        = new ArrayList<DoorObject>(PoleDveri);
                return cloned_list;
            }
            public List<PathField> getPaths() {
                List<PathField> cloned_list
                        = new ArrayList<PathField>(PolePolicek);
                return cloned_list;
            }
        };
        for (int r = 0; r < this.rows; r++){
            for (int c = 0; c < this.cols; c++){
                this.fields[r][c].setMaze(maze);
            }
        }

        return maze;
    }
}
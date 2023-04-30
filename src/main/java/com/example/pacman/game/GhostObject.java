package com.example.pacman.game;


import com.example.pacman.common.Field;
import com.example.pacman.common.MazeObject;

import java.util.Random;

/**
 * Třída GhostObject implementující rozhraní MazeObject
 */
public class GhostObject implements MazeObject {
    public enum Direction {
        UP, DOWN, LEFT, RIGHT, NONE;

        private static final Random PRNG = new Random();

        public static Direction randomDirection()  {
            Direction[] directions = values();
            return directions[PRNG.nextInt(directions.length)];
        }
    };

    public int row, col;
    private int orow, ocol;
    private PacmanObject.Direction direction;
    public PathField field;

    public Field.Direction lastDir = Field.Direction.L;
    public Field.Direction currDir = null;


    /**
     * Konstruktor
     */
    public GhostObject(int row,
                        int col) {
        this.row = row;
        this.col = col;
        this.orow = row;
        this.ocol = col;
    }

    public int ghostX() {
        return col;
    }
    public int ghostY() {
        return row;
    }

    public Field.Direction GetLastDir() {
        return lastDir;
    }

    public Field.Direction GetCurrDir() {
        return currDir;
    }
    public void setCurrDir(Field.Direction dir) {
        currDir = dir;
    }
    public void setLastDir(Field.Direction dir) {
        lastDir = dir;
    }


    public void reset() {
        this.field.objectOnField = null;
        this.row = orow;
        this.col = ocol;
        this.field.maze.getField(this.row, this.col).put(this);
        this.field = (PathField) this.field.maze.getField(this.row, this.col);
        this.currDir = null;
        this.lastDir = Field.Direction.L;
    }

    /**
     * Funkce pro zjištění, zda se může duch přemístit na políčko
     */
    public boolean canMove(Field.Direction dir) {
        int nextcol = dir.getColDelta() + this.col;
        int nextrow = dir.getRowDelta() + this.row;

        return this.field.maze.getField(nextrow, nextcol).canMove();
    }

    /**
     * Funkce se stará o přemístění ducha na políčko
     */
    public boolean move(Field.Direction dir) {
        if(canMove(dir)){
            int nextcol = dir.getColDelta() + this.col;
            int nextrow = dir.getRowDelta() + this.row;
            this.field.objectOnField = null;
            this.row = nextrow;
            this.col = nextcol;
            this.field = (PathField) this.field.maze.getField(nextrow, nextcol);
            this.field.objectOnField = this;
            return true;
        }
        return false;
    }

    public Field getField() {
        return field;
    }
}

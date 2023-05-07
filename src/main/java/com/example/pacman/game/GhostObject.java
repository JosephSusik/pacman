/*
 * Author: Marek Putala, Josef Susík
 * File: GhostObject.java
 */

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
     * @param row row
     * @param col col
     */
    public GhostObject(int row,
                        int col) {
        this.row = row;
        this.col = col;
        this.orow = row;
        this.ocol = col;
    }

    /**
     * Return ghost X position
     * @return Return ghost X position
     */
    public int ghostX() {
        return col;
    }
    /**
     * Return ghost Y position
     * @return Return ghost Y position
     */
    public int ghostY() {
        return row;
    }

    /**
     * Return last direction of ghost
     * @return Return last direction of ghost
     */
    public Field.Direction GetLastDir() {
        return lastDir;
    }

    /**
     * Return current direction of ghost
     * @return Return current direction of ghost
     */
    public Field.Direction GetCurrDir() {
        return currDir;
    }

    /**
     * Set current direction of ghost
     * @return Set current direction of ghost
     */
    public void setCurrDir(Field.Direction dir) {
        currDir = dir;
    }
    /**
     * Set last direction of ghost
     * @return Set last direction of ghost
     */
    public void setLastDir(Field.Direction dir) {
        lastDir = dir;
    }

    /**
     * Reset ghost
     */
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
     * Check if ghost can move
     * @param dir direction to move
     */
    public boolean canMove(Field.Direction dir) {
        int nextcol = dir.getColDelta() + this.col;
        int nextrow = dir.getRowDelta() + this.row;

        return this.field.maze.getField(nextrow, nextcol).canMove();
    }

    /**
     * Move ghost
     * @param dir direction to move
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
    /**
     * Return ghost field
     * @return return ghost field
     */
    public Field getField() {
        return field;
    }
}

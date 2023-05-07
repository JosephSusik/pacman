/*
 * Author: Marek Putala, Josef Susík
 * File: PacmanObject.java
 */

package com.example.pacman.game;


import com.example.pacman.common.Field;
import com.example.pacman.common.MazeObject;

public class PacmanObject implements MazeObject {
    public enum Direction {
        D, L, R, U, NONE
    };
    public int row, col;
    private int orow, ocol;
    public Direction next_direction;
    public Direction current_direction;
    public PathField field;
    public int lives;
    public int steps;
    public int score;
    public boolean won;

    /**
     * Constructor
     * @param row row
     * @param col col
     */
    public PacmanObject(int row,
                        int col) {
        this.row = row;
        this.col = col;
        this.orow = row;
        this.ocol = col;
        this.next_direction = Direction.NONE;
        this.current_direction = Direction.NONE;
        this.lives = 3;
        this.steps = 0;
        this.won = false;
        this.score = 0;
    }
    /**
     * Can move?
     * @param dir direction
     * @return true on success
     */
    public boolean canMove(Field.Direction dir) {
        int nextcol = dir.getColDelta() + this.col;
        int nextrow = dir.getRowDelta() + this.row;

        return this.field.maze.getField(nextrow, nextcol).canMove();
    }
    /**
     * Move Pacman
     * @param dir direction to move in
     */
    public boolean move(Field.Direction dir) {
        if(canMove(dir)) {
            int nextcol = dir.getColDelta() + this.col;
            int nextrow = dir.getRowDelta() + this.row;
            this.field.objectOnField = null;
            this.row = nextrow;
            this.col = nextcol;
            this.field = (PathField) this.field.maze.getField(nextrow, nextcol);
            this.field.objectOnField = this;
            this.steps++;
            return true;
        }
        return false;
    }
    /**
     * Reset pacman
     */
    public void reset() {
        this.field.objectOnField = null;
        this.row = orow;
        this.col = ocol;
        this.field.maze.getField(this.row, this.col).put(this);
        this.field = (PathField) this.field.maze.getField(this.row, this.col);
        this.current_direction = Direction.NONE;
        this.next_direction = Direction.NONE;
        this.won = false;
    }

    /**
     * Gets field
     * @return false
     */
    public Field getField() {
        return field;
    }

    /**
     * not ghost
     * @return 0
     */
    public int ghostX() {return 0;}
    /**
     * not ghost
     * @return 0
     */
    public int ghostY() {return 0;}
    /**
     * not ghost
     * @return null
     */
    public Field.Direction GetLastDir() {return null;}
    /**
     * not ghost
     * @return null
     */
    public Field.Direction GetCurrDir() {return null;}
    /**
     * not ghost
     * @param dir direction
     */
    public void setCurrDir(Field.Direction dir) {}
    /**
     * not ghost
     * @param dir direction
     */
    public void setLastDir(Field.Direction dir) {}

}

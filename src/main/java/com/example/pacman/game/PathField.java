/*
 * Author: Marek Putala, Josef Susík
 * File: PathField.java
 */

package com.example.pacman.game;

import com.example.pacman.common.Field;
import com.example.pacman.common.Maze;
import com.example.pacman.common.MazeObject;

public class PathField implements Field {
    private int row, col;
    public Maze maze;
    public MazeObject objectOnField = null;
    public boolean point;

    /**
     * Constructor
     * @param row row
     * @param col col
     */
    public PathField(int row,
                     int col){
        this.row = row;
        this.col = col;
        this.point = false;
    }
    /**
     * Set field to maze
     * @param maze Maze
     */
    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * Get field in direction
     * @param dirs Direction
     * @return field in direction
     */
    public Field nextField(Field.Direction dirs) {

        int nextcol = dirs.getColDelta() + this.col;
        int nextrow = dirs.getRowDelta() + this.row;

        return maze.getField(nextrow, nextcol);
    }

    /**
     * Put object to field
     * @param object object to put on field
     * @return true on success
     */
    public boolean put(MazeObject object) {
        this.objectOnField = object;
        return true;
    }

    /**
     * Remove object from field
     * @param object object to remove
     * @return true on success
     */
    public boolean remove(MazeObject object) {
        if(object == this.objectOnField){
            this.objectOnField = null;
            return true;
        }
        return false;
    }

    /**
     * is empty?
     * @return true if empty
     */
    public boolean isEmpty() {
        if (this.objectOnField == null){
            return true;
        }
        return false;
    }

    /**
     * Get object
     * @return maze object
     */
    public MazeObject get() {
        return objectOnField;
    }

    /**
     * Can move
     * @return true
     */
    public boolean canMove() {
        return true;
    }

    /**
     * Compare objects
     * @param obj Object to compare
     * @return true if same
     */
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PathField other = (PathField) obj;
        if (this.row!= other.row) {
            return false;
        }
        if (this.col!= other.col) {
            return false;
        }
        return true;
    }
}

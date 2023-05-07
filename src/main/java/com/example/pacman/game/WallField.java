/*
 * Author: Marek Putala, Josef Susík
 * File: WallField.java
 */

package com.example.pacman.game;

import com.example.pacman.common.Field;
import com.example.pacman.common.Maze;
import com.example.pacman.common.MazeObject;

public class WallField implements Field {
    private int row, col;
    public Maze maze;
    public MazeObject objectOnField = null;

    /**
     * Constructor
     * @param row row
     * @param col col
     */
    public WallField(int row,
                     int col){
        this.row = row;
        this.col = col;
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
     * @return Exception
     */
    public Field nextField(Field.Direction dirs) {
        throw new UnsupportedOperationException("Invalid operation for sorted list.");
    }
    /**
     * Put object to field
     * @param object object to put on field
     * @return Exception
     */
    public boolean put(MazeObject object) {
        throw new UnsupportedOperationException("Invalid operation for sorted list.");
    }
    /**
     * Remove object from field
     * @param object object to remove
     * @return Exception
     */
    public boolean remove(MazeObject object) {
        throw new UnsupportedOperationException("Invalid operation for sorted list.");
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
     * @return null
     */
    public MazeObject get() {
        return null;
    }
    /**
     * Can move
     * @return false
     */
    public boolean canMove() {
        return false;
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
        final WallField other = (WallField) obj;
        if (this.row!= other.row) {
            return false;
        }
        if (this.col!= other.col) {
            return false;
        }
        return true;
    }

}

package com.example.pacman.game;
/*
 * Author: Marek Putala
 * File: KeyObject.java
 */

import com.example.pacman.common.Field;
import com.example.pacman.common.MazeObject;

public class KeyObject implements MazeObject {

    public Field field;

    public int row, col;

    public boolean used;

    public KeyObject(int row,
                       int col) {
        this.row = row;
        this.col = col;
        this.used = false;
    }
    /**
     * Can key move
     * @param dir Direction
     * @return false
     */
    public boolean canMove(Field.Direction dir){
        return false;
    }
    /**
     * Can key move
     * @param dir Direction
     * @return false
     */
    public boolean move(Field.Direction dir){
        return false;
    }

    /**
     * Return key field
     * @return Return key field
     */
    public Field getField() {
        return field;
    }

    /**
     * Reset key
     * @return Return key field
     */
    public void reset() {
        this.used = false;
        this.field.put(this);
    }

    /**
     * can key ghost
     * @return 0
     */
    public int ghostX() {return 0;}

    /**
     * can key ghost
     * @return 0
     */
    public int ghostY() {return 0;}

    /**
     * can key move
     * @return null
     */
    public Field.Direction GetLastDir() {return null;}
    /**
     * can key move
     * @return null
     */
    public Field.Direction GetCurrDir() {return null;}
    /**
     * can key move
     * @param dir direction
     */
    public void setCurrDir(Field.Direction dir) {}
    /**
     * can key move
     * @param dir direction
     */
    public void setLastDir(Field.Direction dir) {}
}

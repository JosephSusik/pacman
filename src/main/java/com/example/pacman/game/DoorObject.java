package com.example.pacman.game;
/*
 * Author: Marek Putala
 * File: DoorObject.java
 */

import com.example.pacman.common.Field;
import com.example.pacman.common.MazeObject;

public class DoorObject implements MazeObject {

    public Field field;

    public int row, col;

    public boolean open;

    /**
     * Represents door
     * @param row row of door
     * @param col col of door
     */
    public DoorObject(int row,
                     int col) {
        this.row = row;
        this.col = col;
        this.open = false;
    }

    /**
     * Can door move?
     * @param dir direction
     * @return false
     */
    public boolean canMove(Field.Direction dir){
        return false;
    }

    /**
     * Can door move?
     * @param dir direction
     * @return false
     */
    public boolean move(Field.Direction dir){
        return false;
    }

    /**
     * Field of the door
     * @return field of the door
     */
    public Field getField() {
        return field;
    }

    /**
     * Resets door
     */
    public void reset() {
        this.open = false;
        this.field.put(this);
    }

    /**
     * Can door ghost?
     * @return 0
     */
    public int ghostX() {return 0;}

    /**
     * Can door ghost?
     * @return 0
     */
    public int ghostY() {return 0;}

    /**
     * Can door last direction?
     * @return null
     */
    public Field.Direction GetLastDir() {return null;}
    /**
     * Can door last direction?
     * @return null
     */
    public Field.Direction GetCurrDir() {return null;}
    /**
     * Can door have direction?
     */
    public void setCurrDir(Field.Direction dir) {}
    /**
     * Can door have direction?
     */
    public void setLastDir(Field.Direction dir) {}
}

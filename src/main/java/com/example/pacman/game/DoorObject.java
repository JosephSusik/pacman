package com.example.pacman.game;

import com.example.pacman.common.Field;
import com.example.pacman.common.MazeObject;

public class DoorObject implements MazeObject {

    public Field field;

    public int row, col;

    public boolean open;

    public DoorObject(int row,
                     int col) {
        this.row = row;
        this.col = col;
        this.open = false;
    }

    public boolean canMove(Field.Direction dir){
        return false;
    }

    public boolean move(Field.Direction dir){
        return false;
    }

    public Field getField() {
        return field;
    }

    public void reset() {
        this.open = false;
        this.field.put(this);
    }

    public int ghostX() {return 0;}

    public int ghostY() {return 0;}

    public Field.Direction GetLastDir() {return null;}
    public Field.Direction GetCurrDir() {return null;}
    public void setCurrDir(Field.Direction dir) {}
    public void setLastDir(Field.Direction dir) {}
}

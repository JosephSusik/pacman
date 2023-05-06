/*
 * Author: Marek Putala, Josef Susík
 * File: MazeObject.java
 */

package com.example.pacman.common;


public interface MazeObject {
    boolean canMove(Field.Direction dir);

    boolean move(Field.Direction dir);

    Field getField();

    void reset();

    int ghostX();

    int ghostY();

    Field.Direction GetLastDir();

    Field.Direction GetCurrDir();

    void setCurrDir(Field.Direction dir);
    void setLastDir(Field.Direction dir);

}
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
     * Konstruktor
     */
    public PathField(int row,
                     int col){
        this.row = row;
        this.col = col;
        this.point = false;
    }
    /**
     * Funkce pro přiřazení políčka k bludišti
     */
    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * Funkce pro získaní políčka v daném směru od aktuálního
     */
    public Field nextField(Field.Direction dirs) {

        int nextcol = dirs.getColDelta() + this.col;
        int nextrow = dirs.getRowDelta() + this.row;

        return maze.getField(nextrow, nextcol);
    }

    /**
     * Funkce pro vložení zadaného objektu na políčko
     */
    public boolean put(MazeObject object) {
        this.objectOnField = object;
        return true;
    }

    /**
     * Funkce pro odstranění objektu z políčka
     */
    public boolean remove(MazeObject object) {
        if(object == this.objectOnField){
            this.objectOnField = null;
            return true;
        }
        return false;
    }

    /**
     * Funkce pro zjištění, zda je políčko prázdné
     * Pokud ano, vrací se true.
     */
    public boolean isEmpty() {
        if (this.objectOnField == null){
            return true;
        }
        return false;
    }

    /**
     * Funkce pro získání objektu na daném políčku
     */
    public MazeObject get() {
        return objectOnField;
    }

    /**
     * Funkce pro získání informace, zda se dá na políčko přemístit.
     * Vrací true, protože se jedná ho PathField
     */
    public boolean canMove() {
        return true;
    }

    /**
     * Funkce pro porovnávání objektů field.
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

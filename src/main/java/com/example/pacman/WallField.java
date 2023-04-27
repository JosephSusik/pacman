package com.example.pacman;


public class WallField implements Field{
    private int row, col;
    public Maze maze;
    public MazeObject objectOnField = null;

    /**
     * Konstruktor
     */
    public WallField(int row,
                     int col){
        this.row = row;
        this.col = col;
    }
    /**
     * Funkce pro přiřazení políčka k bludišti
     */
    public void setMaze(Maze maze) {
        this.maze = maze;
    }
    /**
     * Funkce pro získaní políčka v daném směru od aktuálního
     * Vrací exception, protože se jedná o WallField
     */
    public Field nextField(Field.Direction dirs) {
        throw new UnsupportedOperationException("Invalid operation for sorted list.");
    }
    /**
     * Funkce pro vložení zadaného objektu na políčko
     * Vrací exception, protože se jedná o WallField
     */
    public boolean put(MazeObject object) {
        throw new UnsupportedOperationException("Invalid operation for sorted list.");
    }
    /**
     * Funkce pro odstranění objektu z políčka
     * Vrací exception, protože se jedná o WallField
     */
    public boolean remove(MazeObject object) {
        throw new UnsupportedOperationException("Invalid operation for sorted list.");
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
        return null;
    }
    /**
     * Funkce pro získání informace, zda se dá na políčko přemístit.
     * Vrací false, protože se jedná ho WallField
     */
    public boolean canMove() {
        return false;
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

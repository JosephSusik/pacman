package com.example.pacman;


public class PacmanObject implements MazeObject {
    public enum Direction {
        UP, DOWN, LEFT, RIGHT, NONE
    };
    private int row, col;
    private Direction direction;
    public PathField field;

    /**
     * Konstruktor
     */
    public PacmanObject(int row,
                        int col) {
        this.row = row;
        this.col = col;
        this.direction = Direction.NONE;
    }
    /**
     * Funkce pro zjištění, zda se může pacman přemístit na políčko
     */
    public boolean canMove(Field.Direction dir) {
        int nextcol = dir.getColDelta() + this.col;
        int nextrow = dir.getRowDelta() + this.row;

        return this.field.maze.getField(nextrow, nextcol).canMove();
    }
    /**
     * Funkce se stará o přemístění pacmana na políčko
     */
    public boolean move(Field.Direction dir) {
        if(canMove(dir)){
            int nextcol = dir.getColDelta() + this.col;
            int nextrow = dir.getRowDelta() + this.row;
            this.row = nextrow;
            this.col = nextcol;
            this.field.objectOnField = null;
            this.field = (PathField) this.field.maze.getField(nextrow, nextcol);
            this.field.objectOnField = this;
            return true;
        }
        return false;
    }
}

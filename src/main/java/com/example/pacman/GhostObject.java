package com.example.pacman;



/**
 * Třída GhostObject implementující rozhraní MazeObject
 */
public class GhostObject implements MazeObject {
    public enum Direction {
        UP, DOWN, LEFT, RIGHT, NONE
    };
    private int row, col;
    private PacmanObject.Direction direction;
    public PathField field;

    /**
     * Konstruktor
     */
    public GhostObject(int row,
                        int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Funkce pro zjištění, zda se může duch přemístit na políčko
     */
    public boolean canMove(Field.Direction dir) {
        int nextcol = dir.getColDelta() + this.col;
        int nextrow = dir.getRowDelta() + this.row;

        return this.field.maze.getField(nextrow, nextcol).canMove();
    }

    /**
     * Funkce se stará o přemístění ducha na políčko
     */
    public boolean move(Field.Direction dir) {
        if(canMove(dir)){
            int nextcol = dir.getColDelta() + this.col;
            int nextrow = dir.getRowDelta() + this.row;
            if (this.field.objectOnField != null && !(this.field.objectOnField instanceof PacmanObject)){
                this.field.objectOnField = null;
            }
            this.row = nextrow;
            this.col = nextcol;
            this.field = (PathField) this.field.maze.getField(nextrow, nextcol);
            if (this.field.objectOnField != null && this.field.objectOnField instanceof PacmanObject){
                ((PacmanObject) this.field.objectOnField).lives--;
            }
            else{
                this.field.objectOnField = this;
            }
            return true;
        }
        return false;
    }
}

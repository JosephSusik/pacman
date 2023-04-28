package com.example.pacman;


public interface MazeObject {
    boolean canMove(Field.Direction dir);
    boolean move(Field.Direction dir);
    Field getField();
    void reset();
}

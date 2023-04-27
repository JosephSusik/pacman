package com.example.pacman;


public interface Maze {
    Field getField(int row,
                   int col);
    int numRows();
    int numCols();
}

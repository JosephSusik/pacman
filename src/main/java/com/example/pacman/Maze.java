package com.example.pacman;


import java.util.List;

public interface Maze {
    Field getField(int row,
                   int col);
    int numRows();
    int numCols();
    public PacmanObject getPacman();
    public List<MazeObject> getGhosts();
}

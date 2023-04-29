package com.example.pacman.common;


import com.example.pacman.game.PacmanObject;

import java.util.List;

public interface Maze {
    Field getField(int row,
                   int col);
    int numRows();
    int numCols();
    public PacmanObject getPacman();
    public List<MazeObject> getGhosts();
}

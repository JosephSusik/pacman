package com.example.pacman.common;


import com.example.pacman.game.DoorObject;
import com.example.pacman.game.KeyObject;
import com.example.pacman.game.PacmanObject;

import java.util.List;

public interface Maze {
    Field getField(int row,
                   int col);
    int numRows();
    int numCols();
    public PacmanObject getPacman();
    public List<MazeObject> getGhosts();
    public List<KeyObject> getKeys();
    public List<DoorObject> getDoors();
}

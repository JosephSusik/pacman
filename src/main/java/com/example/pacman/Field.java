package com.example.pacman;

public interface Field {

    static enum Direction {
        D(-1, 0),
        L(0, -1),
        R(0, 1),
        U(1, 0);

        private final int rowDelta;
        private final int colDelta;

        Direction(int rowDelta, int colDelta){
            this.rowDelta = rowDelta;
            this.colDelta = colDelta;
        }

        public int getRowDelta(){
            return rowDelta;
        }

        public int getColDelta(){
            return colDelta;
        }
    }

    void setMaze(Maze maze);
    Field nextField(Direction dirs);
    boolean put(MazeObject object);
    boolean remove(MazeObject object);
    boolean isEmpty();
    MazeObject get();
    boolean canMove();

}

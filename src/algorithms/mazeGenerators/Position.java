package algorithms.mazeGenerators;

import algorithms.search.AState;
import algorithms.search.MazeState;

public class Position extends MazeState {

    private int row;
    private int column;
//    private boolean visited = false;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    @Override
    public String toString() {
        return "{"+row+","+column+"}";
    }

    public int getRowIndex() {
        return row;
    }

    public int getColumnIndex() {
        return column;
    }



    @Override
    public boolean equals(Object obj) {
        Position p = (Position) obj;
        if(this.getRowIndex()==p.getRowIndex() && this.getColumnIndex()==p.getColumnIndex()){
            return true;
        }
        return false;
    }

//    public boolean isVisited() {
//        return visited;
//    }

//    public void setVisited(boolean visited) {
//        this.visited = true;
//    }



}

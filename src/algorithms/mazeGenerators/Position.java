package algorithms.mazeGenerators;

import algorithms.search.MazeState;

public class Position extends MazeState {

    private final int row;
    private final int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    @Override
    public String toString() {
        return "{" + row + "," + column + "}";
    }

    public int getRowIndex() {
        return row;
    }

    public int getColumnIndex() {
        return column;
    }


    @Override
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass()) {
            return false;
        }
        Position p = (Position) obj;
        return this.getRowIndex() == p.getRowIndex() && this.getColumnIndex() == p.getColumnIndex();
    }
}

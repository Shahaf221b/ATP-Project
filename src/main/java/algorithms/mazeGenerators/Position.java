package algorithms.mazeGenerators;

import java.io.Serializable;

public class Position implements Serializable {

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

}

package algorithms.maze3D;

public class Position3D {
    private int DepthIndex, RowIndex, ColumnIndex;

    public Position3D(int depthIndex, int rowIndex, int columnIndex) {
        DepthIndex = depthIndex;
        RowIndex = rowIndex;
        ColumnIndex = columnIndex;
    }

    public int getDepthIndex() {
        return DepthIndex;
    }

    public int getRowIndex() {
        return RowIndex;
    }

    public int getColumnIndex() {
        return ColumnIndex;
    }
}

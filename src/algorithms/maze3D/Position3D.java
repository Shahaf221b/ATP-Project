package algorithms.maze3D;

public class Position3D extends Maze3DState {
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

    @Override
    public String toString() {
        return "{"+DepthIndex+","+RowIndex+","+ColumnIndex+ "}";
    }

    @Override
    public boolean equals(Object obj) {
        Position3D p = (Position3D) obj;
        int d= p.getDepthIndex();
        int r = p.getRowIndex();
        int c = p.getColumnIndex();
        return(DepthIndex==d && RowIndex==r && ColumnIndex==c);
    }
}

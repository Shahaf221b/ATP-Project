package algorithms.maze3D;

public class Position3D  {
    private int DepthIndex, RowIndex, ColumnIndex;

    public Position3D(int depthIndex, int rowIndex, int columnIndex) {
        this.DepthIndex = depthIndex;
        this.RowIndex = rowIndex;
        this.ColumnIndex = columnIndex;
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

    public void setDepthIndex(int depthIndex) {
        DepthIndex = depthIndex;
    }

    public void setRowIndex(int rowIndex) {
        RowIndex = rowIndex;
    }

    public void setColumnIndex(int columnIndex) {
        ColumnIndex = columnIndex;
    }
}

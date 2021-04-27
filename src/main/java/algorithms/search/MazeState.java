package algorithms.search;

import algorithms.mazeGenerators.Position;

public class MazeState extends AState {

    private final Position MyPosition;

    public MazeState(int row, int column) {
        MyPosition = new Position(row, column);
    }

    public Position getPosition() {
        return MyPosition;
    }


    @Override
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass()) {
            return false;
        }
        MazeState ms = (MazeState) obj;
        Position p = ms.getPosition();
        return this.MyPosition.getRowIndex() == p.getRowIndex() && this.MyPosition.getColumnIndex() == p.getColumnIndex();
    }

    public String toString() {
        return MyPosition.toString();
    }



}

package algorithms.maze3D;

import algorithms.search.AState;

import java.util.Objects;

/**
 * Represent a state in Maze3D
 * every Maze3DState has a private Position3D that he represents.
 */
public class Maze3DState extends AState {

    private final Position3D MyPosition;

    public Maze3DState(int depth, int row, int column) {

        MyPosition = new Position3D(depth, row, column);
    }

    public Position3D getPosition() {
        return MyPosition;
    }


    @Override
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass()) {
            return false;
        }
        Maze3DState ms = (Maze3DState) obj;
        Position3D p = ms.getPosition();
        return (this.MyPosition.getDepthIndex() == p.getDepthIndex() && this.MyPosition.getRowIndex() == p.getRowIndex() && this.MyPosition.getColumnIndex() == p.getColumnIndex());
    }

    public String toString() {
        return MyPosition.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(MyPosition);
    }
}

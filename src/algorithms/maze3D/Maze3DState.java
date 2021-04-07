package algorithms.maze3D;

import algorithms.mazeGenerators.Position;
import algorithms.search.AState;
import algorithms.search.MazeState;

import java.util.Objects;

public class Maze3DState extends AState {

    private final Position3D MyPosition;

    public Maze3DState(int depth,int row, int column) {

        MyPosition = new Position3D(depth,row, column);
    }

    public Maze3DState(Position3D p){
        MyPosition = p;
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

    public String toString(){
        return MyPosition.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(MyPosition);
    }
}
package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator {

    @Override
    public Maze generate(int rows, int columns) {
        Maze m = new Maze(rows, columns);
        if (rows > 0 && columns > 0) {
            m.setStartPosition(new Position(0, 0));
            m.setGoalPosition(new Position(rows - 1, columns - 1));
        }
        return m;
    }
}

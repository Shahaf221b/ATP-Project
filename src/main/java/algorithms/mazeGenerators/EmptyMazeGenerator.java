package algorithms.mazeGenerators;

/**
 * EmptyMazeGenerator generates an empty maze(only 0)
 */
public class EmptyMazeGenerator extends AMazeGenerator {


    @Override
    public Maze generate(int rows, int columns) {
        Maze m = null;
        try {
            m = new Maze(rows, columns);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (m == null)
            return null;

        if (rows > 0 && columns > 0) {
            m.setStartPosition(new Position(0, 0));
            m.setGoalPosition(new Position(rows - 1, columns - 1));
        }
        return m;
    }


}

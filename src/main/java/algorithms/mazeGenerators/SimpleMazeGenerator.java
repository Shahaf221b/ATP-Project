package algorithms.mazeGenerators;

import java.util.Random;

/**
 * SimpleMazeGenerator generates a simple maze
 */
public class SimpleMazeGenerator extends AMazeGenerator {


    @Override
    public Maze generate(int rows, int columns) throws Exception {
        Maze aMaze = null;
        try {
            aMaze = new Maze(rows, columns);
        } catch (Exception e) {
            System.out.printf(e.getMessage());
        }
        if (aMaze == null)
            return null;
        Random rand = new Random();
        if (rows > 0 && columns > 0) {
            aMaze.setGoalPosition(new Position(rows - 1, columns - 1));
            aMaze.setStartPosition(new Position(0, 0));
            for (int r = 1; r < rows; r++) {
                for (int c = 0; c < columns - 1; c++) {
                    int randInt = rand.nextInt(2);
                    if (randInt == 1) {
                        Position wall = new Position(r, c);
                        aMaze.setCell(wall, 1);
                    }
                }
            }
        }
        return aMaze;
    }


}

package algorithms.mazeGenerators;

/**
 * AMazeGenerator implements the IMazeGenerator interface.
 * It's an abstract class for a 2D Maze Generator.
 */
public abstract class AMazeGenerator implements IMazeGenerator {

    /**
     * an abstract method.
     * @param rows - define maze rows
     * @param columns - define maze columns
     * @return a Maze object.
     */


    @Override
    public abstract Maze generate(int rows, int columns) throws Exception;

    @Override
    public long measureAlgorithmTimeMillis(int rows, int columns) throws Exception {
        long aTime = System.currentTimeMillis();
        generate(rows, columns);
        long bTime = System.currentTimeMillis();
        return (bTime - aTime);
    }


}

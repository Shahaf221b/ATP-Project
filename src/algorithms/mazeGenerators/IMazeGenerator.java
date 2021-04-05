package algorithms.mazeGenerators;

/**
 * IMazeGenerator defines the functions that a  2D maze generator must implement.
 */
public interface IMazeGenerator {

    Maze generate(int rows, int columns) throws Exception;

    long measureAlgorithmTimeMillis(int rows, int columns) throws Exception;
}

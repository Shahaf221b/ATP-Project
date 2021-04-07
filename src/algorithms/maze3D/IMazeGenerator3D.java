package algorithms.maze3D;

public interface IMazeGenerator3D {
    Maze3D generate(int depth, int row, int column) throws Exception;
    long measureAlgorithmTimeMillis(int depth, int row, int column) throws Exception;

}

package algorithms.maze3D;

public abstract class AMaze3DGenerator implements IMaze3DGenerator {
    @Override
    public abstract Maze3D generate(int depth, int row, int column) throws Exception;

    @Override
    public long measureAlgorithmTimeMillis(int depth, int row, int column) throws Exception {
        long aTime = System.currentTimeMillis();
        generate(depth, row, column);
        long bTime = System.currentTimeMillis();
        return (bTime - aTime);
    }
}

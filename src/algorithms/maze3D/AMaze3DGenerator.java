package algorithms.maze3D;

public abstract class AMaze3DGenerator implements IMazeGenerator3D {
    @Override
    public abstract Maze3D generate(int depth, int row, int column);

    @Override
    public long measureAlgorithmTimeMillis(int depth, int row, int column) {
        long aTime = System.currentTimeMillis();
        generate(depth,row,column);
        long bTime = System.currentTimeMillis();
        return (bTime-aTime);
    }
}

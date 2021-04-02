package algorithms.mazeGenerators;

import java.util.Random;

public abstract class AMazeGenerator implements IMazeGenerator{

    @Override
    public abstract Maze generate(int rows, int columns);

    @Override
    public long measureAlgorithmTimeMillis(int rows, int columns){
        long aTime = System.currentTimeMillis();
        generate(rows,columns);
        long bTime = System.currentTimeMillis();
        return (bTime-aTime);
    }

}

package algorithms.maze3D;

import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;

import java.util.Random;

public class MyMaze3DGenerator extends AMaze3DGenerator{

    @Override
    public Maze3D generate(int depth, int row, int column) {
        Maze3D aMaze = new Maze3D(depth,row,column);
        IMazeGenerator img = new MyMazeGenerator();
        for(int i=0; i<depth; i++){
            Maze m = img.generate(row,column);
            aMaze.setDim(i,m.getM_maze());
            aMaze.setMazeinDim(i,m);
        }
        Random rand = new Random();
        int i = rand.nextInt(depth);
        Position p = aMaze.getAllMazes()[i].getStartPosition();
        aMaze.setStartPosition(new Position3D(i,p.getRowIndex(),p.getColumnIndex()));
        aMaze.setGoalPosition(new Position3D(i,p.getRowIndex(),p.getColumnIndex()));

        return aMaze;
    }
}

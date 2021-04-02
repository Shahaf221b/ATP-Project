package algorithms.mazeGenerators;

import java.util.Random;

public class SimpleMazeGenerator extends AMazeGenerator{

    @Override
    public Maze generate(int rows, int columns) {
        Maze aMaze = new Maze(rows,columns);
        Random rand = new Random();
        if(rows>0 && columns>0){
            aMaze.setGoalPosition(new Position(rows-1,columns-1));
            aMaze.setStartPosition(new Position(0,0));
            int numOfWalls = (int)(rows/2)*(columns/2);
            for (int r=1; r<rows; r++){
                for(int c=0; c<columns-1; c++){
                    int randInt = rand.nextInt(2);
                    if(randInt==1){
                        Position wall =new Position(r,c);
                        aMaze.setCell(wall,1);
                    }
                }
            }
        }
        return aMaze;
    }
}

package algorithms.maze3D;

import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.*;

import java.util.ArrayList;
import java.util.Random;

public class MyMaze3DGenerator extends AMaze3DGenerator{

    @Override
    public Maze3D generate(int depth, int row, int column) {
        Maze3D aMaze = new Maze3D(depth,row,column);
        IMazeGenerator img = new MyMazeGenerator();

        Maze m = img.generate(row,column);

        // TODO: search
        SearchableMaze searchableMaze = new SearchableMaze(m);
        ISearchingAlgorithm isa = new noDiagonalMovesDFS();
        Solution solution = isa.solve(searchableMaze);
        ArrayList<AState> solutionPath = solution.getSolutionPath();

        Position p = (Position) solutionPath.get(0);
        aMaze.setStartPosition(new Position3D(0,p.getRowIndex(),p.getColumnIndex()));
        int num = (solutionPath.size()-1)%depth;
        boolean extra = false;
        if(num != 0){extra =true;};
        int unit = (solutionPath.size()-1)/depth;
        int border = unit, j=1,i;
        for(i=0; i<depth; i++){
            while(j<=border){
                p = (Position) solutionPath.get(j);
                aMaze.setCell(new Position3D(i,p.getRowIndex(),p.getColumnIndex()),0);
 /*               if(p.getName() == "Diagonal"){
                    Position newPosition = fromDiagonalToSideHelper(p);
                    aMaze.setCell(new Position3D(i,newPosition.getRowIndex(),newPosition.getColumnIndex()),0);
                } */
                try{
                    p = (Position) solutionPath.get(j+1);
                    aMaze.setCell(new Position3D(i,p.getRowIndex(),p.getColumnIndex()),0);
                }
                catch (Exception e){}
                j++;
            }
            border = unit+border;
            if(border>(solutionPath.size()-1)){break;}
        }
        if(extra){
            while(j< solutionPath.size()){
                p = (Position) solutionPath.get(j);
                aMaze.setCell(new Position3D(depth-1,p.getRowIndex(),p.getColumnIndex()),0);
/*                if(p.getName() == "Diagonal"){
                    Position newPosition = fromDiagonalToSideHelper(p);
                    aMaze.setCell(new Position3D(depth-1,newPosition.getRowIndex(),newPosition.getColumnIndex()),0);
                } */
                j++;
            }
        }
        p = (Position) solutionPath.get(solutionPath.size()-1);
        aMaze.setGoalPosition(new Position3D(depth-1,p.getRowIndex(),p.getColumnIndex()));

        Random rand = new Random();
        int numOfPaths=(row*column)/4,a,b;
        for(i=0; i<depth; i++){
            for(j=0; j<numOfPaths; j++){
                a = rand.nextInt(row);
                b = rand.nextInt(column);
                aMaze.setCell(new Position3D(i,a,b),0);
            }
        }
        return aMaze;
    }

    /*
    public Position fromDiagonalToSideHelper(Position p){
        Position pParent =(Position) p.getParent();
        int pProw = pParent.getRowIndex();
        int pPcolumn = pParent.getColumnIndex();
        int pX = p.getRowIndex();
        int pY = p.getColumnIndex();
        int[] diagonal_x = {pX-1, pX+1, pX+1, pX-1}; //rows
        int[] diagonal_y = {pY-1, pY-1, pY+1, pY+1}; //columns
        int[][] options = { {pX,pY-1},{pX,pY-1},{pX,pY+1},{pX,pY+1}};
        int x,y;
        for(int i=0; i<4; i++){
            x = diagonal_x[i];
            y = diagonal_y[i];
            if(x==pProw && y==pPcolumn){
                int rowIndex = options[i][0];
                int colIndex = options[i][1];
                Position newPosition = new Position(rowIndex,colIndex);
                return newPosition;
            }
        }
        return null;
    }
    */

}

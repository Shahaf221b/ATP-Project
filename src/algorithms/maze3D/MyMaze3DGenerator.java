package algorithms.maze3D;

import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.*;

import java.util.*;

public class MyMaze3DGenerator extends AMaze3DGenerator {


    @Override
    public Maze3D generate(int depth, int row, int column) throws Exception {
        Maze3D aMaze = new Maze3D(depth, row, column);
        IMazeGenerator img = new MyMazeGenerator();

        Maze m = img.generate(row, column);
        SearchableMaze searchableMaze = new SearchableMaze(m);
        ISearchingAlgorithm isa = new DepthFirstSearch();
        Solution solution = isa.solve(searchableMaze);
        ArrayList<AState> solutionPath = solution.getSolutionPath();
        int sSize = solutionPath.size();
        MazeState msp = (MazeState) solutionPath.get(0);
        Position p = msp.getPosition();
        aMaze.setStartPosition(new Position3D(0, p.getRowIndex(), p.getColumnIndex()));
//        int num = (sSize-1)%depth;
        int num = depth % (sSize - 1);
        boolean extra = false;
        MazeState ms;
        if (num != 0) {
            extra = true;
        }

        int unit = (sSize - 1) / depth;
        int border = unit, j = 1, i;
        for (i = 0; i < depth; i++) {
            while (j <= border) {
                ms = (MazeState) solutionPath.get(j);
                p = ms.getPosition();
                Position3D newp = new Position3D(i, p.getRowIndex(), p.getColumnIndex());
                aMaze.setCell(newp, 0);
                if (isDiagonalMove(ms)) {
//                   aMaze.setCell(new Position3D(i,newPosition.getRowIndex(),newPosition.getColumnIndex()),0);
                    fromDiagonalToSideHelper(ms, m, aMaze, i);
                }
                try {
                    ms = (MazeState) solutionPath.get(j + 1);
                    p = ms.getPosition();
                    Position3D newp2 = new Position3D(i, p.getRowIndex(), p.getColumnIndex());
                    aMaze.setCell(newp2, 0);
                    if (isDiagonalMove(ms)) {
//                   aMaze.setCell(new Position3D(i,newPosition.getRowIndex(),newPosition.getColumnIndex()),0);
                        fromDiagonalToSideHelper(ms, m, aMaze, i);
                    }
                } catch (Exception e) {
                }
                j++;
            }
            border = unit + border;
            if (border > (sSize - 1)) {
                break;
            }
        }
        if (extra) {
            while (j < sSize) {
                ms = (MazeState) solutionPath.get(j);
                p = ms.getPosition();
                Position3D newp3 = new Position3D(depth - 1, p.getRowIndex(), p.getColumnIndex());
                aMaze.setCell(newp3, 0);
                newp3 = null;
                if (isDiagonalMove(ms)) {
//                   aMaze.setCell(new Position3D(i,newPosition.getRowIndex(),newPosition.getColumnIndex()),0);
                    fromDiagonalToSideHelper(ms, m, aMaze, depth - 1);
                }
                j++;
            }
        }
        ms = (MazeState) solutionPath.get(sSize - 1);
        Position endp = ms.getPosition();
        aMaze.setGoalPosition(new Position3D(depth - 1, endp.getRowIndex(), endp.getColumnIndex()));

        Random rand = new Random();
        int numOfPaths = (row * column) / depth, a, b;
        for (i = 0; i < depth; i++) {
            for (j = 0; j < numOfPaths; j++) {
                a = rand.nextInt(row);
                b = rand.nextInt(column);
//                aMaze.setCell(new Position3D(i,a,b),0);
                aMaze.changeVal(i, a, b, 0);
            }
        }
        if (AllNeighborPaths(aMaze.getStartPosition(), aMaze) == 0) {
            generate(depth, row, column);
        }
        return aMaze;
    }

    private void fromDiagonalToSideHelper(MazeState ms, Maze m, Maze3D m3D, int dim) throws Exception {
        MazeState msParent = (MazeState) ms.getParent();
        int pX = msParent.getPosition().getRowIndex();
        int pY = msParent.getPosition().getColumnIndex();
        int msX = ms.getPosition().getRowIndex();
        int msY = ms.getPosition().getColumnIndex();
        int[] diagonal_x = {pX - 1, pX + 1, pX + 1, pX - 1}; //rows
        int[] diagonal_y = {pY - 1, pY - 1, pY + 1, pY + 1}; //columns
        int x, y;
        for (int j = 0; j < 4; j++) {
            x = diagonal_x[j];
            y = diagonal_y[j];
            if (x == msX && y == msY) {
                if (j == 0 || j == 1) {
                    m3D.setCell(new Position3D(dim, pX, pY - 1), 0);
                } else {
                    m3D.setCell(new Position3D(dim, pX, pY + 1), 0);
                }
            }
        }
    }

    private boolean isDiagonalMove(MazeState ms) {
        MazeState msParent = (MazeState) ms.getParent();
        int pX = msParent.getPosition().getRowIndex();
        int pY = msParent.getPosition().getColumnIndex();
        int msX = ms.getPosition().getRowIndex();
        int msY = ms.getPosition().getColumnIndex();
        int[] diagonal_x = {pX - 1, pX + 1, pX + 1, pX - 1}; //rows
        int[] diagonal_y = {pY - 1, pY - 1, pY + 1, pY + 1}; //columns
        int x, y;
        for (int j = 0; j < 4; j++) {
            x = diagonal_x[j];
            y = diagonal_y[j];
            if (x == msX && y == msY) {
                return true;
            }
        }
        return false;
    }

    private Position3D getRandomPosition3D(Maze3D maze3D) {
        int x, y, z;
        Random rand = new Random();
        Position3D start = maze3D.getStartPosition();
        Position3D goal = maze3D.getGoalPosition();
        Position3D newPosition = new Position3D(start.getDepthIndex(), start.getRowIndex(), start.getColumnIndex());
        while (newPosition.equals(start) || newPosition.equals(goal) || newPosition.getDepthIndex() == start.getDepthIndex() || newPosition.getDepthIndex() == goal.getDepthIndex()) {
            x = rand.nextInt(maze3D.getDepth());
            y = rand.nextInt(maze3D.getRows());
            z = rand.nextInt(maze3D.getColumns());
            newPosition.setDepthIndex(x);
            newPosition.setRowIndex(y);
            newPosition.setColumnIndex(z);
        }
        return newPosition;

    }

    private int AllNeighborPaths(Position3D w, Maze3D aMaze) {
        int ans = 0;
        int row = w.getRowIndex();
        int col = w.getColumnIndex();
        int dim = w.getDepthIndex();
        for (int i = 0; i < aMaze.getDepth(); i++) {
            if (aMaze.getVal(i, row, col) == 0 || i == dim) {
                if (aMaze.getVal(i, row - 1, col) == 0) {
                    ans++;
                }
                if (aMaze.getVal(i, row + 1, col) == 0) {
                    ans++;
                }
                if (aMaze.getVal(i, row, col - 1) == 0) {
                    ans++;
                }
                if (aMaze.getVal(i, row, col + 1) == 0) {
                    ans++;
                }
            }
        }

        return ans;
    }

    /*
    private boolean isASeparator(Position3D w,Maze3D aMaze) {
        int dim = w.getDepthIndex();
        int row = w.getRowIndex();
        int col = w.getColumnIndex();
        Position3D a = new Position3D(dim,row-1, col);
        Position3D b = new Position3D(dim,row+1, col);
        if(aMaze.getCellValue(a)==1 && aMaze.getCellValue(b)==0 || aMaze.getCellValue(a)==0 && aMaze.getCellValue(b)==1 ){
            return true;
        }
        Position3D c = new Position3D(dim,row, col-1);
        Position3D d = new Position3D(dim,row, col+1);
        if(aMaze.getCellValue(c)==1 && aMaze.getCellValue(d)==0 || aMaze.getCellValue(c)==0 && aMaze.getCellValue(d)==1 ){
            return true;
        }
        return false;
    }
*/
    /*
    private Position3D getRandomEndPosition(Maze3D aMaze) {
        Random rand = new Random();
        int dim;
        int col,row;
        dim = rand.nextInt(aMaze.getDepth());
        row = rand.nextInt(aMaze.getRows());
        if(dim>0 && dim<(aMaze.getDepth()-1)){ // then the column could be the rightest or leftest column
            col = rand.nextInt(2);
            if(col == 1){
                return new Position3D(dim,row, aMaze.getColumns()-1);
            }
            return new Position3D(dim,row,0);
        }
        col = rand.nextInt(aMaze.getColumns());
        if(dim==0){
            return new Position3D(dim,0,col);
        }
        return new Position3D(dim, aMaze.getRows()-1, col);
    }
*/
}

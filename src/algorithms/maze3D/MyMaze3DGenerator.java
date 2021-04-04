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
        Position3D endP = getRandomEndPosition(aMaze);
        aMaze.setGoalPosition(endP);
        aMaze.setCell(endP,0);
        Position3D startP = getRandomStartPosition(aMaze);
        aMaze.setStartPosition(startP);
        aMaze.setCell(startP,0);

        ArrayList<Position3D> allWalls ;
        allWalls = AllNeighborWalls(endP,aMaze);
        Random rand = new Random();
        int i;
        Position3D w ;

        while(allWalls.size()>0){
            i = rand.nextInt(allWalls.size());
            w = allWalls.get(i);
            allWalls.remove(i);

            if(isASeparator(w,aMaze)||AllNeighborWalls(w,aMaze).size()>2*depth ){
                if(AllNeighborWalls(w,aMaze).size()>4*depth || isNeighbor(w,startP,aMaze)){
                    aMaze.setCell(w,0);
                    allWalls.addAll(AllNeighborWalls(w,aMaze));
                }
            }

        }
        if(AllNeighborWalls(startP,aMaze).size()>=2*depth){ //TODO:check if 6 is enough
            aMaze =generate(depth,row,column);
        }
        return aMaze;
    }

    private Position3D getRandomPosition3D(Maze3D maze3D) {
        int x,y,z;
        Random rand = new Random();
        Position3D newPosition = maze3D.getStartPosition();
        Position3D start = maze3D.getStartPosition();
        Position3D goal = maze3D.getGoalPosition();
        while (newPosition.equals(start) || newPosition.equals(goal)){
            x = rand.nextInt(maze3D.getDepth());
            y = rand.nextInt(maze3D.getRows());
            z = rand.nextInt(maze3D.getColumns());
            newPosition.setDepthIndex(x);
            newPosition.setRowIndex(y);
            newPosition.setColumnIndex(z);
        }
        return newPosition;

    }

    /** check if two positions are neighbors in given maze.
     * @param p1 Position 1
     * @param p2 Position 2
     * @param aMaze given Maze
     * @return returns true if two positions are neighbors in given maze. else false.
     */
    private boolean isNeighbor(Position3D p1, Position3D p2, Maze3D aMaze) {
        if(aMaze.getCellValue(p1)!=-1 && aMaze.getCellValue(p2)!=-1){
            int row1 = p1.getRowIndex(), row2 = p2.getRowIndex();
            int col1 = p1.getColumnIndex(), col2 = p2.getColumnIndex();
            if(row1 == row2){
                if(Math.abs(col1-col2)==1){
                    return true;
                }
            }
            else if(col1==col2){
                if(Math.abs(row1-row2)==1){
                    return true;
                }
            }
        }
        return false;
    }

    private Position3D getRandomStartPosition(Maze3D aMaze) {
        Random rand = new Random();
        int col;
        int row = rand.nextInt(aMaze.getRows());
        int dim = rand.nextInt(aMaze.getDepth());
        while(row == aMaze.getGoalPosition().getRowIndex()&& dim==aMaze.getGoalPosition().getDepthIndex()){
            row = rand.nextInt(aMaze.getRows());
            dim = rand.nextInt(aMaze.getDepth());
        }
        if(dim>0 && dim<(aMaze.getDepth()-1)){ // then the column could be the rightest or leftest column
        //    col = rand.nextInt(2);
            if(aMaze.getGoalPosition().getColumnIndex()!= aMaze.getColumns()-1 ){
                return new Position3D(dim,row, aMaze.getColumns()-1);
            }
            return new Position3D(dim,row,0);
        }
        col = rand.nextInt(aMaze.getColumns());
        while(col == aMaze.getGoalPosition().getColumnIndex()){
            col = rand.nextInt(aMaze.getColumns());
        }
        if(col==0 || col==aMaze.getColumns()-1){
            if(dim==0){
                while(row==aMaze.getRows()-1){
                    row= rand.nextInt(aMaze.getRows());
                }
                return new Position3D(dim,row,col);
            }
            while(row==0){
                row= rand.nextInt(aMaze.getRows());
            }
            return new Position3D(dim,row,col);
        }
        if(dim==0){
            return new Position3D(dim,0,col);
        }
        return new Position3D(dim, aMaze.getRows()-1, col);
    }


    private ArrayList<Position3D> AllNeighborWalls(Position3D w, Maze3D aMaze) {
        ArrayList<Position3D> arrayList = new ArrayList<Position3D>();
        int row = w.getRowIndex();
        int col = w.getColumnIndex();
        for(int i=0; i<aMaze.getDepth(); i++){
            Position3D p = new Position3D(i,row,col);
//            if(aMaze.getCellValue(p)==0){
                Position3D a = new Position3D(i,row-1, col);
                if(aMaze.getCellValue(a)==1){
                    arrayList.add(a);
                }
                Position3D b = new Position3D(i,row+1, col);
                if(aMaze.getCellValue(b)==1){
                    arrayList.add(b);
                }
                Position3D c = new Position3D(i,row, col-1);
                if(aMaze.getCellValue(c)==1){
                    arrayList.add(c);
                }
                Position3D d = new Position3D(i,row, col+1);
                if(aMaze.getCellValue(d)==1){
                    arrayList.add(d);
                }
//            }
        }

        return arrayList;
    }

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

}

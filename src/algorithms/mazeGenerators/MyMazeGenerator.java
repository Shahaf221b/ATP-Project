package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.Random;

public class MyMazeGenerator extends AMazeGenerator{


    @Override
    public Maze generate(int rows, int columns) {
        Maze aMaze = new Maze(rows,columns, 1);
        Position endP = getRandomEndPosition(aMaze);
        aMaze.setGoalPosition(endP);
        aMaze.setCell(endP,0);
        Position startP = getRandomStartPosition(aMaze);
        aMaze.setStartPosition(startP);
        aMaze.setCell(startP,0);

        ArrayList<Position> allWalls ;
        allWalls = AllNeighborWalls(endP,aMaze);
        Random rand = new Random();
        int i;
        Position w ;

        while(allWalls.size()>0){
            i = rand.nextInt(allWalls.size());
            w = allWalls.get(i);
            allWalls.remove(i);
            if(isASeparator(w,aMaze)){
                if(AllNeighborWalls(w,aMaze).size()>2 || isNeighbor(w,startP,aMaze)){
                    aMaze.setCell(w,0);
                    allWalls.addAll(AllNeighborWalls(w,aMaze));
                }

            }

        }
        if(AllNeighborWalls(startP,aMaze).size()>=2){
            aMaze =generate(rows,columns);
        }
        return aMaze;
    }

    /** check if two positions are neighbors in given maze.
     * @param p1 Position 1
     * @param p2 Position 2
     * @param aMaze given Maze
     * @return returns true if two positions are neighbors in given maze. else false.
     */
    private boolean isNeighbor(Position p1, Position p2, Maze aMaze) {
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

    private Position getRandomStartPosition(Maze aMaze) {
        Random rand = new Random();
        int col;
        int row = rand.nextInt(aMaze.getRows());
        while(row == aMaze.getGoalPosition().getRowIndex()){
            row = rand.nextInt(aMaze.getRows());
        }
        if(row>0 && row<(aMaze.getRows()-1)){ // then the column could be the rightest or leftest column
            col = rand.nextInt(2);
            if(aMaze.getGoalPosition().getColumnIndex()!= aMaze.getColumns()-1 ){
                return new Position(row, aMaze.getColumns()-1);
            }
            return new Position(row,0);
        }
        col = rand.nextInt(aMaze.getColumns());
        while(col == aMaze.getGoalPosition().getColumnIndex()){
            col = rand.nextInt(aMaze.getColumns());
        }
        return new Position(row,col);
    }


    private ArrayList<Position> AllNeighborWalls(Position w, Maze aMaze) {
        ArrayList<Position> arrayList = new ArrayList<Position>();
        Position a = new Position(w.getRowIndex()-1, w.getColumnIndex());
        if(aMaze.getCellValue(a)==1){
            arrayList.add(a);
        }
        Position b = new Position(w.getRowIndex()+1, w.getColumnIndex());
        if(aMaze.getCellValue(b)==1){
            arrayList.add(b);
        }
        Position c = new Position(w.getRowIndex(), w.getColumnIndex()-1);
        if(aMaze.getCellValue(c)==1){
            arrayList.add(c);
        }
        Position d = new Position(w.getRowIndex(), w.getColumnIndex()+1);
        if(aMaze.getCellValue(d)==1){
            arrayList.add(d);
        }
        return arrayList;
    }

    private boolean isASeparator(Position w,Maze aMaze) {
        Position a = new Position(w.getRowIndex()-1, w.getColumnIndex());
        Position b = new Position(w.getRowIndex()+1, w.getColumnIndex());
        if(aMaze.getCellValue(a)==1 && aMaze.getCellValue(b)==0 || aMaze.getCellValue(a)==0 && aMaze.getCellValue(b)==1 ){
            return true;
        }
        Position c = new Position(w.getRowIndex(), w.getColumnIndex()-1);
        Position d = new Position(w.getRowIndex(), w.getColumnIndex()+1);
        if(aMaze.getCellValue(c)==1 && aMaze.getCellValue(d)==0 || aMaze.getCellValue(c)==0 && aMaze.getCellValue(d)==1 ){
            return true;
        }
        return false;
    }

    private Position getRandomEndPosition(Maze aMaze) {
        Random rand = new Random();
        int col;
        int row = rand.nextInt(aMaze.getRows());
        if(row>0 && row<(aMaze.getRows()-1)){ // then the column could be the rightest or leftest column
            col = rand.nextInt(2);
            if(col == 1){
                return new Position(row, aMaze.getColumns()-1);
            }
            return new Position(row,0);
        }
        col = rand.nextInt(aMaze.getColumns());
        return new Position(row,col);
    }
}

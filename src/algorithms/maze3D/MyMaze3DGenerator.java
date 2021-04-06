package algorithms.maze3D;

import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.*;

import javax.swing.text.html.HTMLDocument;
import java.util.*;

public class MyMaze3DGenerator extends AMaze3DGenerator {


    @Override
    public Maze3D generate(int depth, int row, int column) {
        Maze3D aMaze = new Maze3D(depth, row, column);
        Position3D endP = getRandomEndPosition(aMaze);
        aMaze.setGoalPosition(endP);
        aMaze.setCell(endP, 0);
        Position3D startP = getRandomStartPosition(aMaze);
        aMaze.setStartPosition(startP);
        aMaze.setCell(startP, 0);
        Maze3DState startMS = new Maze3DState(startP);

        int minV = Math.min(depth,Math.min(row,column));
        int maxV = Math.max(depth,Math.max(row,column));

        for (int i = 0; i < maxV; i++) {
            Position3D p = getRandomPosition3D(aMaze);
            aMaze.setCell(p, 0);
            p=null;
        }

        Set<Maze3DState> allWalls = new HashSet<>();
//        ArrayList<Position3D> allWalls ;
        Maze3DState endMS = new Maze3DState(endP);
        allWalls.addAll(AllNeighborWalls(endMS, aMaze));
        Random rand = new Random();
        int randDim;
        Iterator<Maze3DState> it = allWalls.iterator();
        Maze3DState w;
        ArrayList<Maze3DState> toRemove = new ArrayList<>();
        ArrayList<Maze3DState> allNwalls = null;
//        ArrayList<Position3D> toRemove = new ArrayList<>();


//        while(allWalls.size()>0){
        while (it.hasNext() && allWalls.size()<maxV*minV ) { //&& allWalls.size()<maxV*maxV/minV
//            i = rand.nextInt(allWalls.size());
//            w = allWalls.get(i);
//            w = it.next();
            w = getRandomSetElement(allWalls);
            toRemove.add(w);

//            if(isASeparator(w,aMaze)||AllNeighborWalls(w,aMaze).size()>2*depth ){
            Position3D wP = w.getPosition();
            if (isASeparator(wP, aMaze)) {
                allNwalls =AllNeighborWalls(w, aMaze);
                if (allNwalls.size() >= 2 || isNeighbor(w, startMS, aMaze)) {
                    aMaze.setCell(wP, 0);
                    randDim = rand.nextInt(maxV*2);
                    if(randDim<depth){
 //                       aMaze.setCell(new Position3D(randDim,wP.getRowIndex(),wP.getColumnIndex()), 0);
                        aMaze.changeVal(randDim,wP.getRowIndex(),wP.getColumnIndex(), 0);
                    }
                    allWalls.removeAll(toRemove);
                    allWalls.addAll(AllNeighborWalls(w, aMaze));
                }
                allNwalls = null;
            }
//            else{
//                Position3D p = getRandomPosition3D(aMaze);
//                aMaze.setCell(p, 0);
//            }

           if(wP.getRowIndex()>=0){

               toRemove.remove(0);
               it = allWalls.iterator();
            }

        }
 //       if (AllNeighborWalls(startMS, aMaze).size() >= 2) { //TODO:check
        if(NumOfAllNeighborPaths(startMS,aMaze)==0){
            aMaze = generate(depth, row, column);
        }
        return aMaze;
    }

    private  Maze3DState getRandomSetElement(Set<Maze3DState> set) {
        return set.stream().skip(new Random().nextInt(set.size())).findFirst().orElse(null);
    }

    private Position3D getRandomPosition3D(Maze3D maze3D) {
        int x, y, z;
        Random rand = new Random();
        Position3D start = maze3D.getStartPosition();
        Position3D goal = maze3D.getGoalPosition();
        Position3D newPosition = new Position3D(start.getDepthIndex(), start.getRowIndex(), start.getColumnIndex());
 //       while (newPosition.equals(start) || newPosition.equals(goal) || newPosition.getDepthIndex() == start.getDepthIndex() || newPosition.getDepthIndex() == goal.getDepthIndex()) {
        while (newPosition.equals(start) || newPosition.equals(goal) ) {

            x = rand.nextInt(maze3D.getDepth());
            y = rand.nextInt(maze3D.getRows());
            z = rand.nextInt(maze3D.getColumns());
            newPosition.setDepthIndex(x);
            newPosition.setRowIndex(y);
            newPosition.setColumnIndex(z);
        }
        return newPosition;

    }

    /**
     * check if two positions are neighbors in given maze.
     *
     * @param ms1   Position 1
     * @param ms2   Position 2
     * @param aMaze given Maze
     * @return returns true if two positions are neighbors in given maze. else false.
     */
    private boolean isNeighbor(Maze3DState ms1, Maze3DState ms2, Maze3D aMaze) {
        if (aMaze.getCellValue(ms1) != -1 && aMaze.getCellValue(ms2) != -1) {
            Position3D p1 = ms1.getPosition();
            Position3D p2 = ms2.getPosition();
            int row1 = p1.getRowIndex(), row2 = p2.getRowIndex();
            int col1 = p1.getColumnIndex(), col2 = p2.getColumnIndex();
            if (row1 == row2) {
                if (Math.abs(col1 - col2) == 1) {
                    return true;
                }
            } else if (col1 == col2) {
                if (Math.abs(row1 - row2) == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    private Position3D getRandomStartPosition(Maze3D aMaze) {
/*        Random rand = new Random();
        int col;
        int row = rand.nextInt(aMaze.getRows());
        int dim = rand.nextInt(aMaze.getDepth());
        while (row == aMaze.getGoalPosition().getRowIndex() && dim == aMaze.getGoalPosition().getDepthIndex()) {
            row = rand.nextInt(aMaze.getRows());
            dim = rand.nextInt(aMaze.getDepth());
        }
        if (dim > 0 && dim < (aMaze.getDepth() - 1)) { // then the column could be the rightest or leftest column
            //    col = rand.nextInt(2);
            if (aMaze.getGoalPosition().getColumnIndex() != aMaze.getColumns() - 1) {
                return new Position3D(dim, row, aMaze.getColumns() - 1);
            }
            return new Position3D(dim, row, 0);
        }
        col = rand.nextInt(aMaze.getColumns());
        while (col == aMaze.getGoalPosition().getColumnIndex()) {
            col = rand.nextInt(aMaze.getColumns());
        }
        if (col == 0 || col == aMaze.getColumns() - 1) {
            if (dim == 0) {
                while (row == aMaze.getRows() - 1 || row==aMaze.getGoalPosition().getRowIndex()) {//TODO
                    row = rand.nextInt(aMaze.getRows());
                }
                return new Position3D(dim, row, col);
            }
            while (row == 0 || row==aMaze.getGoalPosition().getRowIndex()) { //TODO
                row = rand.nextInt(aMaze.getRows());
            }
            return new Position3D(dim, row, col);
        }
        if (dim == 0) {
            return new Position3D(dim, 0, col);
        }
        return new Position3D(dim, aMaze.getRows() - 1, col);*/
        Random rand = new Random();
        int col;
        int row = rand.nextInt(aMaze.getRows());
        int dim = rand.nextInt(aMaze.getDepth());
        while (row == aMaze.getGoalPosition().getRowIndex() && dim == aMaze.getGoalPosition().getDepthIndex() ) {
            row = rand.nextInt(aMaze.getRows());
            dim = rand.nextInt(aMaze.getDepth());
        }
        if (row > 0 && row < (aMaze.getRows() - 1)) { // then the column could be the rightest or leftest column

            if (aMaze.getGoalPosition().getColumnIndex() != aMaze.getColumns() - 1) {
                return new Position3D(dim,row, aMaze.getColumns() - 1);
            }
            return new Position3D(dim,row, 0);
        }
        col = rand.nextInt(aMaze.getColumns());
        while (col == aMaze.getGoalPosition().getColumnIndex()) {
            col = rand.nextInt(aMaze.getColumns());
        }
        while (row == aMaze.getGoalPosition().getRowIndex() && dim == aMaze.getGoalPosition().getDepthIndex() ) {
            row = rand.nextInt(aMaze.getRows());
            dim = rand.nextInt(aMaze.getDepth());
        }
        return new Position3D(dim,row, col);
    }


    private ArrayList<Maze3DState> AllNeighborWalls(Maze3DState w, Maze3D aMaze) {
        ArrayList<Maze3DState> arrayList = new ArrayList<Maze3DState>();
        Position3D position3D = w.getPosition();
        int row = position3D.getRowIndex();
        int col = position3D.getColumnIndex();
        int dim = position3D.getDepthIndex();
        for (int i = 0; i < aMaze.getDepth(); i++) {
//            Maze3DState p = new Maze3DState(i, row, col);
//            if (aMaze.getCellValue(p) == 0 || i == dim) {
////                Maze3DState a = new Maze3DState(i,row-1, col);
////                if(aMaze.getCellValue(a)==1){
////                    arrayList.add(a);
////                }
////                Maze3DState b = new Maze3DState(i,row+1, col);
////                if(aMaze.getCellValue(b)==1){
////                    arrayList.add(b);
////                }
////                Maze3DState c = new Maze3DState(i,row, col-1);
////                if(aMaze.getCellValue(c)==1){
////                    arrayList.add(c);
////                }
////                Maze3DState d = new Maze3DState(i,row, col+1);
////                if(aMaze.getCellValue(d)==1){
////                    arrayList.add(d);
////                }

                if (aMaze.getVal(i, row, col) == 0 || i == dim) {
                    //                   Position3D a = new Position3D(i,row-1, col);
                    if (aMaze.getVal(i, row - 1, col) == 1) {
                        Maze3DState a = new Maze3DState(i, row - 1, col);
                        arrayList.add(a);
                    }
//                Position3D b = new Position3D(i,row+1, col);
                    if (aMaze.getVal(i, row + 1, col) == 1) {
                        Maze3DState b = new Maze3DState(i, row + 1, col);
                        arrayList.add(b);
                    }
//                Position3D c = new Position3D(i,row, col-1);
                    if (aMaze.getVal(i, row, col - 1) == 1) {
                        Maze3DState c = new Maze3DState(i, row, col - 1);
                        arrayList.add(c);
                    }
//                Position3D d = new Position3D(i,row, col+1);
                    if (aMaze.getVal(i, row, col + 1) == 1) {
                        Maze3DState d = new Maze3DState(i, row, col + 1);
                        arrayList.add(d);
                    }
                }
            }

            return arrayList;
        }


    private ArrayList<Maze3DState> AllNeighborPaths(Maze3DState w, Maze3D aMaze) {
        ArrayList<Maze3DState> arrayList = new ArrayList<Maze3DState>();
        Position3D wp = w.getPosition();
        int row = wp.getRowIndex();
        int col = wp.getColumnIndex();
        int dim = wp.getDepthIndex();
        for(int i=0; i<aMaze.getDepth(); i++){
 //           Position3D p = new Position3D(i,row,col);
            if (aMaze.getVal(i, row, col) == 0 || i == dim) {
                //                   Position3D a = new Position3D(i,row-1, col);
                if (aMaze.getVal(i, row - 1, col) == 0) {
                    Maze3DState a = new Maze3DState(i, row - 1, col);
                    arrayList.add(a);
                }
//                Position3D b = new Position3D(i,row+1, col);
                if (aMaze.getVal(i, row + 1, col) == 0) {
                    Maze3DState b = new Maze3DState(i, row + 1, col);
                    arrayList.add(b);
                }
//                Position3D c = new Position3D(i,row, col-1);
                if (aMaze.getVal(i, row, col - 1) == 0) {
                    Maze3DState c = new Maze3DState(i, row, col - 1);
                    arrayList.add(c);
                }
//                Position3D d = new Position3D(i,row, col+1);
                if (aMaze.getVal(i, row, col + 1) == 0) {
                    Maze3DState d = new Maze3DState(i, row, col + 1);
                    arrayList.add(d);
                }
            }
        }

        return arrayList;
    }

    private int NumOfAllNeighborPaths(Maze3DState w, Maze3D aMaze) {
        int ans =0;
        Position3D wp = w.getPosition();
        int row = wp.getRowIndex();
        int col = wp.getColumnIndex();
        int dim = wp.getDepthIndex();
        for(int i=0; i<aMaze.getDepth(); i++){
            //           Position3D p = new Position3D(i,row,col);
            if (aMaze.getVal(i, row, col) == 0 || i == dim) {
                //                   Position3D a = new Position3D(i,row-1, col);
                if (aMaze.getVal(i, row - 1, col) == 0) {
                    ans++;
                }
//                Position3D b = new Position3D(i,row+1, col);
                if (aMaze.getVal(i, row + 1, col) == 0) {
                    ans++;
                }
//                Position3D c = new Position3D(i,row, col-1);
                if (aMaze.getVal(i, row, col - 1) == 0) {
                    ans++;
                }
//                Position3D d = new Position3D(i,row, col+1);
                if (aMaze.getVal(i, row, col + 1) == 0) {
                    ans++;
                }
            }
        }

        return ans;
    }


    private boolean isASeparator (Position3D w, Maze3D aMaze){
            int dim = w.getDepthIndex();
            int row = w.getRowIndex();
            int col = w.getColumnIndex();
            Maze3DState a = new Maze3DState(dim, row - 1, col);
            Maze3DState b = new Maze3DState(dim, row + 1, col);
            if (aMaze.getCellValue(a) == 1 && aMaze.getCellValue(b) == 0 || aMaze.getCellValue(a) == 0 && aMaze.getCellValue(b) == 1) {
                return true;
            }
            Maze3DState c = new Maze3DState(dim, row, col - 1);
            Maze3DState d = new Maze3DState(dim, row, col + 1);
            if (aMaze.getCellValue(c) == 1 && aMaze.getCellValue(d) == 0 || aMaze.getCellValue(c) == 0 && aMaze.getCellValue(d) == 1) {
                return true;
            }
            return false;
        }

        private Position3D getRandomEndPosition (Maze3D aMaze){
            Random rand = new Random();
            int dim;
            int col, row;
            dim = rand.nextInt(aMaze.getDepth());
            row = rand.nextInt(aMaze.getRows());
            if (dim > 0 && dim < (aMaze.getDepth() - 1)) { // then the column could be the rightest or leftest column
                col = rand.nextInt(2);
                if (col == 1) {
                    return new Position3D(dim, row, aMaze.getColumns() - 1);
                }
                return new Position3D(dim, row, 0);
            }
            col = rand.nextInt(aMaze.getColumns());
            if (dim == 0) {
                return new Position3D(dim, 0, col);
            }
            return new Position3D(dim, aMaze.getRows() - 1, col);
        }

    }


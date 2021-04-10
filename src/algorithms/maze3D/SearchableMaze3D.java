package algorithms.maze3D;

import algorithms.search.AState;
import algorithms.search.ISearchable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SearchableMaze3D implements ISearchable {

    private Maze3D myMaze3D;

    public SearchableMaze3D(Maze3D maze3D) {
        if (maze3D != null) {
            myMaze3D = maze3D;
        }
    }

    @Override
    public AState getStartState() {
        Position3D p = myMaze3D.getStartPosition();
        int depth = p.getDepthIndex();
        int row = p.getRowIndex();
        int column = p.getColumnIndex();
        return myMaze3D.getMaze3DState(depth, row, column);
    }

    @Override
    public AState getGoalState() {
        Position3D p = myMaze3D.getGoalPosition();
        int depth = p.getDepthIndex();
        int row = p.getRowIndex();
        int column = p.getColumnIndex();
        return myMaze3D.getMaze3DState(depth, row, column);
    }

    @Override
    public ArrayList<AState> getAllSuccessors(AState s) {
        Maze3DState m3ds = (Maze3DState) s;
        Position3D p = m3ds.getPosition();
        Set<AState> pSet = new HashSet<>();
        int pZ = p.getDepthIndex();
        int pX = p.getRowIndex();
        int pY = p.getColumnIndex();
        int[] x_values = {pX - 1, pX, pX + 1, pX};
        int[] y_values = {pY, pY - 1, pY, pY + 1};

        // other dimensions
        int dim = myMaze3D.getDepth();
        for (int j = 0; j < dim; j++) {
            Maze3DState newP = myMaze3D.getMaze3DState(j, pX, pY);
            if (newP != null) {
                if (j != pZ) {
                    if (myMaze3D.getCellValue(newP.getPosition()) == 0) {
                        pSet.add(newP);
                    }
                }
            }

        }

        // sides
        int x, y;
        for (int i = 0; i < 4; i++) {
            x = x_values[i];
            y = y_values[i];
            if (x >= 0 && x < myMaze3D.getRows() && y >= 0 && y < myMaze3D.getColumns()) {
                Maze3DState newP = myMaze3D.getMaze3DState(pZ, x, y);
                if (newP != null) {
                    if (myMaze3D.getCellValue(newP.getPosition()) == 0) {
                        pSet.add(newP);
                    }
                }
            }
        }

        ArrayList<AState> ans = new ArrayList<>(pSet.size());
        ans.addAll(pSet);
        return ans;
    }

    @Override
    public int getCost(AState state) {
        return 0;
    }


    @Override
    public void initAllStates() {
        int dim = myMaze3D.getDepth();
        int row = myMaze3D.getRows();
        int column = myMaze3D.getColumns();
        int[][][] matrix = myMaze3D.getMap();
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < row; j++) {
                for (int z = 0; z < column; z++) {
                    // TODO: CHANGE TO ARRAY OF O AND 1. 0- NOT VISITED, 1- VISITED
                    if (matrix[i][j][z] == 0) {
                        Maze3DState newP = new Maze3DState(i, j, z);
                        setMaze3DState(newP);
                    }
                }
            }
        }
    }

    public void setMaze3DState(Maze3DState p) {
        myMaze3D.setMaze3DState(p);
    } //setPosition

    @Override
    public void updateVisited(AState s) {
        Maze3DState ms = (Maze3DState) s;
        ms.setVisited();
        myMaze3D.setMaze3DState(ms);
    }

    @Override
    public void updateParent(AState s, AState parent) {
        Maze3DState ms = (Maze3DState) s;
        Maze3DState parent3D = (Maze3DState) parent;
        ms.setParent(parent3D);
        myMaze3D.setMaze3DState(ms);
    }


    public void PRINT() {
        myMaze3D.print3D();
    }
}

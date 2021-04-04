package algorithms.maze3D;

import algorithms.maze3D.Maze3D;
import algorithms.maze3D.Position3D;
import algorithms.mazeGenerators.Position;
import algorithms.search.AState;
import algorithms.search.ISearchable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SearchableMaze3D implements ISearchable {

    private Maze3D myMaze3D;

    public SearchableMaze3D(Maze3D maze3D) {
        if(maze3D != null){
            myMaze3D = maze3D;
        }
    }

    @Override
    public AState getStartState() {
        return myMaze3D.getStartPosition();
    }

    @Override
    public AState getGoalState() {
        return myMaze3D.getGoalPosition();
    }

    @Override
    public boolean isGoalState(AState state) {
        return myMaze3D.getGoalPosition().equals(state);
    }

    @Override
    public ArrayList<AState> getAllPossibleStates(AState s) {
        Position3D p = (Position3D)s;
        Set<AState> pSet = new HashSet<>();
        int pZ = p.getDepthIndex();
        int pX = p.getRowIndex();
        int pY = p.getColumnIndex();
        int[] x_values = {pX - 1, pX, pX + 1, pX};
        int[] y_values = {pY, pY - 1, pY, pY + 1};

        // other dimensions
        int dim = myMaze3D.getDepth();
        for (int j = 0; j < dim; j ++){
            Position3D newP = myMaze3D.getPosition(j, pX, pY);
            if(newP != null){
                if (j != pZ){
                    if (myMaze3D.getCellValue(newP) == 0){
                        pSet.add(newP);
                    }
                }
            }

        }

        // sides
        int x,y;
        for (int i = 0; i < 4; i++) {
            x = x_values[i];
            y = y_values[i];
            if (x >= 0 && x < myMaze3D.getRows() && y >= 0 && y < myMaze3D.getColumns()) {
                Position3D newP = myMaze3D.getPosition(pZ, x, y);
                if(newP != null){
                    if (myMaze3D.getCellValue(newP) == 0){
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
        for (int i = 0; i < dim; i ++){
            for (int j = 0; j < row; j ++){
                for (int z = 0; z < column; z++) {
                    // TODO: CHANGE TO ARRAY OF O AND 1. 0- NOT VISITED, 1- VISITED
                    if(matrix[i][j][z]==0){
                        Position3D newP = new Position3D(i,j,z);
                        setPosition(newP);
                    }
                }
            }
        }
    }

    public void setPosition(Position3D p) {
        myMaze3D.setPosition3D(p);
    }

    @Override
    public void updateVisited(AState s) {
        Position3D p = (Position3D) s;
        p.setVisited();
        myMaze3D.setPosition3D(p);
    }

    @Override
    public void updateParent(AState s, AState parent) {
        Position3D p = (Position3D) s;
        Position3D parent3D = (Position3D) parent;
        p.setParent(parent3D);
        myMaze3D.setPosition3D(p);
    }

    @Override
    public void setState(AState state) {
        Position3D p = (Position3D)state;
        setPosition(p);
    }


    public void changeVal(AState state) {
        Position3D p = (Position3D) state;
        myMaze3D.changeVal(p.getDepthIndex(), p.getRowIndex(), p.getColumnIndex());
    }

    @Override
    public ArrayList<AState> getAllPossibleStatesNoDiagonal(AState startState) {
        return null;
    }

    public void PRINT() {
        myMaze3D.print3D();
    }
}

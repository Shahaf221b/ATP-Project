package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SearchableMaze implements ISearchable {
    private final Maze myMaze;

    /* constructor */
    public SearchableMaze(Maze maze) {
        this.myMaze = maze;
    }

    /* getters and setters */
    @Override
    public AState getStartState() {
        return myMaze.getStartPosition();
    }

    @Override
    public AState getGoalState() {
        return myMaze.getGoalPosition();
    }

    public void setPosition(Position p) {
        myMaze.setPositions(p.getRowIndex(), p.getColumnIndex(), p);
    }

    @Override
    public void initAllStates() {
        int row = myMaze.getRows();
        int column = myMaze.getColumns();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                Position newP = new Position(i, j);
                setPosition(newP);
            }
        }
    }

    @Override
    public void updateVisited(AState s) {
        Position p = (Position) s;
        p.setVisited();
        int row = p.getRowIndex();
        int column = p.getColumnIndex();
        myMaze.updatePosition(row, column, p); //TODO: needed?
    }

    @Override
    public void updateParent(AState s, AState Sparent) {
        Position p = (Position) s;
        Position parent = (Position) Sparent;
        p.setParent(parent);
        int row = p.getRowIndex();
        int column = p.getColumnIndex();
        myMaze.updatePosition(row, column, p);

    }

    public void PRINT() {
        myMaze.print();
    }


    public int getCost(AState state) {
        String name = state.getName();
        if (name.equals("Sides")) {
            return 10;
        }
        return 15;
    }

    @Override
    public ArrayList<AState> getAllPossibleStates(AState s) {
        Position p = (Position) s;
        return getAllPossibleMoves(p);
    }

    public ArrayList<AState> getAllPossibleMoves(Position p) {
        Set<AState> pSet = new HashSet<>();
        int pX = p.getRowIndex();
        int pY = p.getColumnIndex();
        int[] x_values = {pX - 1, pX, pX + 1, pX};
        int[] y_values = {pY, pY - 1, pY, pY + 1};
        boolean[] path = {false, false, false, false};

        int x, y;
        for (int i = 0; i < 4; i++) {
            x = x_values[i];
            y = y_values[i];
            if (x >= 0 && x < myMaze.getRows() && y >= 0 && y < myMaze.getColumns()) {
                Position newP = myMaze.getPositions(x, y);
                if (myMaze.getCellValue(newP) == 0) {
                    pSet.add(newP);
                    newP.setName("Sides");
                    path[i] = true;
                }
            }
        }

        // diagonal
        int[] diagonal_x = {pX - 1, pX + 1, pX + 1, pX - 1}; //rows
        int[] diagonal_y = {pY - 1, pY - 1, pY + 1, pY + 1}; //columns
//        int[][] wantedValues = {{0,3}, {0, 1}, {1, 2}, {2, 3}};
        int val_1 = 0, val_2 = 1;
        for (int j = 0; j < 4; j++) {
            x = diagonal_x[j];
            y = diagonal_y[j];
            if (x >= 0 && x < myMaze.getRows() && y >= 0 && y < myMaze.getColumns()) {
                Position newP = myMaze.getPositions(x, y);
                if (myMaze.getCellValue(newP) == 0) {
                    if (path[val_1] || path[val_2]) {
                        newP.setName("Diagonal");
                        pSet.add(newP);
                    }
                }
            }
            val_1++;
            if (val_2 == 3) {
                val_2 = 0;
            } else {
                val_2++;
            }
        }

        ArrayList<AState> ans = new ArrayList<>(pSet.size());
        ans.addAll(pSet);
        return ans;
    }
}
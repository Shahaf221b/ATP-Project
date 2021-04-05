package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SearchableMaze implements ISearchable {
    private final Maze myMaze;

    /* constructor */
    public SearchableMaze(Maze maze) throws Exception {
        if(maze == null){
            throw new Exception("given maze is null");
        }
        this.myMaze = maze;
    }

    /* getters and setters */
    @Override
    public AState getStartState() {
        int row = myMaze.getStartPosition().getRowIndex();
        int column = myMaze.getStartPosition().getColumnIndex();
        return myMaze.getMazeState(row, column);
    }

    @Override
    public AState getGoalState() {
        int row = myMaze.getGoalPosition().getRowIndex();
        int column = myMaze.getGoalPosition().getColumnIndex();
        return myMaze.getMazeState(row, column);
    }

    public void setMazeState(MazeState ms) {
        myMaze.setMazeState(ms);
    }

    @Override
    public void initAllStates() {
        int row = myMaze.getRows();
        int column = myMaze.getColumns();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                setMazeState(new MazeState(i, j));
            }
        }
    }

    @Override
    public void updateVisited(AState s) {
        MazeState ms = (MazeState) s;
        ms.setVisited();
        myMaze.updateMazeState(ms);
    }

    @Override
    public void updateParent(AState s, AState MsParent) {
        MazeState ms = (MazeState) s;
        MazeState parent = (MazeState) MsParent;
        ms.setParent(parent);
        myMaze.updateMazeState(ms);
    }

    public int getCost(AState state) {
        String name = state.getName();
        if (name.equals("Sides")) {
            return 10;
        }
        return 15;
    }

    @Override
    public ArrayList<AState> getAllSuccessors(AState s) {
        Position p = ((MazeState) s).getPosition();
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
            // checking if the position is in range of the mazw
            if (x >= 0 && x < myMaze.getRows() && y >= 0 && y < myMaze.getColumns()) {
                // checking the value of the cell
                Position newP = myMaze.getMazeState(x, y).getPosition();
                if (myMaze.getCellValue(newP) == 0) {
                    // adding the mazeState to the set
                    MazeState newMS = myMaze.getMazeState(x, y);
                    pSet.add(newMS);
                    newMS.setName("Sides");
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
                Position newP = myMaze.getMazeState(x, y).getPosition();
                if (myMaze.getCellValue(newP) == 0) {
                    if (path[val_1] || path[val_2]) {
                        MazeState newMS = myMaze.getMazeState(x, y);
                        newMS.setName("Diagonal");
                        pSet.add(newMS);
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
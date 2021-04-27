package algorithms.maze3D;

import java.util.Arrays;

/**
 * Represents a 3D Maze
 */
public class Maze3D {
    private final int depth;
    private final int rows;
    private final int columns;
    private final int[][][] m_maze;
    private Position3D startPosition;
    private Position3D goalPosition;
    private final Maze3DState[][][] mazeStates;

    public Maze3D(int depth, int rows, int columns) {
        this.depth = depth;
        this.rows = rows;
        this.columns = columns;
        this.m_maze = new int[depth][rows][columns];
        this.mazeStates = new Maze3DState[depth][rows][columns];
        for (int i = 0; i < depth; i++) {
            for (int j = 0; j < rows; j++) {
                Arrays.fill(m_maze[i][j], 1);
            }
        }
    }

    /**
     * @param depth
     * @param row
     * @param columns
     * @param val
     * changes the cell in the maze matrix according to parameters(depth,row,columns)
     * to given val
     */
    public void changeVal(int depth, int row, int columns,int val){
        m_maze[depth][row][columns] = val;
    }

    public int[][][] getMap() {
        return this.m_maze;
    }

    public Position3D getStartPosition() {
        return startPosition;
    }

    public Position3D getGoalPosition() {
        return goalPosition;
    }


    public int getDepth() {
        return depth;
    }


    public void print3D() {
        int dS = startPosition.getDepthIndex();
        int rS = startPosition.getRowIndex();
        int cS = startPosition.getColumnIndex();
        int dG = goalPosition.getDepthIndex();
        int rG = goalPosition.getRowIndex();
        int cG = goalPosition.getColumnIndex();
        StringBuilder ans = new StringBuilder();
        System.out.println("{");
        for (int i = 0; i < depth; i++) {
            for (int j = 0; j < rows; j++) {
                ans = new StringBuilder();
                ans.append("{ ");
                for (int k = 0; k < columns; k++) {
                    if (i == dS && j == rS && k == cS) {
                        ans.append("S ");
                    } else if (i == dG && j == rG && k == cG) {
                        ans.append("E ");
                    } else {
                        ans.append(m_maze[i][j][k] + " ");
                    }
                }
                ans.append("}");
                System.out.println(ans);
            }
            if (i < depth - 1) {
                System.out.print("---");
                for (int j = 0; j < columns; j++) {
                    System.out.print("--");
                }
                System.out.println();
            }
        }
        System.out.println("}");
    }

    public void setStartPosition(Position3D startPosition) {
        this.startPosition = startPosition;
        setCell(startPosition, 0);
    }

    public void setGoalPosition(Position3D goalPosition) {
        this.goalPosition = goalPosition;
        setCell(goalPosition, 0);
    }


    /**
     * @param maze3DState
     * set cell in mazeStates matrix according to a given Maze3DState
     */
    public void setMaze3DState(Maze3DState maze3DState) {
        Position3D p = maze3DState.getPosition();
        int pDepth = p.getDepthIndex();
        int pRow = p.getRowIndex();
        int pCol = p.getColumnIndex();
        if (pDepth < depth && pRow < rows && pCol < columns && pDepth >= 0 && pRow >= 0 && pCol >= 0) {
            mazeStates[pDepth][pRow][pCol] = maze3DState;
        }
    }

    /**
     * @param p
     * @param val
     * sets cell value to given val,
     * in the maze matrix according to a given Position3D
     */
    public void setCell(Position3D p, int val) {
        int pDepth = p.getDepthIndex();
        int pRow = p.getRowIndex();
        int pCol = p.getColumnIndex();
        if (pDepth < depth && pRow < rows && pCol < columns) {
            if (val == 0 || val == 1) {
                m_maze[pDepth][pRow][pCol] = val;
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public Maze3DState getMaze3DState(int dim, int row, int col) {
        if (dim < depth && row < rows && col < columns && dim >= 0 && row >= 0 && col >= 0) {
            return mazeStates[dim][row][col];
        }
        return null;
    }

    public int getCellValue(Position3D p) {
        int dim = p.getDepthIndex();
        int row = p.getRowIndex();
        int col = p.getColumnIndex();
        if (dim < depth && row < rows && col < columns && dim >= 0 && row >= 0 && col >= 0) {
            return m_maze[dim][row][col];
        }
        return -1;
    }

    /**
     * @param d - depth
     * @param r - row
     * @param c - column
     * @return returns the value of the cell in the maze matrix
     */
    public int getVal(int d, int r, int c) {
        if (d < depth && r < rows && c < columns && d >= 0 && r >= 0 && c >= 0) {
            return m_maze[d][r][c];
        }
        return -1;
    }

}

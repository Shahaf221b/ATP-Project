
package algorithms.mazeGenerators;

import algorithms.search.MazeState;

import java.util.Arrays;

/**
 * A maze object.
 */
public class Maze {
    private final int rows;
    private final int columns;
    private final int[][] m_maze;
    private Position startPosition;
    private Position goalPosition;
    private final MazeState[][] mazeStates;

    /**
     * Constructor of a maze object.
     * all cells are initialized to 0.
     * throws an Exception if given parameters are negative.
     *
     * @param rows    define maze rows
     * @param columns define maze columns
     */
    public Maze(int rows, int columns) throws Exception {
        if (rows < 0 || columns < 0) {
            throw new Exception("Maze rows and columns should be non negative numbers.");
        }
        else if (rows == 1 || columns == 1){
            throw new Exception("there need to be at least 2 rows and columns");
        }
        this.rows = rows;
        this.columns = columns;
        this.m_maze = new int[rows][columns];
        this.mazeStates = new MazeState[rows][columns];
    }

    /**
     * Constructor of a Maze that sets all cells to given number
     * throws an Exception if given parameters rows and columns are negative.
     *
     * @param rows- number of rows
     * @param columns- number of columns
     * @param val     - the val to set in all cells
     */
    public Maze(int rows, int columns, int val) throws Exception {
        if (rows < 0 || columns < 0) {
            throw new Exception("Maze rows and columns should be non negative numbers.");
        }
        else if (rows == 1 || columns == 1){
            throw new Exception("there need to be at least 2 rows and columns");
        }
        this.rows = rows;
        this.columns = columns;
        this.m_maze = new int[rows][columns];
        this.mazeStates = new MazeState[rows][columns];
        for (int i = 0; i < rows; i++) {
            Arrays.fill(m_maze[i], val);
        }
    }

    /* getters and setters */
    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    /**
     * set cell in maze presented by position to a given value
     *
     * @param p   - position to set
     * @param val - the value to be changed to
     */
    public void setCell(Position p, int val) throws Exception {
        if (p.getRowIndex() < rows && p.getColumnIndex() < columns) {
            if (!(val == 0 || val == 1)){
                throw new Exception("cell value must be 0 or 1");
            }
            m_maze[p.getRowIndex()][p.getColumnIndex()] = val;
        }
    }

    /**
     * get cell value that is presented by position p
     *
     * @param p the wanted position
     * @return the value of the cell- 0 or 1
     */
    public int getCellValue(Position p) {
        int rowIndex = p.getRowIndex();
        int colIndex = p.getColumnIndex();
        if (rowIndex >= 0 && rowIndex < rows && colIndex >= 0 && colIndex < columns) {
            return m_maze[rowIndex][colIndex];
        }
        return -1;
    }


    public MazeState getMazeState(int row, int column) {
        return mazeStates[row][column];
    }

    public void setMazeState(MazeState ms) {
        int row = ms.getPosition().getRowIndex();
        int column = ms.getPosition().getColumnIndex();
        mazeStates[row][column] = ms;
    }

    public void updateMazeState(MazeState ms) {
        int row = ms.getPosition().getRowIndex();
        int column = ms.getPosition().getColumnIndex();
        mazeStates[row][column] = ms;
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public Position getGoalPosition() {
        return goalPosition;
    }

    public void setStartPosition(Position p) {
        this.startPosition = p;
    }

    public void setGoalPosition(Position p) {
        this.goalPosition = p;
    }


    // TODO: check the right way to print
    public void print() {
        int rowS = startPosition.getRowIndex();
        int colS = startPosition.getColumnIndex();
        int rowE = goalPosition.getRowIndex();
        int colE = goalPosition.getColumnIndex();
        for (int r = 0; r < rows; r++) {
            StringBuilder arow = new StringBuilder("{ ");
            for (int c = 0; c < columns; c++) {
                if (r == rowS && c == colS) {
                    arow.append("S");
                } else if (r == rowE && c == colE) {
                    arow.append("E");
                } else {
                    arow.append(m_maze[r][c]);
                }
                arow.append(" ");
            }
            arow.append("}");
            System.out.println(arow);
        }
    }
}

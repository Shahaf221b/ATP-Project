package algorithms.mazeGenerators;

import java.util.Arrays;

public class Maze {
    private final int rows;
    private final int columns;
    private final int[][] m_maze;
    private Position startPosition;
    private Position goalPosition;
    private final Position[][] positions;

    public Maze(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.m_maze = new int[rows][columns];
        positions = new Position[rows][columns];
    }

    // TODO: why another construct?
    public Maze(int rows, int columns, int val) {
        this.rows = rows;
        this.columns = columns;
        this.m_maze = new int[rows][columns];
        positions = new Position[rows][columns];
        for (int i = 0; i < rows; i++) {
            Arrays.fill(m_maze[i], 1);
        }
    }

    /* getters and setters */
    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setCell(Position p, int val) {
        if (p.getRowIndex() < rows && p.getColumnIndex() < columns) {
            m_maze[p.getRowIndex()][p.getColumnIndex()] = val;
        }
    }

    public int getCellValue(Position p) {
        int rowIndex = p.getRowIndex();
        int colIndex = p.getColumnIndex();
        if (rowIndex >= 0 && rowIndex < rows && colIndex >= 0 && colIndex < columns) {
            return m_maze[rowIndex][colIndex];
        }
        return -1;
    }

    public Position getPositions(int row, int column) {
        return positions[row][column];
    }

    public void setPositions(int row, int column, Position position) {
        positions[row][column] = position;
    }


    /* Position's functions */
    public void updatePosition(int row, int column, Position p) {
        positions[row][column] = p;
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public Position getGoalPosition() {
        return goalPosition;
    }

    public void setStartPosition(Position startPosition) {
        this.startPosition = startPosition;
    }

    public void setGoalPosition(Position goalPosition) {
        this.goalPosition = goalPosition;
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

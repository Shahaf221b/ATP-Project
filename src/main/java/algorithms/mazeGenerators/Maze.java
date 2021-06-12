package algorithms.mazeGenerators;

import algorithms.search.MazeState;

import java.io.Serializable;
import java.util.Arrays;

/**
 * A maze object.
 */
public class Maze implements Serializable, IBoardGame {
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
        } else if (rows == 1 || columns == 1) {
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
     * @param rows-    number of rows
     * @param columns- number of columns
     * @param val      - the val to set in all cells
     */
    public Maze(int rows, int columns, int val) throws Exception {
        if (rows < 0 || columns < 0) {
            throw new Exception("Maze rows and columns should be non negative numbers.");
        } else if (rows == 1 || columns == 1) {
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

    public Maze(byte[] bytes) throws Exception {
        if (bytes.length < 19) {
            throw new Exception("parameter doesn't contain all data needed");
        }
        //get maze dimensions
        int numOfRows = ((int) bytes[0] & 255) * ((int) bytes[1] & 255) + ((int) bytes[2] & 255);
        int numOfColumns = ((int) bytes[3] & 255) * ((int) bytes[4] & 255) + ((int) bytes[5] & 255);
        // init a maze according to given dimensions
        this.rows = numOfRows;
        this.columns = numOfColumns;
        this.m_maze = new int[numOfRows][numOfColumns];
        this.mazeStates = new MazeState[numOfRows][numOfColumns];
        //get&set maze start position
        int startRow = ((int) bytes[6] & 255) * ((int) bytes[7] & 255) + ((int) bytes[8] & 255);
        int startColumn = ((int) bytes[9] & 255) * ((int) bytes[10] & 255) + ((int) bytes[11] & 255);
        this.setStartPosition(new Position(startRow, startColumn));
        //get&set maze end position
        int endRow = ((int) bytes[12] & 255) * ((int) bytes[13] & 255) + ((int) bytes[14] & 255);
        int endColumn = ((int) bytes[15] & 255) * ((int) bytes[16] & 255) + ((int) bytes[17] & 255);
        this.setGoalPosition(new Position(endRow, endColumn));

        int index = 18;
        for (int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < numOfColumns; j++) {
                m_maze[i][j] = bytes[index];
                index++;
            }
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
            if (!(val == 0 || val == 1)) {
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

    /**
     * @return byte[] that contains all the needed information about the Maze
     * structure: {r1,r2,r3,c1,c2,c3,s1,s2,s3,s4,s5,s6,e1,e2,e3,e4,e5,e6,matrix content}
     * where:
     * r1*r2+r3 = maze's rows
     * c1*c2+c3 = maze's columns
     * s1*s2+s3 = maze's start position row
     * s4*s5+s6 = maze's start position column
     * e1*e2+e3 = maze's goal position row
     * e4*e5+e6 = maze's goal position column
     * matrix content will be a sequence of numbers that represented the contents of the maze matrix -
     * when going through the maze in the order of the lines from left to right.
     * The first digit represents how many times the digit 0 appears in sequence starting from position (0,0).
     * The second digit represents how many times the digit 1 appears afterwards, etc ..
     */
    public byte[] toByteArray() {
        byte r1, r2 = 0, c1, c2 = 0, s1, s2 = 0, s4, s5 = 0, e1, e2 = 0, e4, e5 = 0;
        int r3, c3, s3, s6, e3, e6;
        if (rows > 255) {
            r1 = (byte) 255;
            r3 = rows;
            while (r3 > 255) {
                r2++;
                r3 -= 255;
            }
        } else {
            r1 = (byte) rows;
            r2 = 1;
            r3 = 0;
        }
        if (columns > 255) {
            c1 = (byte) 255;
            c3 = columns;
            while (c3 > 255) {
                c2++;
                c3 -= 255;
            }
        } else {
            c1 = (byte) columns;
            c2 = 1;
            c3 = 0;
        }
        if (startPosition.getRowIndex() > 255) {
            s1 = (byte) 255;
            s3 = startPosition.getRowIndex();
            while (s3 > 255) {
                s2++;
                s3 -= 255;
            }
        } else {
            s1 = (byte) startPosition.getRowIndex();
            s2 = 1;
            s3 = 0;
        }
        if (startPosition.getColumnIndex() > 255) {
            s4 = (byte) 255;
            s6 = startPosition.getRowIndex();
            while (s6 > 255) {
                s5++;
                s6 -= 255;
            }
        } else {
            s4 = (byte) startPosition.getColumnIndex();
            s5 = 1;
            s6 = 0;
        }
        if (goalPosition.getRowIndex() > 255) {
            e1 = (byte) 255;
            e3 = goalPosition.getRowIndex();
            while (e3 > 255) {
                e2++;
                e3 -= 255;
            }
        } else {
            e1 = (byte) goalPosition.getRowIndex();
            e2 = 1;
            e3 = 0;
        }
        if (goalPosition.getColumnIndex() > 255) {
            e4 = (byte) 255;
            e6 = goalPosition.getRowIndex();
            while (e6 > 255) {
                e5++;
                e6 -= 255;
            }
        } else {
            e4 = (byte) goalPosition.getColumnIndex();
            e5 = 1;
            e6 = 0;
        }
        byte[] bytes = {r1, r2, (byte) r3, c1, c2, (byte) c3, s1, s2, (byte) s3, s4, s5, (byte) s6, e1, e2, (byte) e3, e4, e5, (byte) e6};
        byte[] matrixContent = new byte[rows * columns];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrixContent[index] = (byte) m_maze[i][j];
                index += 1;
            }
        }
        byte[] result = new byte[bytes.length + matrixContent.length];
        System.arraycopy(bytes, 0, result, 0, bytes.length);
        System.arraycopy(matrixContent, 0, result, bytes.length, matrixContent.length);
        return result;
    }

}

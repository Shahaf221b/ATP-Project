package algorithms.maze3D;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.Arrays;

public class Maze3D {
    private int depth, rows, columns;
    private int[][][] m_maze;
    private Maze[] allMazes;
    private Position3D startPosition;
    private Position3D goalPosition;
    private Position3D[][][] positions;

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public Maze3D(int depth, int rows, int columns) {
        this.depth = depth;
        this.rows = rows;
        this.columns = columns;
        this.m_maze = new int[depth][rows][columns];
        this.allMazes = new Maze[depth];
        this.positions = new Position3D[depth][rows][columns];
        for(int i=0; i<depth; i++){
            for( int j=0; j<rows; j++){
                Arrays.fill(m_maze[i][j],1);
            }
        }
    }

    public int[][][] getMap(){
        return  this.m_maze;
    }

    public Position3D getStartPosition() {
        return startPosition;
    }

    public Position3D getGoalPosition() {
        return goalPosition;
    }

    public void setDim(int dim, int[][] maze) {
        if(dim<=this.depth){
            m_maze[dim]=maze;
        }
    }

    public void setMazeinDim(int dim, Maze m){
        if(dim<=this.depth){
            allMazes[dim] = m;
        }
    }

    public int getDepth() {
        return depth;
    }

    public void print3D(){
 /*       for (Maze m:allMazes) {
            m.print();
            System.out.println("------------------");
        }*/
        int dS = startPosition.getDepthIndex();
        int rS = startPosition.getRowIndex();
        int cS = startPosition.getColumnIndex();
        int dG = goalPosition.getDepthIndex();
        int rG = goalPosition.getRowIndex();
        int cG = goalPosition.getColumnIndex();
        String ans = "";
        System.out.println("{");
        for(int i=0; i<depth; i++){
            for (int j=0; j<rows; j++){
                ans = "";
                ans += "{ ";
                for(int k=0; k<columns; k++){
                    if(i==dS && j==rS && k==cS){
                        ans+="S";
                    }
                    else if(i==dG && j==rG && k==cG){
                        ans +="E";
                    }
                    else{
                        ans += m_maze[i][j][k];
                    }
                    ans += " ";
                }
                ans += "}";
                System.out.println(ans);
            }
            if(i<depth-1){
                System.out.println("-------------");
            }
        }
        System.out.println("}");
    }

    public void setStartPosition(Position3D startPosition) {
        this.startPosition = startPosition;
        setCell(startPosition,0);
    }

    public void setGoalPosition(Position3D goalPosition) {
        this.goalPosition = goalPosition;
        setCell(goalPosition,0);
    }

    public Maze[] getAllMazes() {
        return allMazes;
    }

    public void setPosition3D(Position3D p){
        int pDepth = p.getDepthIndex();
        int pRow = p.getRowIndex();
        int pCol = p.getColumnIndex();
        if(pDepth< depth && pRow<rows && pCol< columns){
            positions[pDepth][pRow][pCol] =p;
        }
    }

    // TODO: DELETE- FOR TEST
    public void changeVal(int depth, int row, int columns){
        m_maze[depth][row][columns] = 5;
    }

    public Position3D getPositions(int depth, int row, int column) {
        return positions[depth][row][column];
    }

    public void setCell(Position3D p, int val){
        int pDepth =p.getDepthIndex();
        int pRow = p.getRowIndex();
        int pCol = p.getColumnIndex();
        if(pDepth< depth && pRow<rows && pCol< columns){
            if(val==0 || val == 1){
                m_maze[pDepth][pRow][pCol] =val;
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getCellValue(Position3D p){
        int rowIndex = p.getRowIndex();
        int colIndex = p.getColumnIndex();
        int depthIndex = p.getDepthIndex();
        if(rowIndex>=0 && rowIndex<rows && colIndex>=0 && colIndex<columns){
            return m_maze[depthIndex][rowIndex][colIndex];
        }
        return -1;
    }
}

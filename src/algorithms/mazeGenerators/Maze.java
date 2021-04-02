package algorithms.mazeGenerators;

import algorithms.search.AState;
import algorithms.search.ISearchable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Maze {
    private int rows;
    private int columns;
    private int[][] m_maze;
    private Position startPosition;
    private Position goalPosition;
    private Position[][] positions;

    public Maze(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.m_maze = new int[rows][columns];
        positions = new Position[rows][columns];
    }

    public Maze(int rows, int columns, int val){
        this.rows = rows;
        this.columns = columns;
        this.m_maze = new int[rows][columns];
        positions = new Position[rows][columns];
        for(int i=0; i<rows; i++){
            Arrays.fill(m_maze[i],1);
        }
    }





    public void updatePosition(int row, int column, Position p){
        positions[row][column] = p;
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public Position getGoalPosition() {
        return goalPosition;
    }

    public void print(){
        int rowS = startPosition.getRowIndex();
        int colS = startPosition.getColumnIndex();
        int rowE = goalPosition.getRowIndex();
        int colE = goalPosition.getColumnIndex();
        for (int r=0; r<rows; r++) {
            String arow="{ ";
            for(int c=0; c<columns; c++){
                if(r==rowS && c==colS){
                    arow += "S";
                }
                else if(r==rowE && c==colE){
                    arow += "E";
                }
                else{
                    arow+= m_maze[r][c];
                }
                arow+= " ";
            }
            arow+= "}";
            System.out.println(arow);
        }
    }

    public void setStartPosition(Position startPosition) {
        this.startPosition = startPosition;
    }

    public void setGoalPosition(Position goalPosition) {
        this.goalPosition = goalPosition;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setCell(Position p,int val){
        if(p.getRowIndex()<rows && p.getColumnIndex()<columns){
            m_maze[p.getRowIndex()][p.getColumnIndex()] = val;
        }
    }

    public int getCellValue(Position p){
        int rowIndex = p.getRowIndex();
        int colIndex = p.getColumnIndex();
        if(rowIndex>=0 && rowIndex<rows && colIndex>=0 && colIndex<columns){
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

/*
    public ArrayList<AState> getAllPossibleMoves(Position p){
        if(getCellValue(p)==-1 || getCellValue(p)==1){
            return null;
        }
        Set<AState> pSet = new HashSet<>();
        int row = p.getRowIndex();
        int col = p.getColumnIndex();
        boolean bool= false;
        try{
            bool = m_maze[row][col+1]==0;
            if(bool){
                pSet.add(new Position(row,col+1));
                if(m_maze[row+1][col+1]==0){
                    pSet.add(new Position(row+1,col+1));
                }
                if(m_maze[row-1][col+1]==0){
                    pSet.add(new Position(row-1,col+1));
                }
            }
        }
        catch (Exception e){}
        try{
            bool= m_maze[row+1][col]==0;
            if(bool){
                pSet.add(new Position(row+1,col));
                if(m_maze[row+1][col-1]==0){
                    pSet.add(new Position(row+1,col-1));
                }
                if(m_maze[row+1][col+1]==0){
                    pSet.add(new Position(row+1,col+1));
                }
            }
        }
        catch (Exception e){}
        try{
            bool= m_maze[row][col-1]==0;
            if(bool){
                pSet.add(new Position(row,col-1));
                if(m_maze[row-1][col-1]==0){
                    pSet.add(new Position(row-1,col-1));
                }
                if(m_maze[row+1][col-1]==0){
                    pSet.add(new Position(row+1,col-1));
                }
            }
        }
        catch (Exception e){}
        try{
            bool= m_maze[row-1][col]==0;
            if(bool){
                pSet.add(new Position(row-1,col));
                if(m_maze[row-1][col+1]==0){
                    pSet.add(new Position(row-1,col+1));
                }
                if(m_maze[row-1][col-1]==0){
                    pSet.add(new Position(row-1,col-1));
                }
            }
        }
        catch (Exception e){}
        ArrayList<AState> ans = new ArrayList<AState>(pSet.size());
        for (AState aS: pSet) {
            ans.add(aS);
        }
        return ans;
    } */

    public int[][] getM_maze() {
        return m_maze;
    }
}

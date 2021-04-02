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

    public Maze3D(int depth, int rows, int columns) {
        this.depth = depth;
        this.rows = rows;
        this.columns = columns;
        this.m_maze = new int[depth][rows][columns];
        this.allMazes = new Maze[depth];
        this.positions = new Position3D[depth][rows][columns];
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
        for (Maze m:allMazes) {
            m.print();
            System.out.println("------------------");
        }
    }

    public void setStartPosition(Position3D startPosition) {
        this.startPosition = startPosition;
    }

    public void setGoalPosition(Position3D goalPosition) {
        this.goalPosition = goalPosition;
    }

    public Maze[] getAllMazes() {
        return allMazes;
    }
}

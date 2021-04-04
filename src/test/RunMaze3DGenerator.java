package test;

import algorithms.maze3D.IMazeGenerator3D;
import algorithms.maze3D.Maze3D;
import algorithms.maze3D.MyMaze3DGenerator;

public class RunMaze3DGenerator {

    public  static void main(String[] args){
        IMazeGenerator3D img3 = new MyMaze3DGenerator();
        long aTime = System.currentTimeMillis();
        Maze3D m = img3.generate(3,5,10);
        System.out.println("3d Maze: ");
        m.print3D();
        long bTime = System.currentTimeMillis();
        System.out.println("total time: ");
        System.out.println(bTime-aTime);
        System.out.println("Start point: "+ m.getStartPosition());
        System.out.println("Goal point: "+ m.getGoalPosition());

    }
}

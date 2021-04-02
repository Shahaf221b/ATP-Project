package test;

import algorithms.maze3D.IMazeGenerator3D;
import algorithms.maze3D.Maze3D;
import algorithms.maze3D.MyMaze3DGenerator;

public class RunMaze3DGenerator {

    public  static void main(String[] args){
        IMazeGenerator3D img3 = new MyMaze3DGenerator();
        Maze3D m = img3.generate(3,4,5);
        System.out.println("3d Maze: ");
        m.print3D();

    }
}

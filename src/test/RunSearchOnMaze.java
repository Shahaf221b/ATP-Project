package test;

import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.*;

public class RunSearchOnMaze {
    public static void main(String[] args) {
        IMazeGenerator mg = new MyMazeGenerator();
        Maze maze = null;
        try {
            maze = mg.generate(-7, 10);
            SearchableMaze searchableMaze = new SearchableMaze(maze);
            solveProblem(searchableMaze, new BreadthFirstSearch());
            solveProblem(searchableMaze, new DepthFirstSearch());
            solveProblem(searchableMaze, new BestFirstSearch());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private static void solveProblem(ISearchable domain, ISearchingAlgorithm searcher){
//Solve a searching problem with a searcher
        try {
            Solution solution = searcher.solve(domain);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("algorithm -" + searcher.getName() + "nodes evaluated:" + searcher.getNumberOfNodesEvaluated());
//Printing Solution Path

        /*
        System.out.println("Solution path:");
        ArrayList<AState> solutionPath = solution.getSolutionPath();

        for (int i = 0; i < solutionPath.size(); i++) {
            System.out.println(i + "" + solutionPath.get(i));
        }

         */


    }
}
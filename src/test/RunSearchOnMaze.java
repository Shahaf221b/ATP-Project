package test;

import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.*;

public class RunSearchOnMaze {
    public static void main(String[] args) throws Exception {
        IMazeGenerator mg = new MyMazeGenerator();
        Maze maze = mg.generate(7, 10);
        SearchableMaze searchableMaze = new SearchableMaze(maze);
        solveProblem(searchableMaze, new BreadthFirstSearch());
        solveProblem(searchableMaze, new DepthFirstSearch());
        solveProblem(searchableMaze, new BestFirstSearch());

    }

    private static void solveProblem(ISearchable domain, ISearchingAlgorithm searcher) throws Exception {
//Solve a searching problem with a searcher
        Solution solution = searcher.solve(domain);
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
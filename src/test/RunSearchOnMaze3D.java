package test;
import algorithms.maze3D.IMaze3DGenerator;
import algorithms.maze3D.Maze3D;
import algorithms.maze3D.MyMaze3DGenerator;
import algorithms.maze3D.SearchableMaze3D;
import algorithms.search.*;

import java.util.ArrayList;

public class RunSearchOnMaze3D {
    public static void main(String[] args) throws Exception {
        IMaze3DGenerator img3 = new MyMaze3DGenerator();
//        Maze3D m = img3.generate(500,500,500);
        Maze3D m = img3.generate(100,100,100);

        SearchableMaze3D searchableMaze = new SearchableMaze3D(m);
        solveProblem(searchableMaze, new BreadthFirstSearch());
        solveProblem(searchableMaze, new DepthFirstSearch());
        solveProblem(searchableMaze, new BestFirstSearch());
    }
    private static void solveProblem(ISearchable domain, ISearchingAlgorithm
            searcher) throws Exception {
//Solve a searching problem with a searcher
        long aTime = System.currentTimeMillis();
        SearchableMaze3D m = (SearchableMaze3D) domain;
//        m.PRINT();
        Solution solution = searcher.solve(domain);
        System.out.println(String.format("'%s' algorithm - nodes evaluated: %s", searcher.getName(), searcher.getNumberOfNodesEvaluated()));
//Printing Solution Path
        long bTime = System.currentTimeMillis();
        System.out.println("total time: ");
        System.out.println(bTime-aTime);
        System.out.println("Solution path:");
        ArrayList<AState> solutionPath = solution.getSolutionPath();
        for (int i = 0; i < solutionPath.size(); i++) {
            System.out.println(String.format("%s. %s",i,solutionPath.get(i)));
        }

    }
}

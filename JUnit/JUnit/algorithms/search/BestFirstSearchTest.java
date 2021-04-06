package JUnit.algorithms.search;

import algorithms.mazeGenerators.*;
import algorithms.search.BestFirstSearch;
import algorithms.search.SearchableMaze;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BestFirstSearchTest {

    private static final BestFirstSearch BestFirstSearchInstance = new BestFirstSearch();
    private static final IMazeGenerator MazeGeneratorInstance = new MyMazeGenerator();

    private SearchableMaze prepareSearchableMaze(int rows, int columns) throws Exception {
        Maze maze = MazeGeneratorInstance.generate(rows, columns);
        try {
            return new SearchableMaze(maze);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void testNull() {
        Throwable exception = assertThrows(NullPointerException.class, () -> BestFirstSearchInstance.solve(null));

        String expectedMessage = "Domain Can't be null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void noSolution() throws Exception {
        IMazeGenerator mg = new EmptyMazeGenerator();
        Maze maze = mg.generate(5,5);
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++) {
                Position p = new Position(i, j);
                maze.setCell(p, 1);
            }
        }


        SearchableMaze searchableMaze = new SearchableMaze(maze);
        Throwable exception = assertThrows(Exception.class, () -> BestFirstSearchInstance.solve(searchableMaze));

        String expectedMessage = "no solution was found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

/*
    @Test
    public void testNegativeBounds() throws Exception {
        IMazeGenerator mg = new MyMazeGenerator();
        Maze maze = mg.generate(-7, 8);
        SearchableMaze searchableMaze = new SearchableMaze(maze);
        Throwable exception = assertThrows(Exception.class, () -> BestFirstSearchInstance.solve(searchableMaze));

        String expectedMessage = "Maze rows and columns should be non negative numbers.";
        String actualMessage = exception.getMessage();
//        System.out.printf(actualMessage);

        assertTrue(actualMessage.contains(expectedMessage));

    }

 */

}

package JUnit.algorithms.search;

import algorithms.mazeGenerators.*;
import algorithms.search.BestFirstSearch;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BestFirstSearchTest {

    private static final BestFirstSearch BestFirstSearchInstance = new BestFirstSearch();

    /**
     * checks if BestFirstSearch Instance throws correct exception when trying to
     * call solve method with null domain.
     */
    @Test
    public void testNull() {
        Throwable exception = assertThrows(NullPointerException.class, () -> BestFirstSearchInstance.solve(null));

        String expectedMessage = "Domain Can't be null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


    /**
     * checks if BestFirstSearch Instance throws correct exception
     * when a given maze has no solution
     *
     * @throws Exception:
     */
    @Test
    public void noSolution() throws Exception {
        IMazeGenerator mg = new EmptyMazeGenerator();
        Maze maze = mg.generate(5, 5);
        for (int i = 0; i < 5; i++) {
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

    /**
     * checks if the BestFirstSearch Instance prefers the cheaper move(diagonal)
     *
     * @throws Exception:
     */
    @Test
    public void testSelectedPath() throws Exception {
        IMazeGenerator mg = new EmptyMazeGenerator();
        Maze maze = mg.generate(2, 2);
        SearchableMaze searchableMaze = new SearchableMaze(maze);
        Solution s = BestFirstSearchInstance.solve(searchableMaze);

        assertEquals(s.getSolutionPath().size(), 2); //diagonal
    }

    /**
     * checks if BestFirstSearch Instance throws correct exception
     * when a maze doesn't have a goal Position
     *
     * @throws Exception:
     */
    @Test
    public void noGoalPosition() throws Exception {
        IMazeGenerator mg = new SimpleMazeGenerator();
        Maze maze = mg.generate(4, 5);
        maze.setCell(maze.getGoalPosition(), 1);
        SearchableMaze searchableMaze = new SearchableMaze(maze);
        Throwable exception = assertThrows(Exception.class, () -> BestFirstSearchInstance.solve(searchableMaze));

        String expectedMessage = "no solution was found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


}

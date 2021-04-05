package JUnit.algorithms.search;

import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.BestFirstSearch;
import algorithms.search.SearchableMaze;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BestFirstSearchTest {

    private static final BestFirstSearch BestFirstSearchInstance = new BestFirstSearch();
    private static final IMazeGenerator MazeGeneratorInstance = new MyMazeGenerator();

    private SearchableMaze prepareSearchableMaze(int rows, int columns) {
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
    public void testNegativeBounds() {

    }
}

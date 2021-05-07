package Server;

import algorithms.mazeGenerators.*;
import algorithms.search.*;

import java.io.*;
import java.nio.channels.Channels;
import java.util.*;


public class ServerStrategySolveSearchProblem implements IServerStrategy {
    private final String tempDirectoryPath = System.getProperty("java.io.tmpdir");
    static boolean firstRun = true;


    /**
     * @param inFromClient a Maze object.
     * @param outToClient  the specific client
     *                     this method solves the maze that was given by the client
     *                     and returns to that client a Solution object that holds
     *                     maze's solution.
     */
    @Override
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) throws Exception {
        //so it will stop when getting interrupt
        InputStream interruptInputStream = Channels.newInputStream(Channels.newChannel((inFromClient)));
        ObjectInputStream fromClient = new ObjectInputStream(interruptInputStream);

        Maze givenMaze = (Maze) fromClient.readObject();
        byte[] givenMazeByteArray = givenMaze.toByteArray();

        if (firstRun) {
            File mazeFile = new File(tempDirectoryPath + "\\byteArrayMazes.txt");
            mazeFile.createNewFile();
            File SolFile = new File(tempDirectoryPath + "\\solutionPaths.txt");
            SolFile.createNewFile();
            firstRun = false;
        }

        // helper function that checks for an existing solution
        Solution solution = mazeSolutionIsAvailable(givenMazeByteArray);
        // solution exist
        if (solution != null) {
            System.out.println("found solution in memory !");
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            toClient.flush();
            toClient.writeObject(solution);
            toClient.flush();
            return;
        }

        /** adding the new maze to the maze file **/


        FileWriter fwMaze = new FileWriter(tempDirectoryPath + "\\byteArrayMazes.txt", true);

        StringBuilder string = new StringBuilder();
        string.append(Arrays.toString(givenMazeByteArray));
        string.append("\n");
        fwMaze.write(string.toString());

        fwMaze.flush();
        fwMaze.close();


        /** adding the new solution for the solution file **/
        SearchableMaze searchableMaze = new SearchableMaze(givenMaze);

        Properties prop = Configurations.getInstance();
        String solverName = prop.getProperty("mazeSearchingAlgorithm"); // getting mazeSearchingAlgorithm

        ISearchingAlgorithm searcher = switch (solverName) {
            case "BestFirstSearch" -> new BestFirstSearch();
            case "BreadthFirstSearch" -> new BreadthFirstSearch();
            case "DepthFirstSearch" -> new DepthFirstSearch();
            default -> null;
        };

        assert searcher != null;
        solution = searcher.solve(searchableMaze);

        FileWriter fwSol = new FileWriter(tempDirectoryPath + "\\solutionPaths.txt", true);

        string = new StringBuilder();
        string.append(solution.getSolutionPath().toString());
        string.append("\n");
        fwSol.write(string.toString());
        fwSol.flush();
        fwSol.close();


        ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
        toClient.flush();
        toClient.writeObject(solution);
        toClient.flush();
    }


    private int findMazeIndex(byte[] givenMazeByteArray) throws Exception {
        int index = 0;

        Scanner scanner = new Scanner(new File(tempDirectoryPath + "\\byteArrayMazes.txt"));
        scanner.useDelimiter("\n"); // separator

        while (scanner.hasNext()) {
            String line = scanner.next();
            if (line.equals(Arrays.toString(givenMazeByteArray))) {
                return index;
            }
            index++;
        }
        return -1;
    }

    private Solution mazeSolutionIsAvailable(byte[] givenMazeByteArray) throws Exception {

        int foundIndex = findMazeIndex(givenMazeByteArray);
        if (foundIndex == -1) {
            return null;
        }

        /** maze exist- getting the solution **/
        String solPath = null;
        int solIndex = 0;

        Scanner scanner = new Scanner(new File(tempDirectoryPath + "\\solutionPaths.txt"));
        scanner.useDelimiter("\n");


        for (int i = 0; i < foundIndex + 1; i++) {
            String line = scanner.next();
            if (i == foundIndex) {
                solPath = line;
                break;
            }
        }

        if (solPath == null) {
            return null;
        }
        ArrayList<AState> solPathArray = fromStringToArrayList(solPath);
        if (solPathArray.size() == 0) {
            return null;
        }
        return new Solution(solPathArray);
    }

    private ArrayList<AState> fromStringToArrayList(String solPath) {
        ArrayList<AState> solutionPath = new ArrayList<>();
        String cleanPath = solPath.replaceAll("[{, }]", "");

        for (int i = 1; i < cleanPath.length() - 1; i += 2) {
            char row = cleanPath.charAt(i);
            char col = cleanPath.charAt(i + 1);
            solutionPath.add(new MazeState(row - '0', col - '0'));
        }
        return solutionPath;
    }
}

package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.EmptyMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.SimpleMazeGenerator;
import algorithms.search.*;

import java.io.*;
import java.nio.channels.Channels;
import java.util.Arrays;
import java.util.Properties;

public class ServerStrategySolveSearchProblem implements IServerStrategy{
    /**
     * @param inFromClient a Maze object.
     * @param outToClient the specific client
     * this method solves the maze that was given by the client
     * and returns to that client a Solution object that holds
     * maze's solution.
     */
    @Override
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) throws Exception {
        //so it will stop when getting interrupt
        InputStream interruptInputStream = Channels.newInputStream(Channels.newChannel((inFromClient)));
        ObjectInputStream fromClient = new ObjectInputStream(interruptInputStream);

        Maze givenMaze = (Maze) fromClient.readObject();
        SearchableMaze searchableMaze = new SearchableMaze(givenMaze);

        //TODO: change
        Properties prop = Configurations.getInstance();
        String solverName = prop.getProperty("mazeSearchingAlgorithm"); // getting mazeSearchingAlgorithm

        ISearchingAlgorithm searcher = null;
        if(solverName.equals("BestFirstSearch")) {
            searcher = new BestFirstSearch();
        }
        else if (solverName.equals("BreadthFirstSearch")){
            searcher = new BreadthFirstSearch();
        }
        else if(solverName.equals("DepthFirstSearch")){
            searcher = new DepthFirstSearch();
        }
        try {
            Solution solution = searcher.solve(searchableMaze);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            toClient.flush();
            toClient.writeObject(solution);
            toClient.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

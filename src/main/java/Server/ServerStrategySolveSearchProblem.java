package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.Maze;
import algorithms.search.BreadthFirstSearch;
import algorithms.search.ISearchingAlgorithm;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;

import java.io.*;
import java.nio.channels.Channels;
import java.util.Arrays;

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
        ISearchingAlgorithm searcher = new BreadthFirstSearch();
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

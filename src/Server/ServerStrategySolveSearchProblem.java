package Server;

import java.io.InputStream;
import java.io.OutputStream;

public class ServerStrategySolveSearchProblem implements IServerStrategy{
    /**
     * @param inFromClient a Maze object.
     * @param outToClient the specific client
     * this method solves the maze that was given by the client
     * and returns to that client a Solution object that holds
     * maze's solution.
     */
    @Override
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) {

    }
}

package test;

import Client.Client;
import IO.MyDecompressorInputStream;
import Server.Server;
import Server.ServerStrategyGenerateMaze;
import Server.ServerStrategySolveSearchProblem;
import Client.IClientStrategy;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.AState;
import algorithms.search.Solution;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import Server.Configurations;
public class testingClientClass {
    public static void main(String[] args) {

        Properties p = Configurations.getInstance(); // TODO: needs to be in server?

//Initializing servers
        Server mazeGeneratingServer = new Server(5400, 1000, new
                ServerStrategyGenerateMaze());

        Server solveSearchProblemServer = new Server(5401, 1000, new
                ServerStrategySolveSearchProblem());




//Starting servers

        solveSearchProblemServer.start();
        mazeGeneratingServer.start();

//Communicating with servers
        CommunicateWithServer_MazeGenerating();

        CommunicateWithServer_SolveSearchProblem();


//Stopping all servers
        try{
            Thread.sleep(5000);
        }
catch (Exception e){}
        mazeGeneratingServer.stop();

        solveSearchProblemServer.stop();

    }

    static IClientStrategy generateStrat = new IClientStrategy() {
        @Override
        public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
            try {
                ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                toServer.flush();
                int[] mazeDimensions = new int[]{16, 25};
                toServer.writeObject(mazeDimensions); //send maze dimensions to server
                toServer.flush();
                ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                byte[] compressedMaze = (byte[]) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
                //    System.out.println("compressed bytes: "+Arrays.toString(compressedMaze)); //TODO: remove
                InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                byte[] decompressedMaze = new byte[1000000 /*CHANGE SIZE ACCORDING TO YOU MAZE SIZE*/]; //allocating byte[] for the decompressed maze -
                is.read(decompressedMaze); //Fill decompressedMaze with bytes
                //    System.out.println(Arrays.toString(decompressedMaze)); //TODO remove
                Maze maze = new Maze(decompressedMaze);
                maze.print();

            } catch (Exception e) {
                e.getMessage();
            }
        }
    };

    static IClientStrategy solveStrat = new IClientStrategy() {
        @Override
        public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
            try {
                ObjectOutputStream toServer = new ObjectOutputStream(outToServer);

                toServer.flush();
                MyMazeGenerator mg = new MyMazeGenerator();

                Maze maze = mg.generate(50, 50);
                toServer.writeObject(maze); //send maze to server
                toServer.flush();
                ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                Solution mazeSolution = (Solution) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server

                //Print Maze Solution retrieved from the server
                ArrayList<AState> mazeSolutionSteps = mazeSolution.getSolutionPath();
                for (int i = 0; i < mazeSolutionSteps.size(); i++) {
                    System.out.println(String.format("%s. %s", i,mazeSolutionSteps.get(i).toString()));
                }
            } catch (Exception e) {
            }
        }
    };
    private static void CommunicateWithServer_MazeGenerating() {
        try {
            Client client1 = new Client(InetAddress.getLocalHost(), 5400, generateStrat);
            client1.communicateWithServer();
            Client client2 = new Client(InetAddress.getLocalHost(), 5400, generateStrat);
            client2.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
    private static void CommunicateWithServer_SolveSearchProblem() {
        try {
            Client client1 = new Client(InetAddress.getLocalHost(), 5401, solveStrat);
            client1.communicateWithServer();
            Client client2 = new Client(InetAddress.getLocalHost(), 5401, solveStrat);
            client2.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

}

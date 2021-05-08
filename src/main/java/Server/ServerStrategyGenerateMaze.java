package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.*;

import java.io.*;
import java.nio.channels.Channels;
import java.util.Arrays;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

public class ServerStrategyGenerateMaze implements IServerStrategy{
    /**
     * @param inFromClient int[] array of size 2, where:
     *                     array[0]=maze rows, array[1]=maze columns
     * @param outToClient the specific client
     * this method creates a maze according to given parameters,
     * compresse it by MyCompressorOutputStream and return a byte[]
     * that represent the maze to the specific client.
     */
    @Override
    public void ServerStrategy(InputStream inFromClient, OutputStream outToClient) throws IOException, ClassNotFoundException {
        //so it will stop when getting interrupt
        InputStream interruptInputStream = Channels.newInputStream(Channels.newChannel((inFromClient)));

       ObjectInputStream fromClient = new ObjectInputStream(interruptInputStream);
       ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();


        try {

           Thread.sleep(2000);
            int[] givenArray ;
            givenArray = (int[])fromClient.readObject();
            int rows=givenArray[0];
            int columns = givenArray[1];

            Properties prop = Configurations.getInstance();
            String mazeGeneratorName = prop.getProperty("mazeGeneratingAlgorithm"); // getting mazeGeneratingAlgorithm
            IMazeGenerator mazeGenerator = null;
            if(mazeGeneratorName.equals("SimpleMazeGenerator")) {
                mazeGenerator = new SimpleMazeGenerator();
            }
            else if (mazeGeneratorName.equals("EmptyMazeGenerator")){
                mazeGenerator = new EmptyMazeGenerator();
            }
            else if(mazeGeneratorName.equals("MyMazeGenerator")){
                mazeGenerator = new MyMazeGenerator();
            }

            //generate maze
            Maze newMaze = mazeGenerator.generate(rows,columns);

            ObjectOutputStream out = new ObjectOutputStream(outToClient);

            MyCompressorOutputStream myOut = new MyCompressorOutputStream(byteArrayOutputStream);
            byte[] mazeBytes = newMaze.toByteArray();
            myOut.write(mazeBytes);
            out.writeObject(byteArrayOutputStream.toByteArray());
            out.flush();

            fromClient.close();
            out.close();

        } catch (IOException e) {
//            e.printStackTrace(); ->implement in part c
        } catch (InterruptedException e) {
//            e.printStackTrace(); ->implement in part c
        } catch (Exception e) {
//            e.printStackTrace(); ->implement in part c
        }
    }


}

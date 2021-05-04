package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;

import java.io.*;
import java.nio.channels.Channels;
import java.util.Arrays;
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
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) throws IOException, ClassNotFoundException {
        //so it will stop when getting interrupt
        InputStream interruptInputStream = Channels.newInputStream(Channels.newChannel((inFromClient)));

       ObjectInputStream fromClient = new ObjectInputStream(interruptInputStream);
       ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();


        try {

           Thread.sleep(2000);
            int[] givenArray ;
            givenArray = (int[])fromClient.readObject();
//            System.out.println("given array: "+Arrays.toString(givenArray));//TODO: remove
            int rows=givenArray[0];
            int columns = givenArray[1];
            MyMazeGenerator mazeGenerator = new MyMazeGenerator(); //TODO: check
            Maze newMaze = mazeGenerator.generate(rows,columns);

            ObjectOutputStream out = new ObjectOutputStream(outToClient);

            MyCompressorOutputStream myOut = new MyCompressorOutputStream(byteArrayOutputStream);
            byte[] mazeBytes = newMaze.toByteArray();
//            System.out.println("maze bytes: "+Arrays.toString(mazeBytes)); //TODO: remove
            myOut.write(mazeBytes);
  //          System.out.println("bytearrayout: "+Arrays.toString(byteArrayOutputStream.toByteArray())); //TODO: remove
            out.writeObject(byteArrayOutputStream.toByteArray());
            out.flush();

            fromClient.close();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

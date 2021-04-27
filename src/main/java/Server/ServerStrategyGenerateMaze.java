package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.Maze;

import java.io.*;
import java.nio.channels.Channels;
import java.util.Arrays;

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
        System.out.println("***only for test, check if client got into applyStrategy in Generate***"); //TODO:remove

        //so it will stop when getting interrupt
//        InputStream interruptInputStream = Channels.newInputStream(Channels.newChannel((inFromClient)));

        ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
        System.out.println("***"+Arrays.toString((int[])fromClient.readObject())+"***");
//        int[] givenArray = (int[])fromClient.readObject();

        int[] givenArray = getArray(fromClient);
        System.out.println("***"+ Arrays.toString(givenArray)+"***");

        int rows=givenArray[0];
        int columns = givenArray[1];
        System.out.println("*** rows,columns: "+rows+","+columns+"***");

        System.out.println("***only for test, if got the input array(ServerStrategyGenerateMaze)***");//TODO:remove
        System.out.println("***given array: "+givenArray+" ***");//TODO:remove
        try {
            while (fromClient != null ) {
               Thread.sleep(2000);
                Maze newMaze = new Maze(rows,columns);

                System.out.println("only for test, the maze server created: ");
                newMaze.print();

                OutputStream out = new MyCompressorOutputStream(outToClient);
                out.write(newMaze.toByteArray());
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized int[] getArray(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        return (int[]) objectInputStream.readObject();
    }
}

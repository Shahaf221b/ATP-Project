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
        System.out.println("***only for test, check if client got into applyStrategy in Generate***"); //TODO:remove

        //so it will stop when getting interrupt
//        InputStream interruptInputStream = Channels.newInputStream(Channels.newChannel((inFromClient)));

       ObjectInputStream fromClient = new ObjectInputStream(inFromClient);

/*        if(fromClient != null){
            System.out.println("***input from client not null***");
        }
        System.out.println("*** getting the int[2] array***");
        Object obj = fromClient.readObject();
        System.out.println("***obj is not null***");
        int[] testArray = new int[2];
        testArray = (int[])obj;
        System.out.println("***"+Arrays.toString(testArray)+"***");
//        int[] givenArray = (int[])fromClient.readObject();

        int[] givenArray = getArray(fromClient);
        System.out.println("***"+ Arrays.toString(givenArray)+"***");

        int rows=givenArray[0];
        int columns = givenArray[1];
        System.out.println("*** rows,columns: "+rows+","+columns+"***");

        System.out.println("***only for test, if got the input array(ServerStrategyGenerateMaze)***");//TODO:remove
        System.out.println("***given array: "+givenArray+" ***");//TODO:remove*/
        int[] givenArray = new int[2];

        try {
            while (fromClient != null ) {
               Thread.sleep(2000);
//               givenArray = (int[]) fromClient.readObject();
                byte[] allBytes = new byte[0];
                fromClient.read(allBytes);
                System.out.println("*** input from client in byte[]: "+Arrays.toString(allBytes)+" ***");

                int rows=givenArray[0];
                int columns = givenArray[1];
                MyMazeGenerator mazeGenerator = new MyMazeGenerator();
                Maze newMaze = mazeGenerator.generate(rows,columns);

                System.out.println("only for test, the maze server created: ");
                newMaze.print();

                OutputStream out = new MyCompressorOutputStream(outToClient);
                out.write(newMaze.toByteArray());
                out.flush();

                fromClient.close();
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class GetArray extends TimerTask{
        private ObjectInputStream objectInputStream;
        private int[] ans;
        @Override
        public void run() {
            try {
                this.ans=(int[]) objectInputStream.readObject();
                System.out.println(ans);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        public int[] getAns(){
            return this.ans;
        }
    }

}

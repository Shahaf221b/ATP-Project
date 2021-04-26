package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.Maze;

import java.io.*;

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
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) throws IOException {
        BufferedReader fromClient = new BufferedReader(new InputStreamReader(inFromClient));
//        BufferedWriter toClient = new BufferedWriter(new PrintWriter(outToClient));

        String clientCommandInString = fromClient.readLine();
        int rows=0,columns=0,index=0;
        for (int i=0; i<clientCommandInString.length(); i++)
        {
            char c = (char)clientCommandInString.charAt(i);
            while(Character.isDigit(c)&&index==0) {
                rows += Character.getNumericValue(c);
            }

            while(Character.isDigit(c) && index==1){
                columns += Character.getNumericValue(c);
            }
            if(c==','){
                index=1;
            }
        }

        try {
            while (fromClient != null ) {
                Thread.sleep(2000);
                Maze newMaze = new Maze(rows,columns);
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
}

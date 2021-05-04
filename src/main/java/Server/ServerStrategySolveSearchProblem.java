package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.*;
import algorithms.search.*;

import java.io.*;
import java.nio.channels.Channels;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class ServerStrategySolveSearchProblem implements IServerStrategy{
    private String tempDirectoryPath =System.getProperty("java.io.tmpdir");
    private Path folderPath = Paths.get(tempDirectoryPath);
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
        byte[] givenMazeByteArray = givenMaze.toByteArray();
        Solution solution = mazeSolutionIsAvailable(givenMazeByteArray);
        if(solution != null){
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            toClient.flush();
            toClient.writeObject(solution);
            toClient.flush();
            return;
        }

        //save maze.toByteArray in file
        try {
            File f1 = new File(tempDirectoryPath+"\\byteArrayMazes.csv");
            if(!f1.exists()) {
                f1.createNewFile();
            }

            FileWriter fileWriter = new FileWriter(f1.getName(),true);
            BufferedWriter bw = new BufferedWriter(fileWriter);
            bw.write(givenMazeByteArray.toString());
            bw.close();
        } catch(IOException e){
            e.printStackTrace();
        }

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
            solution = searcher.solve(searchableMaze);

            //save solution in file
            try {
                File f1 = new File(tempDirectoryPath+"\\solutionPaths.csv");
                if(!f1.exists()) {
                    f1.createNewFile();
                }

                FileWriter fileWriter = new FileWriter(f1.getName(),true);
                BufferedWriter bw = new BufferedWriter(fileWriter);
                bw.write(solution.getSolutionPath().toString());
                bw.close();
            } catch(IOException e){
                e.printStackTrace();
            }

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

    private Solution mazeSolutionIsAvailable(byte[] givenMazeByteArray) throws Exception {
        List<String> linesMazes = null;
        List<String> linesSolutions = null;
        int index =0;
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(folderPath)) {
            for (Path path : directoryStream) {
                if(path.toString().equals("byteArrayMazes")){
                    try{
                        linesMazes = Files.readAllLines(folderPath.resolve(path.toString()));

                    }
                    catch (Exception e){
                        e.getMessage();
                    }
                }
            }
            if(linesMazes == null){
                return null;
            }
            for (String line: linesMazes
                 ) {
                if(line.equals(givenMazeByteArray.toString())){
                    index = linesMazes.indexOf(line);
                }
            }
        } catch (IOException ex) {
            System.err.println("Error reading files");
            ex.printStackTrace();
        }

        // get solution
        String solPath = null;
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(folderPath)) {
            for (Path path : directoryStream) {
                if(path.toString().equals("solutionPaths")){
                    try{
                        linesSolutions = Files.readAllLines(folderPath.resolve(path.toString()));

                    }
                    catch (Exception e){
                        e.getMessage();
                    }
                }
            }
        } catch (IOException ex) {
            System.err.println("Error reading files");
            ex.printStackTrace();
        }
        solPath = linesSolutions.get(index);
        if(solPath!=null){
            ArrayList<AState> solPathArray = fromStringToArrayList(solPath);
            if(solPathArray.size()==0){
                return null;
            }
            Solution sol = new Solution(solPathArray);
            return sol;
        }

        return null;
    }

    private ArrayList<AState> fromStringToArrayList(String solPath) {

        ArrayList<AState> solutionPath = new ArrayList<>();
        //loop, dont include the brackets
        int i=1;
        String row="",col="";
        int rowIndex,colIndex;
        while(i<solPath.length()-1) {
            row="";
            col="";
            if(solPath.charAt(i)== '{'){
                i++;
                while(solPath.charAt(i) != ','){
                    row += solPath.charAt(i);
                    i++;
                }
                rowIndex = Integer.parseInt(row);
                i++;
                while (solPath.charAt(i) != '}'){
                    col += solPath.charAt(i);
                    i++;
                }
                colIndex = Integer.parseInt(col);
                solutionPath.add(new MazeState(rowIndex,colIndex));
                i++;
            }
        }
        return solutionPath;
    }
}

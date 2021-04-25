package test;

import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import IO.SimpleCompressorOutputStream;
import IO.SimpleDecompressorInputStream;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import java.io.*;
import java.util.Arrays;
import java.util.List;

public class RunCompressDecompressMaze {
    public static void main(String[] args) throws Exception {
        String mazeFileName = "savedMaze.maze";
        AMazeGenerator mazeGenerator = new MyMazeGenerator();
        Maze maze = mazeGenerator.generate(10, 10); //Generate new maze
        try {
// save maze to a file
            OutputStream out = new SimpleCompressorOutputStream(new
                    FileOutputStream(mazeFileName));
            System.out.println("source maze:");
            maze.print();
            String list = "";
            for (byte b: maze.toByteArray()
                 ) {
               // System.out.println(b);
                list +=b;
            }
            System.out.println("maze toByteArray: "+list);

            out.write(maze.toByteArray());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte savedMazeBytes[] = new byte[0];
        try {
//read maze from file
            InputStream in = new SimpleDecompressorInputStream(new
                    FileInputStream(mazeFileName));
            savedMazeBytes = new byte[maze.toByteArray().length];

            int num =in.read(savedMazeBytes);
            System.out.println("num of bytes after decompressor: "+num);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String list2 = "";
        for (byte b: savedMazeBytes
        ) {
            // System.out.println(b);
            list2 +=b;
        }
        System.out.println("saved maze bytes: "+list2);

        Maze loadedMaze = new Maze(savedMazeBytes);
        System.out.println("loaded maze:");
        loadedMaze.print();
        boolean areMazesEquals =
                Arrays.equals(loadedMaze.toByteArray(),maze.toByteArray());
        System.out.println(String.format("Mazes equal: %s",areMazesEquals));
//maze should be equal to loadedMaze
/*        int i = 0;
        byte j = (byte) i;
        System.out.println(j);
        i = j&255;
        System.out.println(i);*/
    }
}

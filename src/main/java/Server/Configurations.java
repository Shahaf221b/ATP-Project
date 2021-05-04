package Server;

import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.BreadthFirstSearch;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class Configurations {
    private static Configurations configurations =null;
    private static Properties config;

    private Configurations(){
        try{
            OutputStream output = new FileOutputStream("projectResources/config.properties");
            //singleton
            config = new Properties();
            config.put("threadPoolSize","1");
            config.put("mazeGeneratingAlgorithm", "MyMazeGenerator");
            config.put("mazeSearchingAlgorithm","BreadthFirstSearch");
            //TODO: check if more properties needed

            // save properties to project root folder
            config.store(output, null);
        }
        catch (IOException io) {
            io.printStackTrace();
        }
    }

    public static Properties getInstance(){
        if(configurations==null){
            configurations = new Configurations();
        }
        return config;
    }




}

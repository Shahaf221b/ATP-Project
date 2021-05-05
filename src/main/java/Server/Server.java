package Server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private int port;
    private int listeningIntervalMS;
    private IServerStrategy strategy;
    private volatile boolean stop;
//    private final Logger LOG = LogManager.getLogger(); //Log4j2
    private ExecutorService threadPool;

    public Server(int port, int listeningIntervalMS, IServerStrategy Strategy) {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        strategy = Strategy;
        Properties prop = Configurations.getInstance();
        String poolSize = prop.getProperty("threadPoolSize");
        int i = Integer.parseInt(poolSize);
        this.threadPool = Executors.newFixedThreadPool(i);
    }

    public void start(){
        new Thread(()-> {
            runServer();
        }).start();
    }
    public void runServer(){
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningIntervalMS);
//            LOG.info("Starting server at port = " + port);
            System.out.println("Starting server at port = " + port);

            while (!stop) {
                try {
                    Thread.sleep(1000);
                    Socket clientSocket = serverSocket.accept();

//                    LOG.info("Client accepted: " + clientSocket.toString());
//                    System.out.println("Client accepted: " + clientSocket.toString());

                    //wait to client input??
 //                   Thread.sleep(10000);

                    // This thread will handle the new Client
                    threadPool.execute(()->{
                        handleClient(clientSocket);
                    });


/*                    new Thread(() -> {
                        handleClient(clientSocket);
                    }).start();*/

                } catch (SocketTimeoutException e){
//                    LOG.debug("Socket timeout");
//                    System.out.println("Socket timeout");
                }
            }
            threadPool.shutdown();
        } catch (Exception e) {
//            LOG.error("IOException", e);
            System.out.println("exception at server"); //TODO:remove
            System.out.println(e.getMessage());
        }
    }

    private void handleClient(Socket clientSocket) {
        try {
//            Thread.sleep(9000);

            strategy.applyStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
//            LOG.info("Done handling client: " + clientSocket.toString());
//            System.out.println("Done handling client: " + clientSocket.toString());
            clientSocket.close();
        } catch (Exception e){
//            LOG.error("IOException", e);
            System.out.println("IOException"+e.getMessage());
        }
    }

    public void stop(){
//        LOG.info("Stopping server...");
//        System.out.println("Stopping server...");
        stop = true;
    }
}

package Server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
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
        this.threadPool = Executors.newFixedThreadPool(3);
    }

    public void start(){
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningIntervalMS);
//            LOG.info("Starting server at port = " + port);
            System.out.println("Starting server at port = " + port);

            while (!stop) {
                try {
                    Thread.sleep(5000);
                    Socket clientSocket = serverSocket.accept();
//                    LOG.info("Client accepted: " + clientSocket.toString());
                    System.out.println("Client accepted: " + clientSocket.toString());

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
            System.out.println(e.getMessage());
        }
    }

    private void handleClient(Socket clientSocket) {
        System.out.println("***only for test, check if client got into handleClient***"); //TODO:remove
        try {
            strategy.applyStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
//            LOG.info("Done handling client: " + clientSocket.toString());
            System.out.println("Done handling client: " + clientSocket.toString());
            clientSocket.close();
        } catch (Exception e){
//            LOG.error("IOException", e);
            System.out.println("IOException"+e.getMessage());
        }
    }

    public void stop(){
//        LOG.info("Stopping server...");
        System.out.println("Stopping server...");
        stop = true;
    }
}

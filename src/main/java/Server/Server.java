package Server;

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
    private Thread thread;

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
        thread = new Thread(()-> runServer());
        thread.start();
    }

    public void joinTermination(){
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void runServer(){
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningIntervalMS);
//            LOG.info("Starting server at port = " + port); ->implement in part c


            while (!stop) {
                try {
                    Thread.sleep(1000);
                    Socket clientSocket = serverSocket.accept();

//                    LOG.info("Client accepted: " + clientSocket.toString());
//                    System.out.println("Client accepted: " + clientSocket.toString()); ->implement in part c

                    // This thread will handle the new Client
                    threadPool.execute(()->{
                        handleClient(clientSocket);
                    });


                } catch (SocketTimeoutException e){
//                    LOG.debug("Socket timeout");
//                    System.out.println("Socket timeout"); ->implement in part c
                }
            }
            threadPool.shutdown();
        } catch (Exception e) {
//            LOG.error("IOException", e); ->implement in part c
 //           System.out.println(e.getMessage());
        }
    }

    private void handleClient(Socket clientSocket) {
        try {
//            Thread.sleep(9000);

            strategy.ServerStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
//            LOG.info("Done handling client: " + clientSocket.toString());
//            System.out.println("Done handling client: " + clientSocket.toString());
            clientSocket.close();
        } catch (Exception e){
//            LOG.error("IOException", e);
//            System.out.println("IOException"+e.getMessage()); ->implement in part c
        }
    }

    public void stop(){
//        LOG.info("Stopping server...");
//        System.out.println("Stopping server...");
        stop = true;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

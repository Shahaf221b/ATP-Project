package Client;


import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;


public class Client {
    private InetAddress serverIP;
    private int serverPort;
    private IClientStrategy strategy;

    public Client(InetAddress serverIP, int serverPort, IClientStrategy Strategy) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        strategy = Strategy;
    }

    public void start(){
        try(Socket serverSocket = new Socket(serverIP, serverPort)){
            strategy.clientStrategy(serverSocket.getInputStream(), serverSocket.getOutputStream());
        } catch (IOException e) {
 //           e.printStackTrace(); ->implement in part c
        }
    }

    public void communicateWithServer() {
        try{
            Thread.sleep(1000);
            start();
        }
    catch(Exception e){
 //      e.getMessage(); ->implement in part c
    }

    }
}

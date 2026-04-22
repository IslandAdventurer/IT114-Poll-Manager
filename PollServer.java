import java.net.*;
import java.io.*;

public class PollServer {
    public static void main(String[] args) {
        PollManager manager = new PollManager()
        try {
            ServerSocket serverSocket = new ServerSocket(9001);
            System.out.println("The server started.");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("The client connected.");
                ClientHandler handler = new ClientHandler)clientSocket, manager);
                Thread thread = new Thread(handler);
                thread.start();
            }
        
        } catch (IOException e) {
            System.out.println("There was a network error." + e.getMessage());
        }
    }

}

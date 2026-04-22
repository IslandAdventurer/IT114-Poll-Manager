import java.net.*;
import java.io.*;

public class PollServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9001);
            System.out.println("The server on port 9001 started.");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("The client is connected.");
            }
        
        } catch (IOException e) {
            System.out.println("There was a network error." + e.getMessage());
        }
    }

}

import java.net.*;
import java.io.*;

public class PollServer {
    public static void main(String[] args) {
        // Create the manager to handle votes and logging
        PollManager manager = new PollManager();
        
        // Declare the socket outside here so we can close it at the bottom to prevent leak
        ServerSocket serverSocket = null;

        try {
            // Initialize the server on port 9001
            serverSocket = new ServerSocket(9001);
            System.out.println("Server is running on port 9001...");
            manager.logActivity("SERVER START: Listening on port 9001");

            // Standard loop to accept clients
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client joined!");
                manager.logActivity("New client connected.");

                // Start a new thread for each client
                ClientHandler handler = new ClientHandler(clientSocket, manager);
                Thread thread = new Thread(handler);
                thread.start();
            }

        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        } finally {
            // This is the simple way to close the door when finished
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                    System.out.println("Server socket closed.");
                }
            } catch (IOException e) {
                System.out.println("Error during close.");
            }
        }
    }
}
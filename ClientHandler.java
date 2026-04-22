import java.net.*;
import java.io.*;

public class ClientHandler implements Runnable {
    private Socket socket;
    private PollManager manager;

    public ClientHandler(Socket socket, PollManager manager) {
        this.socket = socket;
        this.manager = manager;
    }

    @Override
    public void run() {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
            );

            manager.addclient(out);

            out.println("Welcome to the NJ College Poll!");
            out.println("Type Essex, NJIT, Montclair, or Rutgers to vote:");

            String line;

            while ((line = in.readLine()) != null) {

                manager.castvote(line);

                // live update message
                manager.broadcast("LIVE UPDATE: Someone just voted for " + line);

                // send simple results to all clients
                manager.broadcast(manager.getResults());

                // show live results in cmd
                System.out.println(manager.getResults());

                out.println("Thanks! Your vote for " + line + " is recorded.");
            }

        } catch (IOException e) {
            manager.logActivity("A client has disconnected.");
            System.out.println("A client left the server.");
        }
    }
}
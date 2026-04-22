import java.util.*;
import java.io.*;
import java.time.LocalDateTime; 

public class PollManager {

    // Stores each college and how many votes it has
    private Map<String, Integer> votes = new HashMap<>();

    // Keeps track of all connected clients so we can send them messages
    private List<PrintWriter> clients = new ArrayList<>();

    public PollManager() {
        // Setting up the starting vote counts for each school
        votes.put("Essex", 0);
        votes.put("NJIT", 0);
        votes.put("Montclair", 0);
        votes.put("Rutgers", 0);
    }

    // Logs actions into a file (deliverable)
    public synchronized void logActivity(String event) {
        try (PrintWriter logFile = new PrintWriter(new FileWriter("server.log", true))) {
            // Adds timestamp so we know when each event happened
            logFile.println(LocalDateTime.now() + ": " + event);
        } catch (IOException e) {
            // If logging fails, just print error to console
            System.out.println("Logging error: " + e.getMessage());
        }
    }

    // Handles voting logic and updates the count for a college
    public synchronized void castvote(String college) {
        // Make sure the vote is for a valid college
        if (votes.containsKey(college)) {
            int currentVotes = votes.get(college);

            // Increase vote count by 1
            votes.put(college, currentVotes + 1);

            // Save this action in the log file
            logActivity("Vote cast for: " + college);
        }
    }

    // Adds a new connected client so we can send them updates
    public synchronized void addclient(PrintWriter out) {
        clients.add(out);

        // Log that someone joined the server
        logActivity("A new client connection was added.");
    }

    // Sends a message to every connected client
    public synchronized void broadcast(String message) {
        for (PrintWriter client : clients) {
            client.println(message);
        }
    }

    // Builds a simple string showing all current vote counts
    public synchronized String getResults() {
        String result = "";

        // Loop through each college and append its vote count
        for (String key : votes.keySet()) {
            result += key + ": " + votes.get(key) + "  ";
        }

        return result;
    }
}
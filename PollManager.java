import java.util.*;
import java.io.*;

public class PollManager {
    private Map<String, Integer> votes = new HashMap<>();
    private List<PrintWriter> clients = new ArrayList<>();

    public PollManager() {
    votes.put("Essex", 0);
    votes.put("NJIT", 0);
    votes.put("Montclair", 0);
    votes.put("Rutgers", 0);
    }

    public synchronized void castvote(String college) {
        if (votes.containsKey(college)) {
            int currentVotes = votes.get(college);
            votes.put(college, currentVotes + 1);
        }
    }

    public synchronized void addclient(PrintWriter out) {
        clients.add(out);
    }

    public synchronized void broadcast(String message) {
        for (PrintWriter client : clients) {
            client.println(message);
        }
    }
}


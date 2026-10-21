package com.training;

/**
 * You are building a simple social networking service that allows users to register, connect as friends, and search for connections
 * 
 * Implement following methods
 * void addUser(String user)
 * List<String> getFriendShips(String user)
 * void addFriendship(String, user1, String user2)
 * List<String> findShorttestConnection(String user1, String user2)
 * 
 * Functionality required
 *  Add new users
 *  Establish mutual friendships
 *  List all friends of a user
 *  Find the shortest path of connections between two users using BFS
 * 
 * Example output
 *  Berek's friends_[Anna, Celina, Dariusz]
 *  Shortest path from Anna to Ela: [Anna, Bartek, Dariusz, Ela]
 * 
 * Bonus implementation
 *  Use HashMap<String, Set<String>> to store connections
 *  Implement BFS (Breadth-First Search) to find shortest paths
 */

import java.util.*;

public class SocialNetwork {

    interface SocialNetworkService {
        void addUser(String user);
        List<String> getFriendships(String user);
        void addFriendship(String user1, String user2);
        List<String> findShortestConnection(String user1, String user2);
    }

    static class SimpleSocialNetworkService implements SocialNetworkService {
        private final Map<String, Set<String>> friendships = new HashMap<>();

        /**
         * Registers a new user in the system
         * If the user already exists, do nothing
         */
        @Override
        public void addUser(String user) {
            friendships.putIfAbsent(user, new HashSet<>());
        }

        /**
         * Returns a list of the given user's direct friends
         * If the user has no friends or does not exist, return an empty list
         */
        @Override
        public List<String> getFriendships(String user) {
            return new ArrayList<>(friendships.getOrDefault(user, Set.of()));
        }

        /**
         * Establishes a **mutual** friendship between user1 and user2
         * If either user doesn't exist, ignore the request
         */
        @Override
        public void addFriendship(String user1, String user2) {
            if (!friendships.containsKey(user1) || !friendships.containsKey(user2)) {
                throw new IllegalArgumentException("User not found");
            }
            friendships.get(user1).add(user2);
            friendships.get(user2).add(user1);
        }

        /**
         * Returns the ***shortest path** between two users, as a list of usernames,
         * For example: [Anna, Bartek, Dariusz, Ela]
         * If not path exists or either user does not exist, return an empty list
         */
        @Override
        public List<String> findShortestConnection(String user1, String user2) {

            if (!friendships.containsKey(user1) || !friendships.containsKey(user2)) return List.of();
            if (user1.equals(user2)) return List.of(user1);

            Queue<List<String>> queue = new LinkedList<>();
            Set<String> visited = new HashSet<>();
            queue.add(List.of(user1));

            while (!queue.isEmpty()) {
                List<String> path = queue.poll();
                String last = path.get(path.size() - 1);

                if (visited.contains(last)) continue;
                visited.add(last);

                for (String friend : friendships.getOrDefault(last, Set.of())) {
                    List<String> newPath = new ArrayList<>(path);
                    newPath.add(friend);
                    if (friend.equals(user2)) return newPath;
                    queue.add(newPath);
                }
            }

            return List.of();
        }
    }

    /**
     * 
     * Assume usernmaes are unique and non-nul. Your implementation should be case-sensitive
     */

    public static void main(String[] args) {
        SocialNetworkService socialNetwork = new SimpleSocialNetworkService();

        socialNetwork.addUser("Anna");
        socialNetwork.addUser("Bartek");
        socialNetwork.addUser("Celina");
        socialNetwork.addUser("Dariusz");
        socialNetwork.addUser("Ela");

        socialNetwork.addFriendship("Anna", "Bartek");
        socialNetwork.addFriendship("Bartek", "Celina");
        socialNetwork.addFriendship("Celina", "Dariusz");
        socialNetwork.addFriendship("Dariusz", "Ela");
        socialNetwork.addFriendship("Bartek", "Dariusz");

        System.out.println("Bartek's friends: " + socialNetwork.getFriendships("Bartek"));
        // Output: Bartek's friends: [Anna, Celina, Dariusz]

        System.out.println("Shortest path from Anna to Ela: " +
                socialNetwork.findShortestConnection("Anna", "Ela"));
        // Output: Shortest path from Anna to Ela: [Anna, Bartek, Dariusz, Ela]
    }
}


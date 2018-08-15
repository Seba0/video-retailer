package com.sda.video.clients;

import com.sda.video.clients.history.History;
import com.sda.video.clients.history.HistoryType;
import com.sda.video.datastore.CSVDataStore;
import com.sda.video.movies.Movie;
import com.sda.video.prices.Price;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Class for managing Clients
 */
public enum ClientManager {
    INSTANCE;

    // List of clients and rent movies in prices
    private final Map<Client, Set<Price>> clientList;

    // List of clients history
    private final List<History> clientHistory;

    // Strore for Clients dataClient
    private static CSVDataStore<Client> storeClients = null;

    // Strore for Clients history
    private static CSVDataStore<History> storeHistory = null;

    /**
     * Private constructor for init ClientManager
     */
    ClientManager() {
        // Create new HashMap
        clientList = new HashMap<>();
        // Create new ArrayList
        clientHistory = new ArrayList<>();
        // Load data
        load();
    }

    /**
     * Load Client data from store
     */
    private void load() {
        try {
            // Get Clients store
            CSVDataStore<Client> storeClients = getClientsStore();

            // Read all clients
            Set<Client> clients = storeClients.read();

            // Clear Clients list
            clientList.clear();
            //Add all clients to list of clients
            for (Client client : clients) {
                clientList.put(client, new HashSet<Price>());
            }

            // Get History store
            CSVDataStore<History> storeHistory = getHistoryStore();

            // Clear History list
            clientHistory.clear();
            // Add all History into list
            clientHistory.addAll(storeHistory.read());


            //TODO Add restore rent movies
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save Client data to store
     */
    private void save() {
        try {
            // Get store
            CSVDataStore<Client> store = getClientsStore();

            // Write all clients
            store.write(getClients());

            // Get History store
            CSVDataStore<History> storeHistory = getHistoryStore();

            storeHistory.write(new HashSet<>(clientHistory));
            //TODO Add store of rent movies
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Return Client data store if necessary create it
     *
     * @return Client data store
     */
    private static CSVDataStore<Client> getClientsStore() {
        // Check if store is not created
        if (storeClients == null) {
            // Select file for data stroe of Client
            File file = new File("clients.csv");

            // Create new Client store
            storeClients = new CSVDataStore<>(file, new Client());
        }
        // Return store
        return storeClients;
    }

    /**
     * Return Client history data store if necessary create it
     *
     * @return Client history data store
     */
    private static CSVDataStore<History> getHistoryStore() {
        // Check if store is not created
        if (storeHistory == null) {
            // Select file for data stroe of Client
            File file = new File("clients.csv");

            // Create new Client store
            storeHistory = new CSVDataStore<>(file, new History());
        }
        // Return store
        return storeHistory;
    }

    /**
     * Method for rent movie
     *
     * @param price  movie in price for rent
     * @param client Client who wants to rent a movie
     * @return true if success else false
     */
    public boolean rentFilm(Price price, Client client) {
        // Get Movie from Price
        Movie movie = price.getMovie();

        // Check if movie is rent
        if (movie.isRent()) {
            // Return false - movie cannot be rent
            return false;
        }
        // Get list of movies rent by client
        Set<Price> rent = clientList.get(client);
        // Check if client not have list of movies
        if (rent == null) {
            // Create new movies list for store rent movies by client
            rent = new HashSet<>();

            // Put list into client list
            clientList.put(client, rent);
        }
        // Set date of rent in movie
        movie.setRentedDate(new Date());

        // Append information into history list
        History history = new History();
        history.setClientId(client.getId());
        history.setMovieId(movie.getId());
        history.setPrice(price.getPrice().getPrice());
        history.setType(HistoryType.RETURN);
        clientHistory.add(history);

        // Save date
        save();
        // Return information about success of rent movie
        return rent.add(price);
    }

    /**
     * Return of rent movie
     *
     * @param price movie in price to be given back
     * @return true if success else false
     */
    public boolean returnMovie(Price price) {
        // Find client who rent movie
        Client client = findClient(price);
        if (client != null) {

            // Remove movie from client list
            clientList.get(client).remove(price);

            Movie movie = price.getMovie();
            // Clear date of rent from movie
            movie.setRentedDate(null);


            // Append information into history list
            History history = new History();
            history.setClientId(client.getId());
            history.setMovieId(movie.getId());
            history.setPrice(price.getPrice().getPrice());
            history.setType(HistoryType.RETURN);
            clientHistory.add(history);

            // Save date
            save();

            // Return success
            return true;
        }
        // Return failure
        return false;
    }

    /**
     * Return movies holding for longer than given days
     *
     * @param days number of days for search
     * @return clients with collections of movies
     */
    public Map<Client, Set<Price>> overDueMovies(int days) {
        long daysInUnix = ((long) days) * 24 * 60 * 60 * 100;
        Date date = new Date(System.currentTimeMillis() - daysInUnix);

        Map<Client, Set<Price>> out = new HashMap<>();

        for (Map.Entry<Client, Set<Price>> films : clientList.entrySet()) {
            Client client = films.getKey();
            Set<Price> prices = films.getValue();
            for (Price price : prices) {
                Date rentDate = price.getMovie().getRentedDate();
                if (rentDate.before(date)) {
                    Set<Price> outPrices = out.get(client);
                    if (outPrices == null) {
                        outPrices = new HashSet<>();
                        out.put(client, outPrices);
                    }
                    outPrices.add(price);
                }
            }
        }
        return out;
    }

    /**
     * List of clients
     *
     * @return Set containing all clients
     */
    public Set<Client> getClients() {
        // Return clients list
        return clientList.keySet();
    }

    /**
     * History of rent and return of movies
     *
     * @return history
     */
    public List<History> getHistory() {
        return clientHistory;
    }

    private Client findClient(Price price) {
        for (Client client : clientList.keySet()) {
            Set<Price> prices = clientList.get(client);
            for (Price price1 : prices) {
                if (price1.equals(price)) {
                    System.out.println(client);
                    return client;
                }
            }
        }
        return null;
    }
}



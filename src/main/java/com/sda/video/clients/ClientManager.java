package com.sda.video.clients;

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
    private final Map<Clients, Set<Price>> clientList;

    // Strore for Clients data
    private static CSVDataStore<Clients> store = null;

    /**
     * Private constructor for init ClientManager
     */
    ClientManager() {
        // Create new HashMap
        clientList = new HashMap<>();
        // Load data
        load();
    }

    /**
     * Load Clients data from store
     */
    private void load() {
        try {
            // Get store
            CSVDataStore<Clients> store = getStore();

            // Read all clients
            Set<Clients> clients = store.read();

            //Add all clients to list of clients
            for (Clients client : clients) {
                clientList.put(client, new HashSet<Price>());
            }
            //TODO Add restore rent movies
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save Clients data to store
     */
    private void save() {
        try {
            // Get store
            CSVDataStore<Clients> store = getStore();

            // Write all clients
            store.write(getClients());

            //TODO Add store of rent movies
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Return Clients data store if necessary create it
     *
     * @return Clients data store
     */
    private static CSVDataStore<Clients> getStore() {
        // Check if store is not created
        if (store == null) {
            // Select file for data stroe of Clients
            File file = new File("clients.csv");

            // Create new Client store
            store = new CSVDataStore<Clients>(file, new Clients());
        }
        // Return store
        return store;
    }

    /**
     * Method for rent movie
     *
     * @param price  movie in price for rent
     * @param client Client who wants to rent a movie
     * @return true if success else false
     */
    public boolean rentFilm(Price price, Clients client) {
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
        Clients clients = findClient(price);
        if (clients != null) {

            // Remove movie from client list
            clientList.get(clients).remove(price);

            // Clear date of rent from movie
            price.getMovie().setRentedDate(null);

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
    public Map<Clients, Set<Price>> overDueMovies(int days) {
        long daysInUnix = ((long) days) * 24 * 60 * 60 * 100;
        Date date = new Date(System.currentTimeMillis() - daysInUnix);

        Map<Clients, Set<Price>> out = new HashMap<>();

        for (Map.Entry<Clients, Set<Price>> films : clientList.entrySet()) {
            Clients clients = films.getKey();
            Set<Price> prices = films.getValue();
            for (Price price : prices) {
                Date rentDate = price.getMovie().getRentedDate();
                if (rentDate.before(date)) {
                    Set<Price> outPrices = out.get(clients);
                    if (outPrices == null) {
                        outPrices = new HashSet<>();
                        out.put(clients, outPrices);
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
    public Set<Clients> getClients() {
        // Return clients list
        return clientList.keySet();
    }

    private Clients findClient(Price price) {

        for (Clients clients : clientList.keySet()) {


            Set<Price> prices = clientList.get(clients);

            for (Price price1 : prices) {

                if (price1.equals(price)) {

                    System.out.println(clients);
                    return clients;


                }
            }


        }

        return null;

    }
}



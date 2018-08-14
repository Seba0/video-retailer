package com.sda.video.movies;

import com.sda.video.datastore.CSVDataStore;
import java.io.File;
import java.io.IOException;
import java.util.*;

public enum MovieManager {
    INSTANCE;

    // List of movies
    private final Set<Movie> movieList;

    // Store for Movie data
    private static CSVDataStore<Movie> store = null;

    /**
     * Private constructor for init MovieManager
     */
    MovieManager() {
        // Create new HashMap
        movieList = new HashSet<>();
        // Load data
        load();
    }

    /**
     * Load Clients data from store
     */
    private void load() {
        try {
            // Get store
            CSVDataStore<Movie> store = getStore();

            // Read all clients
            Set<Movie> movies = store.read();

            //Add all clients to list of clients
            for (Movie movie : movies) {
                movieList.add(movie);
            }
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
            CSVDataStore<Movie> store = getStore();

            // Write all clients
            store.write(movieList);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Return Movie data store if necessary create it
     *
     * @return Movie data store
     */
    private static CSVDataStore<Movie> getStore() {
        // Check if store is not created
        if (store == null) {
            // Select file for data stroe of Clients
            File file = new File("movies.csv");

            // Create new Client store
            store = new CSVDataStore<Movie>(file, new Movie());
        }
        // Return store
        return store;
    }
}

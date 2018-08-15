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
     * Load Client data from store
     */
    private void load() {
        try {
            // Get store
            CSVDataStore<Movie> store = getStore();

            // Read all movies
            Set<Movie> movies = store.read();

            //Add all movies to list of movies
            for (Movie movie : movies) {
                movieList.add(movie);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save Movie data to store
     */
    private void save() {
        try {
            // Get store
            CSVDataStore<Movie> store = getStore();

            // Write all movies
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
            // Select file for data store of Movie
            File file = new File("movies.csv");

            // Create new Movie store
            store = new CSVDataStore<Movie>(file, new Movie());
        }
        // Return store
        return store;
    }

    /**
     * Add Movie to movieList
     */
    public void addMovie(Movie movie){
        movieList.add(movie);
    }

    /**
     * Remove Movie from movieList
     */
    public void removeMovie(Movie movie){
        movieList.remove(movie);
    }

    /**
     * Searches and returns movies according to given parameters
     *
     * each of the following parameters can be null:
     * @param title
     * @param director
     * @param yearProduction
     * @param gener
     *
     * @return the list of found movies
     */
    public Set<Movie> searchMovie(String title, String director, int yearProduction, Gener gener){
        Set<Movie> searchMovie = new HashSet<>();
        for (Movie list : movieList){
            if (title.equals(list.getTitle())){
                searchMovie.add(list);
            }
            if (director.equals(list.getDirector())){
                searchMovie.add(list);
            }
            if (yearProduction == list.getYearProduction()){
                searchMovie.add(list);
            }
            if (gener.equals(list.getGener())){
                searchMovie.add(list);
            }
        }
        return searchMovie;
    }
}

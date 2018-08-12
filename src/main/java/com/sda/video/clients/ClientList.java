package com.sda.video.clients;

import com.sda.video.movies.Movie;
import com.sda.video.prices.Price;

import java.util.*;

public enum ClientList {
    INSTANCE;

    private final Map<Clients, Set<Price>> clientList;

    ClientList() {
        clientList = new HashMap<>();
    }

    public boolean rentFilm(Price price, Clients client) {
        Movie movie = price.getMovie();
        if (movie.isRent()) {
            return false;
        }
        Set<Price> rent = clientList.get(client);
        if (rent == null) {
            rent = new HashSet<>();
            clientList.put(client, rent);

        }
        movie.setRentedDate(new Date());
        return rent.add(price);
    }

    public boolean returnMovie(Price price) {
        Clients clients = findClient(price);
        if (clients != null) {
            clientList.get(clients).remove(price);
            price.getMovie().setRentedDate(null);
            return true;
        }
        return false;
    }

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

    public Set<Clients> getClients() {
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



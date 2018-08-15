package com.sda.video.clients.history;

import com.sda.video.datastore.CSVStorable;

import java.util.Date;
import java.util.Objects;

/**
 * Store history
 */
public class History extends CSVStorable {

    // Client id
    private int clientId;

    // Movie id
    private int movieId;

    // Date of rent or return
    private Date date;

    // Praice
    private int price;

    // Type is RENT or RETURN
    private HistoryType type;

    public History() {
        date = new Date();
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public HistoryType getType() {
        return type;
    }

    public void setType(HistoryType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        History history = (History) o;
        return clientId == history.clientId &&
                movieId == history.movieId &&
                Double.compare(history.price, price) == 0 &&
                Objects.equals(date, history.date) &&
                type == history.type;
    }

    @Override
    public int hashCode() {

        return Objects.hash(clientId, movieId, date, price, type);
    }

    @Override
    public String[] writeRow() {
        return new String[]{
                Integer.toString(clientId),
                Integer.toString(movieId),
                Long.toString(date.getTime()),
                Integer.toString(price),
                type.name()
        };
    }

    @Override
    public void readRow(String[] cells) {
        clientId = Integer.parseInt(cells[0]);
        movieId = Integer.parseInt(cells[1]);
        date = new Date(Long.parseLong(cells[2]));
        price = Integer.parseInt(cells[3]);
        type = HistoryType.valueOf(cells[4]);
    }

    @Override
    public History newInstance() {
        return new History();
    }
}

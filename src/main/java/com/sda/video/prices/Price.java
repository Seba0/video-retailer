package com.sda.video.prices;

import com.sda.video.movies.Movie;

import java.util.Date;
import java.util.Objects;

public class Price {
    private int id;
    private Date date;
    private double price;
    private Movie movie;

    @Override
    public String toString() {
        return "Price{" +
                "id=" + id +
                ", date=" + date +
                ", price=" + price +
                ", movie=" + movie +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price1 = (Price) o;
        return id == price1.id &&
                Double.compare(price1.price, price) == 0 &&
                Objects.equals(date, price1.date) &&
                Objects.equals(movie, price1.movie);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, date, price, movie);
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}

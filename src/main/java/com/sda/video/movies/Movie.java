package com.sda.video.movies;

import com.sda.video.datastore.CSVStorable;
import java.util.Date;
import java.util.Objects;

public class Movie implements CSVStorable {

    private int id;
    private String title;
    private String director;
    private int yearProduction;
    private Gener gener;
    private Date rentedDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getYearProduction() {
        return yearProduction;
    }

    public void setYearProduction(int yearProduction) {
        this.yearProduction = yearProduction;
    }

    public Gener getGener() {
        return gener;
    }

    public void setGener(Gener gener) {
        this.gener = gener;
    }

    public Date getRentedDate() {
        return rentedDate;
    }

    public void setRentedDate(Date rentedDate) {
        this.rentedDate = rentedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return id == movie.id &&
                yearProduction == movie.yearProduction &&
                Objects.equals(title, movie.title) &&
                Objects.equals(director, movie.director) &&
                gener == movie.gener &&
                Objects.equals(rentedDate, movie.rentedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, director, yearProduction, gener, rentedDate);
    }

    public boolean isRent() {
        return rentedDate != null;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", director='" + director + '\'' +
                ", yearProduction=" + yearProduction +
                ", gener=" + gener +
                ", rentedDate=" + rentedDate +
                '}';
    }

    @Override
    public String[] writeRow() {
        String date = "";
        if(rentedDate != null) {
            date = Long.toString(rentedDate.getTime());
        }
        return new String[]{
                Integer.toString(id),
                title,
                director,
                Integer.toString(yearProduction),
                gener.name(),
                date
        };
    }

    @Override
    public void readRow(String[] cells) {
        id = Integer.parseInt(cells[0]);
        title = cells[1];
        director = cells[2];
        yearProduction = Integer.parseInt(cells[3]);
        gener = Gener.valueOf(cells[4]);
        if(cells[5].isEmpty()) {
            rentedDate = null;
        } else{
            rentedDate = new Date(Long.parseLong(cells[5]));
        }
    }

    @Override
    public CSVStorable newInstance() {
        return new Movie();
    }
}

package com.sda.video.clients;

import com.sda.video.datastore.CSVStorable;

import java.util.Objects;

public class Clients implements CSVStorable {

    private int id;
    private String name;
    private String surname;
    private String tele;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clients clients = (Clients) o;
        return id == clients.id &&
                Objects.equals(name, clients.name) &&
                Objects.equals(surname, clients.surname) &&
                Objects.equals(tele, clients.tele);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, surname, tele);
    }

    @Override
    public String toString() {
        return "Clients{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", tele='" + tele + '\'' +
                '}';
    }


    @Override
    public String[] writeRow() {
        return new String[]{
                Integer.toString(id),
                name,
                surname,
                tele
        };
    }

    @Override
    public void readRow(String[] cells) {
        id = Integer.parseInt(cells[0]);
        name = cells[1];
        surname = cells[2];
        tele = cells[3];
    }

    @Override
    public Clients newInstance() {
        return new Clients();
    }
}

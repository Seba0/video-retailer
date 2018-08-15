package com.sda.video.clients;

import com.sda.video.datastore.CSVStorable;

import java.util.Objects;

public class Client extends CSVStorable {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(name, client.name) &&
                Objects.equals(surname, client.surname) &&
                Objects.equals(tele, client.tele);
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), name, surname, tele);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", tele='" + tele + '\'' +
                '}';
    }


    @Override
    public String[] writeRow() {
        return new String[]{
                name,
                surname,
                tele
        };
    }

    @Override
    public void readRow(String[] cells) {
        name = cells[0];
        surname = cells[1];
        tele = cells[2];
    }

    @Override
    public Client newInstance() {
        return new Client();
    }
}

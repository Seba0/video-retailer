package com.sda.video.clients;

import java.util.Objects;

public class Clients {

    private String name;
    private String surname;
    private String tele;
    private int id;

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

        return Objects.hash(name, surname, tele, id);
    }

    @Override
    public String toString() {
        return "Clients{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", tele='" + tele + '\'' +
                ", id=" + id +
                '}';
    }



}

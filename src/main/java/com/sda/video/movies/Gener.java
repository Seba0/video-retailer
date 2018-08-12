package com.sda.video.movies;

public enum Gener {

    SF("Science Fiction"),
    ACTION ("Action"),
    COMEDY("Comedy"),
    ROMANCE("Romance"),
    DRAMA("Drama"),
    HORROR("Horror"),
    FANTASY("Fantasy"),
    THRILLER("Thriller"),
    MUSICAL("Musical"),
    ANIMATED("Animated");


    private  final String label;
    private Gener (String label){


        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

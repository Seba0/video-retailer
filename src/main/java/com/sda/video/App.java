package com.sda.video;

import com.sda.video.clients.ClientManager;
import com.sda.video.movies.MovieManager;

/**
 * Hello world!
 *
 */
public class App 
{
    ClientManager clientManager = ClientManager.INSTANCE;
    MovieManager movieManager = MovieManager.INSTANCE;


    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

    }
}

package org.rio;

import org.rio.server.Server;

import java.io.IOException;

public class RioDatabase {

    public static void main(String[] args) throws IOException {

        Server server = new Server(6122, 100);
        server.start();
    }
}
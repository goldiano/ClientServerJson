/**
 * Starts the client and connects to the server on localhost:5000
 * Sends string commands and prints the response.
 */

package org.serverclient;

import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class Client {
    private final int port = 5000;
    private final String address = "localhost";

    private void clientStart() throws IOException {

        Gson gson = new Gson();

        System.out.println("Client is starting: ");
        Socket socket = null;
        boolean connection = false;
        while (!connection) {
            try {
                System.out.println("Connection to server: ");
                socket = new Socket(address, port);
                System.out.println("Hello from the other side :) ");
                connection = true;
            } catch (Exception e) {
                System.out.println("Server is busy, try later ... wait 3 seconds..");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        BufferedReader brIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter pwOut = new PrintWriter(socket.getOutputStream(), true);
        Scanner scan = new Scanner(System.in);

        System.out.println("Write command to Server. Maybe start with help ?");
        while (socket.isConnected()) {
            System.out.println("Waiting for command: ");
            String temp = gson.toJson(scan.nextLine());
            pwOut.println(temp);
            ServerResponse response = gson.fromJson(brIn.readLine(), ServerResponse.class);
            System.out.println("[" + response.status + "]\n" + response.message);
            if (response.message.equalsIgnoreCase("quit")) break;

        }
        socket.close();
        System.exit(0);

    }

    public static void main(String[] args) {
        try {
            new Client().clientStart();
        } catch (IOException e) {
            System.err.println("Connection failed: " + e.getMessage());
        }
    }
}



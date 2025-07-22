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
        try (Socket socket = new Socket(address, port);
             BufferedReader brIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter pwOut = new PrintWriter(socket.getOutputStream(), true);
             Scanner scan = new Scanner(System.in)) {

            System.out.println("Write command to Server. Maybe start with help ?");
            while (socket.isConnected()) {
                System.out.println("Waiting for command: ");
                String temp = gson.toJson(scan.nextLine());
                pwOut.println(temp);
                ServerResponse response = gson.fromJson(brIn.readLine(), ServerResponse.class);
                System.out.println("[" + response.status + "]\n" + response.message);
                if (response.message.equalsIgnoreCase("quit")) break;

            }

        } catch (IOException e) {
            System.err.println("Connection error: " + e.getMessage());

            System.exit(1);

        }


    }

    public static void main(String[] args) {
        try {
            new Client().clientStart();
        } catch (IOException e) {
            System.err.println("Connection failed: " + e.getMessage());
        }
    }
}



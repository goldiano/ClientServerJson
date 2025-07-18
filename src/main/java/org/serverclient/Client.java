package org.serverclient;

import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class Client {
    private final int port = 5000;
    private final String address = "localhost";

    private void clientStart() throws IOException {
        Scanner scan = new Scanner(System.in);
        Gson gson = new Gson();

        System.out.println("Client is starting: ");
        Socket socket = new Socket(address,port);
        BufferedReader brIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter pwOut = new PrintWriter(socket.getOutputStream(),true);

        System.out.println("Write command to Server. Maybe start with help ?");
        while(socket.isConnected()) {
            System.out.println("Waiting for command: ");
            String temp = gson.toJson(scan.nextLine());
            pwOut.println(temp);
            temp = gson.fromJson(brIn.readLine(),String.class);
            System.out.println(temp);
            if(temp.equalsIgnoreCase("quit")) break;

        }
        socket.close();
        System.exit(1);


    }

    public static void main(String[] args) throws IOException {
        new Client().clientStart();
    }
}


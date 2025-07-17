package org.serverclient;

import com.google.gson.Gson;

import java.io.*;
import java.net.InetAddress;
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
        BufferedWriter bwOut = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        System.out.println("Write command to Server. Maybe start with help ?");
        while(socket.isConnected()) {
            String temp;
            bwOut.write(scan.nextLine() + "\n");
            bwOut.flush();
            System.out.println(temp = brIn.readLine());
        }
        socket.close();


    }

    public static void main(String[] args) throws IOException {
        new Client().clientStart();
    }
}


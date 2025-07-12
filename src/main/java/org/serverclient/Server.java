package org.serverclient;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

public class Server {
    private LocalDateTime startTime;

    private int port;
    private String adressIP;

    Server(int port, String adressIP) throws IOException {
        this.port = port;
        this.adressIP = adressIP;

        startTime = LocalDateTime.now();
        startServer();
    }
    void startServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        Socket server = serverSocket.accept();
    }
}

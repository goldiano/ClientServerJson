package org.serverclient;

import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

public class Server {
    private LocalDateTime startTime;

    private final int port;

    Server(int port) throws IOException {
        this.port = port;

        startTime = LocalDateTime.now();
        startServer();
    }

    private void startServer() throws IOException {
        String lineCommand;
        ServerSocket serverSocket = new ServerSocket(port);

        try (Socket socket = serverSocket.accept()) {
            BufferedReader brIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bwOut = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            while(socket.isConnected()) {
                lineCommand = brIn.readLine();
                System.out.println("Server get command: " + lineCommand);
                bwOut.write(chooseCommand(lineCommand));
                bwOut.flush();

                if(lineCommand.equalsIgnoreCase("stop")) {
                    brIn.close();
                    bwOut.close();
                    socket.close();
                }
            }

        } finally {
            serverSocket.close();
        }
    }

    private String chooseCommand(String lineCommand) {
        String command = null;
        Gson gson = new Gson();
        switch(lineCommand.toLowerCase()) {
            case "help" :
                command = ("""
                        uptime: Returns the server time,
                        info: Returns server version and create time,
                        help: Returns the list of available commands,
                        stop: Stop and close server and client.\s
                       \s""");
                break;
            case "uptime":
                break;
            case "info": command = """
                    version: 0.1.0
                    creation date 2025-07-14.
                    """;
                break;
            case "stop" :
                System.out.println("Server is shutdown");
                command = ("Server will be shutdown now");
                break;
            default:
                command = ("Unknown command");
                break;
        }
        return gson.toJson(command);
    }
}

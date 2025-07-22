package org.serverclient;

import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Duration;
import java.time.LocalDateTime;

public class Server {
    private final LocalDateTime startTime = LocalDateTime.now();
    private final Gson gson = new Gson();

    private final int port = 5000;


    private void startServer() throws IOException {
        System.out.println("Server is starting: ");
        String lineCommand;
        ServerSocket serverSocket = new ServerSocket(port);

        try (Socket socket = serverSocket.accept()) {
            BufferedReader brIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pwOut = new PrintWriter(socket.getOutputStream(),true);

            while(socket.isConnected()) {
                lineCommand = gson.fromJson(brIn.readLine(), String.class);
                System.out.println("Server get command: " + lineCommand);
                pwOut.println(chooseCommand(lineCommand));

                if(lineCommand.equalsIgnoreCase("stop")) {
                    brIn.close();
                    pwOut.close();
                    socket.close();
                    serverSocket.close();
                }
            }

        } finally {
            System.exit(1);
        }
    }

    private String chooseCommand(String lineCommand) {
        String msg;
        switch(lineCommand.toLowerCase()) {
            case "help" :
                msg = ("""
                        uptime: Returns the server time,
                        info: Returns server version and create time,
                        help: Returns the list of available commands,
                        stop: Stop and close server and client.\s
                       \s""");
                break;
            case "uptime":
                Duration time = Duration.between(startTime, LocalDateTime.now());
                msg = String.format("Server uptime: %d hours, %d minutes, %d seconds",
                        time.toHours(), time.toMinutes(), time.toSeconds());
                break;
            case "info": msg = """
                    version: 0.1.0
                    creation date 2025-07-14.
                    """;
                break;
            case "stop" :
                System.out.println("Server is shutdown");
                msg = "quit";
                break;
            default:
                return gson.toJson(new ServerResponse("error","Unknown command"));
        }
        return gson.toJson(new ServerResponse("ok",msg));
    }

    public static void main(String[] args) throws IOException {
        new Server().startServer();
    }
}

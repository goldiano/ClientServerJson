package org.serverclient;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Podaj port dla serwera i klienta");
        int tempPort = scan.nextInt();
        System.out.println("Podaj adres sieciowy dla klienta");
        String tempIP = scan.nextLine();

        new Server(tempPort);
    }
}
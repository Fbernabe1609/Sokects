package org.example.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static ServerSocket server;
    static Socket client;
    static PrintWriter out;
    static BufferedReader in;

    public static void start(int port) {
        System.out.println("Iniciando servidor");
        try {
            server = new ServerSocket(port);
            client = server.accept();
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String input;
            while ((input = in.readLine()) != null) {
                System.out.println("Mensaje del cliente: " + input);
                if (input.equals("salir")){
                    System.out.println("Apagando servidor");
                    stop();
                    break;
                }

                System.out.println("Enviando respuesta");
                out.println(input);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Servidor apagado.");
    }
    public static void stop() {
        try {
            in.close();
            out.close();
            client.close();
            server.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        start(4900);
    }
}

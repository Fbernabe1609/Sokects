package org.example.Controller;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static ServerSocket server;
    static Socket client;
    static DataOutputStream out;
    static DataInputStream in;

    public static void start(int port) {
        System.out.println("Iniciando servidor");
        try {
            server = new ServerSocket(port);

            while (true){
                client = server.accept();
                out = new DataOutputStream(client.getOutputStream());
                in = new DataInputStream(client.getInputStream());
                new Thread(new CalculatorThread(in,out,client)).start();
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
        start(6900);
    }
}

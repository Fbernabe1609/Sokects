package org.example.Controller;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    static Socket client;
    static DataOutputStream out;
    static DataInputStream in;
    static String response = "";
    static Scanner sc = new Scanner(System.in);

    public static void start(String ip, int port) {
        try {
            System.out.println("Cliente encendido.\nConectando con el servidor.");
            client = new Socket(ip, port);
            out = new DataOutputStream(client.getOutputStream());
            in = new DataInputStream(client.getInputStream());
            System.out.println("Conectado con el servidor.");
            send();
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
        stop();
    }

    public static void send() {
        String text = "";
        do {
            try {
                System.out.println(in.readUTF());
                text = sc.nextLine();
                out.writeUTF(text);
            } catch (IOException e) {
                System.out.println("Error: " + e);
                break;
            }
            response = text;
        } while (!response.equals("salir"));
    }

    public static void stop() {
        try {
            System.out.println("Apagando cliente.");
            in.close();
            out.close();
            client.close();
            System.out.println("Cliente apagado.");
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public static void main(String[] args) {
        start("127.0.0.1", 6900);
    }
}

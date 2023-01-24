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
            do {
                send();
            } while (!response.equals("salir"));
            stop();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void send() {
        String text = "";
        try {
            System.out.println("Introduce un mensaje: ");
            text = sc.nextLine();
            System.out.println("Enviando mensaje.");
            out.writeUTF(text);
            System.out.println(in.readUTF());
            text = sc.nextLine();
            out.writeUTF(text);
            System.out.println(in.readUTF());
            text = sc.nextLine();
            out.writeUTF(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        response = text;
    }
    public static void stop() {
        try {
            in.close();
            out.close();
            client.close();
            System.out.println("Cliente apagado.");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void main(String[] args) {
        start("127.0.0.1",6900);
    }
}

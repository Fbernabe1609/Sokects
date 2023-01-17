package org.example.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    static Socket client;
    static PrintWriter out;
    static BufferedReader in;
    static Scanner sc = new Scanner(System.in);
    public static void start(String ip, int port) {
        System.out.println("Cliente encendido.\nConectando con el servidor.");
        try {
            client = new Socket(ip, port);
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public static String send() {
        String response = "";
        String text = "";
        try {
            System.out.println("Introduce un mensaje: ");
            text = sc.nextLine();
            System.out.println("Enviando mensaje.");
            out.println(text);
            response = in.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            response = "Error, no hubo mensaje.";
        }
        return response;
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
        start("localhost",4900);
    }
}

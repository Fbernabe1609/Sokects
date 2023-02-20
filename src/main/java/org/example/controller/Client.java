package org.example.controller;

import javax.net.ssl.*;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Scanner;

public class Client {

    static DataOutputStream out;
    static DataInputStream in;
    static String response = "";
    static Scanner sc = new Scanner(System.in);

    private final String CLIENTPASS = "clientpass";
    private final SSLSocket clientSSL;

    public Client(String direccionservidor, int puerto) throws
            KeyStoreException, FileNotFoundException, IOException,
            NoSuchAlgorithmException, CertificateException,
            UnrecoverableKeyException, KeyManagementException
    {
        // Indico los certificados seguros del cliente
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(new FileInputStream(
                        "C:\\Users\\2 DAM\\Desktop\\Sokects\\src\\main\\java\\certificados\\clientKey.jks"),
                CLIENTPASS.toCharArray());

        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(keyStore, "enriqueCG".toCharArray());
        KeyStore trustedStore = KeyStore.getInstance("JKS");
        trustedStore.load(new FileInputStream(
                "C:\\Users\\2 DAM\\Desktop\\Sokects\\src\\main\\java\\certificados\\clientTrustedCerts.jks"), CLIENTPASS.toCharArray());
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

        tmf.init(trustedStore);
        SSLContext sc = SSLContext.getInstance("TLS");
        TrustManager[] trustManagers = tmf.getTrustManagers();
        KeyManager[] keyManagers = kmf.getKeyManagers();
        sc.init(keyManagers, trustManagers, null);

        // Creo el socket seguro del cliente
        SSLSocketFactory ssf = sc.getSocketFactory();
        clientSSL = (SSLSocket) ssf.createSocket(direccionservidor,
                puerto);
        clientSSL.startHandshake();
    }

    public void start() {
        try {
            System.out.println("Cliente encendido.\nConectando con el servidor.");
            out = new DataOutputStream(clientSSL.getOutputStream());
            in = new DataInputStream(clientSSL.getInputStream());
            System.out.println("Conectado con el servidor.");
            send();
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
        stop();
    }

    public void send() {
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

    public void stop() {
        try {
            System.out.println("Apagando cliente.");
            in.close();
            out.close();
            clientSSL.close();
            System.out.println("Cliente apagado.");
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public static void main(String[] args) throws UnrecoverableKeyException, CertificateException, KeyStoreException,
            IOException, NoSuchAlgorithmException, KeyManagementException {
        new Client("127.0.0.1", 6900).start();
    }
}

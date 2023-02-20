package org.example.controller;

import javax.net.ssl.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;
import java.security.cert.CertificateException;

public class Server {
    static ServerSocket server;
    static Socket client;
    static DataOutputStream out;
    static DataInputStream in;

    private final String SERVERPASS = "servpass";
    private final SSLServerSocket serverSocket;
    private final int puerto;

    public void start() {
        System.out.println("Iniciando servidor");
        try {

            while (true) {
                client = serverSocket.accept();
                out = new DataOutputStream(client.getOutputStream());
                in = new DataInputStream(client.getInputStream());
                new Thread(new CalculatorThread(in, out, client)).start();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            stop();
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

    public Server(int puerto) throws FileNotFoundException,
            KeyStoreException, IOException, NoSuchAlgorithmException,
            CertificateException, UnrecoverableKeyException,
            KeyManagementException
    {
        this.puerto = puerto;

        // Indico los certificados seguros del servidor
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(new FileInputStream(
                "C:\\Users\\2 DAM\\Desktop\\Sokects\\src\\main\\java\\certificados\\serverKey3.jks"), SERVERPASS.toCharArray());

        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(keyStore, "enriqueCG".toCharArray());

        KeyStore trustedStore = KeyStore.getInstance("JKS");
        trustedStore.load(new FileInputStream(
                "C:\\Users\\2 DAM\\Desktop\\Sokects\\src\\main\\java\\certificados\\serverTrustedCerts.jks"), SERVERPASS
                .toCharArray());
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

        tmf.init(trustedStore);
        SSLContext sc = SSLContext.getInstance("TLS");
        TrustManager[] trustManagers = tmf.getTrustManagers();
        KeyManager[] keyManagers = kmf.getKeyManagers();
        sc.init(keyManagers, trustManagers, null);

        // Creo el socket seguro del servidor
        SSLServerSocketFactory ssf = sc.getServerSocketFactory();
        serverSocket = (SSLServerSocket) ssf.createServerSocket(puerto);
    }

    public static void main(String[] args) throws UnrecoverableKeyException, CertificateException, KeyStoreException,
            IOException, NoSuchAlgorithmException, KeyManagementException {
        new Server(6900).start();
    }
}

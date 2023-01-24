package org.example.Controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.DataOutputStream;
import java.net.Socket;

public class CalculatorThread extends Thread{
    private DataOutputStream out;
    private DataInputStream in;
    private Socket client;

    CalculatorThread(DataInputStream in, DataOutputStream out, Socket client) {
        this.in = in;
        this.out = out;
        this.client = client;
    }

    public void options(String option, double a, double b) {
        do {
            switch (option) {
                case "1":
                    out.writeUTF(String.valueOf(CalculatorOperations.sum(a,b)));
                    break;
                case "2":
                    out.writeUTF(String.valueOf(CalculatorOperations.sum(a,b)));
                    System.out.println(CalculatorOperations.subtract(a,b));
                    break;
                case "3":
                    out.writeUTF(String.valueOf(CalculatorOperations.sum(a,b)));
                    System.out.println(CalculatorOperations.mul(a,b));
                    break;
                case "4":
                    out.writeUTF(String.valueOf(CalculatorOperations.sum(a,b)));
                    System.out.println(CalculatorOperations.div(a,b));
                    break;
                case "salir":
                    out.writeUTF("Saliendo");
                default:
                    out.writeUTF("Opción no válida");
            }
        } while (option.equals("salir"));
    }

    @Override
    public void run() {
        try {
            String input = in.readUTF();
            System.out.println(input);
            while (true) {
                System.out.println(input);
                System.out.println("Mensaje del cliente: " + input);
                out.writeUTF("Introduce un número");
                double n1 = Double.parseDouble(input);
                out.writeUTF("Introduce otro número");
                double n2 = Double.parseDouble(input);
                System.out.println("Enviando respuesta");
                options(input, n1, n2);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

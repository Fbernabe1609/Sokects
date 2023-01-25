package org.example.Controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.DataOutputStream;
import java.net.Socket;

public class CalculatorThread extends Thread {
    private DataOutputStream out;
    private DataInputStream in;
    private Socket client;
    private double n1, n2;
    private boolean error = false;
    private final String errorS = "Ha habido algún problema con los datos introducidos";
    private final String INFO = "Opciones disponibles:\n1->Suma\n2->Resta\n3->Multiplicación\n4->División\n5->Salir";

    CalculatorThread(DataInputStream in, DataOutputStream out, Socket client) {
        this.in = in;
        this.out = out;
        this.client = client;
    }

    public void options() {
        try {
            String option = "";
            out.writeUTF(INFO);
            do {
                option = in.readUTF();
                System.out.println("Mensaje del cliente: " + option);
                switch (option.toLowerCase()) {
                    case "1" -> {
                        numbers();
                        if (error) {
                            controlError();
                        } else {
                            out.writeUTF(CalculatorOperations.sum(n1, n2) + "\n" + INFO);
                        }
                    }
                    case "2" -> {
                        numbers();
                        if (error) {
                            controlError();
                        } else {
                            out.writeUTF(CalculatorOperations.subtract(n1, n2) + "\n" + INFO);
                        }
                    }
                    case "3" -> {
                        numbers();
                        if (error) {
                            controlError();
                        } else {
                            out.writeUTF(CalculatorOperations.mul(n1, n2) + "\n" + INFO);
                        }
                    }
                    case "4" -> {
                        numbers();
                        if (error) {
                            controlError();
                        } else {
                            out.writeUTF(CalculatorOperations.div(n1, n2) + "\n" + INFO);
                        }
                    }
                    case "5" -> out.writeUTF("Saliendo.");
                    default -> out.writeUTF("Opción no válida." + "\n" + INFO);
                }
            } while (!option.equals("5"));
        } catch (IOException e) {
            error = true;
            System.out.println("Error: " + e);
        }
        stopMethod();
    }

    @Override
    public void run() {
        options();
    }

    public void numbers() {
        try {
            out.writeUTF("Introduce un número:");
            n1 = Double.parseDouble(in.readUTF().replaceAll(",", "."));
            System.out.println(n1);
            out.writeUTF("Introduce otro número:");
            n2 = Double.parseDouble(in.readUTF().replaceAll(",", "."));
            System.out.println(n2);
        } catch (IOException | NumberFormatException e) {
            error = true;
            System.out.println("Error: " + e);
        }
    }

    public void stopMethod() {
        try {
            in.close();
            out.close();
            client.close();
        } catch (IOException e) {
            error = true;
            System.out.println("Error: " + e);
        }
    }

    public void controlError() {
        try {
            out.writeUTF(errorS + "\n" + INFO);
            error = false;
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }
}

package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CalculatorThreadSynchron extends CalculatorDecorator {
    public CalculatorThreadSynchron(Calculator c) {
        super(c);
    }

    @Override
    public void sendToClient(String output){
        System.out.println("Bin in synchron send");
        try {
            Socket socket = calc.getSocket();

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("Synchron " + output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String receiveFromClient(){
        System.out.println("Bin in synchron receive");
        try {
            Socket socket = calc.getSocket();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()));
            return in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}

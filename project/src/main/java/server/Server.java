package server;

import java.net.*;
import java.io.*;

public class Server {

    public static void main(String[] args) throws IOException {

        if (args.length < 1) {
            System.err.println("Server needs port as startup argument");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);
        boolean listening = true;

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (listening) {
                if(args.length != 2)
                    new CalculatorThread(serverSocket.accept()).start();
                else{
                    if(args[1].toLowerCase().equals("synchron")){
                        new CalculatorThreadSynchron(new CalculatorThread(serverSocket.accept())).start();
                    }else if(args[1].toLowerCase().equals("asynchron")){
                        new CalculatorThreadAsynchron(new CalculatorThread(serverSocket.accept())).start();
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
}
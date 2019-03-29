package server;

import java.io.*;
import java.net.*;

public class CalculatorThread extends Thread implements CalculatorInterface{

    private Socket socket = null;
    private int credits = 10;

    public CalculatorThread(Socket s){
        super("CalculatorThread");
        this.socket = s;
    }

    public void run() {

        try (
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()));
        ) {
            String userInput;
            while ((userInput = in.readLine()) != null) {

                if(userInput.contains(" ")) {
                    String wantedMethod = userInput.substring(0, userInput.indexOf(" "));
                    switch (wantedMethod) {
                        case "!add":
                            userInput = "";
                            break;
                        case "!subtract":
                            double arg1 = 0;
                            double arg2 = 0;
                            out.println(subtract(arg1, arg2));
                            userInput = "";
                            break;
                        default:
                            out.println("unsuported calc method");
                            userInput = "";
                            break;
                    }
                }else{
                    switch(userInput){
                        case "!bye":
                            out.println(userInput);
                            exit();
                            return;
                        case "!view":
                            out.println(view());
                            userInput = "";
                            break;
                        default:
                            out.println("The server doesnt understand your request: '" + userInput + "'");
                            userInput = "";
                            break;
                    }
                }
            }
            exit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exit() {
        try{
            this.socket.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public double add(double z1, double z2) {
        return z1 + z2;
    }

    @Override
    public double subtract(double z1, double z2) {
        return z1 - z2;
    }

    @Override
    public int view() {
        return this.credits;
    }
}

package server;

import java.io.*;
import java.net.*;

public class CalculatorThread extends Thread implements Calculator {

    protected Socket socket = null;
    private int credits = 10;

    public CalculatorThread(Socket s) {
        super("CalculatorThread");
        this.socket = s;
    }

    @Override
    public void run() {

        //try(
        //PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        //BufferedReader in = new BufferedReader(
        //new InputStreamReader(
        //socket.getInputStream()));
        //) {
            String userInput;
            //while ((userInput = in.readLine()) != null) {
            while ((userInput = receiveFromClient()) != null) {

                if (userInput.contains(" ")) {
                    String wantedMethod = userInput.substring(0, userInput.indexOf(" "));

                    double calc_arg1 = 0;
                    double calc_arg2 = 0;
                    try {
                        String cuttedString = userInput.substring(userInput.indexOf(" ") + 1, userInput.length());
                        calc_arg1 = Double.parseDouble(cuttedString.substring(0, cuttedString.indexOf(" ")));
                        calc_arg2 = Double.parseDouble(cuttedString.substring(cuttedString.indexOf(" ") + 1, cuttedString.length()));
                    } catch (Exception e) {
                        e.printStackTrace();
                        //out.println("Invalid args");
                        sendToClient("Invalid args");
                    }
                    switch (wantedMethod) {
                        case "!add":
                            if (view() > 0) {
                                //out.println(add(calc_arg1, calc_arg2));
                                sendToClient(add(calc_arg1, calc_arg2) + "");
                                userInput = "";
                            } else {
                                //out.println("You do not have enough credits to do that");
                                sendToClient("You do not have enough credits to do that");
                            }
                            break;
                        case "!subtract":
                            if (view() > 0) {
                                //out.println(subtract(calc_arg1, calc_arg2));
                                sendToClient(subtract(calc_arg1, calc_arg2) + "");
                                userInput = "";
                            } else {
                                //out.println("You do not have enough credits to do that");
                                sendToClient("You do not have enough credits to do that");
                            }
                            break;
                        default:
                            //out.println("unsuported calc method");
                            sendToClient("unsuported calc method");
                            userInput = "";
                            break;
                    }
                } else {
                    switch (userInput) {
                        case "!bye":
                            //out.println(userInput);
                            sendToClient(userInput);
                            exit();
                            return;
                        case "!view":
                            //out.println("Your credits = " + view());
                            sendToClient("Your credits = " + view());
                            userInput = "";
                            break;
                        default:
                            //out.println("The server doesnt understand your request: '" + userInput + "'");
                            sendToClient("The server doesnt understand your request: '" + userInput + "'");
                            userInput = "";
                            break;
                    }
                }
            }
            exit();
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
    }

    @Override
    public void exit() {
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public double add(double z1, double z2) {
        setCredits(view() - 1);
        return z1 + z2;
    }

    @Override
    public double subtract(double z1, double z2) {
        setCredits(view() - 1);
        return z1 - z2;
    }

    @Override
    public int view() {
        return this.credits;
    }

    @Override
    public void setCredits(int c) {
        this.credits = c;
    }

    @Override
    public void sendToClient(String output) {
        System.out.println("Bin im alten");
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String receiveFromClient() {
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()));
            return in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public Socket getSocket(){
        return this.socket;
    }
}
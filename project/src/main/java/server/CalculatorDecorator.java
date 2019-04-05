package server;

import java.net.Socket;

public abstract class CalculatorDecorator extends Thread implements Calculator {
    protected Calculator calc;

    public CalculatorDecorator(Calculator c){
        this.calc = c;
    }

    public void exit() {
        calc.exit();
    }

    public double add(double z1, double z2) {
        return calc.add(z1,z2);
    }

    public double subtract(double z1, double z2) {
        return calc.subtract(z1,z2);
    }

    public int view() {
        return calc.view();
    }

    public void setCredits(int c) {
        calc.setCredits(c);
    }

    public void sendToClient(String output) {
        calc.sendToClient(output);
    }

    public String receiveFromClient() {
        return calc.receiveFromClient();
    }

    public void run(){
        calc.run();
    }

    public Socket getSocket(){
        return calc.getSocket();
    }
}

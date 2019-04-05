package server;

import java.net.Socket;

public interface Calculator {
    public void exit();
    public double add(double z1, double z2);
    public double subtract(double z1, double z2);
    public int view();
    public void setCredits(int c);
    public void sendToClient(String output);
    public String receiveFromClient();
    public void run();
    public Socket getSocket();
}

package server;

public interface CalculatorInterface {
    public void exit();
    public double add(double z1, double z2);
    public double subtract(double z1, double z2);
    public int view();
    public void setCredits(int c);
    public void sendToClient(String output);
    public String receiveFromClient();
}

package server;

public class CalculatorThreadAsynchron extends CalculatorDecorator {
    public CalculatorThreadAsynchron(Calculator c) {
        super(c);
    }

    @Override
    public void sendToClient(String input){

    }

    @Override
    public String receiveFromClient(){
        return "";
    }
}

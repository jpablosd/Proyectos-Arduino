package servertoarduinolights;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Enumeration;
import gnu.io.CommPortIdentifier; 
import gnu.io.SerialPort;

public class ServerToArduinoLights {
    public static void main(String[] args) {
        ServerToArduinoLights serverToArduino = new ServerToArduinoLights();
        serverToArduino.startServer();
    }
    
    private static BufferedReader arduinoInput;
    private static OutputStream arduinoOutput;
    private static final int TIME_OUT = 2000;
    private static final int DATA_RATE = 9600;    
    
    public void startServer(){
        CommPortIdentifier portId = null;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
        
        while (portEnum.hasMoreElements()) {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
            if (currPortId.getName().equals("COM1")) {
                portId = currPortId;
                break;
            }
        }
        if (portId == null) {
            System.out.println("Could not find COM port.");
            return;
        }
        else{
            System.out.println("using com port: " + portId.getName());
        }
        
        try{
            SerialPort serialPort;
            serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);

            // set port parameters
            serialPort.setSerialPortParams(DATA_RATE,
                            SerialPort.DATABITS_8,
                            SerialPort.STOPBITS_1,
                            SerialPort.PARITY_NONE);

            arduinoInput = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
            arduinoOutput = serialPort.getOutputStream();    
            System.out.println("serial port opened");
        }
        catch(Exception e){
            System.err.println(e.toString());
        }
        
        System.out.println("starting server");
        
        try{
            ServerSocket listener = new ServerSocket(9090);
        
            while (true){
                Socket socket = listener.accept();
                System.out.println("Accepted connection");
                
                try {
                    BufferedReader clientInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String req = clientInput.readLine();
                    PrintWriter clientOut = new PrintWriter(socket.getOutputStream(), true);
                    
                    if(req.equals("red")){
                        System.out.println("req is red");
                        arduinoOutput.write(55);
                        clientOut.println("server ack red " + new Date().toString());
                    }
                    else if(req.equals("yellow")){
                        System.out.println("req is yellow");
                        arduinoOutput.write(57);
                        clientOut.println("server ack yellow " + new Date().toString());
                    }
                    else if(req.equals("green")){
                        System.out.println("req is green");
                        arduinoOutput.write(56);
                        clientOut.println("server ack green " + new Date().toString());
                    }
                } 
                catch(IOException io) {
                    System.out.println("Error 2!");
                }
            }
        }
        catch(IOException io){
            System.out.println("Error 1!");
        }
    }
}

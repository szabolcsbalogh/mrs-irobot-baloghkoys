package mrs.irobot.baloghkoys;

import java.io.*;
import javax.comm.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Connector {
    
  //static Enumeration ports;
  //String buffer = "";
  String driverName = "com.sun.comm.Win32Driver";
  CommDriver commdriver;
  static CommPortIdentifier pID;
  InputStream inStream;
  OutputStream outStream;
  SerialPort serPort;

    public static Enumeration getPortNames() {
        return CommPortIdentifier.getPortIdentifiers();
     }
    
    public void printPortNames(){
        
        Enumeration portIdentifiers = CommPortIdentifier.getPortIdentifiers();
        while (portIdentifiers.hasMoreElements())
        {
            CommPortIdentifier pid = (CommPortIdentifier) portIdentifiers.nextElement();
            System.out.println(pid.getName());
        }
        
    }

  public Connector(){
    if( !CommPortIdentifier.getPortIdentifiers().hasMoreElements() ){ // pokus o osetrenie linux/windows
        try{                            // DRIVER FOR WINDOWS
            commdriver = (CommDriver)Class.forName(driverName).newInstance();
            commdriver.initialize();
        }
        catch (Exception e2)
        {
            e2.printStackTrace();
        }
    }
  }
  
  public boolean openPort( String wantedPortName )
  {
    boolean ret = false;
    try{
        pID = javax.comm.CommPortIdentifier.getPortIdentifier(wantedPortName);
        serPort = (SerialPort) pID.open(wantedPortName, 2000);
        inStream = serPort.getInputStream();
        outStream = serPort.getOutputStream();
        serPort.setSerialPortParams(57600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
            SerialPort.PARITY_NONE);
        ret = true;
    }catch(Exception e){
            System.err.println("Could not find serial port " + wantedPortName);
            System.err.println("Exception: " + e.toString());
    }
    return ret;
  }

  public void sendByte( byte[] data ){
        try {
            outStream.write(data);
        } catch (IOException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
  
  public byte receiveByte(){
        byte[] readBuffer = new byte[1];
        try {
            inStream.read(readBuffer,0,1);
        } catch (IOException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return readBuffer[0];
  }
  
  public void flushInStream(){
        try {
            inStream.skip(10000);
        } catch (IOException ex) {
        }
  }
  
  
}


package mrs.irobot.baloghkoys;

import java.io.*;
import java.util.*;
import javax.comm.*;

public class Connector {
    
  String driverName = "com.sun.comm.Win32Driver";
  CommDriver commdriver;
  static CommPortIdentifier pID;
  InputStream inStream;
  OutputStream outStream;
  SerialPort serPort;
  
  public boolean vitrual_input_data=false;
  
    public void printPortNames(){
        
        Enumeration portIdentifiers = CommPortIdentifier.getPortIdentifiers();
        while (portIdentifiers.hasMoreElements())
        {
            CommPortIdentifier pid = (CommPortIdentifier) portIdentifiers.nextElement();
            System.out.println(pid.getName());
        }
        
    }
    
    public static int getNumberOfPorts(){        
        int n=0;
        Enumeration portIdentifiers = CommPortIdentifier.getPortIdentifiers();
        while (portIdentifiers.hasMoreElements()){
            CommPortIdentifier pid = (CommPortIdentifier) portIdentifiers.nextElement();     
            n++;
        }
        return n;
    }
    
    public static Object[] getPortNames(){    
        String[] names = new String[getNumberOfPorts()];
        int i=0;
        
        Enumeration portIdentifiers = CommPortIdentifier.getPortIdentifiers();
        while (portIdentifiers.hasMoreElements())
        {
            CommPortIdentifier pid = (CommPortIdentifier) portIdentifiers.nextElement();
            names[i++] = pid.getName();
        }
        return names;
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
        serPort.setInputBufferSize(0);
        serPort.setOutputBufferSize(0);
        
        ret = true;
    }catch(Exception e){
            Logger.log("Could not open serial port "+ wantedPortName+": " + e.toString());
            System.err.println("Could not open serial port "+ wantedPortName+": " + e.toString());
    }
    return ret;
  }

  public void sendByte( byte[] data ){
        try {
            outStream.write(data);
        } catch (IOException ex) {
            Logger.log("Send byte error: "+ex.toString());
        }
  }
  
  public byte receiveByte(){
        if( this.vitrual_input_data )
            return 0x00;
        
        byte[] readBuffer = new byte[1];
        try {
            inStream.read(readBuffer,0,1);
        } catch (IOException ex) {
            Logger.log( "Receive byte error: " +ex.toString() );
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


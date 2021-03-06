/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mrs.irobot.baloghkoys;

import java.util.Date;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Formatter;

/**
 *
 * @author user
 */
public class Logger {
    
    private static boolean logFile = false;
    private static String fileName = null;
    private static final int GUI_LOG_LEVEL = 2;
    private static final int STDOUT_LOG_LEVEL = 1;

    
    /**
     * Make a log to file, to stdout and to GUI, no priorities
     * 
     * @param   str         string to be logged
     */
    static public void log( String str ){  
        if( !logFile ){
            newLogFile();
            logFile = true;
        }
        String logText = date()+str+"\r\n";
        appendText(logText);
        System.out.print(logText);
        if( GUI.logTextArea != null ) {
            GUI.logTextArea.append(logText);
        }  
    }
    
    /**
     * Make a log to file, to stdout and to GUI
     * 
     * @param   str         string to be logged
     * @param   priority    higher number is higher priority, priority lower than GUI_LOG_LEVEL won't be logged to GUI, priority lower than STDOUT_LOG_LEVEL won't be logged to STDOUT
     */
    static public void log( String str, int priority ){  
        if( !logFile ){
            newLogFile();
            logFile = true;
        }
        String logText = date()+str+"\r\n";
        appendText(logText);
        if( priority >= STDOUT_LOG_LEVEL ) {
            System.out.print(logText);
        }
        if( priority >= GUI_LOG_LEVEL ) {
            if( GUI.logTextArea != null ) {
                GUI.logTextArea.append(logText);
            }
        }  
    }
    
    /**
     * Method return String with time stamp to log
     * @return String with date
     */
    private static String date(){
        DateFormat dateFormat = new SimpleDateFormat("[yyyy/MM/dd HH:mm:ss]: ");
        Date date = new Date();
        return dateFormat.format(date);  
    }    
    
    /**
     * Creates new log file irobot_date.log
     * Show logging level for each logging output
     */
    private static void newLogFile(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        Date date = new Date();
        fileName = "iRobot_"+dateFormat.format(date)+".log";
        System.out.println( "Logging to "+fileName+" started" );
        System.out.println(date()+"Logging level is "+STDOUT_LOG_LEVEL+"\r\n");
        if( GUI.logTextArea != null ) {
            GUI.logTextArea.append(date()+"Logging level is "+GUI_LOG_LEVEL+"\r\n");
        } 
    }
    
       /**
        * Appends text to log file
        * @param text text to log
        */
    private static void appendText( String text )
    {
        try
        {
            FileWriter fw = new FileWriter( fileName, true );
            fw.append(text);
            fw.close();
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * For printing bytes as hex string
     * @param bytes bytes
     * @return String
     */
    public static String bytesToHexString(byte[] bytes) {  
        StringBuilder sb = new StringBuilder(bytes.length * 2);  
      
        Formatter formatter = new Formatter(sb);  
        for (byte b : bytes) {  
            formatter.format("%02x", b);  
        }  
      
        return sb.toString();  
    }
    
}

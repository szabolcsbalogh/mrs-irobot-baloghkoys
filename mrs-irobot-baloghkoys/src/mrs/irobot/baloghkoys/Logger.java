/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mrs.irobot.baloghkoys;

import java.awt.TextArea;
import java.io.File;
import java.util.Date;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author user
 */
public class Logger {
    
    private static boolean logFile = false;
    private static String fileName = null;
    private static final int GUI_LOG_LEVEL = 0;
    private static final int STDOUT_LOG_LEVEL = 0;
    String targeds;

    
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
        if( GUI.logTextArea != null )
            GUI.logTextArea.append(logText);  
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
        if( priority >= STDOUT_LOG_LEVEL )
            System.out.print(logText);
        if( priority >= GUI_LOG_LEVEL )
            if( GUI.logTextArea != null )
                GUI.logTextArea.append(logText);  
    }
    
    
    private static String date(){
        DateFormat dateFormat = new SimpleDateFormat("[yyyy/MM/dd HH:mm:ss]: ");
        Date date = new Date();
        return dateFormat.format(date);  
    }    
    
    private static void newLogFile(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        Date date = new Date();
        fileName = "iRobot_"+dateFormat.format(date)+".log";
        System.out.println( "Logging to "+fileName+" started" );
    }
    
       
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
    
}

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
    String targeds;
    
    static public void log( String str ){  
        if( !logFile ){
            newLogFile();
            logFile = true;
        }
        String logText = date()+str+"\r\n";
        appendText(logText);
        //System.out.print(logText);
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

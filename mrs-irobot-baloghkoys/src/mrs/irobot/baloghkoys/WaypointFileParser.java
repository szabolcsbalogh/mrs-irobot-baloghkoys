/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mrs.irobot.baloghkoys;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author juko
 */
public class WaypointFileParser {
    
    //Waypoint[] waypoints = null;
    ArrayList<Waypoint> waypoints = new ArrayList();
    int i=0;
    
    /**
     * Constructor which read file and parse waypoints in it
     * @param fileName file with x,y,speed parameters separated with ; and \r\n
     */
    public WaypointFileParser( String fileName ){
        Logger.log("Loading waypoints from file "+fileName,5);
        String fileText = readStringFromFile( fileName );
        parseString( fileText );
        Logger.log("Loaded "+waypoints.size()+" waypoints",5);
    }
    
    /**
     * Array of Waypoints read from file
     * @return Object[] you need to cast Object to Waypoint
     */
    public Object[] getWaypoints(){
        return waypoints.toArray();
    }
    
    /**
     * Returns Iterator for waypoints read from file
     * @return iterator for waypoints
     */
    public Iterator<Waypoint> iterator(){
        return waypoints.iterator();
    }
    
    /**
     * Parses string to lines separated by \r\n and use method parseLine to parse waypoint from each line
     * @param string String with waypoints separated with ; and \r\n
     */
    private void parseString( String string ){
        
        while( !string.isEmpty() ){
            String line = "";
            if( string.indexOf("\r\n") > 0 ){
                line = string.substring(0, string.indexOf("\r\n"));
                Waypoint wpt = parseLine( line, ";" );
                waypoints.add(wpt);
                string = string.substring( string.indexOf("\r\n")+2);
            }else
                break;
        }
    }
    
    /**
     * Parse one line with 3 parameters separated like this x;y;speed\r\n
     * and creates new instance of Waypoint
     * @param line line to parse
     * @return object Waypoint 
     */
    private Waypoint parseLine( String line, String delimeter ){
        int x,y,speed;
        String xstr=null,ystr=null,spdstr=null,sensors_str=null;
        byte[] sensors=null;
        
        Logger.log("WaypointFileParser: parsing line: "+line,0);
        try{
            String[] parse = line.split( delimeter );
            xstr = parse[0];
            ystr = parse[1];
            spdstr = parse[2];
            sensors_str = parse[3];
            
            if( !sensors_str.equals(" ") ) {
                sensors_str = sensors_str.substring(1, sensors_str.length()-1 );
                String[] parse2 = sensors_str.split(", ");            
                sensors = new byte[parse2.length];
                for( int i=0; i< parse2.length ; i++ )
                    sensors[i] = Byte.parseByte(parse2[i]);
            }else{
                sensors = null;
            }
        }catch(Exception e){
            Logger.log("Failed to parse line, wrong file format" );          
            Logger.log("Exception: "+e.toString() );
        }
        try{
            x = Integer.parseInt(xstr);
            y = Integer.parseInt(ystr);
            speed = Integer.parseInt(spdstr);
            Waypoint wpt = null;
            wpt =  new Waypoint(x,y,speed,sensors);
            Logger.log("Waypoint successfully parsed: "+wpt.toString(),2 );
            return wpt;
        }catch(Exception e){
            Logger.log("Failed to convert strings to integers: x: \""+xstr+"\" y: \""+ystr+"\" spd: \""+spdstr+"\"" );
            Logger.log("Exception: "+e.toString() );
        }
        return null;
    }
    
    /**
     * Reads whole string from file
     * @param fileName file to read 
     * @return 
     */
    private String readStringFromFile( String fileName )
    {
        FileReader fr;
        char cbuf[] = new char[65535];
        String s = new String("");
        try
        {
            fr = new FileReader( fileName );
            while(true)
            {
                if( fr.read( cbuf ) == -1 )
                    break;
                s = s.concat( new String(cbuf) );
            }
            fr.close();
        }
        catch (Exception e)
        {
            Logger.log("Failed to read from file. Exception: "+e.toString());
        }
        return s;
    }
}

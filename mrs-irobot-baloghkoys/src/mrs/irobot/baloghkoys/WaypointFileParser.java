/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mrs.irobot.baloghkoys;

import java.io.FileReader;
import java.util.ArrayList;

/**
 *
 * @author juko
 */
public class WaypointFileParser {
    
    //Waypoint[] waypoints = null;
    ArrayList<Waypoint> waypoints = new ArrayList();
    
    public WaypointFileParser( String fileName ){
        String fileText = readStringFromFile( fileName );
        parseString( fileText );
        Logger.log("Loaded "+waypoints.size()+" waypoints from file "+fileName,5);
    }
    
    public Object[] getWaypoints(){
        return waypoints.toArray();
    }
    
    private void parseString( String string ){
        
        while( !string.isEmpty() ){
            String line = "";
            if( string.indexOf("\r\n") > 0 ){
                line = string.substring(0, string.indexOf("\r\n"));
                Waypoint wpt = parseLine( line );
                waypoints.add(wpt);
                string = string.substring( string.indexOf("\r\n")+2);
            }else
                break;
        }
    }
    
    private Waypoint parseLine( String line ){
        int x,y,speed;
        String xstr=null,ystr=null,spdstr=null;
        
        Logger.log("WaypointFileParser: parsing line: "+line,0);
        try{
            xstr = line.substring(0, line.indexOf(";"));
            line = line.substring(line.indexOf(";")+1);
            ystr = line.substring(0, line.indexOf(";"));
            line = line.substring(line.indexOf(";")+1);
            spdstr = line;
        }catch(Exception e){
            Logger.log("Failed to parse line, wrong file format" );          
            Logger.log("Exception: "+e.toString() );
        }
        try{
            x = Integer.parseInt(xstr);
            y = Integer.parseInt(ystr);
            speed = Integer.parseInt(spdstr);
            Waypoint wpt =  new Waypoint(x,y,speed);
            Logger.log("Waypoint successfully parsed: "+wpt.toString(),2 );
            return wpt;
        }catch(Exception e){
            Logger.log("Failed to convert strings to integers: x: \""+xstr+"\" y: \""+ystr+"\" spd: \""+spdstr+"\"" );
            Logger.log("Exception: "+e.toString() );
        }
        return null;
    }
    
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

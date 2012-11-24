/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mrs.irobot.baloghkoys;

import java.util.Arrays;

/**
 *
 * @author juko
 */
public class Waypoint {
    
    /**
     * x coordinate in cartesian coordinate system in milimeters
     */
    private int x;
    /**
     * y coordinate in cartesian coordinate system in milimeters
     */
    private int y;
    /**
     * speed of robot to get to this point in milimeters/second
     * orientation 
     */
    private int speed_orientation;
    /**
     * notes for the waypoint
     * data means iRobot waypoint position with sensors state and third parameter is considered as orientation
     * null means ordinary waypoint then third parameter is considered as desired speed
     */    
    private byte[] irobot_reply = null;

    public Waypoint(int x, int y, int speed_orientation) {
        this.x = x;
        this.y = y;
        this.speed_orientation = speed_orientation;
        this.irobot_reply = null;
    }
    
    public Waypoint(int x, int y, int speed_orientation, byte[] irobot_reply) {
        this.x = x;
        this.y = y;
        this.speed_orientation = speed_orientation;
        if( irobot_reply != null ) {
            this.irobot_reply = Arrays.copyOf( irobot_reply, irobot_reply.length);
        }        
        else {
            this.irobot_reply = null;
        }
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed_orientation;
    }    
    
    public int getOrientation() {
        return speed_orientation;
    }    
    
    public byte[] getReply(){
        return irobot_reply;
    }
    
    @Override
    public String toString(){
        if( irobot_reply == null) {
            return "x: "+x+" y: "+y+" speed: "+speed_orientation+"mm/s waypoint";
        }
        else {
            return "x: "+x+" y: "+y+" orientation: "+speed_orientation+"degrees iRobot position";
        }
    }
    
    public String toFile(){
        if(irobot_reply != null) {
            return x+";"+y+";"+speed_orientation+";"+Arrays.toString(irobot_reply) +"\r\n";
        } 
        return x+";"+y+";"+speed_orientation+"; \r\n"; 
    }
    
    public boolean isOrdinary(){
        return irobot_reply == null;
    }
    
    public boolean equals( Waypoint wpt ){
        if( wpt == null ){
            return false;
        }
        if( this.x == wpt.x && this.y == wpt.y && this.speed_orientation == wpt.speed_orientation && 
            this.irobot_reply.length == wpt.irobot_reply.length){
            for( int i=0; i<this.irobot_reply.length; i++ ) {
                if( this.irobot_reply[i] != wpt.irobot_reply[i] ) {
                    return false;
                }
            }
            return true;
        }
        return false;        
    }
}

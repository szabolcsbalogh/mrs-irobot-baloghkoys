/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mrs.irobot.baloghkoys;

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
     * "R" means iRobot position then third parameter is considered as orientation
     * "W" means ordinary waypoint then third parameter is considered as desired speed
     */
    private String notes;
    
    public Waypoint(int x, int y, int speed_orientation, String notes) {
        this.x = x;
        this.y = y;
        this.speed_orientation = speed_orientation;
        this.notes = notes;
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
    
    public String getNotes(){
        return notes;
    }
    
    @Override
    public String toString(){
        if( notes.equals("W"))
            return "x: "+x+" y: "+y+" speed: "+speed_orientation+"mm/s Waypoint";
        else if( notes.equals("R"))
            return "x: "+x+" y: "+y+" orientation: "+speed_orientation+"degrees iRobot position";
        else
            return "x: "+x+" y: "+y+" speed_orientation: "+speed_orientation+"mm/s or degrees notes: "+notes;
    }
    
    public String toFile(){
        return x+";"+y+";"+speed_orientation+";"+notes+"\r\n";
    }
    
    public boolean isOrdinary(){
        return notes.equals("W");
    }
}

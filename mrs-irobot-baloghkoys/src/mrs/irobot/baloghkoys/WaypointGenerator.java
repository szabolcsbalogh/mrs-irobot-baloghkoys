package mrs.irobot.baloghkoys;

import java.util.ArrayList;

/**
 *
 * @author juko
 */
public class WaypointGenerator {
    
    /**
     * Generates waypoints for iRobot to vacuum rectangle area with a_side length and b_side length by speed
     * @param a_side rectangle a side length in mm
     * @param b_side rectangle b side length in mm
     * @param speed speed of vacuuming in mm/s
     * @return  waypoints to vacuum
     */
    public static ArrayList<Waypoint> vacuumRectangle( int a_side, int b_side, int speed ){
        ArrayList<Waypoint> waypoints = new ArrayList();
        final int ROBOT_DIAMETER = 200;//mm
        int x = 0;
        int y = 0;
        
        while( true ){
            waypoints.add( new Waypoint( x, y, speed ) );
            if( y == 0 ) {
                y = b_side;
            }
            else {
                y = 0;
            }
            waypoints.add( new Waypoint( x, y, speed ) );
            x += ROBOT_DIAMETER;
            
            if( (x > a_side) && (x != a_side) )
                x = a_side;
            else if( x == a_side ) {
                break;
            }            
        }
        
        return waypoints;
    }
    
    
    /**
     * Generates waypoints for iRobot to vacuum square area with a_side length by speed
     * @param a_side rectangle a side length in mm
     * @param b_side rectangle b side length in mm
     * @param speed speed of vacuuming in mm/s
     * @return  waypoints to vacuum
     */
    public static ArrayList<Waypoint> vacuumSquare( int a_side, int speed ){
        return vacuumRectangle( a_side, a_side, speed);
    }
}

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
     */
    private int speed;

    public Waypoint(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }    
    
    @Override
    public String toString(){
         return "x: "+x+" y: "+y+" speed: "+speed+"mm/s";
    }
}

package mrs.irobot.baloghkoys;

import java.util.ArrayList;

/**
 *
 * @author Szabi
 */
public class LowLevelDrv {
    private Connector conn;
    public LowLevelSensors llsensors;
    public MidLevelSensors sensors;
    
    /**
     * Constructor with communication interface of type Connector
     * @param conn 
     */
    public LowLevelDrv(Connector conn) {
        this.conn = conn;
        this.llsensors = new LowLevelSensors(conn);
        this.sensors = new MidLevelSensors(conn);
    }
    
    /**
    * Initialize and set safe mode on robot
    */
    public void init() {
        Logger.log("lldrv:init()",0);
        byte buf[] = {(byte)128,(byte)131}; // Start, SafeMode
        conn.sendByte(buf);
    }
    
    /**
    * Run or stop demo on robot
    *
    * @param    no  -1(off), 0-9(demo)
    * @return      void
    * @see         init
    */
    public void demo(int no) {
        if(no<-1 || no>9) {
            return;
        }
        Logger.log("lldrv:drive("+no+")",0);
        byte buf[] = {(byte)136, 0x00};
        conn.sendByte(buf);
    }
    
    /**
     * Drive the robot
     * 
     * @param velocity average velocity of the drive wheels in mm/s (-500 - 500)
     * @param radius radius from the center of cicrcle to the center of robot in mm (-2000 - 2000)
     */
    public void drive(int velocity, int radius) {
        if(velocity < -500 || velocity > 500) {
            return;
        }
        if(radius < -2000 || radius > -2000) {
            
        }
        byte buf[] = {(byte)137, 0x00, 0x00, 0x00, 0x00};
        Logger.log("lldrv:drive("+velocity+","+radius+")",0);
        buf[1] = (byte)((char)velocity >> 8 & 0xff);
        buf[2] = (byte)((char)velocity & 0xff);
        buf[3] = (byte)((char)radius >> 8 & 0xff);
        buf[4] = (byte)((char)radius & 0xff);
        
        conn.sendByte(buf);
    }
    
    /**
     * Go forward
     * 
     * @param velocity average velocity of the drive wheels in mm/s (-500 - 500)
     */
    public void go_forward(int velocity) {
        if(velocity < -500 || velocity > 500) {
            return;
        }
        Logger.log("lldrv:go_forward("+velocity+")",0);
        byte buf[] = {(byte)137, 0x00, 0x00, (byte)0x80, 0x00}; // straight
        buf[1] = (byte)((char)velocity >> 8 & 0xff);
        buf[2] = (byte)((char)velocity & 0xff);
        conn.sendByte(buf);
    }
    
    /**
     * Go backward
     * 
     * @param velocity average velocity of the drive wheels in mm/s (-500 - 500)
     */
    public void go_backward(int velocity) {
        if(velocity < -500 || velocity > 500) {
            return;
        }
        Logger.log("lldrv:go_backward("+velocity+")",0);
        byte buf[] = {(byte)137, 0x00, 0x00, (byte)0x7F, (byte)0xFF}; // straight
        buf[1] = (byte)((char)(-velocity) >> 8 & 0xff);
        buf[2] = (byte)((char)(-velocity) & 0xff);
        conn.sendByte(buf);
    }
    
    /**
     * Turn clockwise
     * 
     * @param velocity average velocity of the drive wheels in mm/s (-500 - 500)
     */
    public void turn_clockwise(int velocity) {
        if(velocity < -500 || velocity > 500) {
            return;
        }
        Logger.log("lldrv:turn_clockwise("+velocity+")",0);
        byte buf[] = {(byte)137, 0x00, 0x00, (byte)0xFF, (byte)0xFF}; // straight
        buf[1] = (byte)((char)velocity >> 8 & 0xff);
        buf[2] = (byte)((char)velocity & 0xff);
        conn.sendByte(buf);
    }
    
    /**
     * Turn counter-clockwise
     * 
     * @param velocity average velocity of the drive wheels in mm/s (-500 - 500)
     */
    public void turn_counterclockwise(int velocity) {
        if(velocity < -500 || velocity > 500) {
            return;
        }
        Logger.log("lldrv:turn_counterclockwise("+velocity+")",0);
        byte buf[] = {(byte)137, 0x00, 0x00, (byte)0x00, (byte)0x01}; // straight
        buf[1] = (byte)((char)velocity >> 8 & 0xff);
        buf[2] = (byte)((char)velocity & 0xff);
        conn.sendByte(buf);
    }
    
    /**
     * Controll wheels directly
     * 
     * @param left (signed using two complements) 16-bit speed in mm/s
     * @param right (signed using two complements) 16-bit speed in mm/s
     */
    public void drive_direct(int left, int right) {
        byte buf[] = {(byte)145, 0x00, 0x00, 0x00, 0x00};
        buf[1] = (byte)((char)right >> 8 & 0xff);
        buf[2] = (byte)((char)right & 0xff);
        buf[3] = (byte)((char)left >> 8 & 0xff);
        buf[4] = (byte)((char)left & 0xff);
        Logger.log("lldrv:drive_direct("+left+","+right+")",0);
        conn.sendByte(buf);
    }
    
    /**
     * Stop the robot
     */
    public void stop() {
        Logger.log("lldrv:stop()",0);
        byte buf[] = {(byte)145, 0x00, 0x00, 0x00, 0x00};
        conn.sendByte(buf);
    }
       
    /**
     * Set LEDs on Creative
     * 
     * @param Advance   Advance led status (1=on, 0=off)
     * @param Play  Play led status (1=on, 0=off)
     * @param PowerLedColor Power LED Color 0(green) - 255(red)
     * @param PowerLedIntensity PowerLedIntensity 0(Off) - 255(FullIntensity)
     */
    public void set_leds(boolean Advance, boolean Play, int PowerLedColor, int PowerLedIntensity) {
        if(PowerLedColor < 0 || PowerLedColor > 255) {
            return;
        }
        if(PowerLedIntensity < 0 || PowerLedIntensity > 255) {
            return;
        }
        byte buf[] = {(byte)139, 0x00, 0x00, 0x00};
        buf[1] = (byte)((Advance ? 4:0) + (Play ? 2:0));
        buf[2] = (byte) PowerLedColor;
        buf[3] = (byte) PowerLedIntensity;
        Logger.log("lldrv:set_leds("+ Advance+ "," +Play+ ","+PowerLedColor+","+PowerLedIntensity+")",0);
        conn.sendByte(buf);
    }
    
    /**
     * Request the OI to send a packet of sensor data bytes
     * 
     * @param no Packet id
     * @see Create Open Interface Sensor Packet
     * @see Sensors Quick Reference
     */
    public byte[] sensor_packet(int no) {
        //implemented in subclass
        return llsensors.sensor_packet(no);
    }
    
   /**
     * Request the OI to send a packets of sensor data bytes
     * !!!not implemented in subclass in time of writing this!!!
     * USE sensor_packet instead
     * 
     * @param no[] Packet id's
     * @return Response is in order of request
     * @see Create Open Interface Sensor Packet
     * @see Sensors Quick Reference
     */
    protected byte[] sensor_packets(int[] no) {
        return llsensors.sensor_packets(no);
    }
    
    public void go_forward(int velocity, int dist) {
        int direction = (velocity<0||dist<0 ? -1:1);
        int sdistance = this.sensors.distance();
        this.go_forward(Math.abs(velocity)*direction);
        if(direction == 1) {
            while(this.sensors.distance() < sdistance + Math.abs(dist)) {
                this.sensors.query();
            }
        }else{
            while(this.sensors.distance() > sdistance - Math.abs(dist)) {
                this.sensors.query();
            }
        }
        this.stop();
    }
    
    
    public void go_forward(int velocity, int dist, ArrayList<Waypoint> waypointslog) {
        int direction = (velocity<0||dist<0 ? -1:1);
        int sdistance = this.sensors.distance();
        this.go_forward(Math.abs(velocity)*direction);
        if(direction == 1) {
            while(this.sensors.distance() < sdistance + Math.abs(dist)) {
                this.sensors.query();
                Waypoint wpt = new Waypoint( (int)sensors.get_x_position(), (int)sensors.get_y_position(), sensors.angle(), sensors.getLast_reply());
                waypointslog.add(wpt);
                
            }
        }else{
            while(this.sensors.distance() > sdistance - Math.abs(dist)) {
                this.sensors.query();
                Waypoint wpt = new Waypoint( (int)sensors.get_x_position(), (int)sensors.get_y_position(), sensors.angle(), sensors.getLast_reply());
                waypointslog.add(wpt);
            }
        }
        this.stop();
    }
    
    public void turn(int velocity, int ang) {
        int sangle = this.sensors.angle();
        if(ang > 0) { 
            this.turn_clockwise(velocity);
            while(this.sensors.angle() > sangle - ang) {
                this.sensors.query();
            }
        } else {
            this.turn_counterclockwise(velocity);
            while(this.sensors.angle() < sangle - ang) {
                this.sensors.query();
            }
        }
        this.stop();
    }
    
    public void turn(int velocity, int ang, ArrayList<Waypoint> waypointslog ) {
        int sangle = this.sensors.angle();
        if(ang > 0) { 
            this.turn_clockwise(velocity);
            while(this.sensors.angle() > sangle - ang) {
                this.sensors.query();
                Waypoint wpt = new Waypoint( (int)sensors.get_x_position(), (int)sensors.get_y_position(), sensors.angle(), sensors.getLast_reply());
                waypointslog.add(wpt);
            }
        } else {
            this.turn_counterclockwise(velocity);
            while(this.sensors.angle() < sangle - ang) {
                this.sensors.query();                
                Waypoint wpt = new Waypoint( (int)sensors.get_x_position(), (int)sensors.get_y_position(), sensors.angle(), sensors.getLast_reply());
                waypointslog.add(wpt);
            }
        }
        this.stop();
    }
   
}

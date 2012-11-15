/*
 * TODO: check byte*To*Int() functions 
 */
package mrs.irobot.baloghkoys;

/**
 *
 * @author Szabi
 */
public class MidLevelSensors {
    private Connector conn;
    byte last_reply[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; //60x 0x00
    boolean lock = true;
    
    /**
     * Constructor
     * @param conn - connection to robot 
     */
    public MidLevelSensors(Connector conn) {
        this.conn = conn;
        this.lock = false;
    }
    
    /**
     * Request the OI to send a packet of sensors data bytes
     * 
     * @see Create Open Interface Sensor Packet
     * @see Sensors Quick Reference
     */
    private void sense() {
        byte buf[] = {(byte)142, 0x06};

        conn.sendByte(buf);
        
        //get and process reply
        for (int i=0; i<52; i++) {
            this.last_reply[i] = conn.receiveByte();
        }

        Logger.log("midlvldrv:sense()",0);
    }

    /**
     * Wait for next sensor data
     * Don't read sensors data more, than once per 15ms
     */
    private void sensor_wait() {
        try {
            Thread.sleep(15);
        } catch (InterruptedException ex) {
            System.err.println(ex.toString());
        }
    }
    
    /**
     * Query the robot to send data
     */
    public void query() {
        if(this.lock) {
            Logger.log("midlvldrv: query request under lock!");
            return;
        }
        this.lock = true;
        
        this.sensor_wait();
        this.sense();
        
        this.lock = false;
    }
    
    /**
     * Conversion of 2 bytes to signed 16bit integer
     * TODO: check conversion of 2 bytes to signed integer
     * TODO: should work
     * 
     * @param high  high byte
     * @param low   low byte
     * @return 
     */
    private int bytesToSignedInt(byte high, byte low) {
        return (short)(((high & 0xff) << 8) + (low & 0xff));
    }
    
    /**
     * Conversion of 2 bytes to unsigned 16bit integer
     * TODO: check conversion of 2 bytes to unsigned integer
     * TODO: should work
     * 
     * @param high  high byte
     * @param low   low byte
     * @return 
     */
    private int bytesToUnsignedInt(byte high, byte low) {
        return (int)(((low & 0xff) << 8) + (high & 0xff));
    }

        /**
     * Conversion of 2 bytes to signed 16bit integer
     * TODO: check conversion of 2 bytes to signed integer
     * TODO: should work
     * 
     * @param high  high byte
     * @param low   low byte
     * @return 
     */
    private int byteToSignedInt(byte b) {
        return (int)(b);
    }
    
    /**
     * Conversion of 2 bytes to unsigned 16bit integer
     * TODO: check conversion of 2 bytes to unsigned integer
     * TODO: should work
     * 
     * @param high  high byte
     * @param low   low byte
     * @return 
     */
    private int byteToUnsignedInt(byte b) {
        return (int)(b & 0xff);
    }
    
    public boolean[] get_cliff_sensors(){
        boolean[] sensors = { last_reply[2] == 1, last_reply[3] == 1, last_reply[4] == 1, last_reply[5] == 1 }; 
        return sensors;
    }
    
    public boolean[] get_wheel_drops(){
        byte t = bumps_and_wheel_drops();
        boolean b[] = new boolean[3];
        b[0] = (t&0x08)==0x08;
        b[1] = (t&0x10)==0x10;
        b[2] = (t&0x04)==0x04;
        Logger.log("wheel drops: "+t,0);   // hadze to haluze 0 -114 mozno kvoli mojmu bluetoothu
        return b;
    }
    
    public boolean virtual_wall() {
        return last_reply[6] == 1;
    }
    
    public boolean wall() {
        return last_reply[1] == 1;
    }
    
    public boolean bump_left(){
        byte t = bumps_and_wheel_drops();
        return (t&0x02)==0x02;
    }
    
    public boolean bump_right(){
        byte t = bumps_and_wheel_drops();
        return (t&0x01)==0x01;
    }
    
    public byte bumps_and_wheel_drops() {
        return last_reply[0];
    }
    
    public int battery_capacity() {
        return this.bytesToUnsignedInt(last_reply[24], last_reply[25]);
    }
    
    public int battery_charge() {
        return this.bytesToUnsignedInt(last_reply[22], last_reply[23]);
    }
    
    public int battery_current() {
        return this.bytesToSignedInt(last_reply[19], last_reply[20]);
    }
    
    public int requested_velocity() {
        return this.bytesToSignedInt(last_reply[44], last_reply[45]);
    }
    
    public int distance() {
        return this.bytesToSignedInt( last_reply[12], last_reply[13] );
    }
    
    /**
     * temporary method
     * TODO return double
     * @return nothing
     */
    public int angle(){
        return 0;
    }
}

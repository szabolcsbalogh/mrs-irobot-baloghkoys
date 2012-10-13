/*
 * TODO: check byte*To*Int() functions 
 */
package mrs.irobot.baloghkoys;

/**
 *
 * @author Szabi
 */
public class LowLevelSensors {
    private Connector conn;
    
    public LowLevelSensors(Connector conn) {
        this.conn = conn;
    }
    
    /**
     * Request the OI to send a packet of sensor data bytes
     * 
     * @param no Packet id
     * @see Create Open Interface Sensor Packet
     * @see Sensors Quick Reference
     */
    protected byte[] sensor_packet(int no) {
        int reply_lens[] = {26, 10, 6, 10, 14, 12, 52, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 1, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 1, 2, 1, 1, 1, 1, 1, 2, 2, 2, 2};
        byte reply[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; //60x 0x00
        byte buf[] = {(byte)142, 0x00};
        int reply_len;
        if (no<0 || no > 42) {
            return reply;
        }

        //send request
        conn.sendByte(buf);
        
        //get and process reply
        reply_len = reply_lens[no];
        for (int i=0; i<reply_len; i++) {
            reply[i] = conn.receiveByte();
        }
        
        Logger.log("lldrv:sensors:sensor_packet(" + no + ")");
        return reply;
    }
    
    /**
     * Request the OI to send a packets of sensor data bytes
     * !!!not implemented!!!
     * USE sensor_packet instead
     * 
     * @param no[] Packet id's
     * @return Response is in order of request
     * @see Create Open Interface Sensor Packet
     * @see Sensors Quick Reference
     */
    protected byte[] sensor_packets(int[] no) {
        byte buf[] = {(byte)149, 0x00};
        byte ret[] = {0x00, 0x00};
        /*
        if (no<0 || no > 42) {
            return;
        }
        Logger.log("lldrv:sensor_packet(" + no + ")"); 
        */
        Logger.log("lldrv:WARRNING:sensor_packets not implemented yet.");
        //conn.sendByte(buf);
        return ret;
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
     * Bumps and Wheel Drops
     * 
     * @return Bumps and Wheel Drops state as byte
     * 4. bit = Wheeldrop Caster (boolean) ((ret >> 4 & 0x01) == 1)
     * 3. bit = Wheeldrop Left (boolean) ((ret >> 3 & 0x01) == 1)
     * 2. bit = Wheeldrop Right (boolean) ((ret >> 2 & 0x01) == 1)
     * 1. bit = Bump Left (boolean) ((ret >> 1 & 0x01) == 1)
     * 0. bit = Bump Right (boolean) ((ret >> 0 & 0x01) == 1)
     */
    public byte bumps_and_wheel_drops() {
        byte t[] = this.sensor_packet(7);
        return t[0];
    }
    
    /**
     * Wall sensor state
     * 
     * @return 1=wall seen 0=no wall
     */
    public boolean wall() {
        byte t[] = this.sensor_packet(8);
        return t[0] == 1;
    }
    
    /**
     * State of cliff sensor on the left side
     * 
     * @return 1=cliff 0=no cliff
     */
    public boolean cliff_left() {
        byte t[] = this.sensor_packet(9);
        return t[0] == 1;
    }
    
    /**
     * State of cliff sensor on the front left side
     * 
     * @return 1=cliff 0=no cliff
     */
    public boolean cliff_front_left() {
        byte t[] = this.sensor_packet(10);
        return t[0] == 1;
    }
    
    /**
     * State of cliff sensor on the front right side
     * 
     * @return 1=cliff 0=no cliff
     */
    public boolean cliff_front_right() {
        byte t[] = this.sensor_packet(11);
        return t[0] == 1;
    }
    
    /**
     * State of cliff sensor on the right side
     * 
     * @return 1=cliff 0=no cliff
     */
    public boolean cliff_right() {
        byte t[] = this.sensor_packet(12);
        return t[0] == 1;
    }
    
    /**
     * State of virtual wall sensor
     * 
     * @return 1=virtual wall 0=no wall
     */
    public boolean virtual_wall() {
        byte t[] = this.sensor_packet(13);
        return t[0] == 1;
    }
    
    /**
     * Play and Advance button status
     * @return 2.bit is Advance ((ret >> 2 & 0x01) == 1)
     * 0.bit is Play ((ret >> 0 & 0x01) == 1)
     * button status
     * 
     */
    public byte button_state() {
        byte t[] = this.sensor_packet(18);
        return t[0];
    }
    
    /**
     * Distance of robot center (sum of both wheel distances / 2) since last read
     * 
     * @return signed integer as distance
     */
    public int distance() {
        byte t[] = this.sensor_packet(19);
        return this.bytesToSignedInt(t[0], t[1]);
    }
    
    /**
     * Angle since last read in degrees
     * 
     * @return angle in degrees, counter-clockwise is positive
     */
    public int angle() {
        byte t[] = this.sensor_packet(20);
        return this.bytesToSignedInt(t[0], t[1]);
    }
    
    /**
     * Get charging state
     * 
     * @return 
     * 0 - not charging
     * 1 - reconditioning charging
     * 2 - full charging
     * 3 - trickle charging
     * 4 - waiting
     * 5 - charging fault indicator
     */
    public int charging_state() {
        byte t[] = this.sensor_packet(21);
        return this.byteToUnsignedInt(t[0]);
    }
    
    /**
     * Get battery voltage
     * 
     * @return voltage in mV
     */
    public int battery_voltage() {
        byte t[] = this.sensor_packet(22);
        return this.bytesToUnsignedInt(t[0], t[1]);
    }
    
    /**
     * Get battery current
     * 
     * @return current in mA (positive = into battery(charging) , negative = from battery);
     */
    public int battery_current() {
        byte t[] = this.sensor_packet(23);
        return this.bytesToSignedInt(t[0], t[1]);
    }
    
    /**
     * Get battery temperature
     * 
     * @return degrees
     */
    public int battery_temperature() {
        byte t[] = this.sensor_packet(24);
        return this.byteToSignedInt(t[0]);
    }
    
    /**
     * Get battery charge
     * 
     * @return battery current charge in mAh;
     */
    public int battery_charge() {
        byte t[] = this.sensor_packet(25);
        return this.bytesToUnsignedInt(t[0], t[1]);
    }
    
    /**
     * Get battery capacity
     * 
     * @return battery current capacity in mAh;
     */
    public int battery_capacity() {
        byte t[] = this.sensor_packet(26);
        return this.bytesToUnsignedInt(t[0], t[1]);
    }
    
    /**
     * Get wall sensor signal
     * 
     * @return signal strength
     */
    public int wall_signal() {
        byte t[] = this.sensor_packet(27);
        return this.bytesToUnsignedInt(t[0], t[1]);
    }

    /**
     * Get Left cliff sensor signal
     * 
     * @return signal strength
     */
    public int cliff_left_signal() {
        byte t[] = this.sensor_packet(28);
        return this.bytesToUnsignedInt(t[0], t[1]);
    }

    /**
     * Get Front Left cliff sensor signal
     * 
     * @return signal strength
     */
    public int cliff_front_left_signal() {
        byte t[] = this.sensor_packet(29);
        return this.bytesToUnsignedInt(t[0], t[1]);
    }

    /**
     * Get Front Right cliff sensor signal
     * 
     * @return signal strength
     */
    public int cliff_front_right_signal() {
        byte t[] = this.sensor_packet(30);
        return this.bytesToUnsignedInt(t[0], t[1]);
    }

    /**
     * Get Right cliff sensor signal
     * 
     * @return signal strength
     */
    public int cliff_right_signal() {
        byte t[] = this.sensor_packet(31);
        return this.bytesToUnsignedInt(t[0], t[1]);
    }

    /**
     * Get OI Mode
     * 
     * @return 
     * 0 - off
     * 1 - Passive
     * 2 - Safe
     * 3 - Full
     */
    public int OI_mode() {
        byte t[] = this.sensor_packet(35);
        return this.byteToUnsignedInt(t[0]);
    }

    /**
     * Return last requested velocity
     * 
     * @return +-500 mm/s
     */
    public int requested_velocity() {
        byte t[] = this.sensor_packet(39);
        return this.bytesToSignedInt(t[0], t[1]);
    }
    
    /**
     * Return last requested radius
     * 
     * @return -32768 - 32767 mm
     */
    public int requested_radius() {
        byte t[] = this.sensor_packet(40);
        return this.bytesToSignedInt(t[0], t[1]);
    }
    
    /**
     * Return last requested right wheel velocity with drive direct command
     * 
     * @return +-500 mm/s
     */
    public int requested_right_velocity() {
        byte t[] = this.sensor_packet(41);
        return this.bytesToSignedInt(t[0], t[1]);
    }
    
    /**
     * Return last requested right wheel velocity with drive direct command
     * 
     * @return +-500 mm/s
     */
    public int requested_left_velocity() {
        byte t[] = this.sensor_packet(42);
        return this.bytesToSignedInt(t[0], t[1]);
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
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mrs.irobot.baloghkoys;

/**
 *
 * @author Szabi
 */
public class LowLevelDrv {
    Connector conn;
    
    public LowLevelDrv(Connector conn) {
        this.conn = conn;
    }
    
    /**
    * Initialize and set safe mode on robot
    */
    public void init() {
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
        Logger.log("lldrv:drive("+velocity+","+radius+")");
        buf[1] = (byte)((char)velocity >> 8 & 0xff);
        buf[2] = (byte)((char)velocity & 0xff);
        buf[3] = (byte)((char)radius >> 8 & 0xff);
        buf[4] = (byte)((char)radius & 0xff);
        
        conn.sendByte(buf);
    }
    
    public void stop() {
        Logger.log("lldrv:stop()");
        byte buf[] = {(byte)145, 0x00, 0x00, 0x00, 0x00};
        conn.sendByte(buf);
    }
    
    
}

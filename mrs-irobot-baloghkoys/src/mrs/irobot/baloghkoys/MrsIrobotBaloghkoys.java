/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mrs.irobot.baloghkoys;
import javax.comm.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author baloghsz
 */
public class MrsIrobotBaloghkoys {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Connector connector = new Connector();
        
        String wantedPortName = "COM5";//"/dev/ttyS0";
        
        System.err.println("Available ports");
        connector.printPortNames();
        connector.openPort( wantedPortName );
        
        GUI gui = new GUI();
        gui.setVisible(true);
        
        byte[] data = {(byte)128,(byte)131,(byte)136,(byte)3};
        connector.sendByte(data);
        
        try {
            Thread.sleep(5*1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(MrsIrobotBaloghkoys.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        data[2] = 0;
        connector.sendByte(data);
        
    }
}

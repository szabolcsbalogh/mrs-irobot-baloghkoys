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
        
        String driverName = "com.sun.comm.Win32Driver";
        CommDriver commdriver;
        try {
                // TODO code application logic here
                commdriver = (CommDriver)Class.forName(driverName).newInstance( );
                commdriver.initialize();
        } catch (Exception ex) {
            Logger.getLogger(MrsIrobotBaloghkoys.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String wantedPortName = "COM5";//"/dev/ttyS0";
        
        Enumeration portIdentifiers = CommPortIdentifier.getPortIdentifiers();
        CommPortIdentifier portId = null;  // will be set if port found
        while (portIdentifiers.hasMoreElements())
        {
            CommPortIdentifier pid = (CommPortIdentifier) portIdentifiers.nextElement();
            System.err.println(pid.getName());
            if(pid.getPortType() == CommPortIdentifier.PORT_SERIAL &&
               pid.getName().equals(wantedPortName)) 
            {
                portId = pid;
                break;
            }
        }
        
        GUI gui = new GUI();
        gui.setVisible(true);
        
        if(portId == null)
        {
            System.err.println("Could not find serial port " + wantedPortName);
            //System.exit(1);
        }
    }
}
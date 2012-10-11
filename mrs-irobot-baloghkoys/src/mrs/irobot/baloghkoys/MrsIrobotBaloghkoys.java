/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mrs.irobot.baloghkoys;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author baloghsz
 */
public class MrsIrobotBaloghkoys {

    static String wantedPortName = "COM23";//"/dev/ttyS0";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Logger.log( "iRobot application log started" );
        
        Connector connector = new Connector();
            
        System.out.println("Available ports");
        connector.printPortNames();
        Logger.log( "Opening " + wantedPortName );
        if(!connector.openPort( wantedPortName ) ) {
            Logger.log("Failed to open " + wantedPortName);
        }
        
        Logger.log("Starting low level driver.");
        LowLevelDrv lldrv = new LowLevelDrv(connector);
        lldrv.init();
        
        GUI gui = new GUI();
        gui.setVisible(true);
        
        
        //byte[] data = {(byte)128,(byte)131,(byte)136,(byte)3};
        //connector.sendByte(data);
        
        lldrv.drive(100, 100);
        try {
            Thread.sleep(5*1000);
        } catch (InterruptedException ex) {
            Logger.log("No SLEEP function!!!");
        }
        lldrv.stop();
        //data[2] = 0;
        //connector.sendByte(data);
       
      //  Thread blikanie = new Thread(new Runnable() {
      //          public void run() {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
                    while( true ){      // blikanie senzorov
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException ex) {
                            System.err.println(ex.toString());
                        }
                        //gui.jPanel1.repaint();  // netreba repaint staci ze editnem timeLabel
                        Date date = new Date();
                        gui.timeLabel.setText( dateFormat.format(date) );
                    }
      //          }
      //  });
            
        
    }
}

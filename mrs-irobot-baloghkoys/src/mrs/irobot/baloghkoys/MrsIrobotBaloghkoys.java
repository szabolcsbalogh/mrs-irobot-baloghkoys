/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mrs.irobot.baloghkoys;

import java.io.IOException;

/**
 *
 * @author baloghsz
 */
public class MrsIrobotBaloghkoys {

    static String wantedPortName = "COM3";//"/dev/ttyS0";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Logger.log( "iRobot application log started" );
        
        Connector connector = new Connector();
            
        System.out.println("Available ports");
        connector.printPortNames();
        connector.openPort( wantedPortName );
        Logger.log( "Opening " + wantedPortName );
        
        GUI gui = new GUI();
        gui.setVisible(true);
        
        /*
        byte[] data = {(byte)128,(byte)131,(byte)136,(byte)3};
        connector.sendByte(data);
        
        try {
            Thread.sleep(5*1000);
        } catch (InterruptedException ex) {
            System.err.println(ex.toString());
        }
        
        data[2] = 0;
        connector.sendByte(data);*/
       
      //  Thread blikanie = new Thread(new Runnable() {
      //          public void run() {
                    while( true ){      // blikanie senzorov
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException ex) {
                            System.err.println(ex.toString());
                        }
                        gui.jPanel1.repaint();   
                    }
      //          }
      //  });
            
        
    }
}

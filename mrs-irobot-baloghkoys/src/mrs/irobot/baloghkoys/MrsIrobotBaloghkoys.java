/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mrs.irobot.baloghkoys;

/**
 *
 * @author baloghsz
 */
public class MrsIrobotBaloghkoys {

    static String wantedPortName = ""; //"COM17";//"/dev/ttyS0";
        
    static GUI gui = new GUI();
    
    static void Sleep(int ms){        
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            System.err.println(ex.toString());
        }
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Logger.log( "iRobot application log started", 10 );
        
        Connector connector = new Connector();
            
        //System.out.println("Available ports");
        //connector.printPortNames();
        
        SelectPortGUI portGUI = new SelectPortGUI();
        portGUI.setVisible(true);
        
            
        while( wantedPortName.isEmpty() ){
            Sleep(100);
        }
        
        Logger.log( "Opening " + wantedPortName,100  );
        if(!connector.openPort( wantedPortName ) ) {
            Logger.log("Failed to open " + wantedPortName);
        }
        
        Logger.log("Starting low level driver.",1);
        LowLevelDrv lldrv = new LowLevelDrv(connector);
        lldrv.init();
        LowLevelSensors llsensors = new LowLevelSensors(connector);
        iRobotImage robotImage = new iRobotImage();
        robotImage.setSensors(llsensors);

        gui.setVisible(true);
        gui.setRobotImage(robotImage);      
       
        
        //byte[] data = {(byte)128,(byte)131,(byte)136,(byte)3};
        //connector.sendByte(data);
        
        lldrv.drive(100, 100);
        Sleep(5000);

        lldrv.stop();
        //data[2] = 0;
        //connector.sendByte(data);
       
        
    }
}

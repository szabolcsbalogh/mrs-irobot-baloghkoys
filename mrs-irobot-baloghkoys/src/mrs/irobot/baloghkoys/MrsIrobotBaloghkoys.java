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
        
    static GUI gui;
    
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
        connector.vitrual_input_data = false;    
              
        SelectPortGUI portGUI = new SelectPortGUI();
        portGUI.setVisible(true);
        portGUI.portsComboBox.setSelectedItem("COM8");
        
        while( wantedPortName.isEmpty() ){
            Sleep(100);
        }
        
        Logger.log( "Opening " + wantedPortName,100  );
        if(!connector.openPort( wantedPortName ) ) {
            Logger.log("Failed to open " + wantedPortName);
            System.exit(1);
        }
        
        Logger.log("Starting low level driver.",1);
        LowLevelDrv lldrv = new LowLevelDrv(connector);
        lldrv.init();
        lldrv.sensors.query();
        lldrv.sensors.reset_angle();
        lldrv.sensors.reset_distance();
        lldrv.sensors.reset_xy();
        iRobotImage robotImage = new iRobotImage();
        robotImage.setSensors(lldrv.sensors);

        //gui should be initialized after lldrv init, because of thread for update in it
        gui = new GUI();
        gui.setRobotImage(robotImage);      
        gui.setLowLevelDrv(lldrv);
        gui.setVisible(true);
        
    }
}

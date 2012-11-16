/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mrs.irobot.baloghkoys;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

/**
 *
 * @author user
 */
public class iRobotImage {
    
        ImageIcon iRobotIcon = new javax.swing.ImageIcon(getClass().getResource("/img/irobot.JPG"));
        ImageIcon[] batteryIcons = new ImageIcon[7];
        private boolean[] cliffSensors = new boolean[4];
        private boolean[] wallSensors  = new boolean[2];
        private boolean[] wheelSensors = new boolean[3];
        private String[]  cliffNames   = {"Left","Left front","Right front","Right"};
        private String[]  wallNames    = {"Virtual wall","Wall"};
        private String[]  wheelNames   = {"Left","Center","Right"};
        private boolean blik = true;
        private int chargingImage = 1;
        
        MidLevelSensors mls;
        
        /**
         * Set all sensor states to false
         * Reads battery icons from files
         */
        public iRobotImage(){
            for( int i=0 ; i<cliffSensors.length ; i++ ){
                cliffSensors[i] = false;
            }
            for( int i=0 ; i<wallSensors.length ; i++ ){
                wallSensors[i] = false;
            }
            for( int i=0 ; i<wheelSensors.length ; i++ ){
                wheelSensors[i] = false;
            }      
            for( int i=0 ; i<=5 ; i++ ){
                batteryIcons[i] = new ImageIcon( getClass().getResource("/img/battery/battery_"+i+".jpg") );
            }
            batteryIcons[6] = new ImageIcon( getClass().getResource("/img/battery/battery_1_green.jpg") );
                
        }
        
        
    /**
     * Set LowLevelSensors class instance for displaying sensors actual state
     * @param lls
     */
    public void setSensors( MidLevelSensors mls ){
        this.mls=mls;
    }
        
    /**
     * Draws iRobot with sensor diagnostic
     * @return Image with iRobot and actual state of its sensors
     */
    public Image getImage() {
        
        boolean lastCliffSensors[] = cliffSensors;
        boolean lastWheelSensors[] = wheelSensors;
        boolean lastWallSensors[] = wallSensors;
        
        mls.query(); 
        
        cliffSensors  =mls.get_cliff_sensors();
        wheelSensors  =mls.get_wheel_drops();
        wallSensors[0]=mls.bump_left();//mls.virtual_wall();
        wallSensors[1]=mls.bump_right();//mls.wall();
               
        for( int i=0; i< cliffNames.length ; i++ ) {
            if( cliffSensors[i]!=lastCliffSensors[i] ) {
                Logger.log( cliffNames[i]+" cliff sensor "+(cliffSensors[i]?"triggered":"ok"), 1);
            }
        }
        
        for( int i=0; i< wallNames.length ; i++ ) {
            if( wallSensors[i]!=lastWallSensors[i] ) {
                Logger.log( wallNames[i]+" sensor "+(wallSensors[i]?"triggered":"ok"), 1);
            }
        }
        
        for( int i=0; i< wheelNames.length ; i++ ) {
            if( wheelSensors[i]!=lastWheelSensors[i] ) {
                Logger.log( wheelNames[i]+" sensor "+(wheelSensors[i]?"triggered":"ok"), 1);
            }
        }
        
        int w  = iRobotIcon.getIconWidth();
        int h  = iRobotIcon.getIconHeight();
        int wb = batteryIcons[0].getIconWidth();
        int hb = batteryIcons[0].getIconHeight();

        BufferedImage dimg = new BufferedImage(iRobotIcon.getIconWidth(), iRobotIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = dimg.createGraphics();
        g.drawImage(iRobotIcon.getImage(), 0, 0, null);
        
        double battery_percent = 0;
        if( mls.battery_capacity() > 0 )
            battery_percent = mls.battery_charge()/mls.battery_capacity();         
        int imageNumber = (int) (battery_percent*5+0.5);  
        if( mls.battery_current() <= 0 ) {
            g.drawImage( batteryIcons[imageNumber].getImage(), w/2-wb/2, h/2-hb/2, null);
        }else{
            if( chargingImage <= 1 ) chargingImage = 6;
            g.drawImage( batteryIcons[chargingImage].getImage(), w/2-wb/2, h/2-hb/2, null);
            if( chargingImage == 6 ) chargingImage = 1;
            chargingImage++;
            chargingImage%=6;
        }
/*
        if(mls.wheel_bump_left()){ 
            this.drawLeftWheel(g,Color.ORANGE);
        }
        if(mls.wheel_bump_right()) {
            this.drawRightWheel(g,Color.ORANGE);
        }
*/        
        if (cliffSensors[0] && blik || (!blik && cliffSensors[0] && !lastCliffSensors[0]) ) {
            this.drawLeftCliffSensor(g, Color.RED);
        }
        if (cliffSensors[1] && blik || (!blik && cliffSensors[1] && !lastCliffSensors[1]) ) {
            this.drawLeftFrontCliffSensor(g, Color.RED);
        }
        if (cliffSensors[2] && blik || (!blik && cliffSensors[2] && !lastCliffSensors[2]) ) {
            this.drawRightFrontCliffSensor(g, Color.RED);
        }
        if (cliffSensors[3] && blik || (!blik && cliffSensors[3] && !lastCliffSensors[3]) ) {
            this.drawRightCliffSensor(g, Color.RED);
        }
        
        g.setStroke(new BasicStroke(10F));
        if (wheelSensors[0] && blik || (!blik && wheelSensors[0] && !lastWheelSensors[0]) ) {
            this.drawLeftWheel(g,Color.RED);
        }
        if (wheelSensors[1] && blik || (!blik && wheelSensors[1] && !lastWheelSensors[1])) {
            this.drawCenterWheel(g, Color.RED);
        }
        if (wheelSensors[2] && blik || (!blik && wheelSensors[2] && !lastWheelSensors[2])) {
            this.drawRightWheel(g,Color.RED);
        }

        if (wallSensors[0] && blik || (!blik && wallSensors[0] && !lastWallSensors[0])) {
            this.drawLeftWallSensor(g, Color.RED);
        }
        if (wallSensors[1] && blik || (!blik && wallSensors[1] && !lastWallSensors[1])) {
            this.drawRightWallSensor(g, Color.RED);
        }
        
        g.setColor(Color.GREEN);
        g.setStroke(new BasicStroke(5F));
        if (!cliffSensors[0]) {
            this.drawLeftCliffSensor(g, Color.GREEN);
        }
        if (!cliffSensors[1]) {
            this.drawLeftFrontCliffSensor(g, Color.GREEN);
        }
        if (!cliffSensors[2]) {
            this.drawRightFrontCliffSensor(g, Color.GREEN);
        }
        if (!cliffSensors[3]) {
            this.drawRightCliffSensor(g, Color.GREEN);
        }

        if (!wheelSensors[0]) {
            this.drawLeftWheel(g,Color.GREEN);
        }
        if (!wheelSensors[2]) {
            this.drawRightWheel(g,Color.GREEN);
        }
        if (!wheelSensors[1]) {
            this.drawCenterWheel(g, Color.GREEN);
        }
       
        if (!wallSensors[0]) {
            this.drawLeftWallSensor(g, Color.GREEN);
        }
        if (!wallSensors[1]) {
            this.drawRightWallSensor(g, Color.GREEN);
        }
       
        
        g.dispose();
        blik = !blik;
        return new ImageIcon(dimg).getImage();
    }
    
    
    private void drawLeftWheel( Graphics2D g, Color color ){   
        g.setColor(color);  
        g.setStroke(new BasicStroke(10F));   
        g.drawLine(31, 195, 43, 195);  
    }
    
    private void drawRightWheel( Graphics2D g, Color color ){  
        g.setColor(color);
        g.setStroke(new BasicStroke(10F)); 
        g.drawLine(248, 195, 260, 195);
    }
    
    private void drawCenterWheel( Graphics2D g, Color color ){  
        g.setColor(color);
        g.setStroke(new BasicStroke(9F));
        g.drawLine(143, 47, 150, 47);
    }
       
    private void drawLeftWallSensor( Graphics2D g, Color color ){  
        g.setColor(color);
        g.setStroke(new BasicStroke(10F));
        g.drawArc( -5,  0, 295, 305, 95, 70);
    }
    
    private void drawRightWallSensor( Graphics2D g, Color color ){  
        g.setColor(color);
        g.setStroke(new BasicStroke(10F));
        g.drawArc( 0,  -1, 295, 300, 15, 70);
    }
    
    
    private void drawLeftCliffSensor( Graphics2D g, Color color ){  
        g.setColor(color);
        g.setStroke(new BasicStroke(5F));
        g.drawLine( 14, 103, 26, 80);       
    }
    
    private void drawLeftFrontCliffSensor( Graphics2D g, Color color ){  
        g.setColor(color);
        g.setStroke(new BasicStroke(5F));
        g.drawLine( 97, 19, 121, 13);      
    }
       
    private void drawRightFrontCliffSensor( Graphics2D g, Color color ){  
        g.setColor(color);
        g.setStroke(new BasicStroke(5F));
        g.drawLine(168, 13, 194, 21);       
    }
    
    private void drawRightCliffSensor( Graphics2D g, Color color ){  
        g.setColor(color);
        g.setStroke(new BasicStroke(5F));
        g.drawLine(264, 79, 275, 102);      
    }
}


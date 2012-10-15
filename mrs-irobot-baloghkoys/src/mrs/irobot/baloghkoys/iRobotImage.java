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
import mrs.irobot.baloghkoys.LowLevelSensors;

/**
 *
 * @author user
 */
public class iRobotImage {
    
        ImageIcon iRobotIcon = new javax.swing.ImageIcon(getClass().getResource("/img/irobot.JPG"));
        private boolean[] cliffSensors = new boolean[4];
        private boolean[] wallSensors  = new boolean[2];
        private boolean[] wheelSensors = new boolean[3];
        private String[]  cliffNames   = {"Left","Left front","Right front","Right"};
        private String[]  wallNames    = {"Virtual wall","Wall"};
        private String[]  wheelNames   = {"Left","Center","Right"};
        private boolean blik = true;
        
        LowLevelSensors lls;
        
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
            // pre ukazku:
           // cliffSensors[0]=cliffSensors[1]=true;
           // wheelSensors[0]=true;
           // wallSensors[0]=true;
        }
        
        
    /**
     * Set LowLevelSensors class instance for displaying sensors actual state
     * @param lls
     */
    public void setSensors( LowLevelSensors lls ){
        this.lls=lls;
    }
        
    public Image getImage() {
        
        boolean lastCliffSensors[] = cliffSensors;
        boolean lastWheelSensors[] = wheelSensors;
        boolean lastWallSensors[] = wallSensors;
        
        cliffSensors  =lls.get_cliff_sensors();
        wheelSensors  =lls.get_wheel_drops();
        wallSensors[0]=lls.virtual_wall();
        wallSensors[1]=lls.wall();
               
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
        
        int w = iRobotIcon.getIconWidth();
        int h = iRobotIcon.getIconHeight();

        BufferedImage dimg = new BufferedImage(iRobotIcon.getIconWidth(), iRobotIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = dimg.createGraphics();
        g.drawImage(iRobotIcon.getImage(), 0, 0, null);
                
        g.setColor(Color.RED);
        g.setStroke(new BasicStroke(5F));
        if (cliffSensors[0] && blik || (!blik && cliffSensors[0] && !lastCliffSensors[0]) ) {
            g.drawLine( 14, 103, 26, 80);
        }
        if (cliffSensors[1] && blik || (!blik && cliffSensors[1] && !lastCliffSensors[1]) ) {
            g.drawLine( 97, 19, 121, 13);
        }
        if (cliffSensors[2] && blik || (!blik && cliffSensors[2] && !lastCliffSensors[2]) ) {
            g.drawLine(168, 13, 194, 21);                
        }
        if (cliffSensors[3] && blik || (!blik && cliffSensors[3] && !lastCliffSensors[3]) ) {
            g.drawLine(264, 79, 275, 102);             
        }

        g.setStroke(new BasicStroke(10F));
        if (wheelSensors[0] && blik || (!blik && wheelSensors[0] && !lastWheelSensors[0]) ) {
            g.drawLine(31, 195, 43, 195);
        }
        if (wheelSensors[1] && blik || (!blik && wheelSensors[1] && !lastWheelSensors[1])) {
            g.setStroke(new BasicStroke(9F));
            g.drawLine(143, 47, 150, 47);
            g.setStroke(new BasicStroke(10F));
        }
        if (wheelSensors[2] && blik || (!blik && wheelSensors[2] && !lastWheelSensors[2])) {
            g.drawLine(248, 195, 260, 195);
        }

        if (wallSensors[0] && blik || (!blik && wallSensors[0] && !lastWallSensors[0])) {
            g.drawArc( -5,  0, 295, 305, 95, 70);
        }
        if (wallSensors[1] && blik || (!blik && wallSensors[1] && !lastWallSensors[1])) {
            g.drawArc( 0,  -1, 295, 300, 15, 70);
        }
                        
        g.setColor(Color.GREEN);
        g.setStroke(new BasicStroke(5F));
        if (!cliffSensors[0]) {
            g.drawLine( 14, 103, 26, 80);
        }
        if (!cliffSensors[1]) {
            g.drawLine( 97, 19, 121, 13);
        }
        if (!cliffSensors[2]) {
            g.drawLine(168, 13, 194, 21);
        }
        if (!cliffSensors[3]) {
            g.drawLine(264, 79, 275, 102);
        }

        g.setStroke(new BasicStroke(10F));
        if (!wheelSensors[0]) {
            g.drawLine(31, 195, 43, 195);
        }
        if (!wheelSensors[2]) {
            g.drawLine(248, 195, 260, 195);
        }
        if (!wheelSensors[1]) {
            g.setStroke(new BasicStroke(9F));
            g.drawLine(143, 47, 150, 47);
        }

        if (!wallSensors[0]) {
            g.drawArc( -5,  0, 295, 305, 95, 70);
        }
        if (!wallSensors[1]) {
            g.drawArc( 0,  -1, 295, 300, 15, 70);
        }
        
        
        g.dispose();
        blik = !blik;
        return new ImageIcon(dimg).getImage();
    }
}

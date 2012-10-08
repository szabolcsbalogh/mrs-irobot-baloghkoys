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
        public static boolean[] stairSensors = new boolean[4];
        public static boolean[] wallSensors  = new boolean[2];
        public static boolean[] wheelSensors = new boolean[3];
        private boolean blik = true;
        
        public iRobotImage(){
            for( int i=0 ; i<stairSensors.length ; i++ ){
                stairSensors[i] = false;
            }
            for( int i=0 ; i<wallSensors.length ; i++ ){
                wallSensors[i] = false;
            }
            for( int i=0 ; i<wheelSensors.length ; i++ ){
                wheelSensors[i] = false;
            }      
            // pre ukazku:
            stairSensors[0]=stairSensors[1]=true;
            wheelSensors[0]=true;
            wallSensors[0]=true;
        }
        
    public Image getImage() {
        int w = iRobotIcon.getIconWidth();
        int h = iRobotIcon.getIconHeight();

        BufferedImage dimg = new BufferedImage(iRobotIcon.getIconWidth(), iRobotIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = dimg.createGraphics();
        g.drawImage(iRobotIcon.getImage(), 0, 0, null);
        
        g.setColor(Color.GREEN);
        g.setStroke(new BasicStroke(5F));
        if (!stairSensors[0]) {
            g.drawLine( 14, 103, 26, 80);
        }
        if (!stairSensors[1]) {
            g.drawLine( 97, 19, 121, 13);
        }
        if (!stairSensors[2]) {
            g.drawLine(168, 13, 194, 21);
        }
        if (!stairSensors[3]) {
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
        
        
        if(blik){
            g.setColor(Color.RED);
            g.setStroke(new BasicStroke(5F));
            if (stairSensors[0]) {
                g.drawLine( 14, 103, 26, 80);
            }
            if (stairSensors[1]) {
                g.drawLine( 97, 19, 121, 13);
            }
            if (stairSensors[2]) {
                g.drawLine(168, 13, 194, 21);
            }
            if (stairSensors[3]) {
                g.drawLine(264, 79, 275, 102);
            }

            g.setStroke(new BasicStroke(10F));
            if (wheelSensors[0]) {
                g.drawLine(31, 195, 43, 195);
            }
            if (wheelSensors[2]) {
                g.drawLine(248, 195, 260, 195);
            }
            if (wheelSensors[1]) {
                g.setStroke(new BasicStroke(9F));
                g.drawLine(143, 47, 150, 47);
            }

            if (wallSensors[0]) {
                g.drawArc( -5,  0, 295, 305, 95, 70);
            }
            if (wallSensors[1]) {
                g.drawArc( 0,  -1, 295, 300, 15, 70);
            }
        }
        g.dispose();
        blik = !blik;
        return new ImageIcon(dimg).getImage();
    }
}

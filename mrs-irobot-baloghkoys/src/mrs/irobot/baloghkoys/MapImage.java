/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mrs.irobot.baloghkoys;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author juko
 */
public class MapImage {
    
    public final int WIDTH = 640;
    public final int HEIGHT = 480;
    public final int SCALE = 20;
    private final int TICK_SIZE = 8;
    
    private int lastX = WIDTH/2;
    private int lastY = HEIGHT/2;
    private BufferedImage image;
   // private Graphics2D g;   
                
    public MapImage(){
        image = new BufferedImage( WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB );
        this.drawGrid();
    }
    
    public Image getImage(){
        return image.getScaledInstance(WIDTH, HEIGHT, 0);
    }
    
    public void addWaypoint( Waypoint w ){
        Graphics2D g = image.createGraphics();
        g.setStroke(new BasicStroke(2F));
        g.setColor(Color.BLUE);
        int x = w.getX()/SCALE+WIDTH/2;
        int y = w.getY()/SCALE+HEIGHT/2;
        g.drawLine(lastX, lastY, x, y);
        g.dispose();    
        g.drawImage(image, null, 0, 0);
        lastX = x;
        lastY = y;
    }
    
    /**
     * Draws grid to mapImage
     */
    private void drawGrid(){
        Graphics2D g = image.createGraphics();
        g.setStroke(new BasicStroke(1F));
        g.setColor(Color.GRAY);
        g.drawLine(0, HEIGHT/2, WIDTH, HEIGHT/2);
        g.drawLine(WIDTH/2, 0, WIDTH/2, HEIGHT);
        
        int step = 1000/SCALE;
        for(int i=0;i<WIDTH/step+1;i++){  
            g.drawLine(i*step+WIDTH/2%step, HEIGHT/2-TICK_SIZE/2, i*step+WIDTH/2%step, HEIGHT/2+TICK_SIZE/2);
        }
        for(int i=0;i<HEIGHT/step+1;i++){  
            g.drawLine( WIDTH/2-TICK_SIZE/2, i*step+HEIGHT/2%step, WIDTH/2+TICK_SIZE/2, i*step+HEIGHT/2%step);
        }
        g.dispose();    
        g.drawImage(image, null, 0, 0);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mrs.irobot.baloghkoys;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.ImageIcon;

/**
 *
 * @author juko
 */
public class MapImage {
    
    private int SCALE = 10;
    
    public final int WIDTH = 800;
    public final int HEIGHT = 600;
    
    private final int TICK_SIZE = 8;
    private final double ROBOT_SCALE = 0.5;
    
    private int lastX = WIDTH/2;
    private int lastY = HEIGHT/2;
    
    private int lastRobotX= WIDTH/2;
    private int lastRobotY= HEIGHT/2;
    private boolean robotPositionUnknown = true;
    
    private int robotX= WIDTH/2;
    private int robotY= HEIGHT/2;
    private int robotAngle = 0;
    
    private BufferedImage image;
    private ImageIcon iRobotIcon;
    private boolean showiRobot;
    private boolean rescaling = false;
    private boolean adding = false;
    
    private ArrayList<Waypoint> waypoints;
    
    private iRobotImage irobotState = new iRobotImage();
                   
    public MapImage( boolean showiRobot ){
        image = new BufferedImage( WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB );
        iRobotIcon = new ImageIcon( getClass().getResource("/img/iRobotSmall.gif") );          
        waypoints = new ArrayList();
        this.drawGrid();
        this.showiRobot = showiRobot;
    }
    
    /**
     * 
     * @return Image with map waypoints
     */
    public Image getImage(){
        if( showiRobot && !robotPositionUnknown )
            return getImageWithRobot();
        else
            return getImageWithoutRobot();
    }
    
    /**
     * 
     * @return Image with ordinary waypoints
     */
    public Image getImageWithoutRobot(){
        return image.getScaledInstance(WIDTH, HEIGHT, 0);
    }
    
    /**
     * 
     * @return Image with robot ordinary waypoints and robot actual position
     */
    public Image getImageWithRobot(){
        BufferedImage imageWithRobot = new BufferedImage( WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB );
        Graphics2D g = imageWithRobot.createGraphics();
        //int width  = (int) (iRobotIcon.getIconWidth()*ROBOT_SCALE);
        //int heigth = (int) (iRobotIcon.getIconHeight()*ROBOT_SCALE);
        g.drawImage( getImageWithoutRobot(), 0, 0, null);
        //g.drawImage( transformIcon( iRobotIcon, robotAngle, ROBOT_SCALE ), 
        //             scaleX(robotX)-width/2, scaleY(robotY)-heigth/2, null);
        byte[] sensors_state = null;
        int i= waypoints.size();
        while( sensors_state == null && i>1 ){
            sensors_state = waypoints.get(i-1).getReply();    
            i--;
        }
        if( sensors_state != null ){
            irobotState.setSensors( new MidLevelSensors( sensors_state ) );
            Image iRobotSensorsState = transformIcon( new ImageIcon(irobotState.getImage()), robotAngle, 0.5 );
            int width = iRobotSensorsState.getWidth(null);
            int height = iRobotSensorsState.getHeight(null);
            g.drawImage( iRobotSensorsState ,scaleX(robotX)-width/2, scaleY(robotY)-height/2, null);
        }
        g.dispose();    
        g.drawImage(imageWithRobot, null, 0, 0);
        return imageWithRobot;
    }
    
    /**
     * Rotates given image icon by angle and returns image 
     * @param icon ImageIcon to rotate
     * @param angle angle of rotation
     * @param scale number to resize image
     * @return Rotated image
     */
    private Image transformIcon( ImageIcon icon, double angle, double scale )
    {
        int w = icon.getIconWidth();
        int h = icon.getIconHeight();
        BufferedImage transformImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB );
        AffineTransform at = new AffineTransform();
        at.rotate(Math.toRadians(angle), w/2.0, h/2.0);
        Graphics2D g2D = transformImage.createGraphics();
        g2D.drawImage(icon.getImage(), at, null);
        g2D.dispose();
        w *= scale;
        h *= scale; // rescale
        return new ImageIcon(transformImage.getScaledInstance(w, h, 0)).getImage();
    }
    
    /**
     * if ordinary waypoint notes=="W"
     * Draws line from last waypoint to new waypoint to the map 
     * if robotPosition waypoint notes=="R"
     * Sets robot position in map
     */
    public void addWaypoint( Waypoint w, boolean add ){
        if( w.isOrdinary() ){
            Graphics2D g = image.createGraphics();
            g.setStroke(new BasicStroke(1F,BasicStroke.CAP_SQUARE,BasicStroke.JOIN_BEVEL));
            g.setColor(Color.BLUE);
            int x =  w.getX()/SCALE+WIDTH/2;
            int y = -w.getY()/SCALE+HEIGHT/2;
            g.drawLine(lastX, lastY, x, y);
            g.dispose();    
            g.drawImage(image, null, 0, 0);
            lastX = x;
            lastY = y;
        }else{
            setRobotPosition( w.getX(), w.getY(), w.getOrientation() );  
            Graphics2D g = image.createGraphics();
            g.setStroke(new BasicStroke(2F,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER));
            g.setColor(Color.RED);
            int x =  w.getX()/SCALE+WIDTH/2;
            int y = -w.getY()/SCALE+HEIGHT/2;
            g.drawLine(lastRobotX, lastRobotY, x, y);
            g.dispose();    
            g.drawImage(image, null, 0, 0);
            lastRobotX = x;
            lastRobotY = y;
        }
        if( add ) {
            waypoints.add(w);
        }
    }
    
    /**
     * if ordinary waypoint notes=="W"
     * Draws line from last waypoint to new waypoint to the map 
     * if robotPosition waypoint notes=="R"
     * Sets robot position in map
     */
    public void addWaypoint( Waypoint w ){
        while( rescaling );
        adding = true;
        addWaypoint( w, true );
        adding = false;
    }
    
    public Waypoint getActualWaypoint(){
        if( waypoints.size() > 0 )
            return waypoints.get(waypoints.size()-1);
        return null;
    }
    
    /**
     * Sets robot position in image
     * @param x coordinate
     * @param y coordinate
     * @param angle robot orientation 
     */
    public void setRobotPosition( int x, int y, int angle ){        
        robotX = x;
        robotY = y;
        robotAngle = angle;          
        robotPositionUnknown = false;
    }
    
    /**
     * Saves all waypoints from this image to file
     * @param fileName name of file to write waypoints
     */
    public void saveToFile( String fileName ){
        String textToFile = "";
        Iterator i = waypoints.iterator();
        while( i.hasNext() ){
            Waypoint w = (Waypoint) i.next();
            textToFile = textToFile.concat(w.toFile());
        }
        saveStringToFile( fileName, textToFile );
    }
    
    /**
     * Adjust robot coordinate x to use it in the map
     * @param x original robot coordinate
     * @return x coordinate in map
     */
    private int scaleX( int x ){
        return x/SCALE+WIDTH/2;
    }
    
    /**
     * Adjust robot coordinate y to use it in the map
     * @param y original robot coordinate
     * @return y coordinate in map
     */
    private int scaleY( int y ){
        return -y/SCALE+HEIGHT/2;
    }
    
    /**
     * Writes text to file
     * @param fileName name of file to save text
     * @param text text to be written to file
     */
    private void saveStringToFile( String fileName, String text ){
        try
        {
            FileWriter fw = new FileWriter( fileName );
            fw.write(text);
            fw.close();
        }
        catch (Exception e)
        {
            Logger.log("Failed to save to file. Exception: "+e.toString());
        }
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
    
    public void setScale(int SCALE) {
        while( adding );
        this.rescaling = true;
        this.SCALE = SCALE;
        // TODO redraw whole image
        image = new BufferedImage( WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB );
        Iterator<Waypoint> i = waypoints.iterator();
        while( i.hasNext() ){
            this.addWaypoint( i.next(), false );
        }
        this.drawGrid();
        this.rescaling = false;
    }
}

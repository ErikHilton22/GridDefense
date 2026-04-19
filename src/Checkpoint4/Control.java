package Checkpoint4;
/**
 * This class utilizes a run method and mouse events to allow for the user to interact with GridDefense.
 * This class also contains the current game objects that are being used on screen and updates 60 times a second
 *
 * Erik Hilton
 * 4/6/2023
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.TreeMap;

public class Control implements Runnable, ActionListener, MouseListener, MouseMotionListener
{
    // Uncommented -- add contracts/comments to understand what's going
    // on.  (Style requirements checked on each checkpoint.)

    Tower1 k;

    private View view;
    private GameState state;
    private Timer timer;
    private Path path;
    private boolean mousePressed;

    private TreeMap<String, BufferedImage> images;


    /**
     * Loads a path, adds a background, and adds objects to move along the path
     */
    public void run ()
    {
        images = new TreeMap<String, BufferedImage>();

        path = loadPath("TronPathFinal");

        state = new GameState();
        view = new View(this, state);
        view.addMouseListener(this);
        view.addMouseMotionListener(this);
        mousePressed = false;

            state.startFrame();

            state.addGameObject(new Background(this, state));
            state.addGameObject(new Menu(this, state));
            state.addGameObject(new TowerButtons(this, state));
            state.addGameObject(new Generator(this, state));

            state.finishFrame();

        timer = new Timer(16,this);
        timer.start();
    }


    // Loads an image

    // Load the background image.

    /**
     * This is a helper function for loading images
     * @param filename an image file
     * @return the image file loaded onto the JFrame
     */
    private BufferedImage loadImage (String filename)
    {
        try
        {
            return javax.imageio.ImageIO.read(new File(filename));
        }
        catch (IOException e)
        {
            System.out.println("Could not read " + filename);
            return null;
        }
    }

    public BufferedImage getImage (String filename)
    {
        if (! images.containsKey(filename))
        {
            BufferedImage image = loadImage(filename);

            images.put(filename, image);

            System.out.println("Image was loaded");
        }

        return images.get(filename);
    }

    // Load a path

    /**
     * gets path coordinates from a file and loads them into the game as a path
     * @param filename the text file with coordinates
     * @return a path
     */
    private Path loadPath (String filename)
    {
        try
        {
            Scanner in = new Scanner(new File(filename));
            Path p = new Path(in);
            in.close();
            return p;
        }
        catch (IOException e)
        {
            System.out.println("Could not load the path.");

            return null;
        }
    }

    // Get the path

    /**
     * Gets the current path and its points so that they can be used in other classes/methods
     * @return the current path's coordinates
     */
    public Path getPath ()
    {
        return path;
    }

    /**
     * updates and repaints the frame after every action
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        // Main update loop
        if (state.lives > 0)
        {
            state.startFrame();

            for (Animatable a : state.getCurrentObjects())
                a.update(state.getTime());

            if (mousePressed)
                for (GameObject a : state.getCurrentObjects()) {
                    if (a.consumeMouseClick())
                        break;
                }
            mousePressed = false;

            state.finishFrame();

            view.repaint();
        }
    }

    /**
     * This class determines the distance between a tower and the path
     * If the tower is too close to the path, the tower cannot be placed there.
     * @param point tower location
     * @return boolean value
     */
    public boolean validLocation (Point point)
    {

        Point towerLoc = state.getMouseLoc();

        for (int i = 1; i < path.getPointCount(); i++)
        {
            double distanceToTower = towerLoc.distance(path.getPoint(i));
            //double previousDistance = towerLoc.distance(path.getPoint(i-1));

            if (distanceToTower < 30)
            {
                System.out.println(distanceToTower);
                return true;
            }
        }

        return false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * sets mouse location (to be used in other methods) when mouse is pressed and sets a boolean to true
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e)
    {
        state.setMouseLoc(e.getX(), e.getY());

        mousePressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * sets mouse location so that other methods can use it when an object is moved
     * @param e the event to be processed
     */
    @Override
    public void mouseDragged(MouseEvent e)
    {
        state.setMouseLoc(e.getX(), e.getY());
    }

    /**
     * sets location when mouse is being moves
     * @param e the event to be processed
     */
    @Override
    public void mouseMoved(MouseEvent e)
    {
        state.setMouseLoc(e.getX(),e.getY());

        //System.out.println(e.getX() + " " + e.getY());
    }
}

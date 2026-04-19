package Checkpoint4;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

/**
 * This class is an abstract class that implements animatable and is meant to be extended by the
 * game objects as a sort of parameter.
 * It contains and initializes the hasExpired boolean which is used to determine if an object exists
 * within the frame or not
 *
 * Erik Hilton
 * 4/12/2023
 */

abstract public class GameObject implements Animatable
{
    protected boolean hasExpired;

    /**
     * Checkpoint3.GameObject constructor
     */
    public GameObject()
    {
        this.hasExpired = false;
    }

    /**
     * returns hasExpired when applied to a certain game object
     * @return hasExpired
     */
    public boolean hasExpired ()
    {
        return hasExpired;
    }

    /**
     * sets consumeMouseClick to false
     * This will be overridden by the classes that extend Checkpoint3.GameObject
     * @return false boolean
     */
    public boolean consumeMouseClick ()
    {
        return false;
    }

    protected void drawCentered (Graphics k, BufferedImage image, int x, int y, double scale)
    {
        int height = (int) (image.getHeight() * scale);
        int width = (int) (image.getWidth() * scale);
        int nx = x - width/2;
        int ny = y - height/2;

        k.drawImage(image, nx, ny, width, height, null);
    }
}

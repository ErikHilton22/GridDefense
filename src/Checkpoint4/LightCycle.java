package Checkpoint4;
/**
 * This class handles an object that will move along the path (from the control class).
 * This object is animatable and also uses a percentage field to allow for the object to move across the screen
 * In this case, the object is a motorcycle.
 * Erik Hilton
 * 4/6/2023
 */

import java.awt.*;
import java.awt.image.BufferedImage;

public class LightCycle extends GameObject implements Targetable
{
    // Uncommented -- add contracts/comments to understand what's going
    // on.  (Style requirements checked on each checkpoint.)

    private Control control;
    private GameState state;
    private double pathPercentage;

    /**
     * The constructor for the Light Cycle
     * @param state Gamestate reference
     * @param control Checkpoint3.Control Reference
     */
    public LightCycle(Control control, GameState state)
    {
        this.control = control;
        this.state = state;
        pathPercentage = 0.0;
    }

    /**
     * Increases the percentage field by .001 everytime the lightCycle object is updated (or repainted)
     * @param timeElapsed how much time has passed since the GridDefense game has started
     */
    @Override
    public void update(double timeElapsed)
    {
        if (!hasExpired)
        {
            pathPercentage += (1.00/22.0) * timeElapsed;

            if (pathPercentage > 1.0)
            {
                hasExpired = true;
                //state.addGameObject(new LightCycle(control, state));

                state.lives = state.lives - 1;
            }
        }
    }

    /**
     * Paints the object in a new location everytime the image is repainted
     * This method calls the convert to coordinates class which uses the percentage field as
     * a basis for its coordinate calculations.
     * @param g the graphics object to be drawn
     */
    @Override
    public void draw(Graphics g)
    {
        BufferedImage asteroid = control.getImage("LightCycle03.png");
        Point loc = control.getPath().convertToCoordinates(pathPercentage);
        g.drawImage(asteroid, loc.x - asteroid.getWidth()/2, loc.y - asteroid.getHeight()/2, null);
    }

    public Point getLocation ()
    {
        return control.getPath().convertToCoordinates(pathPercentage);
    }
}

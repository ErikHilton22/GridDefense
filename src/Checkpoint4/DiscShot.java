package Checkpoint4;
/**
 * this class creates and supplies information so that a shot from a tower can be thrown towards
 * a targetable object with accuracy
 *
 * Erik Hilton
 * 4/18/2023
 */

import java.awt.*;
import java.awt.image.BufferedImage;

public class DiscShot extends GameObject
{
    private Control control;
    private GameState state;
    private Point targetLocation;
    private Point towerLocation;

    private Path trajectory;
    private double pathPercentage;

    private double shotExpires;

    /**
     * This method serves as a constructor for a shot that will be fired toward a targetable object
     * @param control control object
     * @param state state object
     * @param towerLoc start of the shot's path
     * @param targetLoc end of the shot's path
     */
    public DiscShot(Control control, GameState state, Point towerLoc, Point targetLoc)
    {
        this.control = control;
        this.state = state;
        this.towerLocation = towerLoc;
        this.targetLocation = targetLoc;
        this.trajectory = new Path();
        shotExpires = 1.2;

        trajectory.add(towerLoc.x, towerLoc.y); // adds start and end to the disc's path
        trajectory.add(targetLoc.x, targetLoc.y);

        pathPercentage = 0.0;
    }

    /**
     * This method updates the status of the shot being fired as well as the object it targets
     * This method also keeps track of time and uses it to determine what is possible with the shot
     * @param timeElapsed time between frames
     */
    public void update(double timeElapsed)
    {
        shotExpires -= timeElapsed;

        if(! hasExpired)
        {
            pathPercentage += (4) * timeElapsed;
        }
        // expires a shot after one second of existence (assuming the shot hasn't already been expired)
        if (shotExpires <= 0.0)
        {
            hasExpired = true;
        }
        Targetable target = state.findNearest(targetLocation);
        double distanceBetweenObjects = target.getLocation().
                distance(trajectory.convertToCoordinates(pathPercentage));

        //expires both the shot and its target upon collision
        if (distanceBetweenObjects < 30)
        {
            hasExpired = true;
            ((GameObject) target).hasExpired = true;

            state.coins = state.coins + 10;
        }
    }

    @Override
    public void draw(Graphics g)
    {
        BufferedImage disc = control.getImage("Disc.png");
        Point loc = trajectory.convertToCoordinates(pathPercentage);
        g.drawImage(disc, loc.x - disc.getWidth()/2, loc.y - disc.getHeight()/2, null);
    }


}


package Checkpoint4;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Soundwave extends GameObject
{
    private Control control;
    private GameState state;
    private Point towerLocation;
    private Point targetLocation;

//    private Path shotUp;
//    private Path shotDown;
//    private Path shotLowLeft;
//    private Path shotLowRight;
//    private Path shotHighLeft;
//    private Path shotHighRight;
    private double shotExpires;

    private double totalTime;

    /**
     * this method initializes and creates paths for the shots to follow
     * @param control control object
     * @param state state object
     * @param towerLoc where you place the tower
     */
    public Soundwave(Control control, GameState state, Point towerLoc)
    {
        this.control = control;
        this.state = state;
        this.towerLocation = towerLoc;
        shotExpires = 3.0;
        totalTime = 0.0;


    }

    /**
     * this method sends out a shockwave from the tower which destroys all objects that come within its radius.
     * @param timeElapsed time in between frames
     */
    @Override
    public void update(double timeElapsed) {
        shotExpires -= timeElapsed;
        totalTime += timeElapsed;
        BufferedImage soundWave = control.getImage("Soundwave.png");

        // expires a shot after one second of existence (assuming the shot hasn't already been expired)
        if (shotExpires <= 0.0)
        {
            hasExpired = true;
        }
            double updatedHeight = soundWave.getWidth() * (1 + totalTime);
            double updatedWidth = soundWave.getHeight() * (1 + totalTime);

            Point center = new Point((int) updatedWidth, (int) updatedHeight);

            double radius = updatedWidth / 2;

//        Targetable target = state.findNearest(targetLocation);
//            double distanceBetweenObjects = target.getLocation().
//                    distance(center);

            for (GameObject t : state.currentFrameObjects)
            {
                if (t instanceof Targetable)
                {
                    Point objectPoint = ((Targetable) t).getLocation();
                    double distanceToObject = center.distance(objectPoint);

                    if (distanceToObject <= radius +20)
                    {
                        //System.out.println(distanceToObject);
                        ((GameObject) t).hasExpired = true;

                        state.coins = state.coins + 10;
                    }
                }

                //expires both the shot and its target upon collision
//            if (distanceBetweenObjects < radius + 10)
//            {
//                //hasExpired = true;
//                ((GameObject) target).hasExpired = true;
//
//                state.coins = state.coins + 10;
//            }
            }

    }

    @Override
    public void draw(Graphics g)
    {
        BufferedImage soundwave = control.getImage("Soundwave.png");

        //draws an increasingly large soundwave
        if (shotExpires >= 0.0)
        {
            drawCentered(g, soundwave, towerLocation.x, towerLocation.y, 0.5 + totalTime);
        }
    }
}

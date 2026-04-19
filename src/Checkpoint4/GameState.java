package Checkpoint4;
/**
 * This class uses current and next frame fields to allow for objects to be added to the screen in the control method.
 * Also contains a method to change the amount of current objects on the screen
 *
 * Erik Hilton
 * 4/6/2023
 */

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameState
{
    // Uncommented -- add contracts/comments to understand what's going
    // on.  (Style requirements checked on each checkpoint.)

    // Fields

    public List<GameObject> currentFrameObjects;
    private List<GameObject> nextFrameObjects;

    public int coins;
    public int lives;

    private int mouseX, mouseY;

    private double elapsedTime;
    private long previousStartTime;

    /**
     * Accessor to get the value from the elapsedTime field
     * @return time elapsed
     */
    public double getTime ()
    {
        return elapsedTime;
    }

    public void setMouseLoc (int mouseX, int mouseY)
    {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    public Point getMouseLoc ()
    {
        return new Point (mouseX, mouseY);
    }

    /**
     * This is an accessor for the games currency, coins
     * @return the int amount of coins
     */
    public int getCoins ()
    {
        return coins;
    }

    public void changeCoins (int set)
    {
        coins += set;
    }

    /**
     * This is an accessor for the amount of lives you have within the grid
     * @return int amount of lives
     */
    public int getLives ()
    {
        return lives;
    }

    public void changeLives (int set)
    {
        coins += set;
    }

    /**
     * This is a constructor for Checkpoint3.GameState which creates an ArrayList of Animatables.
     * also initializes lives and coins, as well as the currentFrameObjects
     */
    public GameState()
    {
        lives = 100;
        coins = 1000;

        currentFrameObjects = new ArrayList<GameObject>();
    }

    /**
     * This method gets and returns an arraylist of objects that are currently in frame.
     * @return
     */
    public List<GameObject> getCurrentObjects ()
    {
        return currentFrameObjects;
    }

    /**
     * This method takes all the objects that exist in the currentFrameObjects list, and
     * adds them to the nextFrameObjects list
     */
    public void startFrame ()
    {
            long currentStartTime = System.currentTimeMillis();
            elapsedTime = (currentStartTime - previousStartTime) / 1000.0;
            previousStartTime = currentStartTime;


            //totalTimeElapsed += previousStartTime;
            //System.out.println(totalTimeElapsed);

            nextFrameObjects = new ArrayList<GameObject>();    // Creates empty list
            nextFrameObjects.addAll(currentFrameObjects);// Add all the current ones to the new list.
    }

    /**
     * allows for an object to be added to a list of game objects called nextFrameObjects
     * @param a an object that you are adding
     */
    public void addGameObject (GameObject a)
    {
        nextFrameObjects.add(a);
    }

    /**
     * This method sets the currentFrameObjects list equal to the newly updated
     * nextFrameObjects list and then sets nextFrameObjects to null
     * so that there is only a current list
     */
    public void finishFrame ()
    {
        // Remove any current objects that are expired from the
        // next frame.
            for (int i = 0; i < currentFrameObjects.size(); i++) {
                GameObject current = currentFrameObjects.get(i);

                if (current.hasExpired()) {
                    nextFrameObjects.remove(current);
                }
            }
        currentFrameObjects = nextFrameObjects;
        nextFrameObjects = null;  // This makes it clear there is only a current list now.
    }

    /**
     * This method finds the nearest targetable object in relation to a point
     * @param somePoint the point where the tower is placed
     * @return the targetable object closest to the point
     */
    public Targetable findNearest (Point somePoint)
    {
        Targetable target = null;
        double smallestDistance = 100000.0;
        double distanceToObject = 0.0;
        for (GameObject t : this.currentFrameObjects)
        {
            if (t instanceof Targetable)
            {
                Point objectPoint = ((Targetable)t).getLocation();
                distanceToObject = somePoint.distance(objectPoint);

                if(distanceToObject <= smallestDistance)
                {
                    //System.out.println(distanceToObject);
                    smallestDistance = distanceToObject;
                    target = (Targetable) t;
                }
            }
        }
        //System.out.println(distanceToObject);
        return target;
    }


}

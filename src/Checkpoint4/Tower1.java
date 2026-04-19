package Checkpoint4;
/**
 * This class adds a tower to the game area
 * If the tower is clicked on, it can be dragged and places somewhere in the game area
 *
 * Erik Hilton
 * 4/12/2023
 */

import java.awt.*;

public class Tower1 extends GameObject
{
    private Control control;
    private GameState state;
    private int x,y;
    private boolean isMoving;

    private Targetable nearest;
    private double countdownNextShot;

    /**
     * Constructor for the tower
     * @param control control
     * @param state state
     */
    public Tower1(Control control, GameState state)
    {
        this.control = control;
        this.state = state;
        isMoving = true;

        countdownNextShot = 1.0;
    }

    public boolean getMovingStatus ()
    {
        return isMoving;
    }

    /**
     * Updates the location of the tower when clicked on
     * @param elapsedTime time that has passed
     */
    public void update (double elapsedTime)
    {
        countdownNextShot -= elapsedTime;

        if (isMoving)
        {
            Point location = state.getMouseLoc();
            x = location.x;
            y = location.y;
        }

        if ( ! isMoving)
        {
            Point towerLocation = new Point(x,y);
            if ( ! (state.findNearest(towerLocation) == null))
            {
                this.nearest = state.findNearest(towerLocation);
                Point nearestLoc = state.findNearest(towerLocation).getLocation();

                if(nearestLoc.distance(towerLocation) <= 150.0 && countdownNextShot <= 0)
                {
                    System.out.println("fire shot");
                    state.addGameObject(new DiscShot(control, state, towerLocation, nearestLoc));
                    countdownNextShot = 1.5;
                    //fire shot
                }
            }
        }
    }
    @Override
    public void draw(Graphics g)
    {
        drawCentered(g, control.getImage("DiscTower.png"), x, y, 1);
// Test

//        Point p = new Point(x,y);

//        if (! (nearest == null))
//        {
//            g.setColor(Color.GREEN);
//            g.drawRect( (int) nearest.getLocation().getX(), (int) nearest.getLocation().getY(), 100,100);
//        }
    }

    /**
     * If the tower is moving, and the mouse is clicked, this method will place
     * the tower where the mouse was clicked
     * @return a boolean value
     */
    @Override
    public boolean consumeMouseClick()
    {
        Point towerLoc = new Point(x,y);


        if (isMoving)
        {
            isMoving = false;

            if ( control.validLocation(towerLoc) || x > 600 || y > 600 || x < 0 || y < 0)
            {
                hasExpired = true;
                state.coins += 200;
            }

            //state.coins = state.coins - 200;

            return true;
        }
        return false;
    }
}

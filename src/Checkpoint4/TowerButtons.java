package Checkpoint4; /**
 * This class creates a button that, if clicked on, will produce a tower (depending on which button is clicked) that
 * can then be placed in the game area
 *
 * Erik Hilton
 * 4/12/2023
 */

import java.awt.*;

public class TowerButtons extends GameObject
{
    private GameState state;
    private Control control;

    public TowerButtons(Control control, GameState state)
    {
        super();
        this.control = control;
        this.state = state;
    }

    public void draw(Graphics g)
    {
        g.drawImage(control.getImage("DiscTower.png"), 625, 380, null);

        g.drawImage(control.getImage("DaftPunk.png"), 647,470, null);
    }

    public void update (double timeElapsed)
    {

    }

    /**
     * This method sets up areas in the menu area that, if clicked on, will create the clicked object and consume
     * that mouse click
     * @return true or false based on mouse status
     */
    public boolean consumeMouseClick ()
    {
        double xLoc = state.getMouseLoc().getX();
        double yLoc = state.getMouseLoc().getY();

        if(state.coins >= 200 && xLoc > 645 && xLoc < 705 && yLoc > 395 && yLoc < 455)
        {
            System.out.println("Disc Combatant Selected");
            state.addGameObject(new Tower1 (control, state));
            state.coins -= 200;

            return true;
        }

        if (state.coins >= 350 && xLoc > 645 && xLoc < 725 && yLoc > 470 && yLoc < 553)
        {
            System.out.println("Daft Combatant Selected");
            state.addGameObject(new Tower2(control,state));
            state.coins = state.coins - 350;

            return true;
        }

        return false;
    }
}

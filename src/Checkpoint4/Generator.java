package Checkpoint4;
/**
 * This class determines how frequently new game objects are added to the game
 * It allows for levels or waves to be an aspect of tower defense.
 */

import java.awt.*;

public class Generator extends GameObject
{
    // Uncommented -- add contracts/comments to understand what's going
    // on.  (Style requirements checked on each checkpoint.)

    private Control control;
    private GameState state;
    private double countdownNextCycle;
    private int lightCycleCount;
    public double totalTime;

    /**
     * This method initializes fields and serves as a constructor for the Generator class
     * @param control a control object
     * @param state a gamestate object
     */
    public Generator(Control control, GameState state)
    {
        super();
        this.control = control;
        this.state = state;

        countdownNextCycle = 1.0;

        lightCycleCount = 0;

        totalTime = 0.0;
    }

    /**
     * This method keeps track of time and determines when waves of enemies enter the Grid
     * @param timeElapsed how much time is passed between frames
     */
    @Override
    public void update(double timeElapsed)
    {
        countdownNextCycle -= timeElapsed;

        totalTime += timeElapsed;
        //System.out.println(totalTime);

        //this code sends a few lightcycles and a lightjet 1.0 second apart, waits 5 seconds, and sends them again
        //after 30 seconds, the game takes a brief break and another, more difficult round starts.
            if (totalTime < 30.0)
            {
                if (countdownNextCycle <= 0)
                {
                    countdownNextCycle = 1.0;
                    state.addGameObject(new LightCycle(control, state));
                    lightCycleCount++;

                    if (lightCycleCount >= 2.0)
                    {
                        state.addGameObject(new LightJet(control, state));
                        countdownNextCycle = 5.0;
                        lightCycleCount = 0;
                    }
                }
            }

            if (totalTime >= 30.0 && totalTime < 35.0)
            {
                countdownNextCycle = 5.0;
            }

            if (totalTime >= 35.0 && totalTime < 70.0)
            {
                if (countdownNextCycle <= 0.0)
                {
                    countdownNextCycle = 1.0;
                    state.addGameObject(new LightCycle(control, state));
                    lightCycleCount++;

                    if (lightCycleCount >= 5.0)
                    {
                        state.addGameObject(new LightJet(control, state));
                        countdownNextCycle = 1.5;
                        lightCycleCount = 0;
                    }
                }
            }

            if (totalTime >= 70.0 & totalTime < 75.0)
            {
                countdownNextCycle = 5.0;
            }

            if (totalTime >= 75.0 && totalTime < 100.0)
            {
                if (countdownNextCycle <= 0.0)
                {
                    countdownNextCycle = 0.5;
                    state.addGameObject(new LightCycle(control, state));
                    state.addGameObject(new LightJet(control, state));
                    lightCycleCount++;

                    if (lightCycleCount >= 15.0)
                    {
                        state.addGameObject(new LightJet(control, state));
                        countdownNextCycle = 2.0;
                        lightCycleCount = 0;
                    }
                }
            }

    }


    @Override
    public void draw(Graphics g)
    {

    }
}

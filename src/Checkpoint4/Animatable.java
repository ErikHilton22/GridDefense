package Checkpoint4;
/**
 * This interface sets parameters for something to be considered animatable
 * This includes an update function and a draw method
 *
 * Erik Hilton
 * 4/6/2023
 */

import java.awt.*;

public interface Animatable
{
    // Uncommented -- add contracts/comments to understand what's going
    // on.  (Style requirements checked on each checkpoint.)

    public void update(double timeElapsed);
    public void draw(Graphics g);
}

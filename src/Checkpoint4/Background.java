package Checkpoint4;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Background extends GameObject
{
    // Uncommented -- add contracts/comments to understand what's going
    // on.  (Style requirements checked on each checkpoint.)

    private Control control;
    private GameState state;

    public Background(Control control, GameState state)
    {
        super();
        this.control = control;
        this.state = state;
    }

    @Override
    public void update(double timeElapsed)
    {
    }

    @Override
    public void draw(Graphics g)
    {
        BufferedImage background = control.getImage("TronGrid-1420.png");
        g.drawImage(background, 0, 0, null);
    }
}

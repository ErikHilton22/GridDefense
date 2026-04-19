package Checkpoint4;
/**
 * This class starts a new Grid Defense game by calling the control class
 * The gams is similar to Tower Defense but uses Tron inspired artwork
 *
 * Erik Hilton
 * 4/6/2023
 */

import javax.swing.*;

public class WelcomeToTheGrid
{
    // Uncommented -- add contracts/comments to understand what's going
    // on.  (Style requirements checked on each checkpoint.)

    public static void main (String[] args)
    {
        SwingUtilities.invokeLater(new Control());
    }
}

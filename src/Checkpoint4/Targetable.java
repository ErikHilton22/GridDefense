package Checkpoint4;
/**
 * This interface determines of an object is targetable
 * Meaning that a shot fired from a tower can and will target an object that implements this interface
 */

import java.awt.*;

public interface Targetable
{
    public Point getLocation ();
}

package Checkpoint4; /**
 * This class contains the methods for a Checkpoint3.Path class which is essentially an arraylist of points
 * that will be used and changed in the Checkpoint3.Path editor class.
 *
 * Erik Hilton
 * 4/6/2023
 * CS 1420
 */

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Path
{
	// Mostly uncommented -- add contracts/comments to understand what's going
	// on.  (Style requirements checked on each checkpoint.)

	private ArrayList<Point> pathPoints;

	private double totalPathLength;  // Hack - only computed on construction

	public Path()
	{
		pathPoints = new ArrayList<Point>();
	}

	/**
	 * This class is the constructor for a path. It takes in x and y coordinates and produces an object that represents
	 * thise sets of paths
	 * @param in takes in a list of points
	 */
	public Path(Scanner in)
	{
		pathPoints = new ArrayList<Point>();

		int size = in.nextInt();
		for (int i = 0; i < size; i++)
			pathPoints.add(new Point(in.nextInt(), in.nextInt()));

 		// Compute the path length.

		totalPathLength = 0;

		// If we count segments starting at 1,
		//   segment 1 is path[0]   ... path[1], and
		//   segment n is path[n-1] ... path[n].
		//
		// In an array or list, the last entry is indexed one less
		//   than the length of the list, so as long as the index is
		//   less than the length (or size) of the list, it is valid.
		//
		// Remember, for arrays use path.length.  For List objects, use path.size()

		for (int i = 1; i < pathPoints.size(); i++)
		{
			Point start = pathPoints.get(i-1);  // Extract segment start/end
			Point end   = pathPoints.get(i);

			totalPathLength += start.distance(end);
		}
	}

	/**
	 * Calculates the total number of points that are contained within a path
	 * @return an int count of the total points
	 */
	public int getPointCount()
	{
		return pathPoints.size();
	}

	public Point getPoint(int location)
	{
		return this.pathPoints.get(location);
	}

	public int getX (int n)
	{
		return pathPoints.get(n).x;
	}

	public int getY (int n)
	{
		return pathPoints.get(n).y;
	}

	/**
	 * Allows for a point to be added into an already existing path object
	 * @param x,y x and y coordinates
	 */
	public void add (int x, int y)
	{
		pathPoints.add(new Point(x,y));
		totalPathLength = 0;
		for (int i = 1; i < pathPoints.size(); i++)
		{
			Point start = pathPoints.get(i-1);  // Extract segment start/end
			Point end   = pathPoints.get(i);

			totalPathLength += start.distance(end);
		}
	}

	/**
	 * Converts a list of points to a list of strings
	 * @return string coordinates
	 */
	public String toString ()
	{
		String result = "" + getPointCount() + "\n";
		for (Point p : pathPoints)
			result += p.x + " " + p.y + "\n";
		return result;
	}

	/**
	 * Draws a path for the objects within GridDefense to follow
	 * @param g graphics parameter
	 */
	public void draw (Graphics g)
	{
		g.setColor (Color.RED);
		Point last = null;
		for (Point p : pathPoints)
		{
			if (last != null)
			{
				g.drawLine(last.x, last.y, p.x,  p.y);
				g.drawLine(last.x, last.y+1, p.x,  p.y+1);
				g.drawLine(last.x+1, last.y, p.x+1,  p.y);
				g.drawLine(last.x+1, last.y+1, p.x+1,  p.y+1);
			}
			last = p;
		}

	}

	/**
	 * Returns a Point, or x, y coordinates, of some position along this
	 * path.  The position is given as a percentage.  0.0 means the
	 * first position on the path, and 1.0 means the last position on the
	 * path.
	 *
	 * @param percentage   a distance along the path, as a percentage
	 * @return             the x, y coordinate (as a Point object) of this position on the path
	 */
	public Point convertToCoordinates (double percentage)
	{
		// Caution: Do not return any Point object from the path.  A Point
		//   object can have its contents changed (such as setting x or y to 0).
		//   If the caller got back a reference to one of our Point
		//   objects, they could change x or y and screw up the path.
		//   Instead, in the code below I either compute new Point objects,
		//   or I return new points that are copies of points in our path.

		// Debugging only:  In my solution, I can have empty paths.  This
		//   statement deals with this.  (This was not shown during lecture.)
		//   Since we cannot look through non-existant path segments, exit early.

		if (pathPoints.size() < 2)  // empty path - return 0,0
			return new Point(0, 0);

		// Math is annoying when using doubles.  We need to take
		//   extra care.  First, make sure that the percentage
		//   is in the range (0.0...1.0), exclusive.  If not,
		//   return reasonable values.
		//
		// If the percentage is at or before the start of the path,
		//   return the first path coordinate.  If the percentage is past
		//   the end, return the last path coordinate.

		if (percentage <= 0.0)
			return new Point(pathPoints.get(0));  // Make a new Point object that copies ours

		if (percentage >= 1.0)
			return new Point(pathPoints.get(pathPoints.size()-1));  // Make a new Point object that copies ours

		// Convert the percentage to a distance.  This is how far along
		//   the path we are.  We need to find the coordinate that is at this
		//   distance from the start of the path.

		double distanceToTravel = totalPathLength * percentage;

		// Walk through the segments and keep track of the distance traveled as
		//   we go.  If the distance traveled is greater than or equal to
		//   the amount we're supposed to travel, we've found the segment that
		//   we're in.

		// In the code below, I compute values in the loop I need after the loop.
		//   I declare my local variables before the loop (so they'll still be there
		//   after the loop), but they need initial values.
		//
		// Update:  Because my solution allows incomplete paths, the segment length
		//   must be non-zero.  The loop may never loop, and the segment length
		//   might be used as a divisor below.  Also, I rely on the start and
		//   end points being valid, so they must be the first point in the list.
		//   (Consider what would happen below if the path had only one point.)

		Point start = pathPoints.get(0);  // Segment points for the current segment.
		Point end   = pathPoints.get(0);
		double totalDistance = 0;   // Accumulated distance
		double segmentLength = 1;   // Length of the current segment.

		for (int i = 1; i < pathPoints.size(); i++)
		{
			// Extract segment start/end points

			start = pathPoints.get(i-1);
			end   = pathPoints.get(i);

			// Compute the length of this segment, combine it with the total.

			segmentLength = start.distance(end);
			totalDistance = totalDistance + segmentLength;  // Better:  totalDistance += segmentLength;

			// If we've gone far enough (or too far), exit the loop immediately.

			if (totalDistance > distanceToTravel)
				break;
		}

		// Consider the current segment, not the entire path.  The distance we are
		//   seeking is in this segment somewhere.  Calculate how much too far
		//   the end of the segment is.  Then, see what percentage of this segment
		//   the excess distance is.
		//
		// Since the distanceToTravel is no greater than totalDistance, the
		//   result will be non-negative.

		double excessDistance    = totalDistance - distanceToTravel;
		double segmentPercentage = excessDistance / segmentLength;   // Will be between [0..1]

		// We know that the target coordinate is some segmentPercentage from the segment end,
		//   and (1 - segmentPercentage) from the segment start.  Use these to compute a
		//   weighted average of the start and end points.  I've broken this
		//   code into separate lines for clarity.
		//
		// Remember, if we're 0% from the end, we're at the end, so include 100% of the end.
		//   (Vice versa for the start.)

		double targetX = (segmentPercentage)*start.x + (1-segmentPercentage)*end.x;
		double targetY = (segmentPercentage)*start.y + (1-segmentPercentage)*end.y;

		Point result = new Point ((int) targetX, (int) targetY);

		// Done, return the coordinates (as a Point object).

		return result;
	}

	public boolean validLocation (Point point)
	{
		int tooClose = 0;

		for (double  i = 0; i <= 1.00; i += 0.01)
		{
			if (point.distance(this.convertToCoordinates(i)) < 10);
			{
				tooClose ++;
			}
		}

		if (tooClose > 0)
		{
			return true;
		}
		return false;
	}

}

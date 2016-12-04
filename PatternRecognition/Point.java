/*************************************************************************
 * Name:    Jeremy Zhao
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {
    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER;       // YOUR DEFINITION HERE

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        SLOPE_ORDER = new PointComparator();
    }

    // plot this point to standard drawing
    public void draw() {
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        if (y == that.y && x == that.x) {
            return Double.NEGATIVE_INFINITY;
        }
        if (x == that.x) {
            return Double.POSITIVE_INFINITY;
        }
        if (y == that.y) {
            return 0.0;
        }

        return (double) (that.y - y) / (that.x - x);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (y < that.y) {
            return -1;
        }
        else if (y > that.y) {
            return 1;
        }


        if (x < that.x) {
            return -1; 
        } 
        else if (x > that.x) { 
            return 1; 
        } 
        
        return 0;
    }

    // return string representation of this point
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    private class PointComparator implements Comparator<Point> { 
        public int compare(Point o1, Point o2) {
            double s1 = slopeTo(o1);
            double s2 = slopeTo(o2);
            if (s1 < s2) {
                return -1;
            }
            if (s1 > s2) {
                return 1;
            }

            return 0;
        }
    }

    // unit test
    public static void main(String[] args) {
        Point p0 = new Point(0, 0);
        p0.draw();
        p0.drawTo(new Point(1, 1)); 

        // Double.NEGATIVE_INFINITY
        System.out.println(p0.slopeTo(new Point(0, 0)));
        // Double.POSITIVE_INFINITY
        System.out.println(p0.slopeTo(new Point(0, 1)));
        // 0.0
        System.out.println(p0.slopeTo(new Point(1, 0)));
        // 1.0
        System.out.println(p0.slopeTo(new Point(1, 1)));

        // -1
        System.out.println(p0.compareTo(new Point(1, -1)));
        // 1
        System.out.println(p0.compareTo(new Point(1, 1)));
        // 0
        System.out.println(p0.compareTo(new Point(0, 0)));

        // (0, 0)
        System.out.println(p0.toString());

        int res = p0.SLOPE_ORDER.compare(new Point(1, 0), new Point(0, 1));
        // -1
        System.out.println(res);

        res = p0.SLOPE_ORDER.compare(new Point(1, 2), new Point(2, 3));
        // 1
        System.out.println(res);

        res = p0.SLOPE_ORDER.compare(new Point(1, 2), new Point(2, 4));
        // 0
        System.out.println(res);
    }
}

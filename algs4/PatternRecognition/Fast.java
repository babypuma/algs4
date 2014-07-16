import java.util.Arrays;

public class Fast { 
    private Point[] points;
    private PointWithSlope[] pwSlopes;

    public Fast() { 
        StdDraw.setXscale(0, 32768); 
        StdDraw.setYscale(0, 32768);

        int num = StdIn.readInt();
        points = new Point[num];
        for (int i = 0; i < num; i++) {
            int x = StdIn.readInt();
            int y = StdIn.readInt();
            points[i] = new Point(x, y);
            points[i].draw();
        } 

        Arrays.sort(points);
        recognize();
    }

    private Fast(String inputFile) {
        StdDraw.setXscale(0, 32768); 
        StdDraw.setYscale(0, 32768);

        In in = new In(inputFile);
        int num = in.readInt();
        points = new Point[num];
        for (int i = 0; i < num; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
            points[i].draw();
        } 

        Arrays.sort(points);
        recognize();
    }

    private void recognize() {
        for (int i = 0; i < points.length; i++) { 
            pwSlopes = new PointWithSlope[points.length - 1];
            int index = 0;
            for (int j = 0; j < points.length; j++) {
                if (j == i) {
                    continue;
                }
                pwSlopes[index++] = 
                    new PointWithSlope(points[j], points[i].slopeTo(points[j]));
            } 
            
            Arrays.sort(pwSlopes);
            int k = 0;
            while (k < pwSlopes.length - 2) {
                if (pwSlopes[k].slope != pwSlopes[k + 2].slope) {
                    k++;
                    continue;
                }

                boolean skip = false;
                if (points[i].compareTo(pwSlopes[k].p) > 0) {
                    skip = true;
                }

                int l = k + 3; 
                while (l < pwSlopes.length 
                        && pwSlopes[k].slope == pwSlopes[l].slope) {
                    l++;
                }

                if (!skip) {
                    System.out.print(points[i]);
                    for (int m = k; m < l; m++) {
                        System.out.print(" -> " + pwSlopes[m].toString());
                    }
                    System.out.println();
                    points[i].drawTo(pwSlopes[l - 1].p);
                }

                k = l;
            }
        }
    }

    private class PointWithSlope implements Comparable<PointWithSlope> {
        private Point p;
        private double slope;

        PointWithSlope(Point p, double slope) {
            this.p = p;
            this.slope = slope;
        }

        public int compareTo(PointWithSlope o) {
            if (slope < o.slope) {
                return -1;
            }
            if (slope > o.slope) {
                return 1;
            } 
            
            return 0;
        } 
        
        public String toString() { 
            return p.toString();
        }
    }

    public static void main(String[] args) {
        if (args.length == 1) {
            Fast fast = new Fast(args[0]);
        }
        else {
            Fast fast = new Fast();
        }
    }
}

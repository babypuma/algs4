import java.util.Arrays;

public class Brute { 
    private Point[] points;
    private int numberOfPoints;

    public Brute() {
        StdDraw.setXscale(0, 32768); 
        StdDraw.setYscale(0, 32768);

        numberOfPoints = StdIn.readInt();
        points = new Point[numberOfPoints];
        for (int i = 0; i < numberOfPoints; i++) {
            int x = StdIn.readInt();
            int y = StdIn.readInt();
            points[i] = new Point(x, y);
            points[i].draw();
        } 

        Arrays.sort(points);
        recognize();
    }

    private Brute(String inputFile) {
        StdDraw.setXscale(0, 32768); 
        StdDraw.setYscale(0, 32768);

        In in = new In(inputFile);
        numberOfPoints = in.readInt();
        points = new Point[numberOfPoints];
        for (int i = 0; i < numberOfPoints; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
            points[i].draw();
        } 

        Arrays.sort(points);
        recognize();
    }

    private void recognize() {
        for (int i = 0; i < numberOfPoints; i++) {
            for (int j = i + 1; j < numberOfPoints; j++) {
                double slopeij = points[i].slopeTo(points[j]);
                for (int k = j + 1; k < numberOfPoints; k++) {
                    double slopeik = points[i].slopeTo(points[k]);
                    if (slopeij != slopeik) {
                        continue;
                    }

                    for (int l = k + 1; l < numberOfPoints; l++) {
                        double slopeil = points[i].slopeTo(points[l]);
                        if (slopeik != slopeil) {
                            continue;
                        }

                        System.out.println(points[i].toString() + " -> " 
                                        + points[j].toString() + " -> " 
                                        + points[k].toString() + " -> " 
                                        + points[l].toString());

                        points[i].drawTo(points[l]);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        if (args.length == 1) {
            Brute brute = new Brute(args[0]);
        }
        else {
            Brute brute = new Brute();
        }
    }
}

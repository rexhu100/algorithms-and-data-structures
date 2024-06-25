import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    private final List<LineSegment> res = new ArrayList<>();
    private final Point[] ps;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points==null) {
            throw new IllegalArgumentException("Parameter cannot be null.");
        }
        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException("Point cannot be null.");
            }
        }


        Point[] pointsCopy = new Point[points.length];
        System.arraycopy(points, 0, pointsCopy, 0, points.length);

        Arrays.sort(pointsCopy, Point::compareTo);
        for (int i=0; i<pointsCopy.length-1; i++) {
            if (pointsCopy[i+1].compareTo(pointsCopy[i])==0) {
                throw new IllegalArgumentException("Contain duplicates");
            }
        }

        this.ps = points;
        if (points.length<4) return;

        // Things could have gotten a lot easier if we can use HashSet... Using pointsCopy to make sure every point is visited
        // in the loop (because the sorting the loop will change the order of objects in the array 'points'

        for (Point p : pointsCopy) {
//            System.out.println(p.toString());
            Arrays.sort(ps, p.slopeOrder());
            int i=0;
            while (p.compareTo(ps[i])==0) {
                i++;
            }

            while (i < ps.length) {
                int j=i, segLen=0;
                while (j<ps.length && p.slopeTo(ps[j]) == p.slopeTo(ps[i])) {
                    j++;
                    segLen++;
                }
                int nPoints = 4;
                if (segLen>= nPoints -1) {
                    Point[] segPoints = new Point[segLen+1];
                    segPoints[0] = p;
                    System.arraycopy(ps, i, segPoints, 1, segLen);
                    Arrays.sort(segPoints, Point::compareTo);
                    if (p==segPoints[0]) {
                        LineSegment tempSeg = new LineSegment(segPoints[0], segPoints[segLen]);
                        res.add(tempSeg);
                    }
                }
                i = j;
            }

        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return res.size();
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] resArr = new LineSegment[res.size()];
        for (int i=0; i<res.size(); i++) {
            resArr[i] = res.get(i);
        }

        return resArr;
    }
}
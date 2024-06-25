import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private final int nPoints = 4;
    private final Point[] points;
    private final List<LineSegment> res = new ArrayList<>();

    private boolean aligned(Point[] segPoints) {
        if (segPoints.length!=nPoints) {
            System.out.printf("Expecting %s points, but got %s instead.", nPoints, segPoints.length);
            return false;
        }

        double s1 = segPoints[0].slopeTo(segPoints[1]);
        double s2 = segPoints[0].slopeTo(segPoints[2]);
        double s3 = segPoints[0].slopeTo(segPoints[3]);

        return s1 == s2 && s1 == s3;
    }

    private void allCombo(int curIndex, int start, Point[] curPoints) {
        if (curIndex==nPoints){
            if (aligned(curPoints)) {
                Point[] curPointsCopy = new Point[nPoints];
                System.arraycopy(curPoints, 0, curPointsCopy, 0, nPoints);
                Arrays.sort(curPointsCopy, Point::compareTo);
                res.add(new LineSegment(curPointsCopy[0], curPointsCopy[nPoints-1]));
            }
            return;
        }

//        System.out.println(points[start+1]);
        for (int i=start+1; i<points.length-nPoints+1+curIndex; i++) {
            curPoints[curIndex] = points[i];
            allCombo(curIndex+1, i, curPoints);
            curPoints[curIndex] = null;
        }
    }

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points==null) {
            throw new IllegalArgumentException("Parameter cannot be null.");
        }

        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException("Point cannot be null.");
            }
        }

        this.points = points;
        Arrays.sort(this.points, Point::compareTo);
        for (int i=0; i<this.points.length-1; i++) {
            if (this.points[i+1].compareTo(this.points[i])==0) {
                throw new IllegalArgumentException("Contain duplicates");
            }
        }

        if (points.length<4) return;

        allCombo(0, -1, new Point[nPoints]);
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
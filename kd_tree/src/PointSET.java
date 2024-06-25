import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Collections;
import java.util.Comparator;

public class PointSET {
    private TreeSet<Point2D> pointSet;

    // construct an empty set of points
    public PointSET() {
        pointSet = new TreeSet<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return pointSet.size()==0;
    }

    // number of points in the set
    public int size() {
        return pointSet.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p==null) {
            throw new IllegalArgumentException("Point cannot be null!");
        }
        pointSet.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p==null) {
            throw new IllegalArgumentException("Point cannot be null!");
        }

        return pointSet.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p: pointSet) {
            p.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect==null) {
            throw new IllegalArgumentException("Rectangle cannot be null!");
        }

        Point2D upperRight = new Point2D(rect.xmax(), rect.ymax()), lowerLeft = new Point2D(rect.xmin(), rect.ymin());
        Iterable<Point2D> slice = pointSet.subSet(lowerLeft, true, upperRight, true);
        Iterator<Point2D> pItr = slice.iterator();
        List<Point2D> res = new ArrayList<>();
        while (pItr.hasNext()) {
            Point2D pTemp = pItr.next();
            if (rect.contains(pTemp)) {
                res.add(pTemp);
            }
        }

        return res;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p==null) {
            throw new IllegalArgumentException("Point cannot be null!");
        }

        if (isEmpty()) return null;

        return Collections.min(pointSet, Comparator.comparingDouble(a -> a.distanceTo(p)));
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        // initialize the data structures from file
        String filename = args[0];
        In in = new In(filename);
        PointSET brute = new PointSET();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            brute.insert(p);
        }

        double x0 = 0.0, y0 = 0.0;      // initial endpoint of rectangle
        double x1 = 0.0, y1 = 0.0;      // current location of mouse
        boolean isDragging = false;     // is the user dragging a rectangle

        // draw the points
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        brute.draw();
        StdDraw.show();

        // process range search queries
        StdDraw.enableDoubleBuffering();
        while (true) {

            // user starts to drag a rectangle
            if (StdDraw.isMousePressed() && !isDragging) {
                x0 = x1 = StdDraw.mouseX();
                y0 = y1 = StdDraw.mouseY();
                isDragging = true;
            }

            // user is dragging a rectangle
            else if (StdDraw.isMousePressed() && isDragging) {
                x1 = StdDraw.mouseX();
                y1 = StdDraw.mouseY();
            }

            // user stops dragging rectangle
            else if (!StdDraw.isMousePressed() && isDragging) {
                isDragging = false;
            }

            // draw the points
            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            brute.draw();

            // draw the rectangle
            RectHV rect = new RectHV(Math.min(x0, x1), Math.min(y0, y1),
                                     Math.max(x0, x1), Math.max(y0, y1));
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius();
            rect.draw();

            // draw the range search results for brute-force data structure in red
            StdDraw.setPenRadius(0.03);
            StdDraw.setPenColor(StdDraw.RED);
            for (Point2D p : brute.range(rect))
                p.draw();

            StdDraw.show();
            StdDraw.pause(20);
        }
    }
}

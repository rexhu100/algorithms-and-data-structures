import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;

public class KdTree {
    private static class Node {
        public Point2D pt;
        public boolean isVert;
        public Node left;
        public Node right;

        public Node(Point2D p, boolean isVert) {
            pt = p;
            this.isVert = isVert;
        }

        public String toString() {
            return (isVert?"|":"-") + pt.toString();
        }
    }

    private int sz;
    private Node root;

    // construct an empty set of points
    public KdTree() {
        sz = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return sz==0;
    }

    // number of points in the set
    public int size() {
        return sz;
    }

    /*
    private List<List<Node>> toPrint;
    private void printTreeHelper(Node n, int lvl) {
        if (n==null) return;

        while (toPrint.size()<lvl+1) {
            toPrint.add(new ArrayList<>());
        }
        toPrint.get(lvl).add(n);
        printTreeHelper(n.left, lvl+1);
        printTreeHelper(n.right, lvl+1);
    }
    public void printTree() {
        toPrint = new ArrayList<>();

        printTreeHelper(root, 0);
        for (List<Node> l: toPrint) {
            System.out.println(l);
            System.out.println("--------");
        }
    }
    */

    private void insertHelper(Node n, Point2D p) {
        if (p.equals(n.pt)) {
            return;
        }

        if (n.isVert) {
            if (n.pt.x()<=p.x()) {
                if (n.right==null) {
                    n.right = new Node(p, false);
                    sz++;
                } else {
                    insertHelper(n.right, p);
                }
            } else if (n.pt.x()>p.x()) {
                if (n.left==null) {
                    n.left = new Node(p, false);
                    sz++;
                } else {
                    insertHelper(n.left, p);
                }
            }
        } else {
            if (n.pt.y()<=p.y()) {
                if (n.right==null) {
                    n.right = new Node(p, true);
                    sz++;
                } else {
                    insertHelper(n.right, p);
                }
            } else if (n.pt.y()>p.y()) {
                if (n.left==null) {
                    n.left = new Node(p, true);
                    sz++;
                } else {
                    insertHelper(n.left, p);
                }
            }
        }
    }
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p==null) {
            throw new IllegalArgumentException("Point cannot be null!");
        }

        if (isEmpty()) {
//            System.out.println("First Point!");
            root = new Node(p, true);
            sz++;
        } else {
//            System.out.println("More Points!");
            insertHelper(root, p);
        }
    }

    private boolean containsHelper(Node n, Point2D p) {
        if (n==null) {
            return false;
        }

        if (n.pt.equals(p)) {
            return true;
        }

        if (n.isVert) {
            if (n.pt.x()<p.x()) {
                return containsHelper(n.right, p);
            } else {
                return containsHelper(n.left, p);
            }
        } else {
            if (n.pt.y()<p.y()) {
                return containsHelper(n.right, p);
            } else {
                return containsHelper(n.left, p);
            }
        }
    }
    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p==null) {
            throw new IllegalArgumentException("Point cannot be null!");
        }
        return containsHelper(root, p);
    }

    private void drawHelper(Node n) {
        if (n!=null) {
            n.pt.draw();
            drawHelper(n.left);
            drawHelper(n.right);
        }
    }
    // draw all points to standard draw
    public void draw() {
        drawHelper(root);
    }

//    private List<Point2D> rangePoints;
    private void rangeHelper(Node n, RectHV rect, List<Point2D> rangePoints) {
        if (n==null) return;

        if (rect.contains(n.pt)) {
            rangePoints.add(n.pt);
        }

        if (n.isVert) {
            if (n.pt.x()<rect.xmin()) {
                rangeHelper(n.right, rect, rangePoints);
            } else if (n.pt.x()>rect.xmax()) {
                rangeHelper(n.left, rect, rangePoints);
            } else {
                rangeHelper(n.left, rect, rangePoints);
                rangeHelper(n.right, rect, rangePoints);
            }
        } else {
            if (n.pt.y()<rect.ymin()) {
                rangeHelper(n.right, rect, rangePoints);
            } else if (n.pt.y()>rect.ymax()) {
                rangeHelper(n.left, rect, rangePoints);
            } else {
                rangeHelper(n.left, rect, rangePoints);
                rangeHelper(n.right, rect, rangePoints);
            }
        }
    }
    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect==null) {
            throw new IllegalArgumentException("Rectangle cannot be null!");
        }

        List<Point2D> rp = new ArrayList<>();
        rangeHelper(root, rect, rp);

        return rp;
    }

    private Point2D nearestHelper(Node n, Point2D p) {
        if (n==null) {
            return null;
        }

        Point2D ret = n.pt;
        double curMin = p.distanceTo(ret);

        if (n.isVert) {
            if (p.x()>=n.pt.x()) {
                Point2D temp = nearestHelper(n.right, p);
                if (temp!=null && temp.distanceTo(p)<curMin) {
                    curMin = temp.distanceTo(p);
                    ret = temp;
                }
                if (p.x()-n.pt.x()<curMin) {
                    temp = nearestHelper(n.left, p);
                    if (temp!=null && temp.distanceTo(p)<curMin) {
                        curMin = temp.distanceTo(p);
                        ret = temp;
                    }
                }
            } else {
                Point2D temp = nearestHelper(n.left, p);
                if (temp!=null && temp.distanceTo(p)<curMin) {
                    curMin = temp.distanceTo(p);
                    ret = temp;
                }
                if (n.pt.x()-p.x()<curMin) {
                    temp = nearestHelper(n.right, p);
                    if (temp!=null && temp.distanceTo(p)<curMin) {
                        curMin = temp.distanceTo(p);
                        ret = temp;
                    }
                }
            }
        } else {
            if (p.y()>=n.pt.y()) {
                Point2D temp = nearestHelper(n.right, p);
                if (temp!=null && temp.distanceTo(p)<curMin) {
                    curMin = temp.distanceTo(p);
                    ret = temp;
                }
                if (p.y()-n.pt.y()<curMin) {
                    temp = nearestHelper(n.left, p);
                    if (temp!=null && temp.distanceTo(p)<curMin) {
                        curMin = temp.distanceTo(p);
                        ret = temp;
                    }
                }
            } else {
                Point2D temp = nearestHelper(n.left, p);
                if (temp!=null && temp.distanceTo(p)<curMin) {
                    curMin = temp.distanceTo(p);
                    ret = temp;
                }
                if (n.pt.y()-p.y()<curMin) {
                    temp = nearestHelper(n.right, p);
                    if (temp!=null && temp.distanceTo(p)<curMin) {
                        curMin = temp.distanceTo(p);
                        ret = temp;
                    }
                }
            }
        }

        return ret;
    }
    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p==null) {
            throw new IllegalArgumentException("Point cannot be null!");
        }

        return nearestHelper(root, p);
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        // initialize the data structures from file
        String filename = args[0];
        In in = new In(filename);
        KdTree brute = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            brute.insert(p);
        }
        /*
        System.out.println(brute.root.isVert);
        System.out.println(brute.root.left.isVert);
        System.out.println(brute.root.right.isVert);
        System.out.println(brute.root.left.left.isVert);
        System.out.println(brute.root.right.left.isVert);
        */
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
            for (Point2D p : brute.range(rect)) {
                p.draw();
//                System.out.println(p);
            }


            StdDraw.show();
            StdDraw.pause(20);
        }

    }
}
/**
 * Created at Sep 04, 2015, 17:03
 * <p/>
 * File Triangle.java
 */
package math;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;
import static math.AppConstants.LARGE_FACTOR;
import static math.AppConstants.SMALL_FACTOR;
/**
 * TODO Document me
 *
 * @author Adrian Iasinschi (adrian.iasinschi@kepler-rominfo.com)
 *
 */
public class Triangle implements Drawable{
    private Point center;
    private double radius;
    private double startingAngle;
    private boolean topDown;
    private Point A;
    private Point B;
    private Point C;
    private Arc innerA;
    private Arc outerA;
    private Arc innerB;
    private Arc outerB;
    private Arc innerC;
    private Arc outerC;

    private int closed = 0;


    public Triangle(Point center, double radius, boolean topDown){
        this.center = center;
        this.radius = radius;
        this.startingAngle = topDown ? Math.PI / 6 : Math.PI / 2;
        this.topDown = topDown;
        A = getPointAtAngle(0);
        B = getPointAtAngle(Math.PI * 2 / 3);
        C = getPointAtAngle(Math.PI * 4 / 3);

        double startAt = topDown ? 180: 240;

        innerA = new Arc(A, radius * SMALL_FACTOR, startAt, (startAt + 60));
        outerA = new Arc(A, radius * LARGE_FACTOR, startAt, (startAt + 60));

        startAt = (startAt + 120);
        innerB = new Arc(B, radius * SMALL_FACTOR, startAt, (startAt + 60));
        outerB = new Arc(B, radius * LARGE_FACTOR, startAt, (startAt + 60));

        startAt = (startAt + 120);
        innerC = new Arc(C, radius * SMALL_FACTOR, startAt, (startAt + 60));
        outerC = new Arc(C, radius * LARGE_FACTOR, startAt, (startAt + 60));
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public Point2D getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public Point getPointAtAngle(double angle){
        return new Point(
                center.getX() + radius * Math.cos(startingAngle + angle),
                center.getY() + radius * Math.sin(startingAngle + angle)
        );
    }

    public double getSide(){
        return radius * Math.sqrt(3);
    }

    public Point getFirstVertex(){
        return A;
    }

    public Point getSecondVertex(){
        return B;
    }

    public Point getThirdVertex(){
        return C;
    }

    public void close(int vertex){
        closed = vertex;
    }

    public void open(){
        closed = 0;
    }

    public void draw(Graphics2D g) {
        if (AppConstants.LABELS_ON) {
            g.drawString("A", A.getViewX(), A.getViewY());
            g.drawString("B", B.getViewX(), B.getViewY());
            g.drawString("C", C.getViewX(), C.getViewY());
        }

        if (AppConstants.TRIANGLES_ON) {
            Point.drawLine(A, B, g);
            Point.drawLine(A, C, g);
            Point.drawLine(C, B, g);
        }

/*        if (closed != 1) {
            innerA.draw(g);
            outerA.draw(g);
        }
        if (closed != 2) {
            innerB.draw(g);
            outerB.draw(g);
        }
        if (closed != 3) {
            innerC.draw(g);
            outerC.draw(g);
        }       */
        switch (closed) {
            case 0: {
                doIntersections(innerA, innerB, outerB, innerC, outerC, g);
                doIntersections(outerA, innerB, outerB, innerC, outerC, g);

                doIntersections(innerC, innerA, outerA, innerB, outerB, g);
                doIntersections(outerC, innerA, outerA, innerB, outerB, g);

                doIntersections(innerB, innerA, outerA, innerC, outerC, g);
                doIntersections(outerB, innerA, outerA, innerC, outerC, g);
            }
            break;
            case 1: // A is closed
                doLoop(innerB, innerC, g);
                doLoop(outerB, outerC, g);
                break;
            case 2: // B is closed
                doLoop(innerC, innerA, g);
                doLoop(outerC, outerA, g);
                break;
            case 3: // C is closed
                doLoop(innerA, innerB, g);
                doLoop(outerA, outerB, g);
                break;
        }
    }

    private void doIntersections(Arc toDraw, Arc intersOne, Arc intersTwo, Arc intersThree, Arc intersFour, Graphics2D g){
        List<Double> ret = new ArrayList<Double>();
        ret.add(toDraw.intersectAngle(intersOne));
        ret.add(toDraw.intersectAngle(intersTwo));
        ret.add(toDraw.intersectAngle(intersThree));
        ret.add(toDraw.intersectAngle(intersFour));
        Collections.sort(ret);
        Arc firstPart = new Arc(toDraw.getCenter(), toDraw.getRadius(), toDraw.getStartAngle(), ret.get(2));
        Arc secondPart = new Arc(toDraw.getCenter(), toDraw.getRadius(), ret.get(3), toDraw.getEndAngle());
        firstPart.draw(g);
        secondPart.draw(g);
    }

    private void doLoop(Arc firstArc, Arc secondArc, Graphics2D g){
        double angle = firstArc.intersectAngle(secondArc);
        Arc firstPart = new Arc(firstArc.getCenter(), firstArc.getRadius(), firstArc.getStartAngle(), angle);
        angle = secondArc.intersectAngle(firstArc);
        Arc secondPart = new Arc(secondArc.getCenter(), secondArc.getRadius(), angle, secondArc.getEndAngle());
        firstPart.draw(g);
        secondPart.draw(g);
    }
}

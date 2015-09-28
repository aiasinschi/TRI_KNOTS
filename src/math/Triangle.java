/**
 * Created at Sep 04, 2015, 17:03
 * <p/>
 * File Triangle.java
 */
package math;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * TODO Document me
 *
 * @author Adrian Iasinschi (adrian.iasinschi@kepler-rominfo.com)
 *
 */
public class Triangle {
    private Point center;
    private double radius;
    private double startingAngle;
    private boolean topDown;
    private Point A;
    private Point B;
    private Point C;
    private int closed = 0;

    public Triangle(Point center, double radius, boolean topDown){
        this.center = center;
        this.radius = radius;
        this.startingAngle = topDown ? Math.PI / 6 : Math.PI / 2;
        this.topDown = topDown;
        A = getPointAtAngle(0);
        B = getPointAtAngle(Math.PI * 2 / 3);
        C = getPointAtAngle(Math.PI * 4 / 3);
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

    public void draw(Graphics2D g){
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

        double largeFactor = 1.3;
        double smallFactor = 1.1;

        double startAt = topDown ? 180: 240;
        Arc innerA = new Arc(A, radius * smallFactor, startAt, (startAt + 60));
        Arc outerA = new Arc(A, radius * largeFactor, startAt, (startAt + 60));
        if (closed != 1) {
            innerA.draw(g);
            outerA.draw(g);
        }
        startAt = (startAt + 120);
        Arc innerB = new Arc(B, radius * smallFactor, startAt, (startAt + 60) );
        Arc outerB = new Arc(B, radius * largeFactor, startAt, (startAt + 60) );
        if (closed != 2) {
            innerB.draw(g);
            outerB.draw(g);
        }
        startAt = (startAt + 120);
        Arc innerC = new Arc(C, radius * smallFactor, startAt, (startAt + 60));
        Arc outerC = new Arc(C, radius * largeFactor, startAt, (startAt + 60) );
        if (closed != 3) {
            innerC.draw(g);
            outerC.draw(g);
        }
    }
}

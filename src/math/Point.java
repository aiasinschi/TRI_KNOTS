/**
 * Created at Sep 04, 2015, 17:03
 * <p/>
 * File Point.java
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
public class Point extends Point2D {
    private double x;

    private double y;

    public static double sizeX = AppConstants.APP_WIDTH;

    public static double sizeY = AppConstants.APP_HEIGHT;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public int getViewX(){
        return (int)(/*sizeX / 2 +*/ x);
    }

    public int getViewY(){
        return (int)(/*sizeY / 2 -*/ y);
    }

    /**
     * When k is 0, the point is A, when k is 1, the point is B, otherwise any
     * value of k in between will be a point situated at k * (AB) distance from A
     * @param A the starting point
     * @param B the end point
     * @param k the length ratio
     * @return a new intermediary point
     */
    public static Point computeKInnerPoint(Point A, Point B, double k){
        return new Point(
                A.getX() * (1 - k) + B.getX() * k,
                A.getY() * (1 - k) + B.getY() * k
        );
    }

    public static void drawLine(Point A, Point B, Graphics g){
        g.drawLine(A.getViewX(), A.getViewY(), B.getViewX(), B.getViewY());
    }

    public static double distance(Point A, Point B){
        return Math.sqrt((A.x - B.x) * (A.x - B.x) + (A.y - B.y) * (A.y - B.y));
    }

}

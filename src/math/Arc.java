/**
 * Created at Sep 28, 2015, 11:47
 * <p/>
 * File Arc.java
 */
package math;

import java.awt.*;

/**
 * TODO Document me
 *
 * @author Adrian Iasinschi (adrian.iasinschi@kepler-rominfo.com)
 *
 */
public class Arc implements Drawable{
    private Point center;

    private double startAngle;

    private double endAngle;

    private double radius;

    private double INCREMENT = 0.1;

    public Arc(Point center, double radius, double startAngle, double endAngle) {
        this.center = center;
        this.startAngle = startAngle;
        this.endAngle = endAngle;
        this.radius = radius;
        INCREMENT = AppConstants.STROKE_WIDTH * 180 / (Math.PI * radius);
    }

    public void draw(Graphics2D g){
        double angle = startAngle;
        while (angle <= endAngle){
            Point p = pointOnArc(angle);
            g.drawLine(p.getViewX(), p.getViewY(), p.getViewX(), p.getViewY());
            angle += INCREMENT;
        }
    }

    public Point pointOnArc(double angle){
        return new Point(
                center.getX() + radius * Math.cos(angle * Math.PI / 180),
                center.getY() + radius * Math.sin(angle * Math.PI / 180)
        );
    }

    public double intersectAngle(Arc arc){
        double alpha = startAngle;
        double beta = endAngle;
        double angle = (alpha + beta) / 2;
        while ( Math.abs(alpha - beta) > AppConstants.EPSILON){
            angle = (alpha + beta) / 2;
            double da = Math.abs(Point.distance(arc.getCenter(), pointOnArc(alpha)) - arc.getRadius());
            double db = Math.abs(Point.distance(arc.getCenter(), pointOnArc(beta)) - arc.getRadius());
            if (da > db){
                alpha = angle;
            } else {
                beta = angle;
            }
        }
        return angle;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public double getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(double startAngle) {
        this.startAngle = startAngle;
    }

    public double getEndAngle() {
        return endAngle;
    }

    public void setEndAngle(double endAngle) {
        this.endAngle = endAngle;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}

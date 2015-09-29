/**
 * Created at Sep 29, 2015, 11:49
 * <p/>
 * File Circle.java
 */
package math;

import java.awt.*;

/**
 * TODO Document me
 *
 * @author Adrian Iasinschi (adrian.iasinschi@kepler-rominfo.com)
 *
 */
public class Circle implements Drawable{
    private Point center;

    private double radius;

    public Circle(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawOval(center.getViewX() - (int)radius / 2, center.getViewY() - (int)radius / 2, (int)radius, (int)radius);
    }
}

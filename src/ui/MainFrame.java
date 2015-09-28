/**
 * Created at Sep 04, 2015, 17:24
 * <p/>
 * File MainFrame.java
 */
package ui;

import java.awt.*;
import javax.swing.*;

import math.*;
import math.Point;

import static math.AppConstants.*;

/**
 * TODO Document me
 *
 * @author Adrian Iasinschi (adrian.iasinschi@kepler-rominfo.com)
 *
 */
public class MainFrame extends JFrame {
    private JPanel mainPanel;

    public MainFrame(){
        super("Triangles");
        this.setSize(AppConstants.APP_WIDTH, AppConstants.APP_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(93, 133, 255));
        this.getContentPane().add(mainPanel);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void paint(Graphics g){
        super.paint(g);
        drawStuff();
    }

    public void drawStuff(){
        Graphics2D g = (Graphics2D)getMainPanel().getGraphics();
        g.setStroke(new BasicStroke(AppConstants.STROKE_WIDTH));
        g.setColor(Color.white);
        //drawAxes(g);
        double side = CIRCLE_RADIUS * SIDE_TO_RADIUS_RATIO;
        double radius = CIRCLE_RADIUS;
        // Initial test - hardcoded triangles
/*
        Triangle t1 = new Triangle(new math.Point(0, AppConstants.CIRCLE_RADIUS / 2), AppConstants.CIRCLE_RADIUS, false);
        Triangle t2 = new Triangle(new math.Point(side / 2, AppConstants.CIRCLE_RADIUS ), AppConstants.CIRCLE_RADIUS, true);
        Triangle t3 = new Triangle(new math.Point(side, AppConstants.CIRCLE_RADIUS / 2), AppConstants.CIRCLE_RADIUS, false);
        Triangle t4 = new Triangle(new math.Point(0, - AppConstants.CIRCLE_RADIUS / 2), AppConstants.CIRCLE_RADIUS, true);
        Triangle t5 = new Triangle(new math.Point(side / 2, - AppConstants.CIRCLE_RADIUS), AppConstants.CIRCLE_RADIUS, false);
        Triangle t6 = new Triangle(new math.Point(side, - AppConstants.CIRCLE_RADIUS / 2), AppConstants.CIRCLE_RADIUS, true);
        g.setColor(Color.yellow); t1.draw(g);
        g.setColor(Color.orange); t2.draw(g);
        g.setColor(Color.yellow); t3.draw(g);
        g.setColor(Color.orange); t4.draw(g);
        g.setColor(Color.yellow); t5.draw(g);
        g.setColor(Color.orange); t6.draw(g);
*/
        // Second test - generated tiling
       /* g.setColor(Color.orange);
        for (int i = 0; i < 4; i ++){
            for (int j = 0; j < 7; j ++) {
                Triangle tr = getTile(i, j, side, radius);
                tr.draw(g);
            }
        }*/

        Triangle top = getTile(0, 2, side, radius);
        top.close(3); top.draw(g);
        Triangle left = getTile(1, 1, side, radius);
        left.close(2); left.draw(g);
        Triangle right = getTile(1, 3, side, radius);
        right.close(1); right.draw(g);
        Triangle center = getTile(1, 2, side, radius);
        center.draw(g);

        /*Arc arc = new Arc(new math.Point(0, 0), 150, 120, 180);
        arc.draw(g);
        Arc arc2 = new Arc(new math.Point(0, 0), 175, 120, 180);
        arc2.draw(g);*/
    }

    private Triangle getTile(int i, int j, double side, double radius){
        Point orig;
        if (i % 2 == 0){
            orig = new Point(
                    HOFFSET + (j % 2 == 0 ? side / 2 : side) + side * (j / 2),
                    VOFFSET + (j % 2 == 0 ? radius : radius / 2) + 3 * radius * (i / 2)
            );
        } else {
            orig = new Point(
                    HOFFSET + (j % 2 == 0 ? side / 2 : side) + side * (j / 2),
                    VOFFSET + (j % 2 == 0 ? 2 * radius : 2.5 * radius) + 3 * radius * (i / 2)
            );
        }
        return new Triangle(orig, radius, (i + j) % 2 == 0);
    }

    private void drawAxes(Graphics2D g){
        double maxX = APP_WIDTH / 2 - 10;
        double maxY = APP_HEIGHT / 2 - 10;
        Point.drawLine(new Point(- maxX, 0), new Point(maxX, 0), g);
        Point.drawLine(new Point(0, - maxY), new Point(0, maxY), g);
    }

    public static void main(String[] args) {
        MainFrame mf = new MainFrame();
        mf.setVisible(true);
        try {Thread.sleep(100);} catch (InterruptedException ex){
            System.err.println(ex.toString());
        }
        mf.drawStuff();
    }

}

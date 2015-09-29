/**
 * Created at Sep 04, 2015, 17:24
 * <p/>
 * File MainFrame.java
 */
package ui;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import math.*;
import static math.AppConstants.*;
import math.Point;

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

        // Test - generated tiling
/*        g.setColor(Color.orange);
        for (int i = 0; i < 4; i ++){
            for (int j = 0; j < 7; j ++) {
                Triangle tr = getTile(i, j, side, radius);
                tr.draw(g);
            }
        }*/

        // Simplest celtic node
/*
        Triangle top = getTile(0, 2, side, radius);
        top.close(3); top.draw(g);
        Triangle left = getTile(1, 1, side, radius);
        left.close(2); left.draw(g);
        Triangle right = getTile(1, 3, side, radius);
        right.close(1); right.draw(g);
        Triangle center = getTile(1, 2, side, radius);
        center.draw(g);
*/
        // Level 2 symmetric celtic node
/*
        Triangle top = getTile(0, 4, side, radius);
        top.close(3); top.draw(g);

        top = getTile(1, 2, side, radius);
        top.close(2); top.draw(g);
        top = getTile(1, 3, side, radius);
        top.draw(g);
        top = getTile(1, 4, side, radius);
        top.draw(g);
        top = getTile(1, 5, side, radius);
        top.draw(g);
        top = getTile(1, 6, side, radius);
        top.close(3); top.draw(g);

        top = getTile(2, 2, side, radius);
        top.close(2); top.draw(g);
        top = getTile(2, 3, side, radius);
        top.draw(g);
        top = getTile(2, 4, side, radius);
        top.draw(g);
        top = getTile(2, 5, side, radius);
        top.draw(g);
        top = getTile(2, 6, side, radius);
        top.close(1); top.draw(g);

        top = getTile(3, 4, side, radius);
        top.close(1); top.draw(g);
*/

        // Level 3 symmetric
        List<Triangle> tiling = new ArrayList<Triangle>();

        Triangle top = getTile(1, 3, side, radius);
        top.close(3); tiling.add(top);
        top = getTile(1, 5, side, radius);
        top.close(3); tiling.add(top);

        top = getTile(6, 3, side, radius);
        top.close(1); tiling.add(top);
        top = getTile(6, 5, side, radius);
        top.close(1); tiling.add(top);


        top = getTile(2, 1, side, radius);
        top.close(2); tiling.add(top);
        top = getTile(2, 7, side, radius);
        top.close(3); tiling.add(top);

        top = getTile(5, 1, side, radius);
        top.close(2); tiling.add(top);
        top = getTile(5, 7, side, radius);
        top.close(1); tiling.add(top);

        for (int i = 2; i <= 6; i++){
            top = getTile(2, i, side, radius);
            tiling.add(top);
            top = getTile(5, i, side, radius);
            tiling.add(top);
        }

        top = getTile(3, 0, side, radius);
        top.close(2); tiling.add(top);
        top = getTile(3, 8, side, radius);
        top.close(3); tiling.add(top);

        top = getTile(4, 0, side, radius);
        top.close(2); tiling.add(top);
        top = getTile(4, 8, side, radius);
        top.close(1); tiling.add(top);

        for (int i = 1; i <= 7; i++){
            top = getTile(3, i, side, radius);
            if (i == 4){
                top.close(1);
            } else if (i == 3){
                top.close(1);
            } else if (i == 5){
                top.close(2);
            }
            tiling.add(top);
            top = getTile(4, i, side, radius);
            if (i == 4){
                top.close(3);
            } else if (i == 3){
                top.close(3);
            } else if (i == 5){
                top.close(2);
            }
            tiling.add(top);
        }


        for (Triangle tr: tiling){
            tr.draw(g);
        }

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

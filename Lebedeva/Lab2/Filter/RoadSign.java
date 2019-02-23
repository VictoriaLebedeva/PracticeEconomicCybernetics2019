package Filter;

import java.awt.*;
import java.awt.geom.*;

public class RoadSign implements Shape {
    private Ellipse2D circle;
    private int radius;

    public RoadSign(int x, int y, int radius) {
        this.circle = new Ellipse2D.Double(x, y, radius, radius);
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

    public void paint(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D)graphics;
        Stroke stroke = new BasicStroke(7);
        graphics2D.setPaint(new GradientPaint((float)(circle.getX() + circle.getWidth()), 0, Color.DARK_GRAY,
                (float)circle.getX(), 0, Color.white, true));
        graphics2D.fill(circle);
        graphics2D.setStroke(stroke);
        graphics2D.setColor(Color.RED);
        graphics2D.draw(circle);
        graphics.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
        FontMetrics fontMetrics = graphics.getFontMetrics(new Font(Font.SANS_SERIF, Font.BOLD, 50));
        int charWidth = fontMetrics.charWidth('8');
        int charHeight = fontMetrics.getHeight();
        graphics.drawString("80", (int)(circle.getX() + circle.getWidth() / 2) - charWidth,
                (int)(circle.getY() + circle.getWidth() / 2) + charHeight / 4);
    }

    @Override
    public Rectangle getBounds() {
        return circle.getBounds();
    }

    @Override
    public Rectangle2D getBounds2D() {
        return circle.getBounds2D();
    }

    @Override
    public boolean contains(double x, double y) {
        return circle.contains(x, y);
    }

    @Override
    public boolean contains(Point2D point2D) {
        return circle.contains(point2D);
    }

    @Override
    public boolean intersects(double x, double y, double width, double height) {
        return circle.intersects(x, y, width, height);
    }

    @Override
    public boolean intersects(Rectangle2D rectangle2D) {
        return circle.intersects(rectangle2D);
    }

    @Override
    public boolean contains(double x, double y, double width, double height) {
        return contains(x, y, width, height);
    }

    @Override
    public boolean contains(Rectangle2D rectangle2D) {
        return circle.contains(rectangle2D);
    }

    @Override
    public PathIterator getPathIterator(AffineTransform affineTransform) {
        return circle.getPathIterator(affineTransform);
    }

    @Override
    public PathIterator getPathIterator(AffineTransform affineTransform, double flatness) {
        return getPathIterator(affineTransform, flatness);
    }
}

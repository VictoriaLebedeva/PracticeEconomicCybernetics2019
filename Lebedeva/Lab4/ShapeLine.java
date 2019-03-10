import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class ShapeLine implements Shape {

    private static double length;

    public ShapeLine (double length) {
        this.length = length;
    }

    public static double getLength() {
        return length;
    }

    public PathIterator getPathIterator(AffineTransform affineTransform) {
        return new Iterator(affineTransform);
    }

    public PathIterator getPathIterator(AffineTransform affineTransform, double flatness) {
        return new Iterator(affineTransform);
    }

    public boolean contains(Point2D p) {
        return false;
    }

    public boolean contains(Rectangle2D r) {
        return false;
    }

    public boolean contains(double x, double y) {
        return false;
    }

    public boolean contains(double x, double y, double w, double h) {
        return false;
    }

    public Rectangle getBounds() {
        return new Rectangle();
    }

    public Rectangle2D getBounds2D() {
        return new Rectangle();
    }

    public boolean intersects(Rectangle2D r) {
        return false;
    }

    public boolean intersects(double x, double y, double w, double h) {
        return false;
    }

}
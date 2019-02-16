import java.awt.*;
import java.awt.geom.*;

public class ShapeArc implements Shape {

    private double arcWidth, arcHeight, arcAngle;
    double rotateAngle = 0;
    Shape arc;

    public ShapeArc(double arcWidth, double arcHeight, double arcAngle) {
        assert (arcHeight < 0 || arcAngle < 0 || arcWidth < 0);
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;
        this.arcAngle = arcAngle;
        setRotateAngle(rotateAngle);
    }

    public void setRotateAngle(double rotateAngle) {
        this.rotateAngle = rotateAngle;
        arc = AffineTransform.getRotateInstance(Math.toRadians(rotateAngle), Test.FRAME_WIDTH / 2,
                Test.FRAME_HEIGHT / 2).createTransformedShape(new Arc2D.Double(Test.FRAME_WIDTH / 2- arcWidth / 2,
                Test.FRAME_HEIGHT / 2 - arcHeight / 2, arcWidth , arcHeight, 0 , arcAngle, 2 ));
    }

    public Rectangle getBounds() {  return arc.getBounds(); }
    public Rectangle2D getBounds2D() { return arc.getBounds2D(); }
    public boolean contains(double x, double y) { return arc.contains(x,y); }
    public boolean contains(Point2D p) { return arc.contains(p); }
    public boolean intersects(double x, double y, double w, double h) { return arc.intersects(x, y, w, h); }
    public boolean intersects(Rectangle2D r) { return arc.intersects(r); }
    public boolean contains(double x, double y, double w, double h) { return arc.contains(x, y, w, h); }
    public boolean contains(Rectangle2D r) { return arc.contains(r); }
    public PathIterator getPathIterator(AffineTransform at) { return arc.getPathIterator(at); }
    public PathIterator getPathIterator(AffineTransform at, double flatness) { return arc.getPathIterator(at, flatness); }
}

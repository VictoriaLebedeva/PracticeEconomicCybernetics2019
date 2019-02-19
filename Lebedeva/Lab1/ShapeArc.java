package shapeTransformation;

import java.awt.*;
import java.awt.geom.*;

public class ShapeArc implements Shape {

    private double arcWidth;
    private double arcHeight;
    private double arcAngle;
    private Shape arc;
    private double rotateAngle = 0;

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


    @Override
    public Rectangle getBounds() {
        return arc.getBounds();
    }

    @Override
    public Rectangle2D getBounds2D() {
        return arc.getBounds2D();
    }

    @Override
    public boolean contains(double x, double y) {
        return arc.contains(x, y);
    }

    @Override
    public boolean contains(Point2D point2D) {
        return arc.contains(point2D);
    }

    @Override
    public boolean intersects(double x, double y, double w, double h) {
        return arc.intersects(x, y, w, h);
    }

    @Override
    public boolean intersects(Rectangle2D rectangle2D) {
        return arc.intersects(rectangle2D);
    }

    @Override
    public boolean contains(double x, double y, double width, double height) {
        return arc.contains(x, y, width, height);
    }

    @Override
    public boolean contains(Rectangle2D rectangle2D) {
        return arc.contains(rectangle2D);
    }

    @Override
    public PathIterator getPathIterator(AffineTransform affineTransform) {
        return arc.getPathIterator(affineTransform);
    }

    @Override
    public PathIterator getPathIterator(AffineTransform affineTransform, double flatness) {
        return arc.getPathIterator(affineTransform, flatness);
    }
}

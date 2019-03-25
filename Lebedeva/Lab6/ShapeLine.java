import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import java.io.Serializable;

public class ShapeLine implements Shape, Serializable, Cloneable, Transferable {
    private double a;
    private double x;
    private double y;
    public static DataFlavor decDataFlavor = new DataFlavor(ShapeLine.class, "Witch of Agnesi");
    private static DataFlavor[] supportedFlavors = {decDataFlavor, DataFlavor.stringFlavor};

    public ShapeLine(double a, double x, double y) {
        this.a = a;
        this.x = x;
        this.y = y;
    }

    public Rectangle getBounds() {
        return new Rectangle((int)(x - a), (int)(y - a), (int)(2 * a), (int)(2 * a));
    }

    public Rectangle2D getBounds2D() {
        return new Rectangle.Float((float)(x - a), (float)(y - a), (float)(2 * a),(float)(2 * a));
    }

    public boolean contains(double x, double y) {
        return false;
    }

    public boolean contains(Point2D p) {
        return false;
    }

    public boolean contains(Rectangle2D r) {
        return false;
    }

    public boolean contains(double x, double y, double w, double h) {
        return false;
    }

    public boolean intersects(double x, double y, double w, double h) {
        return getBounds().intersects(x, y, w, h);
    }

    public boolean intersects(Rectangle2D r) {
        return getBounds().contains(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    class ListIterator implements PathIterator {
        AffineTransform transform;
        boolean done = false;
        double angle = 0;

        public ListIterator(AffineTransform transform) {
            this.transform = transform;
        }

        @Override
        public int getWindingRule() {
            return WIND_NON_ZERO;
        }

        @Override
        public int currentSegment (float[] coordinates) {
            double[] doubleCoords = new double[2];
            int result = currentSegment(doubleCoords);
            coordinates[0] = (float) doubleCoords[0];
            coordinates[1] = (float) doubleCoords[1];
            return result;
        }

        @Override
        public int currentSegment (double[] coordinates) {
            coordinates[0] = x + a * Math.cos(Math.toRadians(angle)) * Math.cos(2 * Math.toRadians(angle));
            coordinates[1] = y + a * Math.sin(Math.toRadians(angle)) * Math.cos(2 * Math.toRadians(angle));
            if (angle > 360)
                done = true;
            if (transform != null)
                transform.transform(coordinates, 0, coordinates, 0, 1);
            if (angle < 0.00001)
                return SEG_MOVETO;
            return SEG_LINETO;
        }

        @Override
        public boolean isDone () {
            return done;
        }

        @Override
        public void next() {
            if (done)
                return;
            angle += 1;
        }
    }

    @Override
    public PathIterator getPathIterator(AffineTransform arg0) {
        return new ListIterator(arg0);
    }

    @Override
    public PathIterator getPathIterator(AffineTransform arg0, double arg1) {
        return new ListIterator(arg0);
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return supportedFlavors.clone();
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor dataFlavor) {
        return (dataFlavor.equals(decDataFlavor) || dataFlavor.equals(DataFlavor.stringFlavor));
    }

    @Override
    public Object getTransferData(DataFlavor dataFlavor) throws UnsupportedFlavorException {
        if (dataFlavor.equals(decDataFlavor)) {
            return this;
        } else if (dataFlavor.equals(DataFlavor.stringFlavor)) {
            return toString();
        } else
            throw new UnsupportedFlavorException(dataFlavor);
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return this;
        }
    }

    @Override
    public String toString() {
        return a + " " + this.x + " " + this.y ;
    }


    static ShapeLine getFromString(String line) {

        String[] arr = line.split(" ");
        return new ShapeLine(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]),
                Integer.parseInt(arr[2]));
    }

    void translate(int x, int y) {
        this.x += x;
        this.y += y;
    }
}
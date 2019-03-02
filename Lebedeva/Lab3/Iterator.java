import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;

public class Iterator implements PathIterator {

    private AffineTransform affineTransform;
    private boolean done = false;
    private double angle = 0;

    public Iterator(AffineTransform affineTransform) {
        this.affineTransform = affineTransform;
    }

    public int getWindingRule() {
        return WIND_NON_ZERO;
    }

    public int currentSegment (float[] coordinates) {
        double[] doubleCoordinates = new double[2];
        int type = currentSegment(doubleCoordinates);
        coordinates[0] = (float) doubleCoordinates[0];
        coordinates[1] = (float) doubleCoordinates[1];
        return type;
    }

    public int currentSegment (double[] coordinates) {
        double r = ShapeLine.getLength() * Math.cos(Math.toRadians(2 * angle));
        coordinates[0] = r * Math.cos(Math.toRadians(angle));
        coordinates[1] = r * Math.sin(Math.toRadians(angle));
        if (angle > 360)
            done = true;
            if (affineTransform != null)
                affineTransform.transform(coordinates, 0, coordinates, 0, 1);
        if (angle < 0.00001)
            return SEG_MOVETO;
        return SEG_LINETO;
    }

    public boolean isDone () {
        return done;
    }

    public void next() {
        if (done)
            return;
        angle++;
    }
}
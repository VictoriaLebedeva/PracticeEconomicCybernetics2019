import java.awt.BasicStroke;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;

public class StrokeLine implements Stroke {
    BasicStroke stroke;
    public StrokeLine (float width) {
        stroke = new BasicStroke(width);
    }

    public Shape createStrokedShape (Shape shape) {
        GeneralPath path = new GeneralPath();
        float[] currentCoordinates = new float[2];
        float[] previous = new float[2];
        double got = 0;
        for (PathIterator iter = shape.getPathIterator(null); !iter.isDone(); iter.next()) {
            int type = iter.currentSegment(currentCoordinates);

            switch (type) {
                case PathIterator.SEG_MOVETO:
                    path.moveTo(currentCoordinates[0], currentCoordinates[1]);
                    break;
                case PathIterator.SEG_LINETO:
                    double x1 = previous[0];
                    double y1 = previous[1];
                    double dx = currentCoordinates[0] - x1;
                    double dy = currentCoordinates[1] - y1;
                    double length = Math.sqrt( dx*dx + dy*dy );
                    double cos = dx / length;
                    double sin = dy / length;
                    x1 += cos * got;
                    y1 += sin * got;
                    length -= got;
                    got = 0;
                    double step = 4;
                    boolean isStraight = true;
                    while (got < length) {
                        x1 += cos * step;
                        y1 += sin * step;
                        got += step;
                        if ( !isStraight )
                            path.lineTo(x1 + sin * step, y1 - cos * step);
                        path.lineTo(x1, y1);
                        isStraight = !isStraight;
                    }
                    got -= length;
                    break;
            }

            previous[0] = currentCoordinates[0];
            previous[1] = currentCoordinates[1];
        }
        return stroke.createStrokedShape(path);
    }
}
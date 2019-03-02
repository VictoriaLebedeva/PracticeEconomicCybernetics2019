
import java.awt.BasicStroke;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;

public class StrokeLine implements Stroke {
    private BasicStroke stroke;

    public StrokeLine (float width) {
        stroke = new BasicStroke(width);
    }

    public Shape createStrokedShape (Shape shape) {
        GeneralPath path = new GeneralPath();
        float[] currentCoordinates = new float[2];
        float[] previousCoordinates = new float[2];
        double got = 0;
        for (PathIterator iterator = shape.getPathIterator(null); !iterator.isDone(); iterator.next()) {
            int type = iterator.currentSegment(currentCoordinates);
            switch (type) {
                case PathIterator.SEG_MOVETO:
                    path.moveTo(currentCoordinates[0], currentCoordinates[1]);
                    break;
                case PathIterator.SEG_LINETO:
                    double x = previousCoordinates[0];
                    double y = previousCoordinates[1];
                    double dx = currentCoordinates[0] - x;
                    double dy = currentCoordinates[1] - y;
                    double length = Math.sqrt( dx * dx + dy * dy );
                    double cos = dx / length;
                    double sin = dy / length;
                    x += cos * got;
                    y += sin * got;
                    length -= got;
                    got = 0;
                    double step = 4;
                    boolean isStraight = true;
                    while (got < length) {
                        x += cos * step;
                        y += sin * step;
                        got += step;
                        if ( !isStraight )
                            path.lineTo(x + sin * step, y - cos * step);
                        path.lineTo(x, y);
                        isStraight = !isStraight;
                    }
                    got -= length;
                    break;
            }

            previousCoordinates[0] = currentCoordinates[0];
            previousCoordinates[1] = currentCoordinates[1];
        }
        return stroke.createStrokedShape(path);
    }

}
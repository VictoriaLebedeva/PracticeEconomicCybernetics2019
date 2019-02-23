package Filter;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

public class Main extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 400;
    private static RoadSign roadSign;
    private static AffineTransform sourceTransform;
    public static JPanel panel;

    public static void main(String [] args) {
        JFrame frame = new JFrame();
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        roadSign = new RoadSign(120, 100 , 150);
        panel = new JPanel() {
            public void paintComponent(Graphics graphics) {
                Graphics2D graphics2D = (Graphics2D)graphics;
                graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                graphics.setColor(Color.WHITE);
                graphics2D.fillRect(0, 0, getWidth(), getHeight());
                sourceTransform = graphics2D.getTransform();
                graphics2D.translate(30, 73);
                AffineTransform shadow = AffineTransform.getShearInstance(- 1.0, 0.0);
                shadow.scale(1.0, 0.6);
                graphics.setColor(Color.GRAY);
                graphics2D.fill(shadow.createTransformedShape(new Ellipse2D.Double(WIDTH / 4 + 2 * roadSign.getRadius() ,
                        HEIGHT / 4 + roadSign.getRadius(), roadSign.getRadius(), roadSign.getRadius())));
                graphics2D.setTransform(sourceTransform);
                roadSign.paint(graphics2D);
                graphics2D.translate(400, 0);

                BufferedImage bufImgAlpha = new BufferedImage( getWidth() / 2, getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D filter = bufImgAlpha.createGraphics();
                filter.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                filter.setColor(Color.WHITE);
                filter.fillRect(0, 0, getWidth(), getHeight());
                sourceTransform = filter.getTransform();
                filter.translate(30, 73);
                AffineTransform shadowFilter = AffineTransform.getShearInstance(- 1.0, 0.0);
                shadowFilter.scale(1.0, 0.6);
                filter.setColor(Color.GRAY);
                filter.fill(shadow.createTransformedShape(new Ellipse2D.Double(WIDTH / 4 + 2 * roadSign.getRadius() ,
                        HEIGHT / 4 + roadSign.getRadius(), roadSign.getRadius(), roadSign.getRadius())));
                filter.setTransform(sourceTransform);
                roadSign.paint(filter);

                RescaleOp op = new RescaleOp(1.6f, 0.0f, null);
                graphics2D.drawImage(bufImgAlpha, op, 0, 0);
            }
        };
        frame.add(panel);
        frame.setVisible(true);
    }
}

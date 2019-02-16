import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

public class Test extends JFrame {
       final static int FRAME_WIDTH = 400, FRAME_HEIGHT = 400;
       final static double SHAPE_WIDTH = 300, SHAPE_HEIGHT = 200;
       static double shapeAngle = 45, rotateAngle = 0;

       static Stroke stroke = new BasicStroke(5);
       static Color outlineColor = Color.BLACK, fillColor = Color.RED;
       static AffineTransform affineTransform;

       public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        ShapeArc shapeArc = new ShapeArc(SHAPE_WIDTH, SHAPE_HEIGHT, shapeAngle);
        stroke = new BasicStroke(Integer.parseInt(args[0]));
        outlineColor = new Color(Integer.parseInt(args[1], 16));
        fillColor = new Color(Integer.parseInt(args[2], 16));

        JPanel panel = new JPanel() {
            public void paint(Graphics graphics){
                Graphics2D graphics2D = (Graphics2D)graphics;
                graphics.setColor(Color.white);
                graphics.fillRect(0,0, getWidth(), getHeight());
                affineTransform = AffineTransform.getRotateInstance(Math.toRadians(rotateAngle),FRAME_WIDTH / 2, FRAME_HEIGHT / 2);
                graphics2D.setTransform(affineTransform);
                graphics2D.setStroke(stroke);
                graphics2D.setColor(outlineColor);
                graphics2D.draw(shapeArc);
                graphics2D.setColor(fillColor);
                graphics2D.fill(shapeArc);
            }
        };
        Timer timer = new Timer(20, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rotateAngle < 360)
                    rotateAngle += 1;
                else
                    rotateAngle = 0;
                shapeArc.setRotateAngle(rotateAngle);
                panel.repaint();
            }
        });
        timer.start();
        frame.add(panel);
        frame.setVisible(true);
    }
}

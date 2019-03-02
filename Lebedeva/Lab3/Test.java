import javax.swing.*;
import java.awt.*;

public class Test extends JFrame {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;
    private static JFrame frame;
    private static JPanel panel;
    private static ShapeLine shapeApp;
    private static StrokeLine strokeApp;

    public static void main(String[] args) {
        frame = new JFrame();
        frame.setBackground(Color.white);
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        shapeApp = new ShapeLine(150);
        strokeApp = new StrokeLine(2);
        panel = new JPanel() {
            public void paintComponent(Graphics graphics) {
                Graphics2D graphics2D = (Graphics2D) graphics;
                graphics2D.translate(getWidth() / 2, getHeight() / 2);
                graphics2D.setColor(Color.BLUE);
                graphics2D.setStroke(strokeApp);
                graphics2D.draw(shapeApp);
            }
        };
        frame.add(panel);
        frame.setVisible(true);
    }

}

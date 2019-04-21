package bsu.fpmi.educational_practice;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    private final int FRAME_WIDTH = 300;
    private final int FRAME_HEIGHT = 300;

    public Main() {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Canvas rectangle = new Rectangle();
        ((Rectangle) rectangle).setStartPoint(50, 50);
        add(rectangle);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}

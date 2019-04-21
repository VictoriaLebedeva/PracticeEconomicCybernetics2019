package bsu.fpmi.educational_practice;

import java.awt.*;
import java.io.Serializable;

public class Rectangle extends Canvas implements Serializable {
    private int width;
    private int height;
    private Color color;

    private int arcWidth = 30;
    private int arcHeight = 30;

    private int x = 10;
    private int y = 10;

    public Rectangle(int width, int height, Color color) {
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public void setWidth(int width){
        this.width=width;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    public Color getColor(){
        return color;
    }
    public void setHeight(int height){
        this.height = height;
    }
    public void setColor(Color color){
        this.color = color;
    }
    public Rectangle() {
        width = 100;
        height = 50;
        color = new Color(112, 26, 57);
    }

    public void setStartPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void paint(Graphics graphics) {
        graphics.setColor(color);
        graphics.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
    }
}
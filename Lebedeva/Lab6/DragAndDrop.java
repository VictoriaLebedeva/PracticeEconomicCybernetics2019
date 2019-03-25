import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import java.awt.event.*;
import java.util.ArrayList;


public class DragAndDrop extends JComponent implements DragGestureListener, DragSourceListener,
        DropTargetListener, MouseListener, MouseMotionListener {
    private int length = 200;
    private ArrayList<ShapeLine> witches = new ArrayList();
    private ShapeLine currentScribble;
    private ShapeLine beingDragged;
    private DragSource dragSource;
    private boolean dragMode;
    private static final int LINE_WIDTH = 2;
    private static final BasicStroke LINE_STYLE = new BasicStroke(LINE_WIDTH);
    private static final Border NORMAL_BORDER = new BevelBorder(BevelBorder.LOWERED);
    private static final Border DROP_BORDER = new BevelBorder(BevelBorder.RAISED);

    private DragAndDrop() {
        setBorder(NORMAL_BORDER);
        addMouseListener(this);
        addMouseMotionListener(this);
        dragSource = DragSource.getDefaultDragSource();
        dragSource.createDefaultDragGestureRecognizer(this,
                DnDConstants.ACTION_COPY_OR_MOVE,this);
        DropTarget dropTarget = new DropTarget(this,
                this);
        this.setDropTarget(dropTarget);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLUE);
        g2.setStroke(new StrokeLine(2f));
        for (ShapeLine witch : witches) {
            g2.draw(witch);
        }
        g2.setStroke(LINE_STYLE);
    }

    private void setDragMode(boolean dragMode) {
        this.dragMode = dragMode;
    }

    public void mousePressed(MouseEvent e) {
        if (dragMode) {
            return;
        }
        currentScribble = new ShapeLine(length, e.getX(), e.getY());
        witches.add(currentScribble);

        repaint();
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void dragGestureRecognized(DragGestureEvent e) {
        if (!dragMode) {
            return;
        }
        MouseEvent inputEvent = (MouseEvent) e.getTriggerEvent();
        int x = inputEvent.getX();
        int y = inputEvent.getY();
        Rectangle rectangle = new Rectangle(x - LINE_WIDTH, y - LINE_WIDTH, LINE_WIDTH * 2, LINE_WIDTH * 2);
        for (ShapeLine witch : witches) {
            if (witch.intersects(rectangle)) {
                beingDragged = witch;
                ShapeLine dragScribble = (ShapeLine) witch.clone();
                dragScribble.translate(-x, -y);
                Cursor cursor;
                switch (e.getDragAction()) {
                    case DnDConstants.ACTION_COPY:
                        cursor = DragSource.DefaultCopyDrop;
                        break;
                    case DnDConstants.ACTION_MOVE:
                        cursor = DragSource.DefaultMoveDrop;
                        break;
                    default:
                        return;
                }
                e.startDrag(cursor, dragScribble, this);
                return;
            }
        }
    }

    public void dragDropEnd(DragSourceDropEvent e) {
        if (!e.getDropSuccess())
            return;
        int action = e.getDropAction();
        if (action == DnDConstants.ACTION_MOVE) {
            witches.remove(beingDragged);
            beingDragged = null;
            repaint();
        }
    }

    public void dragEnter(DragSourceDragEvent e) {
    }

    public void dragExit(DragSourceEvent e) {
    }

    public void dropActionChanged(DragSourceDragEvent e) {
    }

    public void dragOver(DragSourceDragEvent e) {
    }

    public void dragEnter(DropTargetDragEvent e) {
        if (e.isDataFlavorSupported(ShapeLine.decDataFlavor) || e.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            e.acceptDrag(DnDConstants.ACTION_COPY_OR_MOVE);
            this.setBorder(DROP_BORDER);
        }
    }

    public void dragExit(DropTargetEvent e) {
        this.setBorder(NORMAL_BORDER);
    }

    public void drop(DropTargetDropEvent e) {
        this.setBorder(NORMAL_BORDER);
        if (e.isDataFlavorSupported(ShapeLine.decDataFlavor) || e.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            e.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
        } else {
            e.rejectDrop();
            return;
        }
        Transferable t = e.getTransferable();
        ShapeLine droppedScribble;
        try {
            droppedScribble = (ShapeLine) t.getTransferData(ShapeLine.decDataFlavor);
        } catch (Exception ex) {
            try {
                String s = (String) t.getTransferData(DataFlavor.stringFlavor);
                droppedScribble = ShapeLine.getFromString(s);
            } catch (Exception ex2) {
                e.dropComplete(false);
                return;
            }
        }
        Point p = e.getLocation();
        droppedScribble.translate((int) p.getX(), (int) p.getY());
        witches.add(droppedScribble);
        repaint();
        e.dropComplete(true);
    }

    public void dragOver(DropTargetDragEvent e) {
    }

    public void dropActionChanged(DropTargetDragEvent e) {
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("DragAndDrop");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        final DragAndDrop scribblePane = new DragAndDrop();
        frame.getContentPane().add(scribblePane, BorderLayout.CENTER);
        JToolBar toolbar = new JToolBar();
        ButtonGroup group = new ButtonGroup();
        JButton draw = new JButton("Draw");
        JButton drag = new JButton("Drag");
        draw.addActionListener(e -> scribblePane.setDragMode(false));
        drag.addActionListener(e -> scribblePane.setDragMode(true));
        group.add(draw);
        group.add(drag);
        toolbar.add(draw);
        toolbar.add(drag);
        frame.getContentPane().add(toolbar, BorderLayout.NORTH);
        draw.setSelected(true);
        scribblePane.setDragMode(false);
        frame.setSize(700, 700);
        frame.setVisible(true);
    }
}
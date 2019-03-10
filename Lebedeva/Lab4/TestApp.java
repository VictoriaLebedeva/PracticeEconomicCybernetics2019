import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.logging.Logger;

public class TestApp extends JFrame {
    private JMenu menu;
    private JMenuBar menuBar;
    private JMenuItem menuItemPrint;
    private JMenuItem menuItemExit;
    private JPanel panel;

     public TestApp () {
         this.setTitle("Four-petalled rose");
         this.setSize(800, 800);
         setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
         panel = new JPanel() {
             public void paintComponent(Graphics g) {
                 Graphics2D g1 = (Graphics2D) g;
                 g1.setColor(Color.WHITE);
                 g1.fillRect(0, 0, this.getWidth(), this.getHeight());
                 g1.setColor(Color.BLUE);
                 g1.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                 g1.setStroke(new StrokeLine(2f));
                 g1.translate(getWidth()/2, getHeight()/2);
                 g1.draw(new ShapeLine(250));
                 g1.drawString("Four-petalled rose", getX() + getWidth() / 2 - 30, getY() + getHeight() - 50);
             }
         };
         menuBar = new JMenuBar();
         menu = new JMenu();
         menuItemPrint = new JMenuItem();
         menuItemExit = new JMenuItem();
         menu.setText("Options");
         menuItemPrint.setText("Print");
         menuItemExit.setText("Exit");
         panel.setBackground(new Color(255, 255, 255));
         GroupLayout jPanel1Layout = new GroupLayout(panel);
         panel.setLayout(jPanel1Layout);
         jPanel1Layout.setHorizontalGroup(
                 jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                         .addGap(0, 400, Short.MAX_VALUE));
         jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                         .addGap(0, 279, Short.MAX_VALUE));
         menuItemExit.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 dispose();
             }
         });
         menuItemPrint.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 printPage();
             }
         });
         menu.add(menuItemPrint);
         menu.add(menuItemExit);
         menuBar.add(menu);
         setJMenuBar(menuBar);
         GroupLayout layout = new GroupLayout(getContentPane());
         getContentPane().setLayout(layout);
         layout.setHorizontalGroup(
                 layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                         .addComponent(panel, GroupLayout.DEFAULT_SIZE,
                                 GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
         );
         layout.setVerticalGroup(
                 layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                         .addComponent(panel, GroupLayout.DEFAULT_SIZE,
                                 GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
         );

     }
    public void printPage() {
        PrintGraphics printGraphics = new PrintGraphics("Four-petalled rose", 14, .75, .75, .75, .75);;
        PrintWriter out = new PrintWriter(printGraphics);
        int rows = printGraphics.getLinesPerPage();
        int cols = printGraphics.getCharactersPerLine();
        out.print("+");
        for (int i = 0; i < cols - 2; i++)
            out.print(" ");
        out.print("+");
        printGraphics.setFontStyle(Font.BOLD + Font.ITALIC);
        out.println("public int currentSegment (double[] coordinates) {");
        out.println("  double r = ShapeLine.getLength() * Math.cos(Math.toRadians(2 * angle));");
        out.println("  coordinates[0] = r * Math.cos(Math.toRadians(angle));");
        out.println("  coordinates[1] = r * Math.sin(Math.toRadians(angle));");
        out.println("  if (angle > 360)");
        out.println("     done = true;");
        out.println("  if (affineTransform != null)");
        out.println("     affineTransform.transform(coordinates, 0, coordinates, 0, 1);");
        out.println("  if (angle < 0.00001)");
        out.println("      return SEG_MOVETO;");
        out.println("  return SEG_LINETO;");
        out.println("}");
        for (int i = 0; i < rows - 30; i++)
            out.println();
        out.print("+");
        for (int i = 0; i < cols - 2; i++)
            out.print(" ");
        out.print("+");
        out.close();
    }

    public static void main(String args[]) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException |
                InstantiationException |
                UnsupportedLookAndFeelException |
                IllegalAccessException ex) {
            Logger.getLogger(TestApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        EventQueue.invokeLater(() -> new TestApp().setVisible(true));
    }
}

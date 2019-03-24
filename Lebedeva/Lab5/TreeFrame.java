import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Enumeration;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

public class TreeFrame extends JFrame {
    private DefaultMutableTreeNode root;
    private DefaultTreeModel model;
    private JTree tree;
    private JTextField textFieldName;
    private JTextField textFieldYear;
    private JTextField textFieldDirector;
    private JTextField textFieldGenre;
    private JTable table;
    private JPanel panel;
    private File file = new File("List of Films.txt");

    public TreeFrame() throws IOException {
        setBackground(Color.white);
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        DefaultTreeCellRenderer cellRenderer = new DefaultTreeCellRenderer();
        cellRenderer.setOpenIcon(new ImageIcon("leaf.png"));
        cellRenderer.setClosedIcon(new ImageIcon("leaf.png"));
        cellRenderer.setClosedIcon(new ImageIcon("film.png"));
        root = new DefaultMutableTreeNode("VIDEO LIBRARY");
        model = new DefaultTreeModel(root);
        tree = new JTree(model);
        makeTree();
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                TreePath path = tree.getSelectionPath();
                if (path == null) {
                    return;
                }
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
                Object c = selectedNode.getUserObject();
                if (c.getClass().getName().equals(VideoLibrary.class.getName())) {
                    VideoLibrary n = (VideoLibrary) c;
                    TableModelTest newModel = new TableModelTest(n.getParameters(), n.getParameterNames(), n.getGenre());
                    table.setModel(newModel);
                }
            }
        });
        tree.setCellRenderer(cellRenderer);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        table = new JTable();
        panel = new JPanel();
        panel.setBackground(Color.white);
        panel.setLayout(new GridLayout(2,1));
        panel.add(new JScrollPane(tree));
        panel.add(new JScrollPane(table));
        add(panel, BorderLayout.CENTER);
        addComponents();
        validate();
    }
    private void addComponents() {
        JPanel panel = new JPanel();
        ActionListener addListener = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(textFieldName.getText() + "  " +
                            textFieldYear.getText() + "  " + textFieldDirector.getText() + "  " +
                            textFieldGenre.getText());
                    addFilm(new VideoLibrary(stringBuilder.toString()));
                    textFieldName.setText("");
                    textFieldYear.setText("");
                    textFieldDirector.setText("");
                    textFieldGenre.setText("");
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        };
        textFieldName = new JTextField(10);
        textFieldName.setText("Name of the Film");
        textFieldYear = new JTextField(10);
        textFieldYear.setText("Year");
        textFieldDirector= new JTextField(10);
        textFieldDirector.setText("Director");
        textFieldGenre = new JTextField(10);
        textFieldGenre = new JTextField("Genre");
        panel.add(textFieldName);
        panel.add(textFieldYear);
        panel.add(textFieldDirector);
        panel.add(textFieldGenre);
        panel.setBackground(Color.PINK);
        JButton addButton = new JButton("Add");
        addButton.addActionListener(addListener);
        panel.add(addButton);
        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TreePath path = tree.getSelectionPath();
                if (path == null) {
                    return;
                }
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
                if (selectedNode.getParent() != null) {
                    model.removeNodeFromParent(selectedNode);
                }
            }
        });
        panel.add(removeButton);
        add(panel, BorderLayout.NORTH);
    }

    public DefaultMutableTreeNode findUserObject(Object obj) {
        Enumeration<TreeNode> e = (Enumeration<TreeNode>) root.breadthFirstEnumeration();
        while (e.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.nextElement();
            if (node.getUserObject().getClass().getName().equals(obj.getClass().getName()) && node.getUserObject().equals(obj)) {
                return node;
            }
        }
        return null;
    }

    public DefaultMutableTreeNode addFilm(VideoLibrary n) {
        DefaultMutableTreeNode node = findUserObject(n);
        if (node != null) {
            return node;
        }
        DefaultMutableTreeNode parent = root;
        String genre = n.getGenre();
        try {
            DefaultMutableTreeNode foundGenre = findUserObject(genre);
            if (foundGenre == null) {
                model.insertNodeInto(new DefaultMutableTreeNode(genre), root, root.getChildCount());
                parent = findUserObject(genre);
            } else {
                parent = foundGenre;
            }
        } catch (Exception e) {

        }
        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(n);
        model.insertNodeInto(newNode, parent, parent.getChildCount());
        TreePath path = new TreePath(model.getPathToRoot(newNode));
        tree.makeVisible(path);
        return newNode;
    }

    public void makeTree() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String buffer;
        while ((buffer = reader.readLine()) != null) {
            addFilm(new VideoLibrary(buffer));
        }
    }

    public static void main(String[] args) throws IOException {
        new TreeFrame().setVisible(true);
    }
}



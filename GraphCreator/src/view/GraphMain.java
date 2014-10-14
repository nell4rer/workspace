package view;

import sun.rmi.runtime.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

public class GraphMain extends JFrame{

    private JMenuBar menuBar;
    private JPanel graphPanel;

    // размер фрейма
    public static int xSizeWindow = 800;
    public static int ySizeWindow = 600;

    public GraphMain(){
        super("Graphs redactor");
        initialize();
        addComponents();
    }

    private void initialize(){
        setLayout(new BorderLayout());
        setSize(xSizeWindow, ySizeWindow);
        setPreferredSize(new Dimension(xSizeWindow, ySizeWindow));
        //setMinimumSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void addPanel (GraphPanel panel) {
        graphPanel.add(panel);
        repaint();
    }

    private void addComponents(){
        addMainPanel();
        addDefaultGraphPanel();
        addMainMenu();
        repaint();
    }


    private void addMainPanel() {
        graphPanel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(graphPanel);
        add(scrollPane, BorderLayout.CENTER);
    }
    private void addDefaultGraphPanel () {
        graphPanel.add(new GraphPanel("12345"), BorderLayout.CENTER);
    }

    private void addMainMenu() {
        menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenu solutionMenu = new JMenu("Solution");

        fileMenu.add(new JMenuItem("New file"));
        fileMenu.add(new JMenuItem("Open file"));
        fileMenu.add(new JMenuItem("Save file"));
        fileMenu.addSeparator();
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        fileMenu.add(exit);


        JMenuItem bfs = new JMenuItem("BFS");
        solutionMenu.add(bfs);

        bfs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GraphPanel.bfs.bfs(0);
            }
        });



        JMenuItem dfs = new JMenuItem("DFS");
        solutionMenu.add(dfs);

        dfs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GraphPanel.dfs.print();
            }
        });


        menuBar.add(fileMenu, BorderLayout.NORTH);
        menuBar.add(solutionMenu);
        setJMenuBar(menuBar);

        repaint();
    }
}

package org.example;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        JPanel jPanel = new JPanel();
        JMenuBar jMenuBar = new JMenuBar();
        JMenu menu = new JMenu("hi");
        JMenuItem item1 = new JMenu("One");
        menu.add(item1);
        jMenuBar.add(menu);
        jFrame.add(jMenuBar);

        jFrame.add(jPanel) ;
        JLabel jLabel = new JLabel("Hello world");
        jPanel.add(jLabel);
        jFrame.setBounds(100, 100, 800, 600);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }
}
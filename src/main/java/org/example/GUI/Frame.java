package org.example.GUI;

import javax.swing.*;
public class Frame extends JFrame{
    public Frame(String title){
        super(title);
        ImageIcon icon = new ImageIcon("src/main/resources/images/icon.png");
        setIconImage(icon.getImage());
        setLayout(null);

        FactoryGCashBox factoryGCashBox = new FactoryGCashBox(5);
        factoryGCashBox.addAllGCashBoxOnFrame(this);

        // Menu
        setJMenuBar(createMenuBar());
        setBounds(100,100, 900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    public JMenuBar createMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("Файл");
        JMenu menu = new JMenu("Другое");
        JMenu exit = new JMenu("Exit");
        menuBar.add(file);
        menuBar.add(exit);
        menuBar.add(menu);
        return menuBar;
    }
    public JButton createButtons(int x, int y){
        JButton button = new JButton("HI");
        button.setBounds(x, y, 100, 50);
        return button;
    }
}

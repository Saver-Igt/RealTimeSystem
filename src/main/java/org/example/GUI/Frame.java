package org.example.GUI;

import org.example.GUI.menu.MyMenu;
import org.example.fileManager.FileManager;
import org.example.model.Cashbox;
import org.example.model.Error;
import org.example.model.ErrorType;
import org.example.model.STATUS;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Frame extends JFrame{
    private JPanel panel;
    //test
    private List<Cashbox> cashboxes;
    public Frame(String title){
        super(title);
        setBounds(100,100, 900, 600);
        ImageIcon icon = new ImageIcon("src/main/resources/images/icon.png");
        setIconImage(icon.getImage());
        setLayout(null);
        //default
        //test
        cashboxes = new ArrayList<>();
        // Generation
        cashboxes.add(new Cashbox(1, STATUS.DISABLED));
        cashboxes.add(new Cashbox(2, STATUS.ENABLED));
        cashboxes.add(new Cashbox(3, STATUS.DISABLED));
        cashboxes.add(new Cashbox(4, STATUS.DISABLED));
        cashboxes.add(new Cashbox(5, STATUS.DISABLED));
        cashboxes.add(new Cashbox(6, STATUS.DISABLED));
        createScheme();
        // Menu
        MyMenu menu = new MyMenu(this);
        setJMenuBar(menu);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        //Localisatioon to Rus
        UIManager.put("OptionPane.yesButtonText"   , "Да"    );
        UIManager.put("OptionPane.noButtonText"    , "Нет"   );
        UIManager.put("OptionPane.cancelButtonText", "Отмена");
    }

    public void createPanelForG1(){
        if(panel != null){
            remove(panel);
        }
        panel = new JPanel();
        panel.setBounds(0,0,getWidth(),getHeight());
        add(panel);

        refreshFrame();
    }
    public void createErrorHistory(){
        if(panel != null){
            remove(panel);
        }
        panel = new JPanel();
        panel.setBounds(0,0,getWidth(),getHeight());
        JPanel grid = new JPanel(new GridLayout(0,5,20,20));
        JLabel label1 = new JLabel("Номер");
        JLabel label2 = new JLabel("Дата");
        JLabel label3 = new JLabel("Тип ошибки");
        JLabel label4 = new JLabel("Номер кассы");
        JLabel label5 = new JLabel("Статус");
        grid.add(label1);
        grid.add(label2);
        grid.add(label3);
        grid.add(label4);
        grid.add(label5);
        FileManager fileManager = new FileManager();
        List<Error> errors = fileManager.getErrors();
        for (Error e:errors) {
            grid.add(new JLabel(String.valueOf(e.getId())));
            grid.add(new JLabel(e.getDate()));
            if(e.getType() == ErrorType.CHECK){
                grid.add(new JLabel("Неисправность в кассе"));
            }else{
                grid.add(new JLabel("Ошибка оплаты"));
            }
            grid.add(new JLabel(String.valueOf(e.getCasId())));
            if(e.isSolved()){
                grid.add(new JLabel("решена"));
            }else {
                grid.add(new JLabel("не решена"));
            }
        }
        panel.add(grid);
        add(panel);
        refreshFrame();
    }
    public void createPanelForG2(){
        if(panel != null){
            remove(panel);
        }
        panel = new JPanel();
        panel.setBounds(0,0,getWidth(),getHeight());
        add(panel);
        JLabel text = new JLabel("Это тоже график");
        text.setBounds(0,0,200,100);
        panel.add(text);
        refreshFrame();
    }
    public void createPanelForG3(){
        if(panel != null){
            remove(panel);
        }
        panel = new JPanel();
        panel.setBorder(new EmptyBorder(10,10,10,10));
        panel.setLayout(new GridLayout(0,3,20,20));
        panel.setBounds(0,0,getWidth(),getHeight());
        add(panel);
        JLabel text = new JLabel("А это диаграмма");

        MyImage label = new MyImage(150,50,"src/main/resources/images/cas/casDef.png");

        text.setBounds(0,0,200,100);
        panel.add(label);
        panel.add(text);
        refreshFrame();
    }
    public void createScheme(){
        if(panel != null){
            remove(panel);
        }
        panel = new JPanel();
        panel.setBorder(new EmptyBorder(10,10,10,10));
        panel.setLayout(new GridLayout(0,3,10,10));
        panel.setBounds(0,0,getWidth(),getHeight());
        add(panel);

        List<GCashBox> jLabelList = new ArrayList<>();
        for (int i=0; i < cashboxes.size(); i++) {
            jLabelList.add(new GCashBox(cashboxes.get(i)));
        }
        for (GCashBox el: jLabelList) {
            panel.add(el);
        }
        refreshFrame();
    }
    public void refreshFrame(){
        getContentPane().validate();
        getContentPane().repaint();
    }
}
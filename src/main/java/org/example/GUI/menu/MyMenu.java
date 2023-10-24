package org.example.GUI.menu;

import org.example.GUI.Frame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MyMenu extends JMenuBar {
    private Frame frame;
    public MyMenu(Frame frame) {
        createSchemeMenu();
        createChartsMenu();
        this.frame = frame;
        addClock();
    }
    public void addClock(){
        add(Box.createHorizontalGlue());
        final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Calendar now = Calendar.getInstance();
        JLabel clock = new JLabel(dateFormat.format(now.getTime()));
        add(clock);
        new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Calendar now = Calendar.getInstance();
                clock.setText(dateFormat.format(now.getTime()));
            }
        }).start();
    }
    public void createChartsMenu(){
        JMenu charts = new JMenu("Графики");

        JMenuItem g1 = new JMenuItem("График доходов");
        g1.addActionListener( e -> frame.createPanelForG1());
        JMenuItem g2 = new JMenuItem("График расходов");
        g2.addActionListener( e -> frame.createPanelForG2());
        JMenuItem g3 = new JMenuItem("Диаграмма");
        g3.addActionListener( e-> frame.createPanelForG3());

        charts.add(g1);
        charts.add(g2);
        charts.add(g3);
        add(charts);
    }
    public void createSchemeMenu(){
        JMenu scheme = new JMenu("Cхема");
        JMenuItem sc1 = new JMenuItem("Схема");
        JMenuItem er = new JMenuItem("История ошибок");
        sc1.addActionListener( e -> frame.createScheme());
        er.addActionListener( e -> frame.createErrorHistory());
        JMenu simulation = new JMenu("Симуляция");
        JMenuItem s1 = new JMenuItem("Симулировать ошибку оплаты");
        JMenuItem s2 = new JMenuItem("Симулировать застревание чека");
        //
        s1.addActionListener(e -> {});
        s2.addActionListener(e -> {});
        simulation.add(s1);
        simulation.add(s2);

        scheme.add(sc1);
        scheme.add(simulation);
        scheme.add(er);
        add(scheme);
    }
}

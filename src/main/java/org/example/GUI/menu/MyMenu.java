package org.example.GUI.menu;

import org.example.GUI.Frame;
import org.example.GUI.MyImage;
import org.example.model.Cashbox;
import org.example.model.STATUS;

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

        JMenuItem g1 = new JMenuItem("График доходов и расходов");
        g1.addActionListener( e -> chooseCashBoxForG2());

        JMenuItem g2 = new JMenuItem("Гистограмма");
        g2.addActionListener( e-> frame.createPanelForG3());

        charts.add(g1);
        charts.add(g2);
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
        JMenuItem s2 = new JMenuItem("Симулировать неисправность в кассе");
        //
        s1.addActionListener(e -> simulatePaymentError());
        s2.addActionListener(e -> simulateCheckError());
        simulation.add(s1);
        simulation.add(s2);

        scheme.add(sc1);
        scheme.add(simulation);
        scheme.add(er);
        add(scheme);
    }
    public void chooseCashBoxForG2(){
        Cashbox[] cashboxes = frame.getCashboxes().toArray(new Cashbox[0]);
        MyImage image = new MyImage(64,64, "src/main/resources/images/cas/casEn.png");
        Object result = JOptionPane.showInputDialog(
                frame,
                "Выберите кассу, для которой хотите посмотреть расходы и доходы:",
                "Доходы и расходы кассы",
                JOptionPane.QUESTION_MESSAGE,
                image.getIcon(),
                cashboxes, cashboxes[0]);
        if(result != null){
            Cashbox chosenCashbox = (Cashbox) result;
            //if(chosenCashbox.getStatus() == STATUS.DISABLED){
            //    JOptionPane.showMessageDialog(frame, "Касса отключена", "Ошибка", JOptionPane.ERROR_MESSAGE);
            //}else{
                frame.createPanelForG1(chosenCashbox);
            //}
        }
    }
    public void simulateCheckError(){
        Cashbox[] cashboxes = frame.getCashboxes().toArray(new Cashbox[0]);
        MyImage image = new MyImage(64,64, "src/main/resources/images/cas/casEn.png");
        Object result = JOptionPane.showInputDialog(
                frame,
                "Выберите кассу, в которой хотите симулировать неисправность:",
                "Неисправность кассы",
                JOptionPane.QUESTION_MESSAGE,
                image.getIcon(),
                cashboxes, cashboxes[0]);
        if(result != null){
            Cashbox chosenCashbox = (Cashbox) result;
            if(chosenCashbox.getStatus() == STATUS.DISABLED){
                JOptionPane.showMessageDialog(frame, "Касса отключена", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }else{
                chosenCashbox.callCheckError();
            }
        }
    }
    public void simulatePaymentError(){
        Cashbox[] cashboxes = frame.getCashboxes().toArray(new Cashbox[0]);
        MyImage image = new MyImage(64,64, "src/main/resources/images/cas/casEn.png");
        Object result = JOptionPane.showInputDialog(
                frame,
                "Выберите кассу, в которой хотите симулировать ошибку:",
                "Ошибка оплаты",
                JOptionPane.QUESTION_MESSAGE,
                image.getIcon(),
                cashboxes, cashboxes[0]);
        if(result != null){
            Cashbox chosenCashbox = (Cashbox) result;
            if(chosenCashbox.getStatus() == STATUS.DISABLED){
                JOptionPane.showMessageDialog(frame, "Касса отключена", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }else{
                chosenCashbox.callPaymentError();
            }
        }
    }
}

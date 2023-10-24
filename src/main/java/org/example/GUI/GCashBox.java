package org.example.GUI;

import org.example.model.Cashbox;
import org.example.model.STATUS;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Timer;
import java.util.TimerTask;

public class GCashBox extends JPanel {
    private Cashbox cashbox;
    private MyImage image;
    private JLabel field1;
    private JLabel field2;
    public GCashBox(Cashbox cashbox){
        this.cashbox = cashbox;
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        image = new MyImage(64,64,cashbox.getIcon());
        // Fields
        field1 = new JLabel("Касса №" + cashbox.getId());
        field1.setFont(new Font("Times new roman", Font.PLAIN, 18));
        field1.setBounds(80, 15, 150, 100);

        field2 = new JLabel("Статус: " + cashbox.getRusStatus());
        field2.setFont(new Font("Times new roman", Font.PLAIN, 16));
        field2.setBounds(80, 40, 150, 100);

        JPanel containerForFields = new JPanel();

        containerForFields.setBorder(new EmptyBorder(0,10,0,10));
        containerForFields.setLayout(new BoxLayout(containerForFields, BoxLayout.PAGE_AXIS));
        containerForFields.add(field1);
        containerForFields.add(field2);

        Border blackLine = BorderFactory.createLineBorder(Color.black);
        setBorder(blackLine);

        add(image);
        add(containerForFields);

        GCashBox ref = this;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(cashbox.getStatus() == STATUS.DISABLED){
                    int result = JOptionPane.showConfirmDialog(
                            getParent(),
                            "Касса№"+cashbox.getId() +
                                    "\nСтатус: " + cashbox.getRusStatus() +"."+
                                    "\nВключить?",
                            "Касса№"+cashbox.getId(),
                            JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION){
                        cashbox.setStatus(STATUS.ENABLED);
                        refresh();
                        startfWork();
                    }
                }else if(cashbox.getStatus() == STATUS.ERROR){
                    PopUpFrame errorPopUp = new PopUpFrame(getParent(), cashbox.getError(), ref);
                }else{
                    PopUpFrame popUpFrame = new PopUpFrame(getParent(),cashbox);
                }
            }
        });
        startfWork();
    }
    public void startfWork(){
        if(cashbox.canWork()){
            int randomNum = ThreadLocalRandom.current().nextInt(2, 4 + 1) * 1000;
            Timer timer = new Timer();
            TimerTask task1 = new TimerTask() {
                public void run() {
                    //System.out.println("r="+ randomNum + "|| "  + " Статус: " + cashbox.getStatus());
                    cashbox.dofWork();
                    refresh();

                    if(!cashbox.canWork()){
                        timer.cancel();
                        timer.purge();
                    }
                }
            };
            timer.schedule(task1, randomNum,randomNum);
        }
    }
    public void refresh(){
        image.setNewIcon(cashbox.getIcon());
        image.repaint();
        field2.setText("Статус: " + cashbox.getRusStatus());
    }
}

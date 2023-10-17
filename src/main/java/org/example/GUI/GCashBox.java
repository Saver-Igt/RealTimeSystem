package org.example.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GCashBox {
    private MyImage background;
    public GCashBox(int x,int y){
        // CashBoxImage
        MyImage image = new MyImage(15,45,50,50, "src/main/resources/images/cas.png");

        background = new MyImage(x, y , 250, 150, "src/main/resources/images/rectangle.png");
        background.getLabel().add(image.getLabel());

        // Fields
        JLabel field1 = new JLabel("Касса №123456789");
        field1.setFont(new Font("Times new roman", Font.PLAIN, 18));
        field1.setBounds(80, 15, 150, 100);

        JLabel field2 = new JLabel("Статус: Оплата...");
        field2.setFont(new Font("Times new roman", Font.PLAIN, 16));
        field2.setBounds(80, 40, 150, 100);

        background.getLabel().add(field1);
        background.getLabel().add(field2);
        background.getLabel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null,
                        "Eggs are not supposed to be green.");
            }
        });
    }
    public void addOnFrame(JFrame frame){
        frame.add(background.getLabel());
    }
}

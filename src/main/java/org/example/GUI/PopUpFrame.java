package org.example.GUI;

import org.example.model.*;
import org.example.model.Error;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;
public class PopUpFrame extends JFrame {
    private GCashBox parent;
    public PopUpFrame(Container panel, Cashbox cashBox){
        super("Описание");
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(panel);
        int width = parentFrame.getWidth()/2;
        int height = parentFrame.getHeight()/2;
        int x = parentFrame.getX() + parentFrame.getWidth()/4;
        int y = parentFrame.getY() + parentFrame.getHeight()/4;

        setBounds(x,y, width, height);
        ImageIcon icon = new ImageIcon("src/main/resources/images/icon.png");
        setIconImage(icon.getImage());
        setLayout(new GridLayout(2,0,0,0));

        JPanel infoPanel = new JPanel(new GridLayout(0,2,20,20));

        MyImage image = new MyImage(100,100, "src/main/resources/images/cas/casEn.png");
        infoPanel.add(image);
        JLabel label = new JLabel("Тут многа текста");
        infoPanel.add(label);
        add(infoPanel);

        JPanel grid = new JPanel(new GridLayout(1, 2, 5, 0) );

        JButton button = new JButton("Отключить кассу");
        JButton close = new JButton("Закрыть");
        close.addActionListener(e -> dispose());
        button.addActionListener(e -> {
            cashBox.setStatus(STATUS.DISABLED);
            dispose();
        });
        grid.add(button);
        grid.add(close);
        JPanel flow = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        flow.add(grid);
        add(flow);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
    public PopUpFrame(Container panel, Error error, GCashBox parent) {
        super("Ошибка");
        this.parent = parent;
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(panel);
        int width = parentFrame.getWidth()/2;
        int height = parentFrame.getHeight()/3;
        int x = parentFrame.getX() + parentFrame.getWidth()/4;
        int y = parentFrame.getY() + parentFrame.getHeight()/4;

        setBounds(x,y, width, height);
        ImageIcon icon = new ImageIcon("src/main/resources/images/icon.png");
        setIconImage(icon.getImage());

        add(createContentForErrorFrame(error));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
    public JPanel createContentForErrorFrame(Error error){
        JPanel mainPanel = new JPanel();
        MyImage image = new MyImage(64,64, "src/main/resources/images/cas/casError.png");
        mainPanel.setBorder(new EmptyBorder(0,20,0,10));

        JPanel infoPanel =new JPanel(new GridLayout(1,2,5,10));
        JLabel cashBox = new JLabel("Касса №" + error.getCasId());
        JLabel date = new JLabel("Дата: " + error.getDate());
        JLabel type;
        JLabel dis;
        if(error.getType() == ErrorType.CHECK){
            type = new JLabel("Неисправность в кассовом аппарате.");
            dis = new JLabel("Вызовите техника.");
        }else {
            type = new JLabel("Ошибка оплаты картой.");
            dis = new JLabel("Обратитесь в банк.");
        }
        infoPanel.add(image);
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.add(cashBox);
        textPanel.add(date);
        textPanel.add(type);
        textPanel.add(dis);

        infoPanel.add(textPanel);
        mainPanel.add(infoPanel);

        JPanel grid = new JPanel(new GridLayout(1, 2, 5, 0) );
        JButton solveBut = new JButton();
        if(error.getType() == ErrorType.PAYMENT){
            solveBut.setText("Обратится в банк");
        }else {
            solveBut.setText("Вызвать техника");
        }
        JButton closeBut = new JButton("Закрыть");
        closeBut.addActionListener(e -> dispose());
        solveBut.addActionListener(e -> {
            JLabel message = new JLabel("Обработка...");
            textPanel.add(message);
            solveBut.setEnabled(false);
            solveProblem(error, message, image);
        });
        grid.add (solveBut);
        grid.add (closeBut);

        JPanel flow = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        flow.add(grid);

        mainPanel.add(flow, BorderLayout.SOUTH);

        return mainPanel;
    }
    public void solveProblem(Error error, JLabel message, MyImage image){
        Timer timer = new Timer();
        TimerTask task1 = new TimerTask() {
            public void run() {
                if(!error.isSolved()){
                    if(error.getType() == ErrorType.CHECK){
                        SolveStatusForCheck status = error.getCheck();
                        error.solveCheck();
                        message.setText(status.getRus());
                    }else {
                        error.solvePayment();
                        message.setText(error.getPayment().getRus());
                    }
                }else{
                    image.setNewIcon("src/main/resources/images/cas/casEn.png");
                    error.getCashbox().setStatus(STATUS.ENABLED);
                    parent.refresh();
                    parent.startfWork();
                    refresh();
                    timer.cancel();
                    timer.purge();
                }
            }
        };
        timer.schedule(task1, getRandomDelta(),getRandomDelta());
    }
    public int getRandomDelta(){
        return ThreadLocalRandom.current().nextInt(1, 3 + 1) * 1000;
    }
    public void refresh(){
        getContentPane().validate();
        getContentPane().repaint();
    }
}

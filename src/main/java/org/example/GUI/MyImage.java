package org.example.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MyImage {
    private JLabel label;
    private ImageIcon icon;
    public MyImage(int x, int y, int width, int height, String filePath){
        try {
            BufferedImage img = ImageIO.read(new File(filePath));
            icon = new ImageIcon(img);
            label = new JLabel(resize(width, height));
            label.setLocation(x,y);
            label.setSize(width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ImageIcon resize(int w, int h){
        BufferedImage bi = new BufferedImage(w,h,BufferedImage.TRANSLUCENT);
        Graphics2D gd = bi.createGraphics();
        gd.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        gd.drawImage(icon.getImage(), 0,0,w,h,null);
        gd.dispose();
        return new ImageIcon(bi);
    }

    public JLabel getLabel() {
        return label;
    }
}

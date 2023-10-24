package org.example.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MyImage extends JLabel{
    private ImageIcon icon;
    private int width;
    private int height;
    public MyImage(int iconWidth, int iconHeight,String filePath){
        icon = new ImageIcon(filePath);
        this.height = iconHeight;
        this.width = iconWidth;
        setIcon(resizeIcon(iconWidth, iconHeight,icon));
    }
    public ImageIcon resizeIcon(int width, int height, ImageIcon icon){
        BufferedImage bi = new BufferedImage(width,height,BufferedImage.TRANSLUCENT);
        Graphics2D gd = bi.createGraphics();
        gd.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        gd.drawImage(icon.getImage(), 0,0,width,height,null);
        gd.dispose();
        return new ImageIcon(bi);
    }
    @Override
    public void repaint(long tm, int x, int y, int width, int height) {
        super.repaint(tm, x, y, width, height);
    }
    public void setNewIcon(String filePath){
        icon = new ImageIcon(filePath);
        setIcon(resizeIcon(width, height,icon));
    }
}

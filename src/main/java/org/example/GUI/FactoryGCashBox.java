package org.example.GUI;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class FactoryGCashBox {
    private List<GCashBox> gCashBoxList = new ArrayList<>();
    public FactoryGCashBox(int count) {
        int rows = count / 3;
        int colInLastRow = count % 3;

        int posY = 0;
        for (int i = 0; i < rows; i++) {
            gCashBoxList.add(new GCashBox(30,posY));
            gCashBoxList.add(new GCashBox(310,posY));
            gCashBoxList.add(new GCashBox(590,posY));
            posY +=150;
        }

        for (int i = 0; i < colInLastRow; i++) {
            gCashBoxList.add(new GCashBox(i*250 + (i+1) * 30,posY));
        }
    }
    public void addAllGCashBoxOnFrame(JFrame frame){
        for (GCashBox e: gCashBoxList) {
            e.addOnFrame(frame);
        }
    }
}

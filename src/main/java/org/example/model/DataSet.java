package org.example.model;

import java.util.Date;

public class DataSet {
    public DataSet(Date date, int value) {
        this.date = date;
        this.value = value;
    }

    private Date date;
    private int value;

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }
}

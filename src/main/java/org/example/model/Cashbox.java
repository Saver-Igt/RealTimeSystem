package org.example.model;

import org.example.fileManager.FileManager;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Cashbox {
    private int id;
    private STATUS status = STATUS.DISABLED;
    private Error error = null;
    private long summ;
    private int income = 0;
    private int cost = 0;
    private final int chance = 98;
    private HashMap<STATUS, String> icons = new HashMap<>();
    public Cashbox(int id, STATUS status, int summ) {
        this.id = id;
        this.status = status;
        this.summ = summ;
        createMapIcons();
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public STATUS getStatus() {
        return status;
    }
    public void setStatus(STATUS status) {
        this.status = status;
    }
    public void dofWork(){
        if (status == STATUS.ENABLED){
            status = STATUS.WORKING;
        } else if (status == STATUS.WORKING) {
            status = STATUS.PAYMENT;
        } else if (status == STATUS.PAYMENT) {
            int a = new Random().nextInt(100);
            if(a > chance){
                callPaymentError();
            }else {
                status = STATUS.PRINTING;
            }
        }else if(status == STATUS.PRINTING){
            int a = new Random().nextInt(100);
            if(a > chance - 1){
                callCheckError();
            }else {
                status = STATUS.SUCCESS;
                addMoney();
            }
        } else if (status == STATUS.SUCCESS) {
            status = STATUS.WORKING;
        }
    }
    public void addMoney(){
        income = ThreadLocalRandom.current().nextInt(10, 1000 + 1);
        summ += income;
        cost = ThreadLocalRandom.current().nextInt(100, 200 + 1);
        summ -= cost;
        FileManager fileManager = new FileManager();
        fileManager.updateCashbox(this);
        fileManager.putData(this.id, income);
        fileManager.putData(this.id, -1 * cost);
        // System.out.println("Money: " + mon);
        // System.out.println("SUmm: " + summ);
    }
    public void idleTimePay(){
        income = 0;
        cost = ThreadLocalRandom.current().nextInt(2000, 4000 + 1);
        summ -= cost;
        FileManager fileManager = new FileManager();
        fileManager.updateCashbox(this);
        fileManager.putData(this.id, income);
        fileManager.putData(this.id, -1 * cost);
    }
    public void callCheckError(){
        status = STATUS.ERROR;
        error = new Error(this, ErrorType.CHECK);
    }
    public void callPaymentError(){
        status = STATUS.ERROR;
        error = new Error(this, ErrorType.PAYMENT);
    }
    public boolean canWork(){
        return status != STATUS.DISABLED && status != STATUS.ERROR;
    }
    public String getRusStatus(){
        switch (status){
            case WORKING -> {
                return "Добавление товара";
            }
            case SUCCESS -> {
                return "Успешно";
            }
            case ENABLED -> {
                return "Готова к работе";
            }
            case PAYMENT -> {
                return "Оплата";
            }
            case ERROR -> {
                return "Ошибка";
            }
            case DISABLED -> {
                return "Отключена";
            }
            case PRINTING -> {
                return "Печать чека";
            }
            default -> {
                return "";
            }
        }
    }
    public void createMapIcons(){
        icons.put(STATUS.ENABLED, "src/main/resources/images/cas/casEn.png");
        icons.put(STATUS.DISABLED, "src/main/resources/images/cas/casDef.png");
        icons.put(STATUS.ERROR, "src/main/resources/images/cas/casError.png");
        icons.put(STATUS.PAYMENT, "src/main/resources/images/cas/casPay.png");
        icons.put(STATUS.PRINTING, "src/main/resources/images/cas/casPrint.png");
        icons.put(STATUS.WORKING, "src/main/resources/images/cas/casWork.png");
        icons.put(STATUS.SUCCESS, "src/main/resources/images/cas/casSuc.png");
    }
    public String getIcon() {
        return icons.get(status);
    }
    public Error getError(){
        return error;
    }
    public long getSumm() {
        return summ;
    }
    public void setSumm(long summ) {
        this.summ = summ;
    }
    public int getIncome() {
        return income;
    }
    public int getCost() {
        return cost;
    }
    @Override
    public String toString() {
        return "Касса №" + this.id;
    }
}

package org.example.model;

import java.util.HashMap;
import java.util.Random;

public class Cashbox {
    private int id;
    private STATUS status = STATUS.DISABLED;
    private Error error = null;
    private final int chance = 50;
    private HashMap<STATUS, String> icons = new HashMap<>();

    public Cashbox(int id, STATUS status) {
        this.id = id;

        this.status = status;
        createMapIcons();
    }
    public Cashbox(int id, String name) {
        this.id = id;
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
                status = STATUS.ERROR;
                error = new Error(this, ErrorType.PAYMENT);
            }else {
                status = STATUS.PRINTING;
            }
        }else if(status == STATUS.PRINTING){
            int a = new Random().nextInt(100);
            if(a > chance - 1){
                status = STATUS.ERROR;
                error = new Error(this, ErrorType.CHECK);
            }else {
                status = STATUS.SUCCESS;
            }
        } else if (status == STATUS.SUCCESS) {
            status = STATUS.WORKING;
        }
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
}

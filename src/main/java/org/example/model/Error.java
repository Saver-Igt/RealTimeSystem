package org.example.model;

import org.example.fileManager.FileManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class Error {
    private int id;
    private String date;
    private int casId;
    private ErrorType type;
    private SolveStatusForCheck check = SolveStatusForCheck.NONE;
    private SolveStatusForPayment payment = SolveStatusForPayment.NONE;
    private boolean solved = false;
    private Cashbox cashbox;
    public Error(Cashbox cashbox, ErrorType type){
        //id
        FileManager fileManager = new FileManager();
        List<Error> errors = fileManager.getErrors();
        if(errors.isEmpty()){
            this.id = 1;
        }else {
            this.id = errors.getLast().getId() + 1;
        }

        final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Calendar now = Calendar.getInstance();
        date = dateFormat.format(now.getTime());
        this.casId = cashbox.getId();
        this.cashbox = cashbox;
        this.type = type;
        fileManager.addError(this);
    }

    public Error(int id, String date,int casId,ErrorType type, boolean solved) {
        this.id = id;
        this.date = date;
        this.casId = casId;
        this.type = type;
        this.solved = solved;
    }

    public void solveCheck(){
        if(check == SolveStatusForCheck.NONE){
            check = SolveStatusForCheck.CALL;
        } else if (check == SolveStatusForCheck.CALL) {
            check = SolveStatusForCheck.WAITING;
        } else if (check == SolveStatusForCheck.WAITING) {
            check = SolveStatusForCheck.TECCAME;
        } else if (check == SolveStatusForCheck.TECCAME) {
            check = SolveStatusForCheck.REPAIR;
        }else if (check == SolveStatusForCheck.REPAIR) {
            check = SolveStatusForCheck.CHECKING;
        } else if (check == SolveStatusForCheck.CHECKING) {
            check = SolveStatusForCheck.DONE;
        }else if (check == SolveStatusForCheck.DONE) {
            solved = true;
            FileManager fileManager = new FileManager();
            fileManager.updateError(this);
        }
    }
    public void solvePayment(){
        if(payment == SolveStatusForPayment.NONE){
            payment = SolveStatusForPayment.CALL;
        } else if (payment == SolveStatusForPayment.CALL) {
            payment = SolveStatusForPayment.DESCRIPTION;
        } else if (payment == SolveStatusForPayment.DESCRIPTION) {
            payment = SolveStatusForPayment.WAITING;
        } else if (payment == SolveStatusForPayment.WAITING) {
            payment = SolveStatusForPayment.CHECKING;
        } else if (payment == SolveStatusForPayment.CHECKING) {
            payment = SolveStatusForPayment.DONE;
        } else if (payment == SolveStatusForPayment.DONE) {
            solved = true;
            FileManager fileManager = new FileManager();
            fileManager.updateError(this);
        }
    }

    @Override
    public String toString() {
        return id + ";" + date + ";" + casId + ";" + type + ";" + solved;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public void setCasId(int casId) {
        this.casId = casId;
    }
    public void setType(ErrorType type) {
        this.type = type;
    }
    public void setSolved(boolean solved) {
        this.solved = solved;
    }
    public SolveStatusForCheck getCheck() {
        return check;
    }
    public SolveStatusForPayment getPayment() {
        return payment;
    }
    public int getId() {
        return id;
    }
    public String getDate() {
        return date;
    }
    public int getCasId() {
        return casId;
    }
    public ErrorType getType() {
        return type;
    }
    public boolean isSolved() {
        return solved;
    }
    public Cashbox getCashbox() {
        return cashbox;
    }
}

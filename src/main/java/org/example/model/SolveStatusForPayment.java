package org.example.model;

public enum SolveStatusForPayment {
    NONE, CALL, DESCRIPTION, WAITING, CHECKING, DONE;
    public String getRus(){
        switch (this){
            case CALL -> {
                return "Вызов техподдержки...";
            }case DONE ->{
                return "Готово";
            }case WAITING -> {
                return "Ожидание ответа банка...";
            }case CHECKING -> {
                return "Проверка";
            }case DESCRIPTION -> {
                return "Описание проблемы";
            }default -> {
                return "";
            }
        }
    }
}

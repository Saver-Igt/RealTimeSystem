package org.example.model;

public enum SolveStatusForCheck{
    NONE, CALL, WAITING, TECCAME, REPAIR, CHECKING, DONE;
    public String getRus() {
        switch (this) {
            case CALL -> {
                return "Вызов техника...";
            }
            case DONE -> {
                return "Готово";
            }
            case WAITING -> {
                return "Ожидание...";
            }
            case CHECKING -> {
                return "Проверка";
            }
            case TECCAME -> {
                return "Техник прибыл...";
            }
            case REPAIR -> {
                return "Ремонт кассы...";
            }
            default -> {
                return "";
            }
        }
    }
}

package org.example.fileManager;

import org.example.model.*;
import org.example.model.Error;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FileManager {
    //private final String PATH = Main.class.getClassLoader().getResource("Errors.txt").getPath();;
    private final String file = "src\\main\\resources\\Errors.txt";
    private final String cashBoxesPath = "src\\main\\resources\\cashboxes.txt";
    private final String dataPath = "src\\main\\resources\\data\\";
    public List<DataSet> getData(int id) {
        List<DataSet> dataSet = new ArrayList<>();
        File dataDir = new File(dataPath + "c" + id + ".txt");
        if(dataDir.isFile()){
            try {
                StringBuilder stringBuilder = new StringBuilder();
                FileInputStream fis = new FileInputStream(dataDir);
                int i = -1;
                while(( i = fis.read()) != -1){
                    char c = (char) i;
                    stringBuilder.append(c);
                }
                String[] array = stringBuilder.toString().split("\r\n");
                for (String str: array) {
                    if(str.equals("")){
                        return null;
                    }
                    String[] strings = str.split(";");
                    final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                    Date date = dateFormat.parse(strings[0]);
                    int res = Integer.parseInt(strings[1]);
                    dataSet.add(new DataSet(date, res));
                }
                fis.close();
            } catch(IOException e){
                e.printStackTrace();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        return dataSet;
    }
    public void putData(int id, int value){
        File dataDir = new File(dataPath + "c" + id + ".txt");
        if (dataDir.isFile()) {
            try {
                FileWriter writer = new FileWriter(dataDir, true);
                final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                Calendar now = Calendar.getInstance();
                String date = dateFormat.format(now.getTime());
                writer.write(date + ";" + value + "\r\n");
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void createDataForAllCashBoxes() {
        List<Cashbox> cashboxes = getCashBoxes();
        for (Cashbox c:cashboxes) {
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(dataPath + "c" + c.getId() + ".txt", StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }finally {
                writer.close();
            }
        }
    }
    public List<Error> getErrors(){
        List<Error> errors = new ArrayList<>();
        try {
            StringBuilder stringBuilder = new StringBuilder();
            FileInputStream fis = new FileInputStream(file);
            int i = -1;
            while(( i = fis.read()) != -1){
                char c = (char) i;
                stringBuilder.append(c);
            }
            String[] array = stringBuilder.toString().split("\r\n");
            for (String str: array) {
                Error error = getError(str);
                if(error != null){
                    errors.add(error);
                }
            }
            fis.close();
        } catch(IOException e){
            e.printStackTrace();
        }
        return errors;
    }
    public List<Cashbox> getCashBoxes(){
        List<Cashbox> cashboxes = new ArrayList<>();
        try {
            StringBuilder stringBuilder = new StringBuilder();
            FileInputStream fis = new FileInputStream(cashBoxesPath);
            int i = -1;
            while(( i = fis.read()) != -1){
                char c = (char) i;
                stringBuilder.append(c);
            }
            String[] array = stringBuilder.toString().split("\r\n");
            for (String str: array) {
                Cashbox cashbox = getCashbox(str);
                if(cashbox != null){
                    cashboxes.add(cashbox);
                }
            }
            fis.close();
        } catch(IOException e){
            e.printStackTrace();
        }
        return cashboxes;
    }
    public Cashbox getCashbox(String string){
        if(string.equals("")){
            return null;
        }
        String[] strings = string.split(";");
        int id = Integer.parseInt(strings[0]);
        STATUS status = STATUS.valueOf(strings[1]);
        int summ = Integer.parseInt(strings[2]);
        return new Cashbox(id, status,summ);
    }
    private Error getError(String string){
        if(string.equals("")){
            return null;
        }
        String[] strings = string.split(";");
        int id = Integer.parseInt(strings[0]);
        String date = strings[1];
        int casId = Integer.parseInt(strings[2]);
        ErrorType type = ErrorType.valueOf(strings[3]);
        boolean solved = Boolean.parseBoolean(strings[4]);
        return new Error(id, date,casId,type, solved);
    }
    public Error updateError(Error error){
        List<Error> errors = getErrors();
        Error findError = errors.stream().filter(e -> e.getId() == error.getId())
                .findFirst().orElse(null);
        if(findError == null) return null;

        findError.setDate(error.getDate());
        findError.setCasId(error.getCasId());
        findError.setType(error.getType());
        findError.setSolved(error.isSolved());

        try {
            save(errors);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return findError;
    }
    public Cashbox updateCashbox(Cashbox cashbox){
        List<Cashbox> cashboxes = getCashBoxes();
        Cashbox findCashbox = cashboxes.stream().filter(e -> e.getId() == cashbox.getId())
                .findFirst().orElse(null);
        if(findCashbox == null) return null;

        findCashbox.setSumm(cashbox.getSumm());

        try {
            saveCashBox(cashboxes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return findCashbox;
    }
    public void addError(Error error){
        try(FileWriter fw = new FileWriter(file, true))
        {
            fw.write(error + "\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void save(List<Error> errorList) throws IOException{
        PrintWriter writer = new PrintWriter(file, "UTF-8");
        for (Error e:errorList) {
            writer.println(e);
        }
        writer.close();
    }
    private void saveCashBox(List<Cashbox> cashboxes) throws IOException{
        PrintWriter writer = new PrintWriter(cashBoxesPath, "UTF-8");
        for (Cashbox c:cashboxes) {
            writer.println(c.getId() + ";" + c.getStatus() + ";" + c.getSumm());
        }
        writer.close();
    }
}

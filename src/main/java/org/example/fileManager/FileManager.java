package org.example.fileManager;

import org.example.model.Error;
import org.example.model.ErrorType;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    //private final String PATH = Main.class.getClassLoader().getResource("Errors.txt").getPath();;
    private final String file = "src\\main\\resources\\Errors.txt";
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
                    errors.add(getError(str));
                }
            }
            fis.close();
        } catch(IOException e){
            e.printStackTrace();
        }
        return errors;
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
    public Error deleteError(Error error) throws IOException {
        List<Error> errors = getErrors();
        Error findError = errors.stream().filter(e -> e.getId() == error.getId())
                .findFirst().orElse(null);
        if(findError == null) return null;
        errors.remove(findError);
        save(errors);
        return findError;
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

}

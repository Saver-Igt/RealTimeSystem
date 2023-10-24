import org.example.fileManager.FileManager;
import org.example.model.Error;
import org.example.model.ErrorType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class TestFileManager {
    private  FileManager fileManager;
    @Before
    public void init(){
        fileManager = new FileManager();
    }
    @Test
    public void updateTest(){
        List<Error> errorList = fileManager.getErrors();
        Error error = errorList.getFirst();
        error.setSolved(true);
        fileManager.updateError(error);

        List<Error> errors = fileManager.getErrors();
        Error ex = errors.getFirst();
        Assert.assertTrue(ex.isSolved());
    }
    @Test
    public void deleteTest(){
        List<Error> errorList = fileManager.getErrors();
        Error error = new Error(errorList.getLast().getId() + 1,"12.12.1212 12:12:12",2, ErrorType.PAYMENT,false);

    }
    @Test
    public void addErrorTest(){
        List<Error> errorList = fileManager.getErrors();
        Error error = new Error(errorList.getLast().getId() + 1,"12.12.1212 12:12:12",2, ErrorType.PAYMENT,false);
        fileManager.addError(error);

        //equals
        List<Error> newErrorList = fileManager.getErrors();
        Assert.assertTrue(newErrorList.contains(error));
    }
}

import org.example.fileManager.FileManager;
import org.example.model.DataSet;
import org.example.model.Error;
import org.example.model.ErrorType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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
        List<DataSet> data = fileManager.getData(1);
        fileManager.putData(1,123);
        List<DataSet> data2 = fileManager.getData(1);
    }
}

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileWorkTest {

    private boolean fileEquals(String way1,String way2) throws IOException {

        FileReader file1 = new FileReader(way1);
        FileReader file2 = new FileReader(way2);
        BufferedReader bufferedReader1 = new BufferedReader(file1);
        BufferedReader bufferedReader2 = new BufferedReader(file2);
        String correctStr1 = bufferedReader1.readLine();
        String correctStr2 = bufferedReader2.readLine();
        while ( correctStr1 != null && correctStr2 != null) {
            if (!correctStr1.equals(correctStr2)) return false;
            correctStr1 = bufferedReader1.readLine();
            correctStr2 = bufferedReader2.readLine();
        }
        if (correctStr1 != null && correctStr2 == null || correctStr2 != null && correctStr1 == null) return false;
        bufferedReader1.close();
        bufferedReader2.close();
        return true;
    }

    @Test
    public void FileWorkTest() throws IOException {
        try {
            new FileWork("tail -n 4 -o outputFiles/testFile1 inputFiles/file1 inputFiles/file2");
            new FileWork("tail -c 30 -o outputFiles/testFile2 inputFiles/file1 inputFiles/file2");
            new FileWork("tail -n 27 -o outputFiles/testFile3 inputFiles/file1 inputFiles/file2 inputFiles/file3 inputFiles/file4" );

        } catch (IOException e) {
            e.printStackTrace();
        }
        assertTrue(fileEquals("outputFiles/testFile1", "testFiles/trueFile1"));
        assertTrue(fileEquals("outputFiles/testFile2", "testFiles/trueFile2"));
        assertTrue(fileEquals("outputFiles/testFile3", "testFiles/trueFile3"));
    }


}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadLine {
    public ArrayList read(int index,String[] command,int nNum) throws IOException {
        ArrayList res = new ArrayList<String>();
        int length = command.length;
        for (int i = index; i < length; i++) {
            String way = command[i];
            FileReader file = new FileReader(way);
            BufferedReader bufferedReader = new BufferedReader(file);
            if (nNum != -1) {
                String correctStr = bufferedReader.readLine();
                while (correctStr != null) {
                    res.add(correctStr);
                    correctStr = bufferedReader.readLine();
                }
                bufferedReader.close();
            }
        }
        return res;
    }
}


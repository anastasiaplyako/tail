import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadChar {
    public ArrayList readC(int index, String[] command, int cNum) throws IOException {
        ArrayList res = new ArrayList<String>();
        int length = command.length;
        for (int i = index; i < length; i++) {
            String way = command[i];
            FileReader file = new FileReader(way);
            BufferedReader bufferedReader = new BufferedReader(file);
            if (cNum != -1) {
                int correctChar = bufferedReader.read();
                while (correctChar != -1) {
                    res.add((char) correctChar);
                    correctChar = bufferedReader.read();
                }
                bufferedReader.close();
            }
        }
        return res;
    }
}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadElse {

    public ArrayList readE(int index, String[] command) throws IOException {
        ArrayList res = new ArrayList<String>();
        int length = command.length;
        index++;
        for (int i = index; i < length; i++) {
            ArrayList tmp = new ArrayList<String>();
            String way = command[i];
            FileReader file = new FileReader(way);
            BufferedReader bufferedReader = new BufferedReader(file);
            String correctStr = bufferedReader.readLine();
            while (correctStr != null) {
                tmp.add(correctStr + "\n");
                correctStr = bufferedReader.readLine();
            }
            bufferedReader.close();
            if (tmp.size() > 10)
                for (int j = tmp.size() - 10; j < tmp.size(); j++) {
                    res.add(tmp.get(j));
                }
            else for (int j = 0; j < tmp.size(); j++) {
                res.add(tmp.get(j));
            }
        }
        return res;
    }

}

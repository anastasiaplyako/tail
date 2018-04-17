import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ParsingLine {
    private int cNum;
    private int nNum;
    private int index = 0;
    private String oName;

    public ParsingLine(String str) throws IOException {
        final String[] command = str.split("[ ]");
        final int length = command.length;
        int cNum = -1;
        int nNum = -1;
        String oName = "";
        int i;
        int index = 1;
        boolean checkC = false;
        boolean checkN = false;
        boolean checkO = false;
        //перебираем флаги
        for (i = 1; i < 4; i++) {
            String current = "";
            if (i < length + 1) {
                current = command[i];
            } else break;
            if (current.equals("-c") && checkC == false) {
                checkC = true;
                index += 2;
                cNum = Integer.parseInt(command[i + 1]);
            } else if (current.equals("-n") && checkN == false && checkC == false) {
                checkN = true;
                index += 2;
                nNum = Integer.parseInt(command[i + 1]);
            } else if (current.equals("-o") && checkO == false) {
                checkO = true;
                index += 2;
                oName = command[i + 1];
                if (oName.equals("")) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    oName = reader.readLine();
                    reader.close();
                }
            }
        }
        this.cNum = cNum;
        this.nNum = nNum;
        this.index = index;
        this.oName = oName;
    }

    public int flagC() {
        return cNum;
    }

    public int flagN() {
        return nNum;
    }

    public int indexInCommand() {
        return index;
    }

    public String flagO() {
        return oName;
    }
}


import java.io.*;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;


public class FileWork {
    private int cNum;
    private int nNum;
    private int index = 0;
    private String oName;

    public FileWork(String str) throws IOException, IllegalFormatException {
        final String[] command = str.split("[ ]");
        if (!command[0].equals("tail")) {
            throw new IllegalArgumentException();
        }
        final int length = command.length;
        //перебираем флаги
        ParsingLine flags = new ParsingLine(str);
        cNum = flags.flagC();
        nNum = flags.flagN();
        oName = flags.flagO();
        index = flags.indexInCommand();
        List res = new ArrayList<String>();
        //случай,в котором указано два флага с и n
        if (cNum != -1 && nNum != -1) throw new IllegalArgumentException("одновременно указаны 2 флага");
        //если имя входных файлов не указано, то считываем с консоли
        if (index == length) {
            res.add(new InputStreamReader(System.in));
        }
        this.cNum = cNum;
        this.nNum = nNum;
        this.index = index;
        this.oName = oName;
        ReadLine line = new ReadLine();
        ReadChar ch = new ReadChar();
        ReadElse el = new ReadElse();
        if (nNum != -1) {
            res = line.read(index, command, nNum);
        } else if (cNum != -1) {
            res = ch.readC(index, command, cNum);
        } else {
            res = el.readE(index, command);
        }
        writeFileOut(command, res);
    }
    //создание и запись в файл
    private void writeFileOut(String[] command, List res) throws IOException {
        try (FileWriter fileOut = new FileWriter(oName)) {
            if (cNum != -1) {
                if (!oName.equals("")) {
                    for (int j = res.size() - cNum; j < res.size(); j++) {
                        fileOut.write(res.get(j).toString());
                    }
                } else {
                    for (int j = res.size() - cNum; j < res.size(); j++) {
                        System.out.println(res.get(j).toString());
                    }
                }
            } else {
                if (nNum != -1) {
                    if (!oName.equals("")) {
                        for (int j = res.size() - nNum; j < res.size() - 1; j++) {
                            fileOut.write(res.get(j).toString() + "\n");
                        }
                        fileOut.write(res.get(res.size() - 1).toString());
                    } else {
                        for (int j = res.size() - nNum; j < res.size() - 1; j++) {
                            System.out.println(res.get(j).toString() + "\n");
                        }
                        System.out.println(res.get(res.size() - 1).toString());
                    }
                } else {
                    for (int j = 0; j < res.size(); j++) {
                        fileOut.write(res.get(j).toString());
                    }
                }
            }
            fileOut.flush();
        } catch (IOException ex) {
            System.out.print(ex.getMessage());
        }
    }
}
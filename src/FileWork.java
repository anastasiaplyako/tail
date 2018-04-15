
import java.io.*;
import java.util.Scanner;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.Scanner;
public class FileWork {
    private String[] flag;
    private  int cNum ;
    private int nNum;
    private int index = 0;
    private String oName;

    public FileWork(String str) throws IOException, IllegalFormatException {
        final String[] command = str.split("[ ]");
        Scanner in = new Scanner(System.in);
        if (!command[0].equals("tail")) {
            throw new IllegalArgumentException();
        }
        final int length = command.length;
        int cNum = -1;
        int nNum = -1;
        String oName = "";
        int i;
        int index = 1;
        boolean checkC = false;
        boolean checkN = false;
        boolean checkO = false;
        for (i = 1; i < 4; i++) {//перебираем флаги
            String current = "";
            if (i < length + 1) {
                current = command[i];
            } else break;
            if (current.equals("-c") && checkC == false) {
                checkC = true;
                index+=2;
                cNum = Integer.parseInt(command[i + 1]);
            } else if (current.equals("-n") && checkN == false && checkC == false) {
                checkN = true;
                index+=2;
                nNum = Integer.parseInt(command[i + 1]);
            } else if (current.equals("-o") &&  checkO == false) {
                checkO = true;
                index+=2;
                oName = command[i + 1];
                if (oName.equals("")) {
                    in.next();
                }
            }
        }
        if (index == length) {
            in.next();
        }
        this.cNum = cNum;
        this.nNum = nNum;
        this.index = index;
        this.oName = oName;
        ArrayList res = new ArrayList<String>();
        FileWriter fileOut = new FileWriter(oName);
        //обработки строки
        if(nNum != -1) {
            res = readLine(command);
        } else if (cNum != -1){
            res = readChar(command);
        } else {
            res = readElse(command);
        }
        writeFileOut(command,res);
    }


    private String[]  analysisStr (String str){
        final String[] command = str.split("[ ]");
        return command;
    }

    private ArrayList<String> readLine(String[] command) throws IOException {
        ArrayList res = new ArrayList<String>();
        int length = command.length;
        for (int i = index; i < length; i++) {
            String way = command[i];
            FileReader file = new FileReader(way);
            BufferedReader bufferedReader = new BufferedReader(file);
            if (nNum != -1) {
                String correctStr = bufferedReader.readLine();
                while ( correctStr != null) {
                    res.add(correctStr);
                    correctStr = bufferedReader.readLine();
                }
                bufferedReader.close();
            }
        }
        return res;
    }

    private ArrayList<String> readChar(String[] command) throws IOException {
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

    //здесь ошибка
    private ArrayList<String> readElse(String[] command) throws IOException{
        ArrayList res = new ArrayList<String>();
        int length = command.length;
        for (int i = index; i < length; i++) {
            String way = command[index];
            FileReader file = new FileReader(way);
            Scanner scanner = new Scanner(file);
            BufferedReader bufferedReader = new BufferedReader(file);
            if (cNum != -1 && nNum != -1){
                int numberOfLine = 0;
                while (scanner.hasNext()){
                    numberOfLine++;
                }
                if (numberOfLine > 10) {
                    for (int j = 0; j < numberOfLine; j++) {
                        if (j > 10) {
                            res.add(scanner.nextLine());
                        }
                    }
                } else for (int j = 0; j < numberOfLine; j++) {
                    res.add(scanner.nextLine());
                }
            }
        }
        return res;
    }

    private void writeFileOut(String[] command,ArrayList res) throws IOException{
        String wayOut = "";
        if (!oName.equals("")) {
            wayOut = oName;
            try  (FileWriter fileOut = new FileWriter(wayOut)) {
                //запись в файл
                if (nNum != -1) {
                    for (int j = res.size() - nNum; j < res.size() - 1; j++) {
                        fileOut.write(res.get(j).toString() + "\n");
                    }
                    fileOut.write(res.get(res.size() -1).toString());
                } else {
                    for (int j = res.size() - cNum; j < res.size(); j++) {
                        fileOut.write(res.get(j).toString());
                    }
                }
                fileOut.flush();

            }
            catch (IOException ex){
                System.out.print(ex.getMessage());
            }
        }
    }
}


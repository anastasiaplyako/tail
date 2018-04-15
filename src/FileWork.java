
import java.io.*;
import java.util.ArrayList;
import java.util.IllegalFormatException;


public class FileWork {
    private String[] flag;
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
        ArrayList res = new ArrayList<String>();
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
        if (nNum != -1) {
            res = readLine(command);
        } else if (cNum != -1) {
            res = readChar(command);
        } else {
            res = readElse(command);
        }
        writeFileOut(command, res);
    }

    //считываение строк
    private ArrayList<String> readLine(String[] command) throws IOException {
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

    //считывание символов
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

    //считывание последних 10 строк ()
    private ArrayList<String> readElse(String[] command) throws IOException {
        ArrayList res = new ArrayList<String>();
        int length = command.length;
        index++;
        for (int i = index; i < length; i++) {
            ArrayList tmp = new ArrayList<String>();
            String way = command[i];
            FileReader file = new FileReader(way);
            BufferedReader bufferedReader = new BufferedReader(file);
            int numberOfLine = 0;
            String correctStr = bufferedReader.readLine();
            while (correctStr != null) {
                tmp.add(correctStr + "\n");
                correctStr = bufferedReader.readLine();
            }
            bufferedReader.close();
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

    //создание и запись в файл
    private void writeFileOut(String[] command, ArrayList res) throws IOException {
        String wayOut = "";
        if (!oName.equals("")) {
            wayOut = oName;
            try (FileWriter fileOut = new FileWriter(wayOut)) {
                if (nNum != -1) {
                    for (int j = res.size() - nNum; j < res.size() - 1; j++) {
                        fileOut.write(res.get(j).toString() + "\n");
                    }
                    fileOut.write(res.get(res.size() - 1).toString());
                } else {
                    if (cNum != -1) {
                        for (int j = res.size() - cNum; j < res.size(); j++) {
                            fileOut.write(res.get(j).toString());
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
        //случай, в котором не указано имя выходного файла (консольный вывод)
        else {
            if (nNum != -1) {
                for (int j = res.size() - nNum; j < res.size() - 1; j++) {
                    System.out.println(res.get(j).toString() + "\n");
                }
                System.out.println(res.get(res.size() - 1).toString());
            } else {
                for (int j = res.size() - cNum; j < res.size(); j++) {
                    System.out.println(res.get(j).toString());
                }
            }
        }
    }
}


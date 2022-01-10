import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class Template {

    public static void run(Scanner scanner, PrintWriter writer){
        // тут писать код решения задачи
        // для получения входных данных используйте scanner например:
        String s = scanner.nextLine();
        // для примера сделаем все буквы заглавными
        String res = s.toUpperCase();
        // для записи ответа используйте writer например:
        writer.println(res);
    }
    // тут написать названия файлов со входными данными
    private static String [] inputFiles = new String[]{ "input1.txt", "input2.txt", "input3.txt"};

    public static void main(String[] args) {
        for (int i = 0; i < inputFiles.length; i++) {
            test(inputFiles[i], i + 1);
        }
    }

    private static void test(String input, int numTest){
        String output = "output" + numTest + ".txt";
        try {
            Scanner in = new Scanner(new FileInputStream(input));
            PrintWriter out = new PrintWriter(new FileOutputStream(output));
            System.gc();
            long startTime = System.currentTimeMillis();
            run(in, out);
            long endTime = System.currentTimeMillis();
            double memory = getMemoryMb();
            in.close();
            out.close();
            System.out.println("Тест №" + numTest);
            System.out.println("---------------------Входные данные----------------------");
            printFile(input);
            System.out.println("---------------------------------------------------------\n");
            System.out.println("--------------------Выходные данные----------------------");
            printFile(output);
            System.out.println("---------------------------------------------------------\n");
            double time = (endTime - startTime) / 1000.0;
            if (time < 0.001) time = 0.001;
            System.out.println("Затраченное время: " + time + " секунд");
            System.out.println("Затраченная память: " + memory + "Mb");
            System.out.println();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private static void printFile(String fileName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream(fileName));
        while (scanner.hasNext()){
            System.out.println(scanner.nextLine());
        }
        scanner.close();
    }


    private static double getMemoryMb(){
        return Math.round((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048.576) / 1000.0;
    }

}


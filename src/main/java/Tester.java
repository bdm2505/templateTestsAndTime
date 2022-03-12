package tests;

import org.apache.poi.hssf.usermodel.*;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Tester {

    public static void run(Algorithm algorithm) {
        try (HSSFWorkbook wb = new HSSFWorkbook()) {


            HSSFSheet sheet = wb.createSheet("Task");
            HSSFRow row = sheet.createRow(0);
            HSSFCellStyle cs = wb.createCellStyle();

            cs.setFont(wb.createFont());
            // Word Wrap MUST be turned on
            cs.setWrapText(true);

            createCell(row, 0, "N", cs);
            createCell(row, 1, "input", cs);
            createCell(row, 2, "output", cs);
            createCell(row, 3, "correctness", cs);
            createCell(row, 4, "time in ms", cs);
            createCell(row, 5, "memory in MB", cs);
            Thread.sleep(300);

            File dir = new File(algorithm.inputFolder());
            if(!dir.isDirectory()){
                System.err.println(dir.getAbsolutePath() + " - not directory");
            }
            File[] files = dir.listFiles(File::isFile);
            Arrays.sort(files);
            for (int i = 0; i < files.length; i++) {
                test(files[i], i + 1, algorithm, sheet.createRow(i + 1), cs);
            }

            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            sheet.autoSizeColumn(5);
            File file = new File(algorithm.inputFolder() + "_table.xls");
            try {
                file.getParentFile().mkdirs();
            } catch (Exception e) {}
            try (FileOutputStream out = new FileOutputStream(file)) {
                wb.write(out);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void test(File input, int numTest, Algorithm algorithm, HSSFRow row, HSSFCellStyle cs) {
        String output = "output/" + input.getName() + ".output.txt";
        try {
            File file = new File(output);
            file.getParentFile().mkdirs();
        } catch (Exception e) {}

        try {
            Scanner in = new Scanner(new FileInputStream(input));
            PrintWriter out = new PrintWriter(new FileOutputStream(output));
            System.gc();
            long startTime = System.currentTimeMillis();
            algorithm.run(in, out);
            long endTime = System.currentTimeMillis();
            double memory = getMemoryMb();
            in.close();
            out.close();
            createCell(row, 0, "" +numTest, cs);
            System.out.println("test N" + numTest);
            System.out.println("---------------------Input data----------------------");
            String inputSt = readFile(input);
            System.out.println(inputSt);
            createCell(row, 1, inputSt, cs);
            System.out.println("---------------------------------------------------------\n");
            System.out.println("--------------------Output data----------------------");
            String outputSt = readFile(new File(output));
            System.out.println(outputSt);
            createCell(row, 2, outputSt, cs);
            System.out.println("---------------------------------------------------------\n");
            createCell(row, 3, "OK", cs);

            long time = (endTime - startTime) ;
            if (time < 1) time = 1;
            createCell(row, 4, "" + time, cs);
            createCell(row, 5, "" + memory, cs);
            System.out.println("time : " + time + " ms");
            System.out.println("memory: " + memory + "MB");
            System.out.println();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static String readFile(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream(file));
        StringBuilder sb = new StringBuilder();
        int count = 0;
        while (scanner.hasNext()) {
            if (count != 0){
                sb.append("\n");
            }
            count++;
            if (count > Algorithm.MAX_LINES) {
                sb.append("...");
                break;
            }
            var ss = scanner.nextLine();
            if (ss.length() > 40)
                ss = ss.substring(0, 40) + "... " + (ss.length() - 40) + " symbols";
            sb.append(ss);
        }
        int nn = 0;
        while (scanner.hasNext()){
            nn++;
            scanner.nextLine();
        }
        if (count > Algorithm.MAX_LINES) {
            sb.append("\n").append(nn).append(" lines");
        }
        scanner.close();
        return sb.toString();
    }


    private static double getMemoryMb() {
        return Math.round((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 104857.6) / 10.0 - 1;
    }

    private static void createCell(HSSFRow rowNumber, int column, String name, HSSFCellStyle cs) {
        HSSFCell cell = rowNumber.createCell(column);
        cell.setCellStyle(cs);
        cell.setCellValue(name);
    }
}

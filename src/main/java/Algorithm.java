package tests;

import java.io.PrintWriter;
import java.util.Scanner;

public interface Algorithm {

    void run(Scanner scanner, PrintWriter writer);

    String inputFolder();

    /**
     * количество строчек при привышении которых вывод будет обрезаться
     */
    int MAX_LINES = 20;
}

import java.io.PrintWriter;
import java.util.Scanner;

public class Main implements Algorithm {

    public static void main(String[] args) {
        Tester.run(new Main());
    }

    @Override
    public void run(Scanner scanner, PrintWriter writer) {
        // тут писать код решения задачи
        // для получения входных данных используйте scanner
        // для записи ответа используйте writer

        // для примера задача a + b
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int sum = a + b;
        writer.println(sum);
    }

    @Override
    public String inputFolder() {
        return "input/task1";
    }
}

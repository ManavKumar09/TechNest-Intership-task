import java.io.*;
import java.util.*;

public class filehandling {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        while (true) {
            System.out.println("\n1.Read  2.Write  3.Append  4.Modify  5.Exit");
            int ch = sc.nextInt(); sc.nextLine();
            System.out.print("File name: ");
            String name = sc.nextLine();

            switch (ch) {
                case 1 -> read(name);
                case 2 -> write(name);
                case 3 -> append(name);
                case 4 -> modify(name);
                case 5 -> { System.out.println("Exit."); return; }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    static void read(String f) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line;
        while ((line = br.readLine()) != null)
            System.out.println(line);
        br.close();
    }

    static void write(String f) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
        System.out.println("Write text (type 'end' to stop):");
        while (true) {
            String line = sc.nextLine();
            if (line.equalsIgnoreCase("end")) break;
            bw.write(line); bw.newLine();
        }
        bw.close();
    }

    static void append(String f) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));
        System.out.println("Append text (type 'end' to stop):");
        while (true) {
            String line = sc.nextLine();
            if (line.equalsIgnoreCase("end")) break;
            bw.write(line); bw.newLine();
        }
        bw.close();
    }

    static void modify(String f) throws IOException {
        List<String> lines = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line;
        while ((line = br.readLine()) != null)
            lines.add(line);
        br.close();

        for (int i = 0; i < lines.size(); i++)
            System.out.println((i + 1) + ": " + lines.get(i));

        System.out.print("Line number to change: ");
        int ln = sc.nextInt(); sc.nextLine();
        System.out.print("New text: ");
        lines.set(ln - 1, sc.nextLine());

        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
        for (String l : lines) {
            bw.write(l); bw.newLine();
        }
        bw.close();
    }
}

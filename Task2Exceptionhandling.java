import java.util.InputMismatchException;
import java.util.Scanner;

public class Task2Exceptionhandling {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {
            try {
                /* --- read first number --- */
                System.out.print("\nFirst number  (or 'q' to quit): ");
                String first = sc.next();
                if (first.equalsIgnoreCase("q")) break;      // exit option
                double a = Double.parseDouble(first);

                /* --- read operator --- */
                System.out.print("Operator (+  -  *  /): ");
                char op = sc.next().charAt(0);

                /* --- read second number --- */
                System.out.print("Second number : ");
                double b = sc.nextDouble();

                /* --- calculate --- */
                double result;
                switch (op) {
                    case '+' -> result = a + b;
                    case '-' -> result = a - b;
                    case '*' -> result = a * b;
                    case '/' -> {
                        if (b == 0) throw new ArithmeticException("Cannot divide by zero");
                        result = a / b;
                    }
                    default -> throw new IllegalArgumentException("Unsupported operator");
                }

                /* --- print answer --- */
                System.out.println("= " + result);

            /* ----------- EXCEPTION HANDLERS ----------- */
            } catch (NumberFormatException | InputMismatchException e) {
                System.out.println(" Please enter valid numbers.");
                sc.nextLine();   // clear any leftover input
            } catch (ArithmeticException e) {
                System.out.println("Arithmetic error " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Illegal argument" + e.getMessage());
            } catch (Exception e) {     // catch‑all for anything else
                System.out.println("Unexpected error: " + e);
            }
        }

        System.out.println("\nGood‑bye!");
        sc.close();
    }
}

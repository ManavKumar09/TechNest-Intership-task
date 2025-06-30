import java.util.ArrayList;
import java.util.Scanner;

public class Task2Studentmanagment {

    /* --- A tiny Student "record" --- */
    static class Student {
        int roll;
        String name;
        int age;
        String course;

        Student(int roll, String name, int age, String course) {
            this.roll = roll;
            this.name = name;
            this.age = age;
            this.course = course;
        }

        @Override
        public String toString() {
            return roll + "  " + name + "  " + age + "  " + course;
        }
    }

    public static void main(String[] args) {

        ArrayList<Student> list = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n1 Add  2 View  3 Search  4 Delete  5 Update  0 Exit");
            System.out.print("Choose: ");
            int ch = sc.nextInt();
            sc.nextLine();                       // clear leftover newline

            switch (ch) {

                /* Add */
                case 1 -> {
                    System.out.print("Roll  : "); int r = sc.nextInt(); sc.nextLine();
                    System.out.print("Name  : "); String n = sc.nextLine();
                    System.out.print("Age   : "); int a = sc.nextInt(); sc.nextLine();
                    System.out.print("Course: "); String c = sc.nextLine();
                    list.add(new Student(r, n, a, c));
                    System.out.println("✅ Added");
                }

                /* View all */
                case 2 -> {
                    if (list.isEmpty()) System.out.println("No records.");
                    else list.forEach(System.out::println);
                }

                /* Search by roll */
                case 3 -> {
                    System.out.print("Roll to find: "); int r = sc.nextInt();
                    list.stream()
                        .filter(s -> s.roll == r)
                        .findFirst()
                        .ifPresentOrElse(
                            System.out::println,
                            () -> System.out.println("Not found"));
                }

                /* Delete by roll */
                case 4 -> {
                    System.out.print("Roll to delete: "); int r = sc.nextInt();
                    boolean ok = list.removeIf(s -> s.roll == r);
                    System.out.println(ok ? "🗑️  Deleted" : "Not found");
                }

                /* Update by roll */
                case 5 -> {
                    System.out.print("Roll to update: "); int r = sc.nextInt(); sc.nextLine();
                    Student target = null;
                    for (Student s : list) if (s.roll == r) { target = s; break; }
                    if (target == null) {
                        System.out.println("Not found");
                    } else {
                        System.out.print("New Name  : "); target.name = sc.nextLine();
                        System.out.print("New Age   : "); target.age  = sc.nextInt(); sc.nextLine();
                        System.out.print("New Course: "); target.course = sc.nextLine();
                        System.out.println("✏️  Updated");
                    }
                }

                /* Exit */
                case 0 -> { System.out.println("Good‑bye!"); return; }

                default -> System.out.println("Invalid choice");
            }
        }
    }
}

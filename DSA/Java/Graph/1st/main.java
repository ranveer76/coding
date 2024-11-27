import java.util.Scanner;

class main {

    public static void magicpattern(int n) {
        int i, num;

        for (i = 100000; i <= 999999; i++) {
            num = i;
            int product = 1;
            while (num > 0) {
                product *= num % 10;
                num = num / 10;
            }
            if (product == n) {
                System.out.println(i);
            }
        }
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int n = sc.nextInt();
            magicpattern(n);
            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
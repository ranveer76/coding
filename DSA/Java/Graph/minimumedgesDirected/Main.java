import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Main {
    static int minimumEdge(int n) {
        if (n == 1)
            return 0;
        int[] minD = new int[n + 1];
        Queue<Integer> q = new LinkedList<>();
        q.add(1);

        while (!q.isEmpty()) {
            int i = q.peek();
            q.remove();
            if(i+1 == n || 3*i == n)
                return minD[i] + 1;
            if (i + 1 <= n && minD[i + 1] == 0) {
                q.add(i + 1);
                minD[i + 1] = minD[i] + 1;
            }
            if (3 * i <= n && minD[3 * i] == 0) {
                q.add(3 * i);
                minD[3 * i] = minD[i] + 1;
            }
        }

        return 0;
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int n = sc.nextInt();
            System.out.println(minimumEdge(n));
        }
        catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
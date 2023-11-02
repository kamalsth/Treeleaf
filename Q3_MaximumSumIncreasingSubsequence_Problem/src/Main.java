import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {
    public static List<Integer> maxSumIncreasingSubsequence(int[] sequence) {
        int n = sequence.length;
        int[] maxSumStore = new int[n];
        int[] previousIndex = new int[n];

        Arrays.fill(previousIndex, -1);

        for (int i = 0; i < n; i++) {
            maxSumStore[i] = sequence[i];
            for (int j = 0; j < i; j++) {
                if (sequence[i] > sequence[j] && maxSumStore[i] < maxSumStore[j] + sequence[i]) {
                    maxSumStore[i] = maxSumStore[j] + sequence[i];
                    previousIndex[i] = j;
                }
            }
        }

        int maxSum = maxSumStore[0];
        int maxSumIndex = 0;

        for (int i = 1; i < n; i++) {
            if (maxSumStore[i] > maxSum) {
                maxSum = maxSumStore[i];
                maxSumIndex = i;
            }
        }

        List<Integer> subsequence = new ArrayList<>();
        while (maxSumIndex != -1) {
            subsequence.add(sequence[maxSumIndex]);
            maxSumIndex = previousIndex[maxSumIndex];
        }

        Collections.sort(subsequence);
        return subsequence;
    }
    public static void main(String[] args) {
        int[] sequence = {0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11};
        List<Integer> result = maxSumIncreasingSubsequence(sequence);
        System.out.println("Maximum Sum Increasing Subsequence: " + result);    }
}
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class App {

    public static void main(String args[]) {

        Instant start = Instant.now();
        int THREADS = 8;

        Random random = new Random();

        int[] arr1 = random.ints(800000, 0, 40).toArray();

        // Split the array in multiple parts
        int[][] split = splits(THREADS, arr1);

        // Each thread should selection sort the sub array
        int[][] holder = new int[THREADS][];
        for (int i = 0; i < THREADS; i++) {
            int[] out = Runner.startThread(split[i]);
            holder[i] = out;
        }

        int[] total = merge(holder[0], holder[1]);
        total = merge(total, holder[2]);
        total = merge(total, holder[3]);
        total = merge(total, holder[4]);
        total = merge(total, holder[5]);
        total = merge(total, holder[6]);
        total = merge(total, holder[7]);

        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();

        System.out.println(timeElapsed);
        System.out.println(isSorted(total));
    }

    // merge the sorted sub arrays
    public static int[] merge(int[] a, int[] b) {
        int[] mergedArray = new int[a.length + b.length];
        int i = a.length - 1, j = b.length - 1, k = mergedArray.length;

        while (k > 0)
            mergedArray[--k] = (j < 0 || (i >= 0 && a[i] >= b[j])) ? a[i--] : b[j--];
        return mergedArray;
    }

    static boolean isSorted(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1])
                return false;
        }
        return true;
    }

    private static int[][] splits(int THREADS, int[] arr) {
        if (arr.length == 0) {
            return new int[0][0];
        }

        int splitLength = (int) Math.ceil((double) arr.length / (double) THREADS);
        int[][] splits = new int[THREADS][];

        int j = 0;
        int k = 0;
        for (int i = 0; i < arr.length; i++) {
            if (k == splitLength) {
                k = 0;
                j++;
            }
            if (splits[j] == null) {
                int remainingNumbers = arr.length - i;
                splits[j] = new int[Math.min(remainingNumbers, splitLength)];
            }
            splits[j][k++] = arr[i];
        }
        return splits;
    };

}

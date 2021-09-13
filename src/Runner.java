import java.util.concurrent.*;

public class Runner {

    /**
     * Starts a thread (gets the minimum number from the subarray)
     * @param subArray is a part of the array
     * @return is the minimum number of the array part
     */
    public static int[] startThread(int[] subArray) {

        ExecutorService executor = Executors.newCachedThreadPool();

        Callable callable = () -> {

            for (int i = 0; i < subArray.length - 1; i++) {
                int index = i;
                for (int j = i + 1; j < subArray.length; j++) {
                    if (subArray[j] < subArray[index]) {
                        index = j;//searching for lowest index
                    }
                }
                int smallerNumber = subArray[index];
                subArray[index] = subArray[i];
                subArray[i] = smallerNumber;
            }

            return subArray;
        };

        Future<int[]> future = executor.submit(callable);

        int[] sortedSubArray = new int[subArray.length];

        try {
            sortedSubArray = future.get(); // Wait for the thread to complete
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        executor.shutdown();

        return sortedSubArray;
    }

}

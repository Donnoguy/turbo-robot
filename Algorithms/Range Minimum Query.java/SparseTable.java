public class RangeMinimumQuerySparseTable {
    int[][] sparseTable;  // Sparse table for storing minimums
    int[] log;            // Precomputed logarithms

    public RangeMinimumQuerySparseTable(int[] arr) {
        int n = arr.length;
        int maxLog = (int) (Math.log(n) / Math.log(2)) + 1;

        sparseTable = new int[n][maxLog];
        log = new int[n + 1];

        // Build the sparse table for range minimum queries
        buildSparseTable(arr, n, maxLog);
    }

    private void buildSparseTable(int[] arr, int n, int maxLog) {
        // Initialize the log array
        log[1] = 0;
        for (int i = 2; i <= n; i++) {
            log[i] = log[i / 2] + 1;
        }

        // Initialize sparse table for the intervals of length 1
        for (int i = 0; i < n; i++) {
            sparseTable[i][0] = arr[i];
        }

        // Compute values for intervals of length 2^j
        for (int j = 1; j < maxLog; j++) {
            for (int i = 0; i + (1 << j) <= n; i++) {
                sparseTable[i][j] = Math.min(sparseTable[i][j - 1], sparseTable[i + (1 << (j - 1))][j - 1]);
            }
        }
    }

    // Function to get the minimum in range [l, r]
    public int query(int l, int r) {
        int j = log[r - l + 1];
        return Math.min(sparseTable[l][j], sparseTable[r - (1 << j) + 1][j]);
    }

    public static void main(String[] args) {
        int[] arr = {1, 3, 2, 7, 9, 11, 3, 2, 6, 8, 10};
        RangeMinimumQuerySparseTable rmq = new RangeMinimumQuerySparseTable(arr);
        System.out.println(rmq.query(1, 5));  // Output: 2
        System.out.println(rmq.query(3, 8));  // Output: 2
    }
}

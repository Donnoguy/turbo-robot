import java.util.Arrays;

public class RangeMinimumQuerySqrt {
    int[] arr;    // Original array
    int[] block;  // Array for storing minimums of blocks
    int blockSize;

    public RangeMinimumQuerySqrt(int[] arr) {
        this.arr = arr;
        int n = arr.length;
        blockSize = (int) Math.sqrt(n);  // Size of each block
        block = new int[(n + blockSize - 1) / blockSize];  // Ceiling division
        Arrays.fill(block, Integer.MAX_VALUE);

        // Preprocess to fill block array
        for (int i = 0; i < n; i++) {
            block[i / blockSize] = Math.min(block[i / blockSize], arr[i]);
        }
    }

    // Function to get the minimum in range [l, r]
    public int query(int l, int r) {
        int min = Integer.MAX_VALUE;

        // Traverse the range block by block
        while (l <= r && l % blockSize != 0) {
            // Traverse the first partial block
            min = Math.min(min, arr[l]);
            l++;
        }
        while (l + blockSize <= r) {
            // Traverse full blocks
            min = Math.min(min, block[l / blockSize]);
            l += blockSize;
        }
        while (l <= r) {
            // Traverse the last partial block
            min = Math.min(min, arr[l]);
            l++;
        }
        return min;
    }

    public static void main(String[] args) {
        int[] arr = {1, 3, 2, 7, 9, 11, 3, 2, 6, 8, 10};
        RangeMinimumQuerySqrt rmq = new RangeMinimumQuerySqrt(arr);
        System.out.println(rmq.query(1, 5));  // Output: 2
        System.out.println(rmq.query(3, 8));  
    }
}

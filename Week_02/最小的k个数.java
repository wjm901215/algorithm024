class Solution {
    /**
     * 解法1：使用排序
     * @param arr
     * @param k
     * @return
     */
    public int[] getLeastNumbers(int[] arr, int k) {
        int[] result = new int[k];
        Arrays.sort(arr);
        for (int i = 0; i < k; ++i) {
            result[i] = arr[i];
        }
        return result;
    }

    /**
     * 解法2：使用堆进行
     * @param arr
     * @param k
     * @return
     */
    public int[] getLeastNumbers(int[] arr, int k) {
        int[] result = new int[k];
        if (k == 0) {
            return result;
        }
        Queue<Integer> heap = new PriorityQueue<>(k,(o1,o2) -> o2 - o1);
        for (int i = 0; i < arr.length; ++i) {
            if (heap.size() < k) {
                heap.add(arr[i]);
            }else if(arr[i] < heap.peek()) {
                heap.poll();
                heap.add(arr[i]);
            }
        }
        int i = k - 1;
        while (!heap.isEmpty()) {
            result[i--] = heap.poll();
        }
        return result;
    }
}
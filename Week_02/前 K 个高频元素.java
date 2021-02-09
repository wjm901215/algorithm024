class Solution {
    /**
     * 解法一 暴力解法
     * 1.使用map以值做key，value为总数存储
     * 2.遍历Map，根据value排序。
     */
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> result = new HashMap();
        for (int i = 0; i < nums.length; ++i) {
            result.put(nums[i], result.getOrDefault(nums[i], 0) + 1);
        }
        Set<Map.Entry<Integer, Integer>> entry2 = result.entrySet();
        //这里将map.entrySet()转换成list
        List<Map.Entry<Integer, Integer>> list = new ArrayList(entry2);

        Collections.sort(list, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        int[] result_k = new int[k];
        for (int j = 0; j < list.size(); ++j) {
            Map.Entry<Integer, Integer> temp = list.get(j);
            if (j < k)
                result_k[j] = temp.getKey();
        }
        return result_k;
    }
    /**
     * 解法2:利用小顶堆
     * 1.将nums遍历，存入map中，key为值，value为该值出现的数量
     * 2.定义小顶堆，小顶堆的大小为k，遍历map，然后往小顶堆中增加元素
     * 3.判断新加入的元素是否>堆顶元素，如果大于则弹出堆顶元素，然后将新元素加入
     * 4.循环小顶堆结果，用数组接收并返回
     */
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer,Integer> map = new HashMap();
        for (int i = 0; i < nums.length; ++i) {
            map.put(nums[i],map.getOrDefault(nums[i],0)+1);
        }
        // 默认为小顶堆，需实现Comparator接口，按map的value进行排序
        Queue<Integer> heap = new PriorityQueue(k,(o1,o2) -> map.get(o1)-map.get(o2));
        for (int key :map.keySet()) {
            if (heap.size() < k) {
                heap.add(key);
            }else if (map.get(key) > map.get(heap.peek())) {
                heap.remove();
                heap.add(key);
            }
        }
        int[] result = new int[k];
        int i = 0;
        while (!heap.isEmpty()) {
            result[i++] = heap.remove();
        }
        return result;
    }
}
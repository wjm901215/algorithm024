public class NthUglyNumber {
    private int[] uglyNumber = {2,3,5};

    /**
     * 解法1
     * 通过遍历，循环判断丑数，直到集合大小到达给定的n值
     * @param n
     * @return
     */
    public int nthUglyNumber(int n) {
        List<Integer> res = new ArrayList();
        int i = 1;
        while  (res.size() < n){
            if (isUgly(i)) {
                res.add(i);
            }
            i++;
        }
        return res.get(n-1);
    }
    /**
     * 判断是否为丑数
     * 首先除2，直到不能整除为止，然后除3到不能整除为止，然后除5直到不能整除为止。最终判断剩余的数字是否为1，如果是1则为丑数，否则不是丑数
     */
    private static boolean isUgly(int num) {
        while (num % 2 == 0) {
            num /= 2;
        }
        while (num % 3 == 0) {
            num /= 5;
        }
        while (num % 5 == 0) {
            num /= 5;
        }
        return num == 1;
    }
    /**
     * 解法2
     * 利用小根堆，然后1作为第一个丑数，每次从小根堆弹出最小的丑数，
     * 然后记录已弹出丑数的个数，如果count>=n,返回当前弹出的元素，否则继续乘以2、3、5
     * @param n
     * @return
     */
    public int nthUglyNumber(int n) {
        //创建小根堆，每次出堆的都是最小值
        Queue<Long> queue = new PriorityQueue<>();
        queue.add(1L);
        //记录出堆的个数，出堆的元素完全按照从小到大排序
        int count = 0;
        while (! queue.isEmpty()){
            long cut = queue.poll();

            //如果出堆的个数>=n,当前cut就是第n个丑数
            if(++count >= n){
                return (int) cut;
            }
            for(int num : uglyNumber){
                //排除重复的数字
                if(! queue.contains(num * cut)){
                    queue.add(num * cut);
                }
            }
        }
        return -1;
    }


    /**
     * 解法3:采用动态规划方式
     * 定义3个指针，分别从下标 0开始，针对3个质因子【2、3、5】分别计算新从丑数，然后取最小数
     * 依次遍历，直到为n，返回结果
     */
    public int nthUglyNumber(int n) {
        int a = 0,b = 0,c = 0;
        int[] result =new int[n];
        result[0] = 1;
        for (int i = 1; i < n; ++i){
            int temp1 = result[a] * 2,temp2 = result[b] * 3,temp3 = result[c] * 5;
            result[i] = Math.min(Math.min(temp1,temp2),temp3);
            if(result[i] == temp1) a++;
            if(result[i] == temp2) b++;
            if(result[i] == temp3) c++;
        }
        return result[n-1];
    }

}

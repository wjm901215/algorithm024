class Solution {
    /**
     * 采用 贪心算法
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        int result = 0;
        for (int i = 0;i < prices.length - 1; ++i) {
            //后一个数减去前一个数，大于0则累加到result
            result += Math.max(prices[i+1] - prices[i],0);
        }
        return  result;
    }
}
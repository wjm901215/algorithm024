class Solution {
    public double myPow(double x, int n) {
        if(n < 0) {
            n = -n;
            x = 1 / x;
        }
        return _pow(x,n);
    }

    /**
     * 递归函数
     * @param x
     * @param n
     * @return
     */
    private double _pow(double x, int n) {
        //递归终止条件
        if (n == 0) {
            return  1;
        }
        //当前层处理逻辑,分治思路，大问题拆分成小问题
        double half =  _pow(x,n / 2);
        //如果是偶数，如n^10，则可以拆分计算一次n^5，然后将结果相乘
        if (n % 2 == 0) {
            return half  * half;
        }else {
            //如果是奇数，如n^5，则可以拆分计算一次n^2，然后将结果相乘，由于少算一个x，则还需要多乘以一个x
            return half  * half  *  x;
        }
        //下探到下一层

        //恢复当前状态
    }
}
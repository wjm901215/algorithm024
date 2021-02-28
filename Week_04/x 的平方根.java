class Solution {
    /**
     * 使用二分查找，套用二分查找模版进行解
     */
    public int mySqrt(int x) {
        int l = 0,r = x,mid,ans = -1;
        while (l <= r) {
            mid = (r -l) / 2 + l;
            //中位数平方如果小于等于x,则左指针右移[中间节点+1]
            if((long)mid * mid <= x) {
                ans = mid;
                l = mid + 1;
            }else {
                //中位数平方如果大于等于x,则右指针左移[中间节点-1]
                r = mid - 1;
            }
        }
        return ans;
    }
}
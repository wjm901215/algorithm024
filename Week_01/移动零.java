class Solution {
    /**
     * 解法1:定义j下标，循环判断不为0的往下标j中移动数据
     */
    public void moveZeroes(int[] nums) {
        int j = 0;
        for (int i=0;i < nums.length; ++i) {
            if (nums[i] != 0) {
                nums[j] = nums[i];
                //j的索引位置只记录不为0的最新下标
                //因为上面以及将nums[i]的数据移动到最新到下标j中，所以需要将当前位置到数据设置为0
                if(i != j) {
                    nums[i] = 0;
                }
                j++;
            }
        }
    }
}
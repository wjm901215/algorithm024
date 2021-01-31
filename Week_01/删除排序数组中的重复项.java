class Solution {
    /**
     * 定义快慢指针，遍历数组，当快指针与慢指针数据不一致时，将不重复的元素移到数组的左侧
     * @param nums
     * @return
     */
    public int removeDuplicates(int[] nums) {
        int j = 0;
        for (int i = 1; i < nums.length; ++i) {
            if (nums[j] != nums[i]) {
                nums[++j] = nums[i];
            }
        }
        return j+1;

    }
}
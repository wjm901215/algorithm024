class Solution {
    /**
     * 解题思路1:暴力求解，利用双重循环
     */
     public int[] twoSum(int[] nums, int target) {
         for (int i = 0; i < nums.length-1; ++i) {
             for (int j= i+1; j < nums.length; ++j){
                 if (nums[i] + nums[j] == target) {
                     return new int[]{i,j};
                 }
             }
         }
         return new int[2];
     }

    /**
     * 解题思路2:利用hash，key存储数组元素，target-nums[i]如果在map中存在，则表示找到答案
     */
    public int[] twoSum(int[] nums, int target) {
        Map<Integer,Integer> map = new HashMap();
        for (int i = 0; i < nums.length; ++i) {
            if(map.containsKey(target - nums[i])){
                return new int[]{map.get(target - nums[i]),i};
            }
            map.put(nums[i],i);
        }
        return new int[2];
    }
}
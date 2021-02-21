class Solution {
    public int climbStairs(int n) {
        Map<Integer,Integer> map = new HashMap<>();
        return _recur(n,map);
    }
    private int _recur(int n,Map<Integer,Integer> map) {
        //终止条件
        if (n == 1) return 1;
        if (n == 2) return 2;
        //当前层处理逻辑
        if(map.containsKey(n)){
            return map.get(n);
        }
        //下探到下一层
        int result = _recur(n - 1,map) + _recur(n - 2,map);
        //状态重置
        map.put(n,result);
        return result;
    }
}
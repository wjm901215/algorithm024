class Solution {
    List<String> result = new ArrayList<>();
    public List<String> generateParenthesis(int n) {
        _generate(0,0,n,"");
        return  result;
    }
    /**
     * 递归函数
     * @param left 左括号个数
     * @param right 右括号个数
     * @param n 终止条件
     * @param s 括号生成结果
     */
    private void _generate(int left,int right,int n,String s) {
        // 终止条件
        if (left >= n && right >= n) {
            result.add(s);
            return;
        }
        //当前层处理逻辑，验证括号合法性
        if (left < n) {
            _generate(left+1,right,n,s+"(");
        }
        if (left > right && right < n) {
            _generate(left,right+1,n,s+")");
        }
        //下探到下一层级

        //状态重置
    }
}
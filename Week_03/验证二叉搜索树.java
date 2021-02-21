class Solution{
    public boolean isValidBST(TreeNode root) {
        return validRecur(root,Long.MIN_VALUE,Long.MAX_VALUE);
    }

    private boolean validRecur(TreeNode root, long minValue, long maxValue) {
        //递归终止条件
        if (root == null) {
            return true;
        }
        //当前层处理逻辑,条件不满足直接返回false
        if (root.val >= maxValue || root.val <=minValue) {
            return false;
        }
        //下探到下一层
        boolean validLeft = validRecur(root.left,minValue,root.val);
        boolean validRight = validRecur(root.right,root.val,maxValue);
        return validLeft  && validRight;
        //恢复当前状态
    }
}
class Solution {
    public TreeNode invertTree(TreeNode root) {
        //递归的终止条件
        if (root == null) {
            return null;
        }
        //当前层逻辑处理
        final TreeNode left = root.left, right = root.right;
        //System.out.println("left:" + (left != null ? left.val : "null") + " right:" + (right != null ? right.val : "null"));
        //下探到下一层
        root.left = invertTree(right);
        root.right = invertTree(left);
        //当前状态重置
        return root;
    }
}
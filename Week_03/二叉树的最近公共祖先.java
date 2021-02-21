class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        //递归终止条件
        //当越过叶节点，则直接返回 null ；
        //当 root 等于 p, q则直接返回 root；
        if (root == null || root == p || root == q) {
            return root;
        }
        //处理当前层逻辑
        //下探到下一层
        //开启递归左子节点，返回值记为 left
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        //开启递归右子节点，返回值记为 right
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left == null) {
            return right;
        }
        if (right == null) {
            return left;
        }
        //恢复当前状态
        //当 left 和 right 同时不为空 ：说明 p,q 分列在 root 的 异侧 （分别在 左 / 右子树），因此 root 为最近公共祖先，返回 root ；
        return root;
    }
}
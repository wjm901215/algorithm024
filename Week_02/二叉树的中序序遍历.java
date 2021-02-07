/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    List<Integer> result = new ArrayList();
    /**
     * 解法一，使用递归
     *【前序遍历】左->根->右 先遍历左节点的数据，然后访问根结点，最后遍历右子树
     */

    public List<Integer> inorderTraversal(TreeNode root) {
        if (root == null) {
            return result;
        }
        inorderTraversal(root.left);
        result.add(root.val);
        inorderTraversal(root.right);
        return result;
    }


    /**
     * 解法二、使用遍历方式
     * TODO 还不是很熟，需要再多看
     * 1.利用双重循环，还是利用递归的思路，左->根->右
     * 2.内层循环中首先将左结点数据加入到Stack中，直到没有左结点数据。
     * 3.将栈顶元素弹出，并将值赋值到集合中。
     * 4.将栈顶元素右结点赋值给node，继续进行下一轮遍历。
     *
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res =new ArrayList();
        if (root == null) {
            return res;
        }
        Deque<TreeNode> stack = new LinkedList<TreeNode>();
        TreeNode node = root;
        while(!stack.isEmpty() || node != null) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
            node = stack.pop();
            res.add(node.val);
            node = node.right;
        }
        return  res;
    }
}

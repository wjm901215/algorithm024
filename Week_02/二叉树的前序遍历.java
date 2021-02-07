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
     *【前序遍历】根->左->右 先访问根节点，然后先遍历左子树，在遍历右子树
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        if (root == null) {
            return result;
        }
        result.add(root.val);
        this.preorderTraversal(root.left);
        this.preorderTraversal(root.right);
        return result;
    }

    /**
     * 解法二、使用遍历方式
     * TODO 还不是很熟，需要再多看
     * 1.利用双重循环，还是利用递归的思路，根->左->右
     * 2.内层循环中首先将根结点数据加入到List中，然后将当前结点放入栈中
     * 3.将左结点赋值给node，继续while循环，直到没有左结点，跳出循环。
     * 4.将栈顶元素弹出，并将栈顶元素右结点赋值给node，然后继续遍历
     *
     * @param root
     * @return
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList();
        if (root == null) {
            return result;
        }
        TreeNode node = root;
        Deque<TreeNode> deque = new LinkedList();
        while (deque.size() > 0 || node != null) {
            while (node != null) {
                result.add(node.val);
                deque.push(node);
                node = node.left;
            }
            node = deque.pop();
            node = node.right;
        }
        return result;
    }

}
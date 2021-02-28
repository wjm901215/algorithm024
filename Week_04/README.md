# 一、深度优先广度优先
## 1.DFS代码模版

使用栈进行实现。

### 1.1递归写法1（Java版）

```
/*
 * DFS递归代码模版
 */
public void dfs(Node node, Set<Node> visited) {
    // 递归终止条件
    if (node in visited) {
        return
    }
    // add node to visted;
    visted.add(node)
    //循环遍历找next node
    for (Node next_node : node.childred()) {
        // 判断next是否访问过，未访问则加入到visited中
        if (next_node is not in visited) {
            dfs(next_node,visited)
        }
    }
}
```



深度优先树的遍历顺序

![image.png](https://cdn.nlark.com/yuque/0/2021/png/1728458/1614491990367-94e9a509-e274-4e9d-9a3d-e0983bb81f27.png)

![image.png](https://cdn.nlark.com/yuque/0/2021/png/1728458/1614491649789-22647e77-e149-43ff-bffa-b48247aa8212.png?x-oss-process=image%2Fresize%2Cw_1500)

深度优先图的遍历顺序

![image.png](https://cdn.nlark.com/yuque/0/2021/png/1728458/1614491767414-4c699289-91eb-49d1-b3ce-c3aa0c5cb84a.png?x-oss-process=image%2Fresize%2Cw_1500)



## 2.BFS代码模版

使用队列来实现

### 2.1代码模版

```
/*
 * DFS递归代码模版
 */
public void bfs(Node node) {
    List<List<Integer>> allResults = new ArrayList<>();
    //使用队列进行维护，先进先出特性
    Queue<TreeNode> queueNode = new LinkedList<>();
    // add node to queueNode;
    nodes.add(node)
    //循环遍历找next node
    while (!queueNode.isEmpty()) {
        List list = new ArrayList<>();
        //将当前层size记录
        int size = queueNode.size();
        for (int i = 0; i < size; ++i) {
            //弹出队列数据
            TreeNode node = nodes.poll();
            list.add(node.val);
            if (node.left != null) queue.add(node.left);
            if (node.right != null) queue.add(node.right);
        }
        allResults.add(list);
    }
    return allResults;
}
```



广度优先树的遍历顺序

![image.png](https://cdn.nlark.com/yuque/0/2021/png/1728458/1614491969904-ccef1d97-4fad-42e9-b294-fd67e26cd1dc.png)  

![image.png](https://cdn.nlark.com/yuque/0/2021/png/1728458/1614491928853-7211bf1b-3cc3-4eae-a156-71b01b7b505f.png)




# 二、二分查找
二分查找模版
```
public class BinarySearch {
    public int binarySearch(int[] array, int target) {
        //定义左、中、右指针
        int left = 0, right = array.length - 1, mid;
        while (left <= right) {
            // 计算中间节点
            mid = (right - left) / 2 + left;
            if (array[mid] == target) {
                //如果中间节点==目标节点则直接返回
                return mid;
            } else if (array[mid] > target) {
                //如果中间节点大于目标节点，则右指针左[移中间节点-1]
                right = mid - 1;
            } else {
                //如果中间节点小于目标节点，则左指针右移[中间节点+1]
                left = mid + 1;
            }
        }
        return -1;
    }
}
```
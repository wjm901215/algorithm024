# 学习笔记

## 1.HashMap 底层是如何实现的？

在jdk1.7中使用的是数组+链表的形式进行存储，在jdk1.8中引入了红黑树，当链表长度大于8且容量大于64则转为红黑树。key- value对存储，key不允许重复。

### 1.1加载因子为什么是 0.75？

> 首先聊下什么是加载因子，加载因子是用来判断什么时候进行扩容的，比如加载因子为0.5，那HashMap的初始容量为16，当16*0.5=8个元素时，则进行扩容。
>
> 那为什么HashMap的加载因子默认是0.75
>
> 出于容量和性能之间的权衡
>
> - 加载因子过小导致容量频繁扩容，存储稀疏，空间利用率较低。
> - 加载因子过大导致容量过容门槛变高，存储紧密，Hash冲突几率提升。

### 1.2初始长度为何是2次幂？

> 1、这个跟计算具体桶位置有关系，使数据可以均匀的分布在桶中。
>
> jdk1.8hash值计算

```
static final int hash(Object key) {
 int h;
 return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
}
```

> 首先将hashCode无符号右移16位,再与原来的hashCode值做异或运算，这样处理的目的是为了有效地避免哈希碰撞。

```
key.hashCode()： 11111111 11111111 11110000 11101010
         h>>>16：00000000 00000000 11111111 11111111
hash=h^(h>>>16)：11111111 11111111 00001111 00010101
```



```
//通过余运算，计算落在具体桶位置
//位运算采用二进制计算的效率比取模高。
hash&(length-1)
    
hash=h^(h>>>16)：11111111 11111111 00001111 00010101
          (n-1)：00000000 00000000 00000000 00001111
            结果：00000000 00000000 00000000 00000101
```

> 

### 2.1HashMap 源码中有哪些重要的方法？

#### 2.1.1 get方法

```
/**
* 查询方法 
*/
public V get(Object key) {
    Node<K,V> e;
    // 对 key 进行哈希操作
    return (e = getNode(hash(key), key)) == null ? null : e.value;
}
final Node<K,V> getNode(int hash, Object key) {
    Node<K,V>[] tab; Node<K,V> first, e; int n; K k;
    // 非空判断
    if ((tab = table) != null && (n = tab.length) > 0 &&
        (first = tab[(n - 1) & hash]) != null) {
        // 判断第一个元素是否是要查询的元素
        if (first.hash == hash && // always check first node
            ((k = first.key) == key || (key != null && key.equals(k))))
            return first;
        // 下一个节点非空判断
        if ((e = first.next) != null) {
            // 如果第一节点是树结构，则使用 getTreeNode 直接获取相应的数据
            if (first instanceof TreeNode)
                return ((TreeNode<K,V>)first).getTreeNode(hash, key);
            do { // 非树结构，循环节点判断
                // hash 相等并且 key 相同，则返回此节点
                if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                    return e;
            } while ((e = e.next) != null);
        }
    }
    return null;
}
```

> **当有哈希冲突时，HashMap 是如何查找并确认元素的？**
>
> hash冲突时需要比较key值是否相等，如果相等则返回节点。
>
> ![image.png](https://cdn.nlark.com/yuque/0/2020/png/1728458/1594870823131-6036c412-1428-42c4-91cc-9c430d0b6ff4.png)

#### 2.1.2 put方法

```
public V put(K key, V value) {
    // 对 key 进行哈希操作
    return putVal(hash(key), key, value, false, true);
}
final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
               boolean evict) {
    Node<K,V>[] tab; Node<K,V> p; int n, i;
    // 哈希表为空则创建表
    if ((tab = table) == null || (n = tab.length) == 0)
        n = (tab = resize()).length;
    // 根据 key 的哈希值计算出要插入的数组索引 i
    if ((p = tab[i = (n - 1) & hash]) == null)
        // 如果 table[i] 等于 null，则直接插入
        tab[i] = newNode(hash, key, value, null);
    else {
        Node<K,V> e; K k;
        // 如果 key 已经存在了，直接覆盖 value
        if (p.hash == hash &&
            ((k = p.key) == key || (key != null && key.equals(k))))
            e = p;
        // 如果 key 不存在，判断是否为红黑树
        else if (p instanceof TreeNode)
            // 红黑树直接插入键值对
            e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
        else {
            // 为链表结构，循环准备插入
            for (int binCount = 0; ; ++binCount) {
                // 下一个元素为空时
                if ((e = p.next) == null) {
                    p.next = newNode(hash, key, value, null);
                    // 转换为红黑树进行处理
                    if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                        treeifyBin(tab, hash);
                    break;
                }
                //  key 已经存在直接覆盖 value
                if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                    break;
                p = e;
            }
        }
        if (e != null) { // existing mapping for key
            V oldValue = e.value;
            if (!onlyIfAbsent || oldValue == null)
                e.value = value;
            afterNodeAccess(e);
            return oldValue;
        }
    }
    ++modCount;
    // 超过最大容量，扩容
    if (++size > threshold)
        resize();
    afterNodeInsertion(evict);
    return null;
}
```

> ![image.png](https://cdn.nlark.com/yuque/0/2020/png/1728458/1594870830417-5de99601-1efb-4acb-be5a-1914ee714c15.png)



## 2.树

### 2.1.二叉树简介

二叉树子节点只能有2个。如果树的子节点指回去也就是非子节点，那就形成了环，也就是所谓的图。

LinkedList（链表）是特殊化对Tree（树），Tree（树）是特殊化对Graph（图）

```
//树节点定义
public class TreeNode {
    //当前节点值
    public int val;
    //左右子树节点
    public TreeNode left,right;
    public TreeNode(int val){
        this.val = val;
        this.left = null;
        this.right = null;
    }
}
```

### 2.2二叉树树遍历

#### 2.2.1.前序（Pre-Order）

【**根-左-右**】,先访问根节点，然后先序遍历左子树，在先序遍历右子树

前序遍历访问结果：[1,2,4,5,7,8,3,6]

![image.png](https://cdn.nlark.com/yuque/0/2021/png/1728458/1612446240895-cf3045dd-e190-4eb0-b283-2180454f536e.png)

```
// 递归先序遍历
public static void recursionPreorderTraversal(TreeNode root) {
    if (root != null) {
        System.out.print(root.val + " ");
        recursionPreorderTraversal(root.left);
        recursionPreorderTraversal(root.right);
    }
}
```

前序遍历结果：1 2 4 6 7 8 3 5



#### 2.2.2.中序（In-Order）

【**左-****根-右**】中序遍历根节点的左子树，然后访问根节点，最后遍历右子树（左根右）

中序遍历结果：[4,2,7,5,8,1,3,6]

![image.png](https://cdn.nlark.com/yuque/0/2021/png/1728458/1612446175670-257ea639-b4e1-4a28-8f78-22623ea2f670.png)

```
// 递归中序遍历
public static void recursionMiddleorderTraversal(TreeNode root) {
    if (root != null) {
        recursionMiddleorderTraversal(root.left);
        System.out.print(root.val + " ");
        recursionMiddleorderTraversal(root.right);
    }
}
```



#### 2.2.3.后序（Post-Order）

【**左-右-****根**】从左到右先遍历叶子后节点的方式遍历访问左右子树，最后访问根节点

后序遍历结果： [4,7,8,5,2,6,3,1]

![image.png](https://cdn.nlark.com/yuque/0/2021/png/1728458/1612446355036-6533a5bf-5cb3-4cc9-845f-7e95765b5ee5.png)

```
// 递归后序遍历
public static void recursionPostorderTraversal(TreeNode root) {
    if (root != null) {
        recursionPostorderTraversal(root.left);
        recursionPostorderTraversal(root.right);
        System.out.print(root.val + " ");
    }
}
```



### 2.3.二叉搜索树

二叉搜索树性质

1. 左子树上**所有的结点**的值均小于它的根结点的值
2. 右子树上**所有的****结****点**的值均大于它的根结点的值



## 3.堆

### 3.1.堆的定义

Heap：可以迅速找到一堆数中堆最大或者最小值堆数据结构。将根节点最大的堆叫做**大顶堆**或大根堆，根节点最小的堆叫做**小顶堆**或者小根堆

一般实现有二叉堆、斐波拉契堆、严格斐波拉契堆、2-3堆等。

![image.png](https://cdn.nlark.com/yuque/0/2021/png/1728458/1612448735230-d4e7ee2a-bd9d-47f0-bf7e-a1ffbf60eebe.png?x-oss-process=image%2Fresize%2Cw_1500)

### 3.2.二叉堆性质

通过完全二叉树来实现（非二叉搜索树）。

二叉堆（大顶）需满足以下性质：

是一棵完全树，除了叶子结点点其他都是满的。

树中的任意节点的值  总是>=其子结点的值（左右儿子）

![image.png](https://cdn.nlark.com/yuque/0/2021/png/1728458/1612449295966-52901f09-448c-47c2-a4c5-2678ccb164b4.png?x-oss-process=image%2Fresize%2Cw_1500)

```
//堆采用一维数组进行存储，上图堆中数组如下
int[] nums={110,100,90,40,80,20,60,10,30,50,70};
//公式（2*i+1) = 索引为i的左孩子的索引
//索引为4的左孩子的索引，2*4+1=9
nums[9]=50

//公式（2*i+2) = 索引为i的右孩子的索引
//索引为4的右孩子的索引，2*4+2=10
nums[10]=70

//公式 floor((i-1)/2) = 索引为i的父结点的索引
//索引为4的右父结点的索引，floor((4-1)/2)=1
nums[1]=100
```



### 3.3堆的插入操作

时间复杂度O(log2n)

1.将值放入到堆尾

2.从下往上一次比较，只要大于父结点就与之交换。（HeapifyUp)

### 3.4堆的删除操作

时间复杂度O(log2n)

删除堆顶操作

1.将堆顶元素替换到顶部

2.依次从根部向下调整整个堆结构（HeapifyDown)



## 4.图

### 4.1.图的定义

言简意赅“有点，有边“。



形式化定义：

图（Graph）是由顶点的有穷非空集合和顶点之间的边的集合组成，通常表示为： G(V,E)。其中，G 表示一个图，V 是图 G 中顶点的集合，E 是图 G 中边的集合。



![image.png](https://cdn.nlark.com/yuque/0/2021/png/1728458/1612581732276-97eae8cc-ab94-4562-af29-8d55b66342f5.png?x-oss-process=image%2Fresize%2Cw_1500)

1. 图中数据元素叫做顶点（Vertext）。
2. 在图中，不允许没有顶点。若 V 是图的顶点的集合，那么，V 是非空有穷集合。
3. 图的任意两个顶点之间都可能有关系，它们的关系用边来表示。边集可以是空的。



### 4.2图示例

#### 4.2.1无向无权图

![image.png](https://cdn.nlark.com/yuque/0/2021/png/1728458/1612583068966-3e68f326-6f90-4cac-896a-78d84f23a984.png)

#### 邻接矩阵

二维数据结构，第一个纬度“行”[0,1,2,3,4]分别代表点的下标，第二个纬度“列”[0,1,2,3,4]也分别代表点的下标。

行和列对应点就是代表有多少个边，0表示没有相连，1表示有边相连。

![image.png](https://cdn.nlark.com/yuque/0/2021/png/1728458/1612583052389-1113d0c9-1147-41b8-b211-bb63cbf4273c.png)

#### 邻接表

二维数据结构，第一个纬度“行”[0,1,2,3,4]分别代表点的下标，第二个纬度则示通过链表的形式将该点所连接的点信息存储下来。

![image.png](https://cdn.nlark.com/yuque/0/2021/png/1728458/1612583060367-901fcb2e-6c72-4b1d-b14c-6d28b3cdffd1.png)



#### 4.2.2有向无权图

有向和无向的区别在于矩阵不再是对称的矩阵。

只能从0->1，无法从1->0,无权指的是所有的边不是0就是1。

![image.png](https://cdn.nlark.com/yuque/0/2021/png/1728458/1612583548692-a180b2eb-95f1-45ce-9510-4d368a70d885.png)

#### 邻接矩阵

二维数据结构，第一个纬度“行”[0,1,2,3,4]分别代表点的下标，第二个纬度“列”[0,1,2,3,4]也分别代表点的下标。



![image.png](https://cdn.nlark.com/yuque/0/2021/png/1728458/1612583579527-3182c1e7-dc51-4e21-9c22-496f32b088b2.png)

#### 邻接表

二维数据结构，第一个纬度“行”[0,1,2,3,4]分别代表点的下标，第二个纬度则示通过链表的形式将该点所连接的点信息存储下来。

![image.png](https://cdn.nlark.com/yuque/0/2021/png/1728458/1612583587174-befa8d7d-eedb-4c00-bcba-6191e57530fd.png)





#### 4.2.3无项有权图

有权指的是所有的边不再0和1，也可以有其他值。

![image.png](https://cdn.nlark.com/yuque/0/2021/png/1728458/1612584439998-5179942a-d2d1-4c08-8718-3e042b6eaeb7.png)

#### 邻接矩阵

二维数据结构，第一个纬度“行”[0,1,2,3,4]分别代表点的下标，第二个纬度“列”[0,1,2,3,4]也分别代表点的下标。



![image.png](https://cdn.nlark.com/yuque/0/2021/png/1728458/1612584488507-a26846dc-424f-4d5b-9bfa-8bbecf23e891.png)

#### 邻接表

二维数据结构，第一个纬度“行”[0,1,2,3,4]分别代表点的下标，第二个纬度则示通过链表的形式将该点所连接的点和边的信息存储下来。

![image.png](https://cdn.nlark.com/yuque/0/2021/png/1728458/1612584502157-c9ef5c3a-0827-47bb-a27f-a8e13f49dd85.png?x-oss-process=image%2Fresize%2Cw_1500)
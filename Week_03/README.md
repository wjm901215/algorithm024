# 递归
## 1. 递归定义

一个函数自己直接或间接调用自己，归去来兮【去的过程叫“递”，回来的过程叫“归”】。



递归需要满足3个条件

1. 一个问题的解可以分解为几个子问题的解。
2. 问题与分解之后的子问题，除了数据规模不同，求解思路完全一样
3. 存在递归终止条件

## 2.递归模版

```
/**
 * 递归模版
 */
public void recursion(int level, int param) {
    //递归终止条件 recursion terminator
    if (level > MAX_LEVEL) {
        return;
    }
    //当前层处理逻辑 process current logic
    process(level,param);
    
    //下探到下一层 drill down
    recursion(level+1,param);
    
    //恢复当前状态
}
```

## 3.思维要点

写递归代码关键在于如何将大问题分解为小问题的规律，并基于此写出**递推公式**、找到**终止条件，**最后将递归公式和终止条件翻译成代码 。

1. 禁止人肉递
2. 找最近重复性，递推公式
3. 数学归纳法
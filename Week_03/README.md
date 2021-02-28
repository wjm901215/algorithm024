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


## 分治的定义

分而治之，就是找重复性，将大的问题拆分成n给子问题，最后再将子问题结果进行组合。

![image.png](https://cdn.nlark.com/yuque/0/2021/png/1728458/1614090019615-9c8c30b5-1772-451c-b5fe-143076b559bf.png)

## 分治代码模版

```
public static int divide_conquer(Probleam probleam,int param) {
  //递归终止条件
  if(probleam == null) {
       int res = process_last_result();
       return res;
  }
  //处理当前层逻辑
  subProbleam=split_probleam(probleam,param)
  //下探到下一层，处理子问题
  subresult1 = divide_conquer(subProbleam[0],param);
  subresult2 = divide_conquer(subProbleam[0],param);
    
   //结果组合
   result = process_result(subresult1,subresult2)
   //恢复当前状态
    
}
```

##  

## 回溯到定义

回溯法采用试错的思想,它尝试分步的去解决一个问题。在分步解决问题的过程中,当它通过尝试发现现有的分步答案不能得到有效的正确的解答的时候,它将取消上一步甚至是上几步的计算,再通过其它的可能的分步解答再次尝试寻找问题的答案。

回溯法一般通常使用递归的方式进行解决。
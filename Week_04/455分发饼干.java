class Solution {
    /**
     * 1.首先对两个数组进行排序
     * 2.同时遍历胃口值g和饼干s两个数组，当胃口值g<=饼干值s，则i+1，并且将饼干值+1;
     * 3.如果胃口值>饼干值,则继续遍历饼干值s。
     *
     * @param g
     * @param s
     * @return
     */
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int i = 0,j = 0;
        while (g.length > i  && s.length > j) {
            if(g[i] <= s[j]) {
                ++i;
            }
            j++;
        }
        return  i;
    }
}

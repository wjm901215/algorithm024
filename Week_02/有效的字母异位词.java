class Solution {
    /**
     * 解法1:
     * 1.将字符串s出现对字母次数存储在hashmap中，
     * 2.遍历另外一个字符串将出现对字符串递减，直到map为空则表示异位词
     */
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        Map<Character,Integer> table = new HashMap();
        for (Character ch :s.toCharArray()) {
            table.put(ch, table.getOrDefault(ch, 0) + 1);
        }
        for (Character ch :t.toCharArray()) {
            table.put(ch, table.getOrDefault(ch, 0) - 1);
            if (table.get(ch) < 0) {
                return false;
            }
        }
        return true;

    }

    /**
     * 解法2
     * TODO Arrays的api需要多看
     * 排序后比较
     */
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        char str1[] = s.toCharArray();
        char str2[] = t.toCharArray();
        Arrays.sort(str1);
        Arrays.sort(str2);
        return Arrays.equals(str1,str2);
    }
}
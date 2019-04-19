package com.skills.algorithm;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 缓存LFU的实现：1 extends LinkedHashMap, 2 constructor->super(xx,xx,true),3 overRide removeEldestEntry
 * <p>
 * 数据的历史访问频率来淘汰数据，其核心思想是“如果数据过去被访问多次，那么将来被访问的频率也更高”。
 * LFU的每个数据块都有一个引用计数，所有数据块按照引用计数排序，具有相同引用计数的数据块则按照时间排序。
 * <p>
 * <ui>实现思路：利用LinkedHashMap。 用这个类有两大好处：
 * <li>
 * 第一它本身已经实现了按照访问顺序的存储，也就是说，最近读取的会放在最前面，最最不常读取的会放在最后（当然，它也可以实现按照插入顺序存储）。
 * </li>
 * <li>
 * 第二LinkedHashMap本身有一个方法用于判断是否需要移除最不常读取的数，
 * 但是，原始方法默认不需要移除（这是，LinkedHashMap相当于一个linkedList），
 * 所以，我们需要override这样一个方法，使得当缓存里存放的数据个数超过规定个数后，就把最不常用的移除掉。
 * </li>
 * </ui>
 */
public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private static final long serialVersionUID = 1L;

    private int cacheSize;  //缓存大小

    private LRUCache(int cacheSize) {
        super(10, 0.75f, true);   //第三个参数true是关键
        this.cacheSize = cacheSize;
    }

    //缓存是否已满
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        boolean r = size() > cacheSize;
        if (r)
            System.out.println("清除缓存key：" + eldest.getKey());
        return r;
    }

    // 测试
    public static void main(String[] args) {
        LRUCache<String, String> cache = new LRUCache<String, String>(5);
        cache.put("1", "1");
        cache.put("2", "2");
        cache.put("3", "3");
        cache.put("4", "4");
        cache.put("5", "5");

        System.out.println("初始化：");
        System.out.println(cache.keySet());
        System.out.println("访问3：");
        cache.get("3");
        System.out.println(cache.keySet());
        System.out.println("访问2：");
        cache.get("2");
        System.out.println(cache.keySet());
        System.out.println("增加数据6,7：");
        cache.put("6", "6");
        cache.put("7", "7");
        System.out.println(cache.keySet());
    }
}



package com.yanlei.springboot.util;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Author: x
 * @Date: Created in 15:17 2018/10/31
 */
public class UUID {
    public static String getUUID32() {
        String uuid = java.util.UUID.randomUUID().toString().replace("-", "").toLowerCase();
        return uuid;
    }

    /**
     * 使用 Map按key进行排序
     * @param map
     * @return
     */
    public static Map<String, Object> sortMapByKey(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, Object> sortMap = new TreeMap<>(
                new MapKeyComparator());

        sortMap.putAll(map);

        return sortMap;
    }

    static class MapKeyComparator implements Comparator<String> {

        @Override
        public int compare(String str1, String str2) {

            return str1.compareTo(str2);
        }
    }
}

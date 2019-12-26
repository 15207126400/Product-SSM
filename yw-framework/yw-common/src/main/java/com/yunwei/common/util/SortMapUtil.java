package com.yunwei.common.util;

import java.util.*;
import java.util.Map.Entry;

/**
 * 功能说明: Map排序工具类<br>
 * 系统版本: v1.0<br>
 * 开发人员: @author guanhui<br>
 * 开发时间: 2016年11月14日<br>
 */
public class SortMapUtil {

    /**
     * map排序，通过value排序
     * @param sortMap
     * @param comparator
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K,V> Map<K,V> sortByValue(Map<K,V> sortMap,Comparator<Entry<K,V>> comparator){
        Map<K,V> sortedMap = new LinkedHashMap<K,V>();
        if (sortMap != null && !sortMap.isEmpty()) {
            List<Entry<K,V>> entryList = new ArrayList<Entry<K,V>>(sortMap.entrySet());
            Collections.sort(entryList,comparator);
            Iterator<Entry<K,V>> iter = entryList.iterator();
            Entry<K,V> tmpEntry = null;
            while (iter.hasNext()) {
                tmpEntry = iter.next();
                sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
            }
        }
        return sortedMap;
    }
}

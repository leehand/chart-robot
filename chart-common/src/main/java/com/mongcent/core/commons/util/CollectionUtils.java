package com.mongcent.core.commons.util;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections4.ComparatorUtils;
import org.apache.commons.collections4.comparators.ComparableComparator;
import org.apache.commons.collections4.comparators.ComparatorChain;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Collection对象的操作
 */
public class CollectionUtils {
    private static Logger logger = LoggerFactory.getLogger(CollectionUtils.class);

    /**
     * 去除重复对象
     *
     * @param list
     * @return
     */
    public static <T> List<T> removeDuplicate(List<T> list) {
        Set<T> set = new HashSet<T>();
        List<T> newList = new ArrayList<T>();
        for (Iterator<T> iter = list.iterator(); iter.hasNext(); ) {
            T element = iter.next();
            if (set.add(element))
                newList.add(element);
        }
        return newList;
    }

    /**
     * 集合是否为空
     *
     * @param collection 集合
     * @return 是否为空
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 集合是否为非空
     *
     * @param collection 集合
     * @return 是否为非空
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return false == isEmpty(collection);
    }

    /**
     * 排序，默认升序
     *
     * @param datas  需要排序的数据
     * @param fields 字段排序 ["currentDate:asc","name:desc"]
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void sort(List<?> datas, String... fields) {
        if (fields != null) {
            // 默认升序asc
            Comparator<?> ascComparator = ComparableComparator.comparableComparator();
            ascComparator = ComparatorUtils.nullLowComparator(ascComparator);
            ascComparator = ComparatorUtils.nullHighComparator(ascComparator); // 允许null
            // 逆序desc
            Comparator<?> descComparator = ComparatorUtils.reversedComparator(ascComparator);
            String[] fieldSortAry = null;
            List<BeanComparator> sortFields = new LinkedList();
            String sort = null;
            String field = "id";
            for (String fieldSort : fields) {
                if (fieldSort.indexOf(":") != -1) {
                    fieldSortAry = StringUtils.split(fieldSort, ":");
                    field = fieldSortAry[0];
                    sort = fieldSortAry[1];
                } else {
                    field = fieldSort;
                    sort = "asc";
                }
                sortFields.add(new BeanComparator(field, "desc".equals(sort) ? descComparator : ascComparator));
            }
            // 创建一个排序链
            ComparatorChain multiSort = new ComparatorChain(sortFields);
            // 开始真正的排序，按照先主，后副的规则
            Collections.sort(datas, multiSort);
        }
    }
}

package com.fth.rbac.server.core.enums.base;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 冯天鹤
 * @version 1.0
 * @date 2020/11/24
 * content:
 */
public class EnumFactory {

    @SuppressWarnings("rawtypes")
    private static ConcurrentHashMap<Class, Set> map = new ConcurrentHashMap<Class, Set>();

    public static <V, T extends BaseEnum<V>> void add(T t) {
        @SuppressWarnings("unchecked")
        Set<BaseEnum<V>> list = map.get(t.getClass());
        if (list == null) {
            list = new HashSet<BaseEnum<V>>();
            map.putIfAbsent(t.getClass(), list);
        }
        list.add(t);
    }

    @SuppressWarnings("unchecked")
    public static <V, T extends BaseEnum<V>> T getByValue(Class<T> clazz, V value) {//接口还是一样的
        Set<BaseEnum<V>> list = getEnum(clazz);
        for (BaseEnum<V> be : list) {
            if (be.getValue().equals(value)) {
                return (T) be;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T extends BaseEnum<V>, V> T getByLabel(Class<T> clazz, String label) {//接口还是一样的
        Set<BaseEnum<V>> list = getEnum(clazz);
        for (BaseEnum<V> be : list) {
            if (be.getLabel().equals(label)) {
                return (T) be;
            }
        }
        return null;
    }

    private static <T extends BaseEnum<V>, V> Set<BaseEnum<V>> getEnum(Class<T> clazz) {
        Set<BaseEnum<V>> list = map.get(clazz);
        if (list == null) {
            try {
                Field[] fields = clazz.getFields();
                for (Field f : fields) {
                    f.get(null);
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            list = map.get(clazz);
        }
        return list;
    }
}

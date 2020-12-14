package com.fth.rbac.server.core.utils;

import org.apache.commons.collections.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 冯天鹤
 * @version 1.0
 * @date 2020/12/7
 * content:
 */
public class TreeHelper {
    private String primaryKey;

    private String parentKey;

    private String childrenKey;

    private String superFlag;

    public TreeHelper(String primaryKey, String parentKey, String childrenKey, String superFlag) {
        this.primaryKey = primaryKey;
        this.parentKey = parentKey;
        this.childrenKey = childrenKey;
        this.superFlag = superFlag;
    }

    /**
     * usage:
     * TreeHelper treeHelper = new TreeHelper("id", "pid", "children", "0");
     * List<TreeItem> c = treeHelper.covert(items);
     *
     * @param lists
     * @param <T>
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public <T> List<T> covert(List<T> lists) throws NoSuchFieldException, IllegalAccessException {
        Class clazz = lists.get(0).getClass();
        // 获取到对应字段
        Field field = clazz.getDeclaredField(this.primaryKey);
        Field parentField = clazz.getDeclaredField(this.parentKey);
        Field childListField = clazz.getDeclaredField(this.childrenKey);
        // 使对应字段可操作
        field.setAccessible(true);
        parentField.setAccessible(true);
        childListField.setAccessible(true);
        // 转成map
        Map<String, T> map = new HashMap<>();
        for (T t : lists) {
            map.put((String.valueOf(field.get(t))), t);
        }

        // 利用红黑树组装树结构
        List<T> res = new ArrayList<>();
        for (T item : lists) {
            if ((String.valueOf(parentField.get(item)).equals(this.superFlag))) {
                res.add(item);
                continue;
            }
            Object parent = map.get(String.valueOf(parentField.get(item)));
            if (parent != null) {
                List children = (List) childListField.get(parent);
                if (CollectionUtils.isEmpty(children)) {
                    children = new ArrayList();
                }
                childListField.set(parent, children);
                children.add(item);
            }
        }
        return res;
    }
}

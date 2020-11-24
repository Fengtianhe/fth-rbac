package com.fth.rbac.server.core.enums.base;

/**
 * @author 冯天鹤
 * @version 1.0
 * @date 2020/11/24
 * content:
 */
public abstract class BaseEnum<T> implements EnumAble<T> {//这里我们用class而不是enum
    private T key;
    private String label;
    public BaseEnum(T key, String label){
        this.key = key;
        this.label = label;
        EnumFactory.add(this);
    }
    @Override
    public T getValue() {
        return this.key;
    }
    @Override
    public String getLabel() {
        return this.label;
    }
    @Override
    public String toString(){
        return getValue() +  "="  + getLabel();
    }
}

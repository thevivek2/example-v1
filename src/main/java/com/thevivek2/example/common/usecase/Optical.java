package com.thevivek2.example.common.usecase;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public interface Optical<T extends TheVivek2Model> {

    String useCase();

    T execute(T data);

    default Class<T> type() {
        Type superClass = getClass().getGenericSuperclass();
        Type actualTypeArgument = ((ParameterizedType) superClass).getActualTypeArguments()[0];
        try {
            return (Class<T>) Class.forName(actualTypeArgument.getTypeName());
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

}

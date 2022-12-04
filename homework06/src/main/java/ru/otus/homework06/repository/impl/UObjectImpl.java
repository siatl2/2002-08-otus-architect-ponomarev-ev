package ru.otus.homework06.repository.impl;

import ru.otus.homework06.repository.UObject;

import java.util.HashMap;
import java.util.Map;

public class UObjectImpl implements UObject {
    private static UObjectImpl instanse;
    private static final Map<String, Object> map = new HashMap<>();

    private UObjectImpl() {
    }

    public static UObjectImpl getInstanse() {
        if (instanse == null) {
            instanse = new UObjectImpl();
        }
        return instanse;
    }

    @Override
    public Object getProperty(String key) {
        return map.get(key);
    }

    @Override
    public void setProperty(String key, Object value) {
        map.put(key, value);
    }
}

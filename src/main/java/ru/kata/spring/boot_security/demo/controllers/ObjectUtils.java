package ru.kata.spring.boot_security.demo.controllers;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

public class ObjectUtils {
    public static Map<String, Object> getObjectProperties(Object obj) {
        Map<String, Object> properties = new LinkedHashMap<>();
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                properties.put(field.getName(), field.get(obj));
            } catch (IllegalAccessException e) {
                properties.put(field.getName(), "Error");
            }
        }
        return properties;
    }
}

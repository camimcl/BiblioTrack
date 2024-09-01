package com.bibliotrack.annotations.util;

import com.bibliotrack.annotations.Identity;

import java.lang.reflect.Field;

public class AnnotationUtil {
    public static String getIdentityFieldName(Class<?> clazz) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Identity.class)) {
                return field.getName();
            }
        }

        return null;
    }
}

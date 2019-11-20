package com.wkj.project.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.util.Validate;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Slf4j
public class StrUtil {


    public static String join(String join, String[] strAry) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0, len = strAry.length; i < len; i++) {
            if (i == (len - 1)) {
                sb.append(strAry[i]);
            } else {
                sb.append(strAry[i]).append(join);
            }
        }
        return sb.toString();
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().equals("");
    }

    public static void addNull(List list, int length) {
        for (int i = list.size(); i < length; i++) {
            list.add(null);
        }
    }

    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    public static String toStr(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    public static String toStr(Object obj, String defaultValue) {
        return obj == null ? StrUtil.toStr(defaultValue) : obj.toString();
    }


    public static JsonObject toJsonObject(String json) {
        return new JsonParser().parse(json).getAsJsonObject();
    }

    public static String toJsonString(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    public static String join(final Object[] target, final String separator) {

        Validate.notNull(target, "Cannot apply join on null");
        Validate.notNull(separator, "Separator cannot be null");

        final StringBuilder sb = new StringBuilder();
        if (target.length > 0) {
            sb.append(target[0]);
            for (int i = 1; i < target.length; i++) {
                sb.append(separator);
                sb.append(target[i]);
            }
        }
        return sb.toString();
    }

    public static String join(final Iterable<?> target, final String separator) {

        Validate.notNull(target, "Cannot apply join on null");
        Validate.notNull(separator, "Separator cannot be null");

        final StringBuilder sb = new StringBuilder();
        final Iterator<?> it = target.iterator();
        if (it.hasNext()) {
            sb.append(it.next());
            while (it.hasNext()) {
                sb.append(separator);
                sb.append(it.next());
            }
        }
        return sb.toString();

    }

    public static Map toMap(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Map.class);
    }
}

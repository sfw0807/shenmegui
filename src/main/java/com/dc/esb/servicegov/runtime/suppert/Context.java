package com.dc.esb.servicegov.runtime.suppert;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Vincent Fan
 * Date: 14-7-18
 * Time: 上午10:15
 */
public class Context {
    private Map<String, String> map;

    public void set(String key, String value) {
        map.put(key, value);
    }

    public String get(String key) {
        return map.get(key);
    }
}

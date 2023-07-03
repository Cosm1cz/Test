package com.cacheFramework.event;

import com.cacheFramework.Cache;
import com.cacheFramework.CacheResult;

/**
 * Created on 2017/2/22.
 *
 * @author <a href="mailto:areyouok@gmail.com">huangli</a>
 */
public class CacheRemoveEvent extends CacheEvent {

    private long millis;
    private Object key;
    private CacheResult result;

    public CacheRemoveEvent(Cache cache, long millis, Object key, CacheResult result) {
        super(cache);
        this.millis = millis;
        this.key = key;
        this.result = result;
    }

    public long getMillis() {
        return millis;
    }

    public Object getKey() {
        return key;
    }

    public CacheResult getResult() {
        return result;
    }

}

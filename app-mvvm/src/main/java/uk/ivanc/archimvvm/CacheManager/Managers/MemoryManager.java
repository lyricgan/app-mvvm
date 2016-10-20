package uk.ivanc.archimvvm.CacheManager.Managers;

import android.util.LruCache;

public enum MemoryManager {
    /**
     * 1.从Java1.5开始支持;
     * 2.无偿提供序列化机制;
     * 3.绝对防止多次实例化，即使在面对复杂的序列化或者反射攻击的时候;
     */
    instance;

    private LruCache<Object, Object> mDataCache = new LruCache<>((int) (Runtime.getRuntime().maxMemory() / 8));

    MemoryManager() {
    }

    public void remove(Object key) {
        mDataCache.remove(key);
    }

    public Object pop(Object key) {
        return mDataCache.get(key);
    }

    public Object push(Object key, Object data) {
        return mDataCache.put(key, data);
    }
}
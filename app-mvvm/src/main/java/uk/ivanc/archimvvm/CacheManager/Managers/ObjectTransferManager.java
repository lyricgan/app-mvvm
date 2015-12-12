package uk.ivanc.archimvvm.CacheManager.Managers;

import android.util.LruCache;

public enum ObjectTransferManager {
  /**
   * 1.从Java1.5开始支持;
   * 2.无偿提供序列化机制;
   * 3.绝对防止多次实例化，即使在面对复杂的序列化或者反射攻击的时候;
   */

  instance;

  private LruCache DataCacher =
      new LruCache<String, Object>((int) (Runtime.getRuntime().maxMemory() / 8));

  ObjectTransferManager() {

  }

  public void remove(Object key) { DataCacher.remove(key); }

  public Object pop(Object key) {
    return DataCacher.get(key);
  }

  public Object pop_remove(Object key) {
    Object object = DataCacher.get(key);
    DataCacher.remove(key);
    return object;
  }
  public Object push(Object key, Object data) {
    return DataCacher.put(key, data);
  }

}
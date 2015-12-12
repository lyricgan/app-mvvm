package uk.ivanc.archimvvm.CacheManager.Managers;

import java.util.HashMap;

import uk.ivanc.archimvvm.CacheManager.Core.AdvancedCache;

public enum SubscriptionCacheManager {
  /**
   * 1.从Java1.5开始支持;
   * 2.无偿提供序列化机制;
   * 3.绝对防止多次实例化，即使在面对复杂的序列化或者反射攻击的时候;
   */

  instance;

  private HashMap<String, AdvancedCache.AdvancedSubscription> subscriptionMap = new HashMap<>();

  SubscriptionCacheManager() {

  }

  public void addSubscription(AdvancedCache.AdvancedSubscription sub) {
    subscriptionMap.put(sub.stamp, sub);
  }

  public void removeSubscription(String key) {
    subscriptionMap.remove(key);
  }

  public void unSubscribeSubscription(String key) {
    if(subscriptionMap.containsKey(key)) {
      AdvancedCache.AdvancedSubscription sub = subscriptionMap.get(key);
      sub.data.unsubscribe();
      subscriptionMap.remove(key);
    }
  }

}
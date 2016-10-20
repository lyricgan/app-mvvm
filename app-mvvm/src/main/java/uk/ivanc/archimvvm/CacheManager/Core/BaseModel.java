package uk.ivanc.archimvvm.CacheManager.Core;

/**
 * Created by marcel on 09/10/15.
 */
public abstract class BaseModel<T> {

    public abstract T transformToRealm();

    public abstract BaseModel transformFromRealm(T realm);

    public abstract Class getRealmClass();
}

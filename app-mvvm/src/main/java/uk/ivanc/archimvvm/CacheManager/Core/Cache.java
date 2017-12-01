package uk.ivanc.archimvvm.CacheManager.Core;

import java.util.HashMap;

import rx.Observable;

/**
 * Simulates three different sources - one from memory, one from disk,
 * and one from network. In reality, they're all in-memory, but let's
 * play pretend.
 * <p>
 * Observable.create() is used so that we always return the latest data
 * to the subscriber; if you use just() it will only return the data from
 * a certain point in time.
 */
public abstract class Cache<T> {

    // Internal Processing
    protected abstract boolean cacheInMemory(T model);

    protected abstract boolean storeToDisk(T model);

    protected abstract boolean arriveFromMemory(T model);

    protected abstract boolean arriveFromDisk(T model);

    protected abstract boolean arriveFromNetwork(T model);

    protected abstract T obtainFromMemory(HashMap<String, Object> params);

    protected abstract T obtainFromDisk(HashMap<String, Object> params);

    protected abstract T obtainFromNetwork(HashMap<String, Object> params);

    public Observable<T> processing(boolean refresh, HashMap<String, Object> params) {
        return refresh ? network(params) : Observable.concat(
                memory(params),
                disk(params),
                network(params)
        ).first(data -> data != null);
    }

    private Observable<T> memory(HashMap<String, Object> params) {
        Observable<T> observable = Observable.create(subscriber -> {
            subscriber.onNext(obtainFromMemory(params));
            subscriber.onCompleted();
        });
        return observable.doOnNext(data -> {
            arriveFromMemory(data);
        }).compose(logSource("MEMORY"));
    }

    private Observable<T> disk(HashMap<String, Object> params) {
        Observable<T> observable = Observable.create(subscriber -> {
            subscriber.onNext(obtainFromDisk(params));
            subscriber.onCompleted();
        });

        // Cache disk responses in memory
        return observable.doOnNext(data -> {
            arriveFromDisk(data);
        }).compose(logSource("DISK"));
    }

    private Observable<T> network(HashMap<String, Object> params) {
        Observable<T> observable = Observable.create(subscriber -> {
            subscriber.onNext(obtainFromNetwork(params));
            subscriber.onCompleted();
        });

        // Save network responses to disk and cache in memory
        return observable.doOnNext(data -> {
            arriveFromNetwork(data);
        }).compose(logSource("NETWORK"));
    }

    // Simple logging to let us know what each source is returning
    private Observable.Transformer<T, T> logSource(final String source) {
        return dataObservable -> dataObservable.doOnNext(data -> {
            if (data == null) {
                System.out.println(source + " does not have any data.");
            } else {
                System.out.println(source + " has the data you are looking for!");
            }
        });
    }
}

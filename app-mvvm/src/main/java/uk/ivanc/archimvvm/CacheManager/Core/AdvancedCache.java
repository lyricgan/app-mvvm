package uk.ivanc.archimvvm.CacheManager.Core;

import java.util.HashMap;

import rx.Observable;
import rx.Observer;
import rx.functions.Action0;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;
import rx.subscriptions.CompositeSubscription;
import uk.ivanc.archimvvm.CacheManager.Managers.SubscriptionCacheManager;
import uk.ivanc.archimvvm.Utils.KeyGenerator;

/**
 * Simulates three different sources - one from memory, one from disk,
 * and one from network. In reality, they're all in-memory, but let's
 * play pretend.
 * <p>
 * Observable.create() is used so that we always return the latest data
 * to the subscriber; if you use just() it will only return the data from
 * a certain point in time.
 */
public abstract class AdvancedCache<T> {
    public static final int TYPE_MEMORY = 1;
    public static final int TYPE_DISK = 2;
    public static final int TYPE_NETWORK = 3;

    public static final int STATE_SET = 1;
    public static final int STATE_APPEND = 2;

    public class AdvancedSubscription {
        public int type;
        public String stamp;
        public CompositeSubscription data;

        public AdvancedSubscription(int type, String stamp, CompositeSubscription data) {
            this.type = type;
            this.stamp = stamp;
            this.data = data;
        }
    }

    public abstract void subscribe(AdvancedModel<T> model);

    //Internal Processing
    protected abstract boolean cacheInMemory(AdvancedModel<T> model);

    protected abstract boolean storeToDisk(AdvancedModel<T> model);

    protected abstract boolean ArriveFromMemory(AdvancedModel<T> model);

    protected abstract boolean ArriveFromDisk(AdvancedModel<T> model);

    protected abstract boolean ArriveFromNetwork(AdvancedModel<T> model);

    protected abstract void obtainFromMemory(Subject<T, T> stream, HashMap<String, Object> param);

    protected abstract void obtainFromDisk(Subject<T, T> stream, HashMap<String, Object> param);

    protected abstract void obtainFromNetwork(Subject<T, T> stream, HashMap<String, Object> param);

    private int curState;

    public Observable<T> processing(boolean refresh, HashMap<String, Object> param) {
        return refresh ? network(param) : Observable.concat(memory(param), disk(param), network(param))
                .first(data -> data != null);
    }

    private Observable<T> memory(HashMap<String, Object> param) {
        Observable<T> observable = Observable.create(subscriber -> {
            final String stamp = KeyGenerator.get();
            curState = STATE_SET;
            CompositeSubscription subscriptions = new CompositeSubscription();
            Subject<T, T> stream = new SerializedSubject<>(PublishSubject.create());

            subscriptions.add(stream.doOnSubscribe(new Action0() {
                @Override
                public void call() {
                    System.out.println("Subscribing to intsObservable");
                }
            }).doOnUnsubscribe(new Action0() {
                @Override
                public void call() {
                }
            }).doOnNext(data -> {
                if (!subscriber.isUnsubscribed()) {
                    AdvancedSubscription sub = new AdvancedSubscription(TYPE_MEMORY, stamp, subscriptions);
                    SubscriptionCacheManager.instance.addSubscription(sub);
                    subscriber.onNext(data);
                    subscriber.onCompleted();
                }
                ArriveFromMemory(new AdvancedModel(TYPE_MEMORY, curState, stamp, data));
            }).compose(logSource("MEMORY")).subscribe(new Observer<T>() {
                @Override
                public void onCompleted() {
                    System.out.println("Observable is complete");
                    SubscriptionCacheManager.instance.removeSubscription(stamp);
                }

                @Override
                public void onError(Throwable e) {
                    System.out.println("Dang! something went wrong. " + e.toString());
                    SubscriptionCacheManager.instance.removeSubscription(stamp);
                }

                @Override
                public void onNext(T model) {
                    System.out.println(String.format("onNext is called"));
                    if (model != null) {
                        subscribe(new AdvancedModel(TYPE_MEMORY, curState, stamp, model));
                    }
                    curState = STATE_APPEND;
                }
            }));

            obtainFromMemory(stream, param);
            subscriptions.unsubscribe();
        });

        return observable;
    }

    private Observable<T> disk(HashMap<String, Object> param) {
        Observable<T> observable = Observable.create(subscriber -> {
            final String stamp = KeyGenerator.get();
            curState = STATE_SET;
            CompositeSubscription subscriptions = new CompositeSubscription();
            Subject<T, T> stream = new SerializedSubject<>(PublishSubject.create());
            subscriptions.add(stream.doOnSubscribe(new Action0() {
                @Override
                public void call() {
                    System.out.println("Subscribing to intsObservable");
                }
            }).doOnUnsubscribe(new Action0() {
                @Override
                public void call() {

                }
            }).doOnNext(data -> {
                if (!subscriber.isUnsubscribed()) {
                    AdvancedSubscription sub = new AdvancedSubscription(TYPE_DISK, stamp, subscriptions);
                    SubscriptionCacheManager.instance.addSubscription(sub);
                    subscriber.onNext(data);
                    subscriber.onCompleted();
                }
                // Cache disk responses in memory
                ArriveFromDisk(new AdvancedModel(TYPE_DISK, curState, stamp, data));
            }).compose(logSource("DISK")).subscribe(new Observer<T>() {
                @Override
                public void onCompleted() {
                    System.out.println("Observable is complete");
                    SubscriptionCacheManager.instance.removeSubscription(stamp);
                }

                @Override
                public void onError(Throwable e) {
                    System.out.println("Dang! something went wrong. " + e.toString());
                    SubscriptionCacheManager.instance.removeSubscription(stamp);
                }

                @Override
                public void onNext(T model) {
                    System.out.println(String.format("onNext is called"));
                    if (model != null) {
                        subscribe(new AdvancedModel(TYPE_DISK, curState, stamp, model));
                    }

                    curState = STATE_APPEND;
                }
            }));

            obtainFromDisk(stream, param);
            subscriptions.unsubscribe();
        });

        return observable;
    }

    private Observable<T> network(HashMap<String, Object> param) {
        Observable<T> observable = Observable.create(subscriber -> {
            final String stamp = KeyGenerator.get();
            curState = STATE_SET;
            CompositeSubscription subscriptions = new CompositeSubscription();
            Subject<T, T> stream = new SerializedSubject<>(PublishSubject.create());

            subscriptions.add(stream.doOnSubscribe(new Action0() {
                @Override
                public void call() {
                    System.out.println("Subscribing to intsObservable");
                }
            }).doOnUnsubscribe(new Action0() {
                @Override
                public void call() {

                }
            }).doOnNext(data -> {
                if (!subscriber.isUnsubscribed()) {
                    AdvancedSubscription sub = new AdvancedSubscription(TYPE_NETWORK, stamp, subscriptions);
                    SubscriptionCacheManager.instance.addSubscription(sub);
                    subscriber.onNext(data);
                    subscriber.onCompleted();
                }
                // Save network responses to disk and cache in memory
                ArriveFromNetwork(new AdvancedModel(TYPE_NETWORK, curState, stamp, data));
            }).compose(logSource("NETWORK")).subscribe(new Observer<T>() {
                @Override
                public void onCompleted() {
                    System.out.println("Observable is complete");
                    SubscriptionCacheManager.instance.removeSubscription(stamp);
                }

                @Override
                public void onError(Throwable e) {
                    System.out.println("Dang! something went wrong. " + e.toString());
                    SubscriptionCacheManager.instance.removeSubscription(stamp);
                }

                @Override
                public void onNext(T model) {
                    System.out.println(String.format("onNext is called"));
                    if (model != null) {
                        subscribe(new AdvancedModel(TYPE_NETWORK, curState, stamp, model));
                    }
                    curState = STATE_APPEND;
                }
            }));

            obtainFromNetwork(stream, param);
            subscriptions.unsubscribe();
        });

        return observable;
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

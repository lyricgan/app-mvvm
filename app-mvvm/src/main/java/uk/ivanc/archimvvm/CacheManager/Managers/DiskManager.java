package uk.ivanc.archimvvm.CacheManager.Managers;


import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.exceptions.RealmMigrationNeededException;
import uk.ivanc.archimvvm.ArchiApplication;
import uk.ivanc.archimvvm.CacheManager.Core.BaseModel;

public class DiskManager<K extends BaseModel, V extends RealmObject> {
    private static Realm realm = null;

    public DiskManager() {
    }

    public static Realm getInstance() {
        if (realm == null) {
            RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(ArchiApplication.getAppContext()).name("myrealm.realm").build();
            try {
                realm = Realm.getInstance(realmConfiguration);
            } catch (RealmMigrationNeededException e) {
                Realm.deleteRealm(realmConfiguration);
                realm = Realm.getInstance(realmConfiguration);
            }
        }
        return realm;
    }

    //public Observable<K> asyncSaveToDisk(K model) {
    //  // map internal UI objects to Realm objects
    //  return RealmObservable.object(context, new Func1<Realm, V>() {
    //    @Override
    //    public V call(Realm realm) {
    //      // internal object instances are not created by realm
    //      // saving them using copyToRealm returning instance associated with realm
    //      return realm.copyToRealm((V) model.transformToRealm());
    //    }
    //  }).map(new Func1<V, K>() {
    //    @Override
    //    public K call(V realmModel) {
    //      // map to UI object
    //      return (K) model.transformFromRealm(realmModel);
    //    }
    //  });
    //}

    //public Observable<List<K>> asyncLoadFromDisk(K model) {
    //  return RealmObservable.results(context, new Func1<Realm, RealmResults<V>>() {
    //    @Override
    //    public RealmResults<V> call(Realm realm) {
    //      // find all model
    //      return (RealmResults<V>) realm.where(model.getRealmClass()).findAll();
    //    }
    //  }).map(new Func1<RealmResults<V>, List<K>>() {
    //    @Override
    //    public List<K> call(RealmResults<V> realmModels) {
    //      // map them to UI objects
    //      final List<K> models = new ArrayList<K>(realmModels.size());
    //      for (V realmModel : realmModels) {
    //        models.add((K) model.transformFromRealm(realmModel));
    //      }
    //      return models;
    //    }
    //  });
    //}

    public void saveToDisk(K model) {
        // map internal UI objects to Realm objects

        //sync
        getInstance().beginTransaction();
        getInstance().copyToRealmOrUpdate((V) model.transformToRealm());
        getInstance().commitTransaction();

        //async
        //realm.executeTransaction(new Realm.Transaction() {
        //  @Override
        //  public void execute(Realm bgRealm) {
        //    bgRealm.copyToRealmOrUpdate((V) model.transformToRealm());
        //  }
        //}, null);
    }

    public void saveToDisk(List<K> model) {
        getInstance().beginTransaction();
        for (K _model : model) {
            getInstance().copyToRealmOrUpdate((V) _model.transformToRealm());
        }
        getInstance().commitTransaction();
    }

    public List<K> loadAllFromDisk(K model) {
        if (model == null) return null;

        RealmResults<V> realmModels = getInstance().where(model.getRealmClass()).findAll();

        if (realmModels == null || realmModels.size() == 0)
            return null;

        final List<K> models = new ArrayList<K>(realmModels.size());
        for (V realmModel : realmModels) {
            models.add((K) model.transformFromRealm(realmModel));
        }
        return models;
    }

    public List<K> loadFromDiskWithCondition(K model, String field, String value) {
        if (model == null) return null;

        RealmResults<V> realmModels = getInstance().where(model.getRealmClass()).contains(field, value).findAll();

        if (realmModels == null || realmModels.size() == 0)
            return null;

        final List<K> models = new ArrayList<K>(realmModels.size());
        for (V realmModel : realmModels) {
            models.add((K) model.transformFromRealm(realmModel));
        }
        return models;
    }

    public void closeRealm() {
        if (realm != null) {
            realm.close();
            realm = null;
        }
    }
}
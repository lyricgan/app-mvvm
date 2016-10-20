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
    private static Realm sRealm = null;

    public DiskManager() {
    }

    public static Realm getInstance() {
        if (sRealm == null) {
            RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(ArchiApplication.getAppContext()).name("myrealm.sRealm").build();
            try {
                sRealm = Realm.getInstance(realmConfiguration);
            } catch (RealmMigrationNeededException e) {
                Realm.deleteRealm(realmConfiguration);
                sRealm = Realm.getInstance(realmConfiguration);
            }
        }
        return sRealm;
    }

//    public Observable<K> asyncSaveToDisk(K model) {
//      // map internal UI objects to Realm objects
//      return RealmObservable.object(context, new Func1<Realm, V>() {
//        @Override
//        public V call(Realm sRealm) {
//          // internal object instances are not created by sRealm
//          // saving them using copyToRealm returning instance associated with sRealm
//          return sRealm.copyToRealm((V) model.transformToRealm());
//        }
//      }).map(new Func1<V, K>() {
//        @Override
//        public K call(V realmModel) {
//          // map to UI object
//          return (K) model.transformFromRealm(realmModel);
//        }
//      });
//    }

//    public Observable<List<K>> asyncLoadFromDisk(K model) {
//      return RealmObservable.results(context, new Func1<Realm, RealmResults<V>>() {
//        @Override
//        public RealmResults<V> call(Realm sRealm) {
//          // find all model
//          return (RealmResults<V>) sRealm.where(model.getRealmClass()).findAll();
//        }
//      }).map(new Func1<RealmResults<V>, List<K>>() {
//        @Override
//        public List<K> call(RealmResults<V> realmModels) {
//          // map them to UI objects
//          final List<K> models = new ArrayList<K>(realmModels.size());
//          for (V realmModel : realmModels) {
//            models.add((K) model.transformFromRealm(realmModel));
//          }
//          return models;
//        }
//      });
//    }

    // map internal UI objects to Realm objects, sync
    public void saveToDisk(K model) {
        getInstance().beginTransaction();
        getInstance().copyToRealmOrUpdate((V) model.transformToRealm());
        getInstance().commitTransaction();
    }

    // async
    public void saveToDiskAsync(K model) {
        getInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate((V) model.transformToRealm());
            }
        }, null);
    }

    public void saveToDisk(List<K> model) {
        getInstance().beginTransaction();
        for (K _model : model) {
            getInstance().copyToRealmOrUpdate((V) _model.transformToRealm());
        }
        getInstance().commitTransaction();
    }

    public List<K> loadAllFromDisk(K model) {
        if (model == null) {
            return null;
        }
        RealmResults<V> realmModels = getInstance().where(model.getRealmClass()).findAll();
        if (realmModels == null || realmModels.size() == 0) {
            return null;
        }
        final List<K> models = new ArrayList<K>(realmModels.size());
        for (V realmModel : realmModels) {
            models.add((K) model.transformFromRealm(realmModel));
        }
        return models;
    }

    public List<K> loadFromDiskWithCondition(K model, String field, String value) {
        if (model == null) {
            return null;
        }
        RealmResults<V> realmModels = getInstance().where(model.getRealmClass()).contains(field, value).findAll();
        if (realmModels == null || realmModels.size() == 0) {
            return null;
        }
        final List<K> models = new ArrayList<K>(realmModels.size());
        for (V realmModel : realmModels) {
            models.add((K) model.transformFromRealm(realmModel));
        }
        return models;
    }

    public void closeRealm() {
        if (sRealm != null) {
            sRealm.close();
            sRealm = null;
        }
    }
}
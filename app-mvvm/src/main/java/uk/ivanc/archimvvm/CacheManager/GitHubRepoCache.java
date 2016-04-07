package uk.ivanc.archimvvm.CacheManager;


import java.util.HashMap;
import java.util.List;

import uk.ivanc.archimvvm.CacheManager.Core.Cache;
import uk.ivanc.archimvvm.CacheManager.Managers.MemoryManager;
import uk.ivanc.archimvvm.CacheManager.Managers.NetworkManager;
import uk.ivanc.archimvvm.MVVM.Model.Repository;

/**
 * Simulates three different sources - one from memory, one from disk,
 * and one from network. In reality, they're all in-memory, but let's
 * play pretend.
 * <p>
 * Observable.create() is used so that we always return the latest data
 * to the subscriber; if you use just() it will only return the data from
 * a certain point in time.
 */
public class GitHubRepoCache extends Cache<List<Repository>> {
    private String key = null;

    public GitHubRepoCache() {
    }

    public GitHubRepoCache(String key) {
        setKey(key);
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public boolean cacheInMemory(List<Repository> model) {
        if (key == null || model == null)
            return false;

        MemoryManager.instance.push(key, model);
        return true;
    }

    @Override
    public boolean storeToDisk(List<Repository> model) {
//    if(model == null || model.size() == 0)
//      return false;
//
//    DiskManager<Repository, RealmRepository> manager = new DiskManager<Repository, RealmRepository>();
//    manager.saveToDisk(model);
//    manager.closeRealm();
//    return true;
        return false;
    }

    @Override
    protected boolean arriveFromMemory(List<Repository> model) {
        return false;
    }

    @Override
    protected boolean arriveFromDisk(List<Repository> model) {
        //cacheInMemory(model);
        //return true;
        return false;
    }

    @Override
    protected boolean arriveFromNetwork(List<Repository> model) {
        cacheInMemory(model);
        //storeToDisk(model);
        return true;
    }

    @Override
    public List<Repository> obtainFromMemory(HashMap<String, Object> param) {
        System.out.println("obtainFromMemory is on processing.");
        return (List<Repository>) MemoryManager.instance.pop(key);
    }

    @Override
    public List<Repository> obtainFromDisk(HashMap<String, Object> param) {
        System.out.println("obtainFromDisk is on processing.");
//    DiskManager<Repository, RealmRepository> manager = new DiskManager<Repository, RealmRepository>();
//    List<Repository> repo = manager.loadAllFromDisk(new Repository());
//    manager.closeRealm();
//    return repo;
        return null;
    }

    @Override
    public List<Repository> obtainFromNetwork(HashMap<String, Object> param) {
        System.out.println("obtainFromNetwork is on processing.");
        return NetworkManager.getApi().publicRepositories((String) param.get("username"));
    }
}

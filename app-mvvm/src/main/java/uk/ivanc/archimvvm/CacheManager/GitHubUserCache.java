package uk.ivanc.archimvvm.CacheManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uk.ivanc.archimvvm.CacheManager.Core.Cache;
import uk.ivanc.archimvvm.CacheManager.Managers.DiskManager;
import uk.ivanc.archimvvm.CacheManager.Managers.MemoryManager;
import uk.ivanc.archimvvm.CacheManager.Managers.NetworkManager;
import uk.ivanc.archimvvm.MVVM.Model.RealmRepository;
import uk.ivanc.archimvvm.MVVM.Model.User;

/**
 * Simulates three different sources - one from memory, one from disk,
 * and one from network. In reality, they're all in-memory, but let's
 * play pretend.
 * <p>
 * Observable.create() is used so that we always return the latest data
 * to the subscriber; if you use just() it will only return the data from
 * a certain point in time.
 */
public class GitHubUserCache extends Cache<List<User>> {
    private String mKey = null;

    public GitHubUserCache(String key) {
        this.mKey = key;
    }

    @Override
    public boolean cacheInMemory(List<User> model) {
        if (mKey == null || model == null) {
            return false;
        }
        MemoryManager.instance.push(mKey, model);
        return true;
    }

    @Override
    public boolean storeToDisk(List<User> model) {
        if (model == null || model.size() == 0) {
            return false;
        }
        DiskManager<User, RealmRepository> manager = new DiskManager<User, RealmRepository>();
        manager.saveToDisk(model);
        manager.closeRealm();
        return true;
    }

    @Override
    protected boolean arriveFromMemory(List<User> model) {
        return false;
    }

    @Override
    protected boolean arriveFromDisk(List<User> model) {
        cacheInMemory(model);
        return true;
    }

    @Override
    protected boolean arriveFromNetwork(List<User> model) {
        cacheInMemory(model);
        storeToDisk(model);
        return true;
    }

    @Override
    public List<User> obtainFromMemory(HashMap<String, Object> param) {
        System.out.println("obtainFromMemory is on processing.");
        return (List<User>) MemoryManager.instance.pop(mKey);
    }

    @Override
    public List<User> obtainFromDisk(HashMap<String, Object> param) {
        System.out.println("obtainFromDisk is on processing.");
        DiskManager<User, RealmRepository> manager = new DiskManager<User, RealmRepository>();
        List<User> repo = manager.loadFromDiskWithCondition(new User(), "login", (String) param.get("id"));
        manager.closeRealm();
        return repo;
    }

    @Override
    public List<User> obtainFromNetwork(HashMap<String, Object> param) {
        System.out.println("obtainFromNetwork is on processing.");
        User user = NetworkManager.getApi().userFromUrl((String) param.get("id"));
        List<User> userList = new ArrayList<>();
        userList.add(user);
        return userList;
    }
}

package uk.ivanc.archimvvm.CacheManager;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rx.subjects.Subject;
import uk.ivanc.archimvvm.CacheManager.Core.AdvancedCache;
import uk.ivanc.archimvvm.CacheManager.Core.AdvancedModel;
import uk.ivanc.archimvvm.CacheManager.Managers.DiskManager;
import uk.ivanc.archimvvm.CacheManager.Managers.MemoryManager;
import uk.ivanc.archimvvm.CacheManager.Managers.NetworkManager;
import uk.ivanc.archimvvm.MVVM.Model.RealmRepository;
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
public class AdvancedGitHubRepoCache extends AdvancedCache<List<Repository>> {
    private String key = null;
    private RepositoryCacheInterface repoCacheInterface;

    public interface RepositoryCacheInterface {
        void subscribe(AdvancedModel<List<Repository>> model);
    }

    public AdvancedGitHubRepoCache(RepositoryCacheInterface repoCacheInterface) {
        this.repoCacheInterface = repoCacheInterface;
    }

    public AdvancedGitHubRepoCache(String key, RepositoryCacheInterface repoCacheInterface) {
        setKey(key);
        this.repoCacheInterface = repoCacheInterface;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public void subscribe(AdvancedModel model) {
        if (repoCacheInterface != null) {
            repoCacheInterface.subscribe(model);
        }
    }

    @Override
    public boolean cacheInMemory(AdvancedModel<List<Repository>> model) {
        if (key == null || model == null || model.data == null) {
            return false;
        }
        List<Repository> repo = new ArrayList<>();
        switch (model.state) {
            case STATE_SET:
                repo.addAll(model.data);
                MemoryManager.instance.push(key, repo);
                break;
            case STATE_APPEND:
                List<Repository> repo_pop = (List<Repository>) MemoryManager.instance.pop(key);
                if (repo_pop == null) {
                    repo.addAll(model.data);
                    MemoryManager.instance.push(key, repo);
                } else {
                    repo_pop.addAll(model.data);
                    MemoryManager.instance.push(key, repo_pop);
                }
                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    public boolean storeToDisk(AdvancedModel<List<Repository>> model) {
        if (model == null || model.data == null || model.data.size() == 0) {
            return false;
        }
        switch (model.state) {
            case STATE_SET:
            case STATE_APPEND:
                DiskManager<Repository, RealmRepository> manager = new DiskManager<Repository, RealmRepository>();
                manager.saveToDisk(model.data);
                manager.closeRealm();
                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    protected boolean arriveFromMemory(AdvancedModel<List<Repository>> model) {
        return false;
    }

    @Override
    protected boolean arriveFromDisk(AdvancedModel<List<Repository>> model) {
        System.out.println("arriveFromDisk is on processing.");
        cacheInMemory(model);
        return true;
    }

    @Override
    protected boolean arriveFromNetwork(AdvancedModel<List<Repository>> model) {
        System.out.println("arriveFromNetwork is on processing.");
        cacheInMemory(model);
        storeToDisk(model);
        return true;
    }

    @Override
    protected void obtainFromMemory(Subject<List<Repository>, List<Repository>> stream, HashMap<String, Object> param) {
        System.out.println("obtainFromMemory is on processing.");
        stream.onNext((List<Repository>) MemoryManager.instance.pop(key));
    }

    @Override
    protected void obtainFromDisk(Subject<List<Repository>, List<Repository>> stream, HashMap<String, Object> param) {
        System.out.println("obtainFromDisk is on processing.");
        DiskManager<Repository, RealmRepository> manager = new DiskManager<Repository, RealmRepository>();
        final int CNT = 5;
        int count = CNT;
        List<Repository> repo = manager.loadAllFromDisk(new Repository());
        if (repo == null || repo.size() == 0) {
            manager.closeRealm();
            stream.onNext(null);
            return;
        }
        while (true) {
            if (count <= 0) {
                manager.closeRealm();
                break;
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            repo = manager.loadAllFromDisk(new Repository());
            repo.get(0).description = String.valueOf(count);
            stream.onNext(repo);
            count--;
        }
    }

    @Override
    protected void obtainFromNetwork(Subject<List<Repository>, List<Repository>> stream, HashMap<String, Object> param) {
        System.out.println("obtainFromNetwork is on processing.");
        stream.onNext(NetworkManager.getApi().publicRepositories((String) param.get("username")));
    }
}

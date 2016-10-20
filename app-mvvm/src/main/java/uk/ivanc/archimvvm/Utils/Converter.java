package uk.ivanc.archimvvm.Utils;

import java.util.LinkedHashMap;

import uk.ivanc.archimvvm.CacheManager.GitHubRepoCache;
import uk.ivanc.archimvvm.CacheManager.GitHubUserCache;

/**
 * @author lyricgan
 * @description use key-value like String-Class for cache convert
 * @time 2016/10/20 15:59
 */
public enum Converter {
    instance;

    private static LinkedHashMap<String, Class<?>> mMap;

    static  {
        mMap = new LinkedHashMap<>();
        mMap.put("https://api.github.com/user", GitHubUserCache.class);
        mMap.put("https://api.github.com/repository", GitHubRepoCache.class);
    }

    public LinkedHashMap<String, Class<?>> getMap() {
        return mMap;
    }

    public <T> T convert(String key) {
        Class<?> cls = getMap().get(key);
        try {
            return (T) cls.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void test() {
        GitHubUserCache userCache = Converter.instance.convert("https://api.github.com/user");
        GitHubRepoCache repoCache = Converter.instance.convert("https://api.github.com/repository");
        if (userCache != null) {
            System.out.println(userCache.toString());
        }
        if (repoCache != null) {
            System.out.println(repoCache.toString());
        }
    }
}

package uk.ivanc.archimvvm.CacheManager.Managers;

import retrofit.RestAdapter;

/**
 * Created by marcel on 09/10/15.
 */
public class NetworkManager {
    private static ServiceApi sInstance;

    private static void build() {
        final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ServiceApi.SERVICE_ENDPOINT).build();
        sInstance = restAdapter.create(ServiceApi.class);
    }

    public static synchronized ServiceApi getApi() {
        if (sInstance == null) {
            build();
        }
        return sInstance;
    }
}

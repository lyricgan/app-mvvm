package uk.ivanc.archimvvm.CacheManager.Managers;


import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;
import uk.ivanc.archimvvm.MVVM.Model.Repository;
import uk.ivanc.archimvvm.MVVM.Model.User;

/**
 * Created by marcel on 09/10/15.
 */
public interface ServiceApi {
    String SERVICE_ENDPOINT = "https://api.github.com";

    @GET("/users/{username}/repos")
    List<Repository> publicRepositories(@Path("username") String username);

    @GET("/users/{id}")
    User userFromUrl(@Path("id") String userId);
}
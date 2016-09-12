package uk.ivanc.archimvp.presenter;

import android.util.Log;

import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import uk.ivanc.archimvp.ArchiApplication;
import uk.ivanc.archimvp.R;
import uk.ivanc.archimvp.model.GithubService;
import uk.ivanc.archimvp.model.Repository;
import uk.ivanc.archimvp.view.MainMvpView;

public class MainPresenter implements Presenter<MainMvpView> {
    public static final String TAG = "MainPresenter";
    private MainMvpView mMainMvpView;
    private Subscription subscription;
    private List<Repository> repositories;

    @Override
    public void attachView(MainMvpView view) {
        this.mMainMvpView = view;
    }

    @Override
    public void detachView() {
        this.mMainMvpView = null;
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    public void loadRepositories(String usernameEntered) {
        String username = usernameEntered.trim();
        if (username.isEmpty()) {
            return;
        }
        mMainMvpView.showProgressIndicator();
        if (subscription != null) subscription.unsubscribe();
        ArchiApplication application = ArchiApplication.get(mMainMvpView.getContext());
        GithubService githubService = application.getGithubService();
        subscription = githubService.publicRepositories(username)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.defaultSubscribeScheduler())
                .subscribe(new Subscriber<List<Repository>>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "Repository loaded " + repositories);
                        if (!repositories.isEmpty()) {
                            mMainMvpView.showRepositories(repositories);
                        } else {
                            mMainMvpView.showMessage(R.string.text_empty_repos);
                        }
                    }

                    @Override
                    public void onError(Throwable error) {
                        Log.e(TAG, "Error loading GitHub repos ", error);
                        if (isHttp404(error)) {
                            mMainMvpView.showMessage(R.string.error_username_not_found);
                        } else {
                            mMainMvpView.showMessage(R.string.error_loading_repos);
                        }
                    }

                    @Override
                    public void onNext(List<Repository> repositories) {
                        MainPresenter.this.repositories = repositories;
                    }
                });
    }

    private static boolean isHttp404(Throwable error) {
        return error instanceof HttpException && ((HttpException) error).code() == 404;
    }
}

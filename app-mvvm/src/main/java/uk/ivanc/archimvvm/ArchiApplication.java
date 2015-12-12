package uk.ivanc.archimvvm;

import android.app.Application;
import android.content.Context;

import rx.Scheduler;
import rx.schedulers.Schedulers;

public class ArchiApplication extends Application {

    //private Scheduler defaultSubscribeScheduler;
    private static Context context;

    public static Context getAppContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

//    public Scheduler defaultSubscribeScheduler() {
//        if (defaultSubscribeScheduler == null) {
//            defaultSubscribeScheduler = Schedulers.io();
//        }
//        return defaultSubscribeScheduler;
//    }

    //User to change scheduler from tests
//    public void setDefaultSubscribeScheduler(Scheduler scheduler) {
//        this.defaultSubscribeScheduler = scheduler;
//    }
}

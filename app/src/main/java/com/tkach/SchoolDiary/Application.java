package com.tkach.SchoolDiary;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        final RealmConfiguration config = new RealmConfiguration
                .Builder()
                .name("SchoolDiary.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }

}

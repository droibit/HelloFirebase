package com.droibit.hello.firebase

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        /**
         * avoid crashing FirebaseDatabase's initialization when used in conjunction with Firebase Crash Reporting
         * http://stackoverflow.com/questions/37346363/java-lang-illegalstateexception-firebaseapp-with-name-default
         */
        if (!FirebaseApp.getApps(this).isEmpty()) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        }
    }
}
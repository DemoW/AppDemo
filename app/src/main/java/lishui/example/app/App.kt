package lishui.example.app

import android.app.Application

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        ProfileProperties.init(this)
    }
}
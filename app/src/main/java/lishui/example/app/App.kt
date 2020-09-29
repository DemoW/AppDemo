package lishui.example.app

import android.app.Application

class App: Application(), AppDemoComponentFactory.ContextInitializer {

    lateinit var mContextAvailableCallback: AppDemoComponentFactory.ContextAvailableCallback

    override fun onCreate() {
        super.onCreate()
        mContextAvailableCallback.onContextAvailable(this, this)
    }

    override fun setContextAvailableCallback(
        callback: AppDemoComponentFactory.ContextAvailableCallback) {
        mContextAvailableCallback = callback
    }

}
package lishui.example.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel

/**
 * Created by lishui.lin on 20-9-24
 */
class MainViewModel(val app: Application) : AndroidViewModel(app) {

    companion object {
        const val TAG = "MainViewModel"
    }
}
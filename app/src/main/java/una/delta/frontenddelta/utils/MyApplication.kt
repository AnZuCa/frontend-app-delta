package una.delta.frontenddelta.utils

import android.app.Application
import androidx.lifecycle.ViewModelProvider

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        createSessionManager(SessionManager(applicationContext))
    }

    companion object{
        var sessionManager: SessionManager?=null
        fun createSessionManager(newInstance:SessionManager){
            sessionManager = newInstance
        }
    }
}
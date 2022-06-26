package una.delta.frontenddelta.utils

import android.content.Context
import android.content.SharedPreferences


//session manager to save and fetch data from SharedPreferences

class SessionManager (context:Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences(
        "TaskApp", Context.MODE_PRIVATE)
    companion object{
        const val USER_TOKEN = "user_token"
        const val USERNAME = "username"
    }

    //Function to save auth token
    fun saveAuthToken(token:String){
        val editor = prefs.edit()
        editor.putString(USER_TOKEN,token)
        editor.apply()
    }


    //Function to fetch auth token
    fun fetchAuthToken(): String?{
        return prefs.getString(USER_TOKEN,null)
    }


    //Function to delete auth token
    fun deleteAuthToken(){
        val editor =  prefs.edit()
        editor.remove(USER_TOKEN)
        editor.apply()
    }

    //Function to save username
    fun saveUsername(username:String){
        val editor = prefs.edit()
        editor.putString(USERNAME,username)
        editor.apply()
    }


    //Function to fetch username
    fun fetchUsername(): String?{
        return prefs.getString(USERNAME,null)
    }


    //Function to delete username
    fun deleteUsername(){
        val editor =  prefs.edit()
        editor.remove(USERNAME)
        editor.apply()
    }
}
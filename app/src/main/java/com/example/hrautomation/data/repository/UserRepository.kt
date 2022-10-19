package com.example.hrautomation.data.repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.hrautomation.data.api.IIUserApi
import com.example.hrautomation.data.api.IUserApi
import com.example.hrautomation.domain.repository.IUserRepository
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private val context: Context, private val api: IIUserApi) :
    IUserRepository {
    override suspend fun getToken(): String? {
        val preferences: SharedPreferences =
            context.getSharedPreferences("app", Context.MODE_PRIVATE)
        return preferences.getString("token", null)
    }


    override fun saveToken(token: String) {
        val preferences: SharedPreferences =
            context.getSharedPreferences("app", Context.MODE_PRIVATE)
        preferences.edit().putString("token",token).apply()
    }

    override suspend fun checkEmail(email: String): Boolean {
        return try {
            api.checkEmail(email)?.body()?: false
        } catch (e: IOException){
            Log.e("exception","Where Internet?")
            false
        } catch (e: HttpException){
            Log.e("exception", "Unexpected response")
            false
        }
    }

    override suspend fun confirmEmail(email: String, code: String): String {
        return try {
            api.confirmEmail(email,code).body()?: ""
        } catch (e: IOException){
            Log.e("exception","Where Internet?")
            ""
        } catch (e: HttpException){
            Log.e("exception", "Unexpected response")
            ""
        }
    }
}
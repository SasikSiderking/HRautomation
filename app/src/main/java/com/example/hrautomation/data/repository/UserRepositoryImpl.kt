package com.example.hrautomation.data.repository

import com.example.hrautomation.data.api.UserApi2
import com.example.hrautomation.domain.repository.UserRepository
import okio.IOException
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(private val api: UserApi2) :
    UserRepository {

    override suspend fun checkEmail(email: String): Boolean {
        return try {
            api.checkEmail(email).body() ?: false
        } catch (e: IOException) {
            Timber.tag("exception").e("Where Internet?")
            false
        } catch (e: HttpException) {
            Timber.tag("exception").e("Unexpected response")
            false
        }
    }

    override suspend fun confirmEmail(email: String, code: String): String {
        return try {
            api.confirmEmail(email, code).body()?.accessToken ?: ""
        } catch (e: IOException) {
            Timber.tag("exception").e("Where Internet?")
            ""
        } catch (e: HttpException) {
            Timber.tag("exception").e("Unexpected response")
            ""
        }
    }
}
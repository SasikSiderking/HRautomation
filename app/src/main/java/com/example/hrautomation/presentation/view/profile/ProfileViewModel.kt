package com.example.hrautomation.presentation.view.profile

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.R
import com.example.hrautomation.data.dispatcher.CoroutineDispatchers
import com.example.hrautomation.domain.repository.MediaContentRepository
import com.example.hrautomation.domain.repository.TokenRepository
import com.example.hrautomation.domain.repository.UserRepository
import com.example.hrautomation.presentation.base.viewModel.BaseViewModel
import com.example.hrautomation.presentation.model.colleagues.EmployeeItem
import com.example.hrautomation.presentation.model.colleagues.EmployeeToEmployeeItemMapper
import com.example.hrautomation.utils.resources.StringResourceProvider
import com.example.hrautomation.utils.tryLaunch
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val userRepo: UserRepository,
    private val tokenRepo: TokenRepository,
    private val dispatchers: CoroutineDispatchers,
    private val employeeToEmployeeItemMapper: EmployeeToEmployeeItemMapper,
    private val mediaContentRepository: MediaContentRepository,
    private val stringResourceProvider: StringResourceProvider
) : BaseViewModel() {
    val data: LiveData<EmployeeItem>
        get() = _data
    private val _data = MutableLiveData<EmployeeItem>()

    val message: LiveData<Int?>
        get() = _message
    private val _message = MutableLiveData<Int?>()

    fun clearMessageState() {
        _message.postValue(null)
    }

    fun reload() {
        viewModelScope.coroutineContext.cancelChildren()
        clearExceptionState()
        loadData()
    }

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.tryLaunch(
            contextPiece = dispatchers.io,
            doOnLaunch = {
                loadUserData()
            },
            doOnError = { error ->
                Timber.e(error)
                _exception.postValue(error)
            }
        )
    }

    private suspend fun loadUserData() {
        tokenRepo.getUserId()?.let { userId ->
            val user = userRepo.getUser(userId)
            _data.postValue(employeeToEmployeeItemMapper.convert(user))
        } ?: throw IllegalStateException(stringResourceProvider.getString(R.string.error_no_auth_token))
    }

    fun saveData(project: String, info: String) {
        viewModelScope.tryLaunch(
            contextPiece = dispatchers.io,
            doOnLaunch = {
                withContext(NonCancellable) {
                    userRepo.saveUser(project, info)
                    _message.postValue(R.string.profile_save_success)
                }
            },
            doOnError = { error ->
                Timber.e(error)
                _message.postValue(R.string.toast_overall_error)
            }
        )
    }

    fun uploadImage(selectedImageUri: Uri) {
        viewModelScope.tryLaunch(
            contextPiece = dispatchers.io,
            doOnLaunch = {
                withContext(NonCancellable) {

                    val bitmap = mediaContentRepository.getBitmapByUri(selectedImageUri)

                    val byteArray = convertBitmapToByteArray(bitmap)

                    data.value?.let {
                        userRepo.uploadProfileImage(byteArray, it.id)
                    }
                    loadUserData()

                }
            },
            doOnError = { error ->
                Timber.e(error)
                _message.postValue(R.string.toast_overall_error)
                _exception.postValue(error)
            }
        )
    }

    private fun convertBitmapToByteArray(bitmap: Bitmap): ByteArray {
        return ByteArrayOutputStream().use {
            bitmap.compress(Bitmap.CompressFormat.PNG, COMPRESSION_QUALITY, it)
            it.toByteArray()
        }
    }

    private companion object {
        const val COMPRESSION_QUALITY = 0
    }
}
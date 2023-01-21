package com.example.hrautomation.data.repository

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.example.hrautomation.domain.repository.MediaContentRepository
import javax.inject.Inject

class MediaContentRepositoryImpl @Inject constructor(private val contentResolver: ContentResolver) :
    MediaContentRepository {
    override fun getBitmapByUri(imageUri: Uri): Bitmap {
        return if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
        } else {
            val source: ImageDecoder.Source =
                ImageDecoder.createSource(contentResolver, imageUri)
            ImageDecoder.decodeBitmap(source)
        }
    }
}
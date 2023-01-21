package com.example.hrautomation.domain.repository

import android.graphics.Bitmap
import android.net.Uri

interface MediaContentRepository {
    fun getBitmapByUri(imageUri: Uri): Bitmap
}
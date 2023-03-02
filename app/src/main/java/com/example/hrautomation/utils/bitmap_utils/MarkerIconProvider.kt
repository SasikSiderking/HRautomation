package com.example.hrautomation.utils.bitmap_utils

import android.graphics.*
import android.graphics.drawable.Drawable
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

object MarkerIconProvider {

    private const val QUANTITY_HEIGHT_AFFIX = 0.8
    private const val TEXT_SIZE = 48f
    private const val WELL_FIT_ICON_WIDTH = 88
    private const val WELL_FIT_TEXT_WIDTH = 27

    fun provide(drawable: Drawable): BitmapDescriptor {
        val canvas = Canvas()
        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        canvas.setBitmap(bitmap)
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        drawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    fun provideWithText(drawable: Drawable, text: String, color: Color): BitmapDescriptor {
        val canvas = Canvas()
        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        canvas.setBitmap(bitmap)
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        drawable.draw(canvas)

        val paint = Paint().apply {
            textSize = TEXT_SIZE
        }

        val scaleW = (WELL_FIT_TEXT_WIDTH * bitmap.width.toFloat()) / (WELL_FIT_ICON_WIDTH * paint.measureText(text))

        paint.apply {
            textSize *= scaleW
            this.color = color.toArgb()
        }

        val bounds = Rect()
        paint.getTextBounds(text, 0, text.length, bounds)
        val x: Float = ((bitmap.width - bounds.width()) / 2).toFloat()
        val y: Float = ((bitmap.height + bounds.height()) / 2 * QUANTITY_HEIGHT_AFFIX).toFloat()

        canvas.drawText(text, x, y, paint)

        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}
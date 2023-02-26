package com.example.hrautomation.utils.BitmapUtils

import android.graphics.*
import android.graphics.drawable.Drawable
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

class MarkerIconProvider {

    companion object {
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

            val scaleW: Float = (27 * bitmap.width.toFloat()) / (88 * paint.measureText(text))

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

        private const val QUANTITY_HEIGHT_AFFIX = 0.8
        private const val TEXT_SIZE = 48f
    }
}
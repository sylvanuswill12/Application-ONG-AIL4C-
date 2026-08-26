package com.example.ui.components

import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import com.example.R

/**
 * Validates and returns a safe valid Drawable resource ID.
 */
fun safeDrawableRes(@DrawableRes resId: Int?, @DrawableRes fallback: Int = R.drawable.img_hero_community): Int {
    if (resId == null || resId == 0) return fallback
    return resId
}

/**
 * Robust painter resource resolver that never crashes even with corrupt or missing resources
 */
@Composable
fun safePainterResource(
    @DrawableRes resId: Int?,
    @DrawableRes fallback: Int = R.drawable.img_hero_community
): Painter {
    val context = LocalContext.current
    val targetId = if (resId == null || resId == 0) fallback else resId

    return remember(targetId, fallback) {
        try {
            val bitmap = BitmapFactory.decodeResource(context.resources, targetId)
            if (bitmap != null) {
                BitmapPainter(bitmap.asImageBitmap())
            } else {
                val fallbackBitmap = BitmapFactory.decodeResource(context.resources, fallback)
                if (fallbackBitmap != null) {
                    BitmapPainter(fallbackBitmap.asImageBitmap())
                } else {
                    ColorPainter(Color(0xFF2E7D32))
                }
            }
        } catch (_: Throwable) {
            ColorPainter(Color(0xFF2E7D32))
        }
    }
}



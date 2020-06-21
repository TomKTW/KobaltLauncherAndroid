package dev.kobalt.launcher.extension

import android.graphics.drawable.Drawable
import dev.kobalt.core.resources.Images

fun Images.fromPackage(packageName: String): Drawable {
    return application.native.packageManager.getApplicationIcon(packageName)
}
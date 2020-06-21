package dev.kobalt.launcher.extension

import android.content.Intent
import android.os.Build
import dev.kobalt.core.application.Application
import dev.kobalt.launcher.entity.PackageEntity

fun Application.launchFromPackage(packageName: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
        native.packageManager.getLaunchIntentForPackage(packageName).apply {
            native.startActivity(this)
        }
    }
}

fun Application.listPackageData(): List<PackageEntity> {
    return native.packageManager.queryIntentActivities(
        Intent(Intent.ACTION_MAIN).apply { addCategory(Intent.CATEGORY_LAUNCHER) },
        0
    ).map {
        PackageEntity(
            it.activityInfo.packageName,
            it.activityInfo.loadLabel(native.packageManager).toString()
        )
    }.sortedBy {
        it.label
    }
}
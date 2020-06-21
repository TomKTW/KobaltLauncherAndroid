package dev.kobalt.launcher.extension

import dev.kobalt.core.components.job.JobManager
import dev.kobalt.launcher.entity.PackageEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun JobManager.load(callback: (List<PackageEntity>) -> Unit) {
    launch {
        application.listPackageData().let {
            withContext(Dispatchers.Main) { callback.invoke(it) }
        }
    }
}
@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package dev.kobalt.launcher

import android.content.Intent
import android.content.IntentFilter
import dev.kobalt.core.utility.Broadcaster
import dev.kobalt.core.utility.WeakReferenceCache
import dev.kobalt.core.view.NavigationView
import dev.kobalt.core.view.StackView
import dev.kobalt.launcher.extension.launchFromPackage
import dev.kobalt.launcher.extension.load

class MainView : StackView(Orientation.Vertical) {

    private val contentNavigation: NavigationView = NavigationView()

    private val packageBroadcaster = Broadcaster(application, IntentFilter().apply {
        addAction(Intent.ACTION_PACKAGE_ADDED)
        addAction(Intent.ACTION_PACKAGE_REMOVED)
        addDataScheme("package")
    })

    val packageLayout = WeakReferenceCache {
        PackageView().apply {
            fun reloadPackages() = application.jobManager.load { packageRecycler.update(it) }
            val listener: (Broadcaster.Data) -> Unit = { reloadPackages() }
            packageRecycler.onItemTap = { application.launchFromPackage(it.name) }
            onAttach = { packageBroadcaster.addListener(listener) }
            onDetach = { packageBroadcaster.removeListener(listener) }
            reloadPackages()
        }
    }

    init {
        add(contentNavigation, width = matchParent, height = matchParent)
        contentNavigation.currentLayout = packageLayout.value
    }

}


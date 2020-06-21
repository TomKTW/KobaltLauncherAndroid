@file:Suppress("unused")

package dev.kobalt.launcher

import dev.kobalt.core.utility.WeakReferenceCache
import dev.kobalt.core.view.NavigationView
import dev.kobalt.core.view.StackView
import dev.kobalt.launcher.extension.launchFromPackage
import dev.kobalt.launcher.extension.load

class MainView : StackView(Orientation.Vertical) {

    private val contentNavigation: NavigationView = NavigationView()

    val packageLayout = WeakReferenceCache {
        PackageView().apply {
            packageRecycler.onItemTap = { application.launchFromPackage(it.name) }
        }
    }

    init {
        add(contentNavigation, width = matchParent, height = matchParent)
        contentNavigation.currentLayout = packageLayout.value.apply {
            application.jobManager.load { packageRecycler.update(it) }
        }
    }

}

